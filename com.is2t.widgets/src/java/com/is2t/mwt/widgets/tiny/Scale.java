/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.model.RangeHolder;
import com.is2t.mwt.widgets.IScale;
import com.is2t.mwt.widgets.contracts.Controller;

/**
 * <p>A scale is a widget that hold a value between two bounds.
 * This value can be modified by selecting a cursor and moving it.</p>
 */
public class Scale extends GenericRange implements IScale {

	private static final int PAGE = 10;

	/**
	 * Creates a new scale specifying its bounds.<br>
	 * A scale is enabled by default.<br>
	 * Equivalent to {@link #Scale(int, int, int)} passing 0 as style.
	 */
	public Scale(int minValue, int maxValue) {
		this(minValue, maxValue, 0);
	}

	/**
	 * Creates a new scale specifying its bounds.<br>
	 * A scale is enabled by default.<br>
	 *
	 * @throws IllegalArgumentException
	 *             if the style is invalid
	 */
	public Scale(int minValue, int maxValue, int style) {
		super(minValue, maxValue);
		this.style = style;
	}

	/**
	 * Creates a new scale specifying its model.<br>
	 * A scale is enabled by default.<br>
	 * Equivalent to {@link #Scale(int, int, int)} passing 0 as style.
	 */
	public Scale(RangeHolder model) {
		this(model, 0);
	}

	/**
	 * Creates a new scale specifying its model.<br>
	 * A scale is enabled by default.<br>
	 *
	 * @throws IllegalArgumentException
	 *             if the style is invalid
	 */
	public Scale(RangeHolder model, int style) {
		super(model);
		this.style = style;
	}

	public int getStyle() {
		return style;
	}

	public void increment(boolean forward) {
		updateValue(forward ? 1 : -1);
	}

	public void pageIncrement(boolean forward) {
		updateValue(forward ? PAGE : -PAGE);
	}

	public boolean handleEvent(int event) {
		Controller renderer = (Controller) getRenderer();
		return renderer.handleEvent(this, event) == Controller.HANDLED;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	private final int style;

}
