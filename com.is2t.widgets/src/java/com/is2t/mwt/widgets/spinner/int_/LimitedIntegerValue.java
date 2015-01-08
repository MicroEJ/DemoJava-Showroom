/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.int_;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.widgets.spinner.Value;
import com.is2t.mwt.widgets.spinner.ValueListener;

import ej.bon.XMath;

public class LimitedIntegerValue implements Value<Integer> {

	private final List<ValueListener<Integer>> listeners = new ArrayList<ValueListener<Integer>>();
	protected final int minimumValue;
	protected final int maximumValue;
	protected final int stepSize;
	protected final String unit;
	protected int currentValue;

	public LimitedIntegerValue(Integer minimumValue, Integer maximumValue, Integer stepSize, Integer initialValue,
			String unit) {
		this(minimumValue.intValue(), maximumValue.intValue(), stepSize.intValue(), initialValue.intValue(), unit);
		// this(new Integer(minimumValue), new Integer(maximumValue), new Integer(stepSize), new Integer(initialValue),
		// unit);
	}

	public LimitedIntegerValue(int minimumValue, int maximumValue, int stepSize, int initialValue, String unit) {
		// Check consistency of values.
		if (minimumValue > initialValue || initialValue > maximumValue) {
			throw new IllegalArgumentException("Iconsistent bounds");
		}

		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
		this.stepSize = stepSize;
		this.currentValue = initialValue;
		this.unit = unit;
	}

	@Override
	public Integer getMinimumValue() {
		return new Integer(minimumValue);
	}

	@Override
	public Integer getMaximumValue() {
		return new Integer(maximumValue);
	}

	@Override
	public Integer getStepSize() {
		return new Integer(stepSize);
	}

	@Override
	public void nextValue() {
		nextValue(1);
	}

	@Override
	public void previousValue() {
		nextValue(-1);
	}

	@Override
	public void nextValue(int stepCount) {
		Integer newValue = computeNextValue(Integer.valueOf(currentValue), stepCount);
		if(newValue != null){
			setValue(newValue);
		}
	}

	@Override
	public Integer computeNextValue(Integer baseValue, int stepCount) {
		int newValue = baseValue.intValue() + stepCount * stepSize;
		if(newValue <= maximumValue && newValue >= minimumValue){
			return new Integer(newValue);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void setValue(Integer value) {
		int intValue = XMath.limit(value.intValue(), minimumValue, maximumValue);
		if (intValue != currentValue) {
			currentValue = intValue;
			valueChanged(new Integer(intValue));
		}
	}

	@Override
	public String getStringValue(Integer value) {
		return value.toString();
	}

	@Override
	public Integer getValue() {
		return new Integer(currentValue);
	}

	@Override
	public String getUnit() {
		return unit;
	}

	public void addListener(ValueListener<Integer> listener) {
		listeners.add(listener);
	}

	public void removeListener(ValueListener<Integer> listener) {
		listeners.remove(listener);
	}

	protected void valueChanged(Integer value) {
		for (ValueListener<Integer> listener : listeners) {
			listener.valueChanged(value);
		}
	}

	@Override
	public String[] getWindow(Integer value, int windowSize) {
		//WARNING : copy/paste in LimitedFloatValue
		
		String[] window = new String[windowSize];
		int halfWindow = windowSize / 2;

		for (int i = 0; i < windowSize; i++) {
			Integer nextValue = computeNextValue(value, i - halfWindow);
			String stringValue;
			if(nextValue != null){
				stringValue = getStringValue(nextValue);
			}
			else {
				stringValue = null;
			}
			window[i] = stringValue;
		}

		return window;
	}
	
	@Override
	public String[] getWindow(int windowSize) {
		return getWindow(getValue(), windowSize);
	}
}
