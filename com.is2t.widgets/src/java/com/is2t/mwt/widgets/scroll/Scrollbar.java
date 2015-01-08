/*
 * Java
 * Copyright 2009-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.scroll;

import com.is2t.mwt.util.SimpleMotionListener;
import com.is2t.mwt.util.Utilities;

import ej.bon.XMath;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.motion.util.MotionAnimator;
import ej.mwt.Widget;

/**
 * A scrollbar holds the current step of a scrolling element.
 */
public class Scrollbar extends Widget {

	private static final int ANIMATION_PERIOD = 80;
	private static final int ANIMATION_DELAY = 1000;

	private final MotionManager motionManager;
	private boolean horizontal;
	private int max;
	private int currentStep;
	private int visibilityLevel;
	private MotionAnimator visiblityAnimator;
	private boolean hidden;

	/**
	 * Creates a new scrollbar with the specified size.<br>
	 */
	public Scrollbar(boolean horizontal, int max) {
		this.horizontal = horizontal;
		this.max = max;
		this.motionManager = new EaseMotionManager();
		hidden = true;
		internalSetVisibilityLevel(0);
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	/**
	 * Hides the scrollbar.
	 */
	public void hide() {
		if (hidden) {
			internalSetVisibilityLevel(0);
		} else {
			Motion visibilityMotion = motionManager.easeOut(ANIMATION_PERIOD, 0, ANIMATION_DELAY);
			visiblityAnimator = new MotionAnimator(visibilityMotion, new SimpleMotionListener() {
				@Override
				public void step(int value) {
					internalSetVisibilityLevel(value);
				}
			});
			visiblityAnimator.start(Utilities.getTimer(), ANIMATION_PERIOD);
		}
	}

	public void showNotify() {
		hidden = false;
	}
	public void hideNotify() {
		hidden = true;
		stopAnimator();
	}

	private void stopAnimator() {
		MotionAnimator visiblityAnimator = this.visiblityAnimator;
		if (visiblityAnimator != null) {
			visiblityAnimator.stop();
		}
	}

	/**
	 * Shows the scrollbar.
	 */
	public void show() {
		stopAnimator();
		internalSetVisibilityLevel(100);
	}

	private void internalSetVisibilityLevel(int visibilityLevel) {
		this.visibilityLevel = XMath.limit(visibilityLevel, 0, 100);
		// setVisible(visibilityLevel != 0);
		repaint();
	}

	//
	// @Override
	// public boolean isVisible() {
	// return super.isVisible() && visibilityLevel != 0;
	// }

	// @Override
	// public void revalidate() {
	// repaint();
	// }

	/**
	 * From 0 (hidden) to 100 (fully visible).
	 *
	 * @return the visibilityLevel
	 */
	public int getVisibilityLevel() {
		return visibilityLevel;
	}

	/**
	 * @return <code>true</code> if the scrollbar is horizontal, <code>false</code> otherwise.
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * Gets the max step.
	 *
	 * @return the max step.
	 */
	public int getMaxStep() {
		return max;
	}

	/**
	 * Gets the current step.
	 *
	 * @return the current step.
	 */
	public int getCurrentStep() {
		return currentStep;
	}

	/**
	 * Updates the current value with an increment.
	 * @param increment the increment to apply
	 */
	public void updateValue(int increment) {
		setValue(currentStep + increment);
	}

	/**
	 * Sets the current value.
	 * @param value the value to set
	 */
	public void setValue(int value) {
		currentStep = XMath.limit(value, 0, max);
		repaint();
	}

	/**
	 * @param max
	 *            the max step to set.
	 */
	public void setMaxStep(int max) {
		this.max = max;
	}

}
