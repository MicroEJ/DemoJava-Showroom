/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.int_;

public class EndlessIntegerValue extends LimitedIntegerValue {

	public EndlessIntegerValue(int minimumValue, int maximumValue, int stepSize, int initialValue, String unit) {
		super(minimumValue, maximumValue, stepSize, initialValue, unit);
	}

	// Base must be in [minimumValue, maximumValue].
	@Override
	public Integer computeNextValue(Integer base, int stepCount) {
		int range = maximumValue - minimumValue;
		int newValue = base.intValue() + (stepCount * stepSize) % range;
		int offset = Math.min(1, stepSize);

		// Overflow case.
		if (newValue > maximumValue) {
			newValue = newValue - range - offset;
		}

		// Underflow case.
		if (newValue < minimumValue) {
			newValue = range + newValue + offset;
		}

		return Integer.valueOf(newValue);
	}

}
