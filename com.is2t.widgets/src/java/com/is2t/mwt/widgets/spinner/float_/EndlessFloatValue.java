/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.float_;

public class EndlessFloatValue extends LimitedFloatValue {

	public EndlessFloatValue(float minimumValue, float maximumValue, float stepSize, float initialValue, String unit) {
		super(minimumValue, maximumValue, stepSize, initialValue, unit);
	}

	// Base must be in [minimumValue, maximumValue].
	@Override
	public Float computeNextValue(Float base, int stepCount) {
		float range = maximumValue - minimumValue;
		float newValue = base.floatValue() + (stepCount * stepSize) % range;
		float offset = Math.min(1, stepSize);

		// Overflow case.
		if (newValue > maximumValue) {
			newValue = newValue - range - offset;
		}

		// Underflow case.
		if (newValue < minimumValue) {
			newValue = range + newValue + offset;
		}

		return Float.valueOf(newValue);
	}

}
