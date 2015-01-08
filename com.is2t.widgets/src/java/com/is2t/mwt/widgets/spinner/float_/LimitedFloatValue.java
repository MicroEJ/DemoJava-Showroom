/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.float_;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.util.DecimalNumber;
import com.is2t.mwt.widgets.spinner.Value;
import com.is2t.mwt.widgets.spinner.ValueListener;

import ej.bon.XMath;

public class LimitedFloatValue implements Value<Float> {

	private final List<ValueListener<Float>> listeners = new ArrayList<ValueListener<Float>>();
	protected final float minimumValue;
	protected final float maximumValue;
	protected final float stepSize;
	protected final String unit;
	protected float currentValue;

	public LimitedFloatValue(Float minimumValue, Float maximumValue, Float stepSize, Float initialValue, String unit) {
		this(minimumValue.floatValue(), maximumValue.floatValue(), stepSize.floatValue(), initialValue.floatValue(),
				unit);
		// this(new Float(minimumValue), new Float(maximumValue), new Float(stepSize), new Float(initialValue), unit);
	}

	public LimitedFloatValue(float minimumValue, float maximumValue, float stepSize, float initialValue, String unit) {
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
	public Float getMinimumValue() {
		return new Float(minimumValue);
	}

	@Override
	public Float getMaximumValue() {
		return new Float(maximumValue);
	}

	@Override
	public Float getStepSize() {
		return new Float(stepSize);
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
		Float newValue = computeNextValue(Float.valueOf(currentValue), stepCount);
		if(newValue != null){
			setValue(newValue);
		}
	}
	
	public Float computeNextValue(Float baseValue, int stepCount){
		float newValue = baseValue.floatValue() + stepCount * stepSize;
		if(newValue <= maximumValue && newValue >= minimumValue){
			return new Float(newValue);
		}
		else {
			return null;
		}
	}

	@Override
	public void setValue(Float value) {
		float floatValue = XMath.limit(value.floatValue(), minimumValue, maximumValue);
		if (floatValue != currentValue) {
			currentValue = floatValue;
			valueChanged(new Float(floatValue));
		}
	}

	@Override
	public String getStringValue(Float value) {
		return DecimalNumber.roundWithSize(value.floatValue(), 1);
	}

	@Override
	public Float getValue() {
		return new Float(currentValue);
	}

	@Override
	public String getUnit() {
		return unit;
	}

	public void addListener(ValueListener<Float> listener) {
		listeners.add(listener);
	}

	public void removeListener(ValueListener<Float> listener) {
		listeners.remove(listener);
	}

	protected void valueChanged(Float value) {
		for (ValueListener<Float> listener : listeners) {
			listener.valueChanged(value);
		}
	}

	@Override
	public String[] getWindow(Float value, int windowSize) {
		//WARNING : copy/paste in LimitedIntegerValue
		
		String[] window = new String[windowSize];
		int halfWindow = windowSize / 2;

		for (int i = 0; i < windowSize; i++) {
			Float nextValue = computeNextValue(value, i - halfWindow);
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
