/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.IScale;
import com.is2t.mwt.widgets.tiny.GenericRange;

import ej.microui.Event;
import ej.microui.io.Pointer;

public class LinedScale extends GenericRange implements IScale {

	private static final int PAGE = 10;

	private boolean underlined;
	private boolean overlined;

	public LinedScale(int minValue, int maxValue) {
		super(minValue, maxValue);
	}

	/**
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return underlined;
	}

	/**
	 * @param overlined
	 *            the overlined to set
	 */
	public void setOverlined(boolean overlined) {
		this.overlined = overlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isOverlined() {
		return overlined;
	}

	@Override
	public void increment(boolean forward) {
		updateValue(forward ? 1 : -1);

	}

	@Override
	public void pageIncrement(boolean forward) {
		updateValue(forward ? PAGE : -PAGE);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);

		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int x = getRelativeX(pointer.getX());
			
			switch (action) {
			case Pointer.PRESSED:
				break;
			case Pointer.RELEASED:
				computeValue(x);
				break;
			case Pointer.DRAGGED:
				computeValue(x);
				break;
			}
			
			return true;
		}
		return false;
	}
	
	private void computeValue(int pointerX) {
		float valueFactor = pointerX / (float) getWidth();
		int min = getMinValue();
		int newValue = (int) (min + (getMaxValue() - min) * valueFactor);
		setValue(newValue);
	}
}
