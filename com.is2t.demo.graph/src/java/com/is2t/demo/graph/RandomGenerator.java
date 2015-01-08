/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import java.util.Random;

public class RandomGenerator implements DataGenerator {

	private final int amplitude;
	int lastValue;
	int diff;
	Random random;

	public RandomGenerator(int amplitude) {
		this.amplitude = amplitude;
		this.lastValue = amplitude / 2;
		this.diff = 0;
		this.random = new Random();
	}

	@Override
	public int getValue() {
		int centerShift = -(amplitude / 2 - lastValue);
		int centerShiftNormalized = centerShift * 3 / amplitude;
		int randomFactor = 20;
		int rand = (random.nextInt(randomFactor * 2 + 1) - randomFactor) / randomFactor;
		int val = rand - centerShiftNormalized;
		diff += val;
		diff = Math.max(-3, Math.min(3, diff));
		// System.out.println(centerShift + " " + centerShiftNormalized + " " + rand);
		final int value = lastValue + diff;
		lastValue = value;
		return value;
	}

}
