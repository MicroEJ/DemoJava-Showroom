/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

public class FloatingAverage {

	private static final int DEFAULT_COUNT = 10;

	private float[] values;
	private int ptr;

	public FloatingAverage() {
		this(DEFAULT_COUNT);
	}

	public FloatingAverage(int count) {
		values = new float[count];
		ptr = 0;
	}

	public void addValue(float value) {
		values[ptr] = value;
		ptr = ++ptr % values.length;
	}

	public float getValue() {
		double cumul = 0;
		int valuesLength = values.length;
		for (int i = -1; ++i < valuesLength;) {
			cumul += values[i];
		}
		return (float) (cumul / valuesLength);
	}

}
