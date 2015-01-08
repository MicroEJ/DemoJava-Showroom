/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner;

import com.is2t.mwt.util.EventHandler;
import com.is2t.mwt.util.TouchConfiguration;
import com.is2t.mwt.util.Utilities;

import ej.bon.Timer;
import ej.microui.Event;
import ej.microui.io.Pointer;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.Widget;

public abstract class Wheel<T extends Comparable<T>> extends Widget implements Spinner<T>, ValueListener<T> {

	// number of items visible when not moving
	private static final int DEFAULT_VISIBLE_ITEMS = 3;
	private static final int STATE_BY_SPIN = 5;
	private static final int SPIN_ANIMATION_PERIOD = 80;
	private static final int MOTION_TIME = STATE_BY_SPIN * SPIN_ANIMATION_PERIOD;

	protected final boolean horizontal;
	protected final int visibleItemsCount;
	private final Timer timer;
	private final MotionManager motionManager;

	protected Value<T> model;

	protected T currentValue;
	private int pressPointerCoordinate;
	private long pressTime;
	private int lastPointerCoordinate;
	private long lastPointerTime;
	private boolean pressed;
	private boolean dragged;
	protected int spinOffset;
	private MotionAnimator motionAnimator;

	private EventHandler eventHandler;

	public Wheel() {
		this(false, DEFAULT_VISIBLE_ITEMS);
	}

	public Wheel(boolean horizontal, int visibleItemsCount) {
		this.horizontal = horizontal;
		this.visibleItemsCount = visibleItemsCount;
		timer = Utilities.getTimer();
		motionManager = new EaseMotionManager();
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * @return the count
	 */
	public int getVisibleItemsCount() {
		return visibleItemsCount;
	}

	@Override
	public void setModel(Value<T> model) {
		if (this.model != null) {
			this.model.removeListener(this);
		}

		this.model = model;
		currentValue = model.getValue();
		model.addListener(this);
	}

	@Override
	public T getMinimumValue() {
		return model.getMinimumValue();
	}

	@Override
	public T getMaximumValue() {
		return model.getMaximumValue();
	}

	@Override
	public T getStepSize() {
		return model.getStepSize();
	}

	@Override
	public void nextValue() {
		model.nextValue();
	}

	@Override
	public void previousValue() {
		model.previousValue();
	}

	@Override
	public T getValue() {
		return model.getValue();
	}

	public T getCurrentValue() {
		return currentValue;
	}

	@Override
	public String getUnit() {
		return model.getUnit();
	}

	@Override
	public void valueChanged(T value) {
		currentValue = value;
		repaint();
	}

	@Override
	public boolean handleEvent(int event) {
		// do not need to synchronize as long as this method is only called in the display thread

		int type = Event.getType(event);

		notifyEventHandler(event);

		try {
			motionAnimator.stop();
		} catch (NullPointerException e) {
			// Nothing to do.
		}

		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerCoordinate = horizontal ? pointer.getX() : pointer.getY();
			switch (action) {
			case Pointer.PRESSED:
				pressed(pointerCoordinate);
				break;
			case Pointer.RELEASED:
				if (pressed) {
					released(pointerCoordinate);
				}
				break;
			case Pointer.DRAGGED:
				if (pressed) {
					dragged(pointerCoordinate);
				} else {
					pressed(pointerCoordinate);
				}
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private void notifyEventHandler(int event) {
		if (eventHandler != null) {
			eventHandler.handleEvent(event);
		}
	}

	private void pressed(int pointerCoordinate) {
		pressed = true;
		dragged = false;
		pressPointerCoordinate = pointerCoordinate;
		pressTime = System.currentTimeMillis();
		lastPointerCoordinate = pointerCoordinate;
		lastPointerTime = System.currentTimeMillis();
	}

	private void released(int pointerCoordinate) {
		pressed = false;
		int screenSize = horizontal ? getWidth() : getHeight();
		int stepSize = screenSize / visibleItemsCount;

		int stepCount;
		if (!dragged) {
			int relativeCoordinate = horizontal ? getRelativeX(pointerCoordinate) : getRelativeY(pointerCoordinate);
			stepCount = relativeCoordinate / stepSize - visibleItemsCount / 2;
		} else {
			long now = System.currentTimeMillis();
			if (now - lastPointerTime > TouchConfiguration.TOUCH_SENSIBILITY) {
				// The closer step will be the current value.
				stepCount = Math.round((float) -spinOffset / stepSize);
			} else {
				float speed = (float) (pointerCoordinate - pressPointerCoordinate) / (now - pressTime);
				// Go to a far step depending on the speed.
				stepCount = Math.round(-speed * MOTION_TIME / stepSize);
			}
		}
		spinTo(stepCount, stepSize);
	}

	private void dragged(int pointerCoordinate) {
		dragged = true;

		int distanceDragged = pointerCoordinate - lastPointerCoordinate;

		if (distanceDragged != 0) {
			lastPointerTime = System.currentTimeMillis();
			lastPointerCoordinate = pointerCoordinate;
			update(distanceDragged);
		}
	}

	protected abstract void update(int distance);

	// protected void update(int distance) {
	// spinOffset += distance;
	// WheelRendererContract renderer = (WheelRendererContract) getRenderer();
	// int height = getHeight();
	// int windowSize = renderer.getWindowSize();
	// int stepHeight = height / windowSize;
	// T oldValue = currentValue;
	// int diff = Math.round((float) -spinOffset / stepHeight);
	// T diffValue = diff * model.getStepSize();
	// float currentFloatValue = oldValue + diffValue;
	// float maximumValue = model.getMaximumValue().floatValue();
	// float minimumValue = model.getMinimumValue().floatValue();
	// float stepSize = model.getStepSize().floatValue();
	// if (currentFloatValue > maximumValue) {
	// currentFloatValue -= (maximumValue - minimumValue + stepSize);
	// } else if (currentFloatValue < minimumValue) {
	// currentFloatValue += (maximumValue - minimumValue + stepSize);
	// }
	// currentValue = new Float(currentFloatValue);
	// spinOffset += diff * stepHeight;
	// repaint();
	// }

	private void spinTo(int stepCount, int stepSize) {
		int stop = (int) (-stepCount * stepSize);
		spinTo(stop);
	}

	private void spinTo(int stop) {
		int start = spinOffset;
		Motion spinMotion = motionManager.easeOut(start, stop, MOTION_TIME);
		motionAnimator = new MotionAnimator(spinMotion, new MotionListener() {
			@Override
			public void start(int value) {
				// Nothing to do.
				oldValue = value;
			}

			private int oldValue;

			@Override
			public void step(int value) {
				int diff = value - oldValue;
				oldValue = value;
				update(diff);
			}

			@Override
			public void stop(int value) {
				spinOffset = 0;
				model.setValue(currentValue);
				repaint();
			}

		});
		motionAnimator.start(timer, SPIN_ANIMATION_PERIOD);
	}

	public String getStringValue(T value) {
		return model.getStringValue(value);
	}

	@Override
	public String[] getWindow(int windowSize) {
		return model.getWindow(currentValue, windowSize);
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	public int getSpinOffset() {
		return spinOffset;
	}

	@Override
	public void setValue(T value) {
		model.setValue(value);
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
