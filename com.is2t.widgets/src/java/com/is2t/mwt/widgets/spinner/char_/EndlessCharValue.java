/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.char_;

public class EndlessCharValue extends LimitedCharValue {

	public EndlessCharValue(char minimumValue, char maximumValue, char stepSize, char initialValue) {
		super(minimumValue, maximumValue, stepSize, initialValue);
	}

	@Override
	public void nextValue(int stepCount) {
		char newValue = computeNextValue(currentValue, stepCount);

		setValue(new Character(newValue));
	}

	// Base must be in [minimumValue, maximumValue].
	private char computeNextValue(char base, int stepCount) {
		int range = maximumValue - minimumValue;
		int newValue = base + (stepCount * stepSize) % range;
		int offset = Math.min(1, stepSize);

		// Overflow case.
		if (newValue > maximumValue) {
			newValue = newValue - range - offset;
		}

		// Underflow case.
		if (newValue < minimumValue) {
			newValue = range + newValue + offset;
		}

		return (char) newValue;
	}

	@Override
	public String[] getWindow(Character value, int windowSize) {
		String[] window = new String[windowSize];
		int halfWindow = windowSize / 2;

		for (int i = 0; i < windowSize; i++) {
			window[i] = getStringValue(new Character(computeNextValue(value.charValue(), i - halfWindow)));
		}

		return window;
	}
}
