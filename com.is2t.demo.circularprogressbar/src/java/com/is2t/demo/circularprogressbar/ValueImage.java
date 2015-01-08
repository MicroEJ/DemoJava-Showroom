/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar;

import java.io.IOException;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.Colors;
import ej.microui.EventGenerator;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.io.Pointer;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.linear.LinearMotionManager;
import ej.motion.none.NoMotion;

public class ValueImage extends Displayable {

	private static final int ANIMATION_DURATION = 5000;
	private static final int PERIOD = 50;

	private static final int START_ANGLE = 90;
	private static final int BACKGROUND_COLOR = 0x202020;
	private static final int LINE_COLOR = 0x404040;
	private static final int CURSOR_COLOR = Colors.WHITE;
	private static final int TEXT_COLOR = Colors.WHITE;

	private static final String INSTRUCTION_IMAGE_PATH = "instruction";
	private static final String VALUE_IMAGE_PATH = "value";

	private final int displayWidth;
	private final int displayHeight;

	private final Pointer pointer;
	private Image instructionImage;
	private Image valueImage;

	private final MotionManager motionManager = new LinearMotionManager();
	private boolean pressed;
	private Motion instructionMotion;
	private Motion valueMotion;

	private final int max;
	private int instruction;
	private int value;
	private final TimerTask task;

	public ValueImage(Display display, Timer timer) {
		super(display);

		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();

		this.pointer = (Pointer) EventGenerator.get(Pointer.class, 0);

		try {
			// ImageLoader imageLoader = new ImageLoader(display);
			// instructionImage = imageLoader.load(INSTRUCTION_IMAGE_PATH);
			// valueImage = imageLoader.load(VALUE_IMAGE_PATH);
			this.instructionImage = load(INSTRUCTION_IMAGE_PATH);
			this.valueImage = load(VALUE_IMAGE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.max = 100;
		this.instruction = this.max / 2;
		this.value = this.instruction;

		this.task = new TimerTask() {
			@Override
			public void run() {
				if (!ValueImage.this.pressed) {
					boolean repaint = false;
					if (ValueImage.this.instructionMotion != null) {
						ValueImage.this.instruction = ValueImage.this.instructionMotion.getCurrentValue();
						if (ValueImage.this.instructionMotion.isFinished()) {
							ValueImage.this.instructionMotion = null;
						}
						repaint = true;
					}
					if (ValueImage.this.valueMotion != null) {
						ValueImage.this.value = ValueImage.this.valueMotion.getCurrentValue();
						if (ValueImage.this.valueMotion.isFinished()) {
							ValueImage.this.valueMotion = null;
						}
						repaint = true;
					}
					if (repaint) {
						repaint();
					}
				}
			}
		};
		timer.scheduleAtFixedRate(this.task, 0, PERIOD);
	}

	public Image load(String imageName) throws IOException {
		return Image.createImage("/images/d480x272/" + imageName + ".png", Image.PNG);
	}

	@Override
	public void paint(GraphicsContext g) {
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, this.displayWidth, this.displayHeight);

		int halfDisplayWidth = this.displayWidth / 2;
		int halfDisplayHeight = this.displayHeight / 2;
		int instructionThickness = 8;
		int size = this.instructionImage.getHeight() - instructionThickness * 2;
		int halfSize = size / 2;
		int xArc = halfDisplayWidth - halfSize;
		int yArc = halfDisplayHeight - halfSize;

		// instruction
		int instructionAngle = this.instruction * 360 / this.max;
		drawAngle(g, this.instructionImage, instructionAngle, halfDisplayWidth, halfDisplayHeight);
		g.setColor(LINE_COLOR);
		g.drawArc(xArc, yArc, size, size, START_ANGLE, 360 - instructionAngle);

		g.setBrush(Brushes.getBrush(11));
		double instructionAngleRadians = Math.toRadians(START_ANGLE + instructionAngle);
		int xAngle = halfDisplayWidth - (int) (Math.cos(instructionAngleRadians) * halfSize);
		int yAngle = halfDisplayHeight - (int) (Math.sin(instructionAngleRadians) * halfSize);
		g.setColor(CURSOR_COLOR);
		g.drawCircle(xAngle - 5, yAngle - 5, 10);
		g.setBrush(null);

		// value
		int valueThickness = 12;
		size = this.valueImage.getHeight() - valueThickness * 2;
		halfSize = size / 2;
		xArc = halfDisplayWidth - halfSize;
		yArc = halfDisplayHeight - halfSize;

		int valueAngle = this.value * 360 / this.max;
		drawAngle(g, this.valueImage, valueAngle, halfDisplayWidth, halfDisplayHeight);
		double valueAngleRadians = Math.toRadians(START_ANGLE + valueAngle);
		int encompassingSize = this.valueImage.getHeight();
		int halfEncompassingSize = encompassingSize / 2;
		xAngle = halfDisplayWidth - (int) (Math.cos(valueAngleRadians) * halfEncompassingSize);
		yAngle = halfDisplayHeight - (int) (Math.sin(valueAngleRadians) * halfEncompassingSize);
		g.drawLine(halfDisplayWidth, halfDisplayHeight, xAngle, yAngle);
		g.drawLine(halfDisplayWidth + 1, halfDisplayHeight, xAngle + 1, yAngle);
		g.drawLine(halfDisplayWidth - 1, halfDisplayHeight, xAngle - 1, yAngle);
		g.setColor(LINE_COLOR);
		g.drawArc(xArc, yArc, size, size, START_ANGLE, 360 - valueAngle);

		g.setColor(TEXT_COLOR);
		g.setFont(DisplayFont.getFont(DisplayFont.LATIN, 40, DisplayFont.STYLE_PLAIN));
		g.drawString(String.valueOf(this.value), halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);
	}

	private void drawAngle(GraphicsContext g, Image image, int angle, int halfDisplayWidth, int halfDisplayHeight) {
		int encompassingSize = image.getHeight();
		int halfEncompassingSize = encompassingSize / 2;
		if (angle > 270) {
			// draw full image
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			// hide
			g.setColor(BACKGROUND_COLOR);
			g.fillArc(halfDisplayWidth - halfEncompassingSize, halfDisplayHeight - halfEncompassingSize,
					encompassingSize, encompassingSize, START_ANGLE, 360 - angle);
		} else if (angle > 180) {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, this.displayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setClip(0, halfDisplayHeight, this.displayWidth, halfDisplayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setClip(0, 0, this.displayWidth, this.displayHeight);
			g.setColor(BACKGROUND_COLOR);
			g.fillArc(halfDisplayWidth - halfEncompassingSize, halfDisplayHeight - halfEncompassingSize,
					encompassingSize, encompassingSize, START_ANGLE + 90, 270 - angle);
		} else if (angle > 90) {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, this.displayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setClip(0, 0, this.displayWidth, this.displayHeight);
			g.setColor(BACKGROUND_COLOR);
			g.fillArc(halfDisplayWidth - halfEncompassingSize, halfDisplayHeight - halfEncompassingSize,
					encompassingSize, encompassingSize, START_ANGLE + 180, 180 - angle);
		} else {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, halfDisplayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setClip(0, 0, this.displayWidth, this.displayHeight);
			g.setColor(BACKGROUND_COLOR);
			g.fillArc(halfDisplayWidth - halfEncompassingSize, halfDisplayHeight - halfEncompassingSize,
					encompassingSize, encompassingSize, START_ANGLE + 270, 90 - angle);
		}
	}

	@Override
	public void performAction(int event) {
		int action = Pointer.getAction(event);
		final int x = this.pointer.getX();
		final int y = this.pointer.getY();
		switch (action) {
		case Pointer.RELEASED:
			this.pressed = false;
			int newInstruction = getInstruction(x, y);
			this.instructionMotion = new NoMotion(this.instruction, newInstruction);
			long duration = Math.abs(newInstruction - this.value) * ANIMATION_DURATION / this.max;
			this.valueMotion = this.motionManager.easeOut(this.value, newInstruction, duration);
			break;
		case Pointer.PRESSED:
			this.pressed = true;
			break;
		case Pointer.DRAGGED:
			this.instruction = getInstruction(x, y);
			repaint();
			break;
		}
	}

	private int getInstruction(int x, int y) {
		int angle = (int) (Math.atan2(y - this.displayHeight / 2, x - this.displayWidth / 2) * 180 / Math.PI);
		angle += START_ANGLE;
		if (angle < 0) {
			angle += 360;
		}
		return angle * this.max / 360;
	}

	public void destroy() {
		if (this.task != null) {
			this.task.cancel();
		}
	}
}
