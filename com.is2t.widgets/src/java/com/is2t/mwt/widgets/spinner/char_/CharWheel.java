/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.char_;

import com.is2t.mwt.widgets.spinner.Wheel;

public class CharWheel extends Wheel<Character> {

	public CharWheel() {
		super();
	}

	public CharWheel(boolean horizontal, int visibleItemsCount) {
		super(horizontal, visibleItemsCount);
	}

	@Override
	protected void update(int distance) {
		spinOffset += distance;
		int screenSize = horizontal ? getWidth() : getHeight();
		int stepHeight = screenSize / visibleItemsCount;
		int oldValue = currentValue.charValue();
		int diff = Math.round((float) -spinOffset / stepHeight);
		int diffValue = (diff * model.getStepSize().charValue());
		int currentFloatValue = (oldValue + diffValue);
		int maximumValue = model.getMaximumValue().charValue();
		int minimumValue = model.getMinimumValue().charValue();
		int stepSize = model.getStepSize().charValue();
		if (currentFloatValue > maximumValue) {
			currentFloatValue -= (maximumValue - minimumValue + stepSize);
		} else if (currentFloatValue < minimumValue) {
			currentFloatValue += (maximumValue - minimumValue + stepSize);
		}
		currentValue = new Character((char) currentFloatValue);
		spinOffset += diff * stepHeight;
		repaint();
	}

}
