/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.int_;

import com.is2t.mwt.widgets.spinner.Wheel;



public class IntegerWheel extends Wheel<Integer> {

	public IntegerWheel() {
		super();
	}

	public IntegerWheel(boolean horizontal, int visibleItemsCount) {
		super(horizontal, visibleItemsCount);
	}

	@Override
	protected void update(int distance) {
		spinOffset += distance;
		int screenSize = horizontal ? getWidth() : getHeight();
		int stepHeight = screenSize / visibleItemsCount;
		
		int diff = Math.round((float) -spinOffset / stepHeight);
		Integer newIntegerValue = model.computeNextValue(currentValue, diff);
		if(newIntegerValue == null){
			return;
		}
		
		currentValue = newIntegerValue;
		spinOffset += diff * stepHeight;
		repaint();
	}

}
