/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

public class InputGenerator implements DataGenerator {

	private MovingAverage movingAverage;
	private int lastValue;

	public InputGenerator() {
		movingAverage = new MovingAverage(5);
	}

	public void setValue(int value) {
		this.lastValue = value;
		movingAverage.addValue(value);
	}

	@Override
	public int getValue() {
		int value = movingAverage.getValue();
		movingAverage.addValue(lastValue);
		return value;
	}
}
