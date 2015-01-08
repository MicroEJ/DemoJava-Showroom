/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.IProgress;

/**
 * <p>A progress bar is a widget that hold a value between zero and a positive number of steps.
 * This value can be modified by calling progress methods.</p>
 */
public class Progress extends GenericRange implements IProgress {

	/**
	 * Creates a new progress bar with 100 steps.<br>
	 * Equivalent to {@link #ProgressBar(int)} passing <code>100</code> as argument.
	 */
	public Progress() {
		this(DEFAULT_STEPS);
	}

	/**
	 * Creates a new progress bar specifying the number of steps.<br>
	 * @param steps the number of steps
	 */
	public Progress(int steps) {
		super(0, steps);
		setEnabled(false);
	}

	/**
	 * Increase by one the current value of the progress bar.
	 * Equivalent to {@link #progress(int)} passing 1.
	 */
	public void progress() {
		updateValue(1);
	}

	/**
	 * Increase by <code>stepAmount</code> the current value of the progress bar.
	 * @param stepAmount the amount of steps
	 */
	public void progress(int stepAmount) {
		updateValue(stepAmount);
	}

	/**
	 * Sets the current value to the number of steps of the progress bar.
	 */
	public void done() {
		setValue(getMaxValue());
	}

	/**
	 * Resets the value to 0.
	 */
	public void reset() {
		setValue(getMinValue());
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected static final int DEFAULT_STEPS = 100;

	public void validate(int widthHint, int heightHint) {
		super.validate(widthHint, heightHint);
		if(widthHint != 0) {
			setPreferredSize(widthHint, getPreferredHeight());
		}
	}

}
