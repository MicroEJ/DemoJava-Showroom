/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar;

import java.io.IOException;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.HomeButton;
import com.is2t.demo.showroom.common.HomeButtonRenderer;
import com.is2t.demo.utilities.Button;
import com.is2t.demo.utilities.ListenerAdapter;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.microui.Event;
import ej.microui.IntHolder;
import ej.microui.Listener;
import ej.microui.io.CompositeView;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.io.Pointer;
import ej.microui.io.View;
import ej.microui.io.Viewable;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.linear.LinearMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.rendering.Look;

public class CircularProgressBar implements Listener {

	public static final int MAX = 100;

	private static final int ANIMATION_DURATION = 5000;
	private static final int PERIOD = 50;
	private static final int START_ANGLE = 90;

	private static final String INSTRUCTION_IMAGE_PATH = "instruction";
	private static final String VALUE_IMAGE_PATH = "value";

	public static void main(String[] args) throws IOException {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		CircularProgressBar heater = new CircularProgressBar(display, timer, null);
		heater.getDisplayable().show();
	}

	// private ValueImage valueImage;
	private final Display display;
	private final Timer timer;

	private final Viewable viewable;
	private final MotionManager motionManager = new LinearMotionManager();
	private final IntHolder instruction;
	private final IntHolder value;

	private boolean pressed;
	private Motion valueMotion;
	private final CircularView instructionView;
	private final CircularView valueView;

	private MotionAnimator animator;

	private Button homeButton;
	private LayersManager layersManager;

	public CircularProgressBar(Display display, Timer timer, final Launcher launcher)
			throws IOException {
		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(
				display);
		MonitoringLayersManager.setLocation(0, 0);
		MonitoringLayersManager.setMonitoringLook(new Look() {

			@Override
			public int getProperty(int resource) {
				switch (resource) {
				case GET_FOREGROUND_COLOR_DEFAULT:
					return 0x0;

				case GET_BACKGROUND_COLOR_DEFAULT:
					return 0xffffff;

				default:
					return 0;
				}
			}

			@Override
			public DisplayFont[] getFonts() {
				return null;
			}
		});
		layersManager = monitoringLayersManager;

		// valueImage = new ValueImage(display, timer);
		this.display = display;
		this.timer = timer;
		Image instructionImage = load(INSTRUCTION_IMAGE_PATH);
		Image valueImage = load(VALUE_IMAGE_PATH);

		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		this.viewable = new Viewable(display);
		this.instruction = new IntHolder(50);
		this.value = new IntHolder(50);
		this.instructionView = new InstructionView(instructionImage, "Setting",
				this.instruction, 0, 0, displayWidth / 2, displayHeight);
		this.valueView = new ValueView(valueImage, "Light", this.value,
				displayWidth / 2, 0, displayWidth / 2, displayHeight);
		final CompositeView compositeView = this.viewable.newCompositeView();
		compositeView.add(this.instructionView);
		compositeView.add(this.valueView);

		this.viewable.setEventListener(this);

		if (launcher != null) {
			int homeButtonX = displayWidth - HomeButton.Width;
			int homeButtonY = 0;
			this.homeButton = new Button(homeButtonX, homeButtonY,
					HomeButton.Width, HomeButton.Height);
			this.homeButton.setListener(new ListenerAdapter() {
				@Override
				public void performAction() {
					launcher.start();
				}
			});

			View homeButtonView = new View(homeButtonX, homeButtonY,
					HomeButton.Width, HomeButton.Height) {
				HomeButtonRenderer homeButtonRenderer = new HomeButtonRenderer();

				@Override
				public void paint(GraphicsContext g) {
					this.homeButtonRenderer.paint(g, getWidth(), getHeight());
					compositeView.arrange(this, CompositeView.BRING_TO_FRONT); // FIXME
																				// hack
																				// for
																				// missing
																				// button
																				// at
																				// startup
																				// change
																				// cette
																				// page
				}
			};
			compositeView.add(homeButtonView);
			compositeView.arrange(homeButtonView, CompositeView.SEND_TO_BACK); // FIXME
																				// hack
																				// for
																				// missing
																				// button
																				// at
																				// startup
			// it should be repaint after value (by chance, it is added after
			// and the loop is in the right way!)
			this.value.addListener(homeButtonView);
		} else {
			this.homeButton = new Button(0, 0, 0, 0);
		}

		layersManager.show();
	}

	@Override
	public void performAction(int event) {
		if (this.homeButton.handleEvent(event)) {
			return;
		}
		if (Event.getType(event) == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(event);

			int action = Pointer.getAction(event);
			final int x = pointer.getX();
			final int y = pointer.getY();
			switch (action) {
			case Pointer.RELEASED:
				if (this.pressed) {
					this.pressed = false;
					int newInstruction = getInstruction(x, y);
					this.instruction.set(newInstruction);
					int oldValue = stopAnimation();
					long duration = Math.abs(newInstruction - oldValue)
							* ANIMATION_DURATION / MAX;
					this.valueMotion = this.motionManager.easeOut(oldValue,
							newInstruction, duration);
					this.animator = new MotionAnimator(this.valueMotion,
							new MotionListener() {
								@Override
								public void start(int value) {
									step(value);
								}

								@Override
								public void step(int value) {
									CircularProgressBar.this.value.set(value);
								}

								@Override
								public void stop(int value) {
									step(value);
								}
							});
					this.animator.start(this.timer, PERIOD);
				}
				break;
			case Pointer.PRESSED:
				this.pressed = x < this.display.getWidth() / 2;
				break;
			case Pointer.DRAGGED:
				if (this.pressed) {
					this.instruction.set(getInstruction(x, y));
				}
				break;
			}
		}
	}

	private int stopAnimation() {
		int oldValue = this.value.get();
		if (this.animator != null) {
			this.animator.stop();
			this.value.set(oldValue);
		}
		return oldValue;
	}

	private int getInstruction(int x, int y) {
		int viewX = this.instructionView.getAbsoluteX();
		int viewY = this.instructionView.getAbsoluteY();
		int viewWidth = this.instructionView.getWidth();
		int viewHeight = this.instructionView.getHeight();
		int angle = (int) (Math.atan2(y - (viewY + viewHeight / 2), x
				- (viewX + viewWidth / 2)) * 180 / Math.PI);
		angle += START_ANGLE;
		if (angle < 0) {
			angle += 360;
		}
		return angle * MAX / 360;
	}

	private Image load(String imageName) throws IOException {
		return Image.createImage("/images/d480x272/" + imageName + ".png",
				Image.PNG);
	}

	public Displayable getDisplayable() {
		// return valueImage;
		return this.viewable;
	}

	public void destroy() {
		// valueImage.destroy();
		stopAnimation();
		layersManager.hide();
	}

	@Override
	public void performAction() {
	}

	@Override
	public void performAction(int value, Object object) {
	}
}
