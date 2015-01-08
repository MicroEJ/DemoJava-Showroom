/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

public class MovingAverage {

	private static final int DEFAULT_COUNT = 10;

	private int[] values;
	private int ptr;

	public MovingAverage() {
		this(DEFAULT_COUNT);
	}

	public MovingAverage(int count) {
		values = new int[count];
		ptr = 0;
	}

	public synchronized void fillValues(int value) {
		while (ptr < values.length) {
			values[ptr++] = value;
		}
		ptr = 0;
	}

	public synchronized void addValue(int value) {
		values[ptr] = value;
		ptr = ++ptr % values.length;
	}

	public synchronized int getValue() {
		long cumul = 0;
		int valuesLength = values.length;
		for (int i = -1; ++i < valuesLength;) {
			cumul += values[i];
		}
		return (int) (cumul / valuesLength);
	}

}
