/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.char_;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.widgets.spinner.Value;
import com.is2t.mwt.widgets.spinner.ValueListener;

import ej.bon.XMath;

public class LimitedCharValue implements Value<Character> {

	private final List<ValueListener<Character>> listeners = new ArrayList<ValueListener<Character>>();
	protected final char minimumValue;
	protected final char maximumValue;
	protected final char stepSize;
	protected char currentValue;

	public LimitedCharValue(Character minimumValue, Character maximumValue, Character stepSize, Character initialValue) {
		this(minimumValue.charValue(), maximumValue.charValue(), stepSize.charValue(), initialValue.charValue());
	}

	public LimitedCharValue(char minimumValue, char maximumValue, char stepSize, char initialValue) {
		// Check consistency of values.
		if (minimumValue > initialValue || initialValue > maximumValue) {
			throw new IllegalArgumentException("Iconsistent bounds");
		}

		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
		this.stepSize = stepSize;
		this.currentValue = initialValue;
	}

	@Override
	public Character getMinimumValue() {
		return new Character(minimumValue);
	}

	@Override
	public Character getMaximumValue() {
		return new Character(maximumValue);
	}

	@Override
	public Character getStepSize() {
		return new Character(stepSize);
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
		setValue(computeNextValue(Character.valueOf(currentValue), stepCount));
	}
		
	@Override
	public Character computeNextValue(Character baseValue, int stepCount) {
		char newValue = (char) (baseValue.charValue() + stepCount * stepSize);
		return new Character(newValue);
	}

	@Override
	public void setValue(Character value) {
		char charValue = (char) XMath.limit(value.charValue(), minimumValue, maximumValue);

		// if (charValue != currentValue) {
		currentValue = charValue;
		valueChanged(new Character(charValue));
		// }
	}

	@Override
	public String getStringValue(Character value) {
		return value.toString();
	}

	@Override
	public String getUnit() {
		return "";
	}

	@Override
	public Character getValue() {
		return new Character(currentValue);
	}

	public void addListener(ValueListener<Character> listener) {
		listeners.add(listener);
	}

	public void removeListener(ValueListener<Character> listener) {
		listeners.remove(listener);
	}

	protected void valueChanged(Character value) {
		for (ValueListener<Character> listener : listeners) {
			listener.valueChanged(value);
		}
	}

	@Override
	public String[] getWindow(Character value, int windowSize) {
		return null;
	}

	@Override
	public String[] getWindow(int windowSize) {
		return getWindow(getValue(), windowSize);
	}
}
