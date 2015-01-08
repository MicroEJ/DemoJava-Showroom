/*
 * Java
 *
 * Copyright 2013-2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.mwt.widgets.scroll;

import com.is2t.mwt.util.DisplayContext;
import com.is2t.mwt.util.TouchConfiguration;
import com.is2t.mwt.util.Utilities;

import ej.bon.XMath;
import ej.microui.Event;
import ej.microui.io.Pointer;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListenerAdapter;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;

/**
 * A list composite is a {@link Composite} that lays out its children in a list.
 */
public class ScrollComposite extends Composite {

	private static final int ANIMATION_PERIOD = 30;
	private static final int ANIMATION_DELAY = 800;

	private final Widget content;
	private final Scrollbar scrollbar;
	private final boolean showScrollbar;
	private final boolean horizontal;
	private MotionManager motionManager;

	private boolean needScrollbar;

	// drag management
	private boolean pressed;
	private int pressCoordinate;
	private long pressTime;
	private long lastTime;
	private int previousCoordinate;
	private int initialStep;
	private int totalShift;
	private boolean upToBottom;
	private MotionAnimator moveAnimator;
	private SetShift setShift;
	private int value;
	private boolean shown;

	/**
	 * Creates a scroll composite with its content.<br>
	 */
	public ScrollComposite(Widget content, boolean horizontal, boolean showScrollbar) {
		this.content = content;
		this.horizontal = horizontal;
		super.add(content);
		this.scrollbar = new Scrollbar(horizontal, 0);
		if (showScrollbar) {
			super.add(this.scrollbar);
		}
		this.showScrollbar = showScrollbar;
		motionManager = new EaseMotionManager();
		setShift = new SetShift();
	}

	@Override
	public void add(Widget widget) {
		// forbidden
		throw new IllegalArgumentException();
	}

	@Override
	public void addWidget(Widget widget) {
		// forbidden
		throw new IllegalArgumentException();
	}

	@Override
	public void remove(Widget widget) {
		// forbidden
		throw new IllegalArgumentException();
	}

	@Override
	public void removeWidget(Widget widget) {
		// forbidden
		throw new IllegalArgumentException();
	}

	@Override
	public void removeAllWidgets() {
		// forbidden
		throw new IllegalArgumentException();
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		if (!isVisible()) {
			// optim: do not validate its hierarchy
			setPreferredSize(0, 0);
			return;
		}

		// get composite renderer and padding
		Renderer cRenderer = getRenderer();
		int cPadding = cRenderer != null ? cRenderer.getPadding() : 0;
		int doubleCPadding = cPadding << 1;

		// if size is not yet defined -> compute it !
		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;

		Widget content = this.content;
		Scrollbar scrollbar = this.scrollbar;

		if (horizontal) {
			content.validate(MWT.NONE, heightHint);
		} else {
			content.validate(widthHint, MWT.NONE);
		}

		int contentWidth = content.getPreferredWidth();
		int contentHeight = content.getPreferredHeight();
		content.setSize(contentWidth, contentHeight);

		Renderer scrollbarRenderer = scrollbar.getRenderer();
		int scrollbarMargin = scrollbarRenderer == null ? 0 : scrollbarRenderer.getMargin();
		scrollbar.validate(MWT.NONE, MWT.NONE);

		if (computeWidth) {
			widthHint = contentWidth + doubleCPadding;
		} else if (horizontal) {
			needScrollbar = contentWidth > widthHint;
			int scrollbarHeight = scrollbar.getPreferredHeight();
			scrollbar.setBounds(cPadding, heightHint - cPadding - scrollbarHeight - scrollbarMargin, widthHint
					- doubleCPadding, scrollbarHeight);
			scrollbar.setMaxStep(contentWidth - widthHint - 2); // -2 see SetShift
		}

		if (computeHeight) {
			heightHint = contentHeight + doubleCPadding;
		} else if (!horizontal) {
			needScrollbar = contentHeight > heightHint;
			int scrollbarWidth = scrollbar.getPreferredWidth();
			scrollbar.setBounds(widthHint - cPadding - scrollbarWidth - scrollbarMargin, cPadding, scrollbarWidth,
					heightHint - doubleCPadding);
			scrollbar.setMaxStep(contentHeight - heightHint - 2); // -2 see SetShift
		}

		setPreferredSize(widthHint, heightHint);
		// setShift(0);
		int contentCoordinate = -scrollbar.getCurrentStep();
		if (horizontal) {
			((ScrollContent) content).updateViewport(contentCoordinate, content.getY(), widthHint, heightHint);
			content.setLocation(contentCoordinate - 1, content.getY()); // -1 see SetShift
		} else {
			((ScrollContent) content).updateViewport(content.getX(), contentCoordinate, widthHint, heightHint);
			content.setLocation(content.getX(), contentCoordinate - 1); // -1 see SetShift
		}
	}

	public void showNotify() {
		shown = true;
		if (showScrollbar) {
			scrollbar.showNotify();
		}
	}

	public void hideNotify() {
		shown = false;
		if (moveAnimator != null) {
			moveAnimator.stop();
		}
		scrollbar.hideNotify();
	}

	@Override
	public boolean handleEvent(int event) {
		if (!needScrollbar) {
			return false;
		}
		int type = Event.getType(event);
		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int coordinate = horizontal ? pointer.getX() : pointer.getY();
			long currentTime = System.currentTimeMillis();
			switch (action) {
			case Pointer.PRESSED:
				pressed(coordinate, currentTime);
				break;
			case Pointer.RELEASED:
				released(coordinate, currentTime);
				break;
			case Pointer.DRAGGED:
				if (pressed) {
					if (dragged(coordinate, currentTime)) {
						return true;
					}
				} else {
					pressed(coordinate, currentTime);
				}
			}
		}
		return super.handleEvent(event);
	}

	private void pressed(int coordinate, long currentTime) {
		// stop animations
		if (moveAnimator != null) {
			moveAnimator.stop();
		}
		scrollbar.show();
		// initialize movement
		pressed = true;
		pressCoordinate = coordinate;
		pressTime = currentTime;
		previousCoordinate = coordinate;
		initialStep = scrollbar.getCurrentStep();
		totalShift = 0;
	}

	private void released(int coordinate, long currentTime) {
		pressed = false;
		// do not hide scrollbar here but at the end of the animation to avoid concurrent repaints
		// scrollbar.hide();

		int currentStep = scrollbar.getCurrentStep();
		Motion moveMotion;
		int contentCoordinate = (horizontal ? content.getX() : content.getY()) + 1; // see SetShift
		if (contentCoordinate != -currentStep) {
			// go to bounds
			moveMotion = motionManager.easeOut(-contentCoordinate, currentStep, ANIMATION_DELAY);
		} else {
			if (currentTime - lastTime < TouchConfiguration.TOUCH_SENSIBILITY) {
				// launch it!
				float speed = -(float) (coordinate - pressCoordinate) / (currentTime - pressTime);
				int stop = (int) (currentStep + speed * ANIMATION_DELAY);
				stop = XMath.limit(stop, 0, scrollbar.getMaxStep());
				moveMotion = motionManager.easeOut(currentStep, stop, ANIMATION_DELAY);
			} else {
				// avoid moving if the user pauses after dragging
				scrollbar.hide();
				return;
			}
		}
		moveAnimator = new MotionAnimator(moveMotion, new MotionListenerAdapter() {
			@Override
			public void step(int value) {
				setShift(value);
			}

			@Override
			public void stop(int value) {
				super.stop(value);
				scrollbar.hide();
			}
		});
		moveAnimator.start(Utilities.getTimer(), ANIMATION_PERIOD);
	}

	private boolean dragged(int coordinate, long currentTime) {
		int shiftY = coordinate - previousCoordinate;
		lastTime = currentTime;
		if (shiftY != 0) {
			// shift content and scrollbar
			totalShift += shiftY;
			setShift(initialStep - totalShift);
			previousCoordinate = coordinate;

			boolean currentUpToBottom = shiftY > 0;
			if (totalShift != 0 && currentUpToBottom != upToBottom) {
				// change way
				pressCoordinate = coordinate;
				pressTime = currentTime;
				upToBottom = currentUpToBottom;
			}
		}
		return Math.abs(totalShift) > TouchConfiguration.DRAG_TOLERANCE;
	}

	private void setShift(int value) {
		this.value = value;
		DisplayContext.call(setShift);
	}

	class SetShift implements Runnable {

		@Override
		public void run() {
			if (shown) {
				int currentStep = scrollbar.getCurrentStep();
				scrollbar.setValue(value);
				int contentCoordinate = (-value - currentStep) / 2;
				if (horizontal) {
					((ScrollContent) content).updateViewport(contentCoordinate, content.getY(), getWidth(), getHeight());
					content.setLocation(contentCoordinate - 1, content.getY()); // -1 to hide first line
				} else {
					((ScrollContent) content).updateViewport(content.getX(), contentCoordinate, getWidth(), getHeight());
					content.setLocation(content.getX(), contentCoordinate - 1); // -1 to hide first line
				}
				repaint();
			}
		}
	}

}
