/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import java.util.Random;

public class SinusGenerator implements DataGenerator {

	private static final float FREQUENCY = 5f;
	private static final int FREQUENCY_RANDOM_FACTOR = 8;
	private static final int PADDING = 5;

	private final int amplitudeMax;
	private int amplitude;
	private int offset;
	private final Random random;
	private int currentTime;
	private int currentTimeOffset;
	private float frequency;
	private MovingAverage movingAverage;
	private MovingAverage amplitudeAverage;

	public SinusGenerator(int amplitude) {
		this.amplitudeMax = amplitude;
		this.random = new Random();
		frequency = FREQUENCY;
		this.amplitude = amplitude * 2 / 3;
		offset = amplitude / 2;
		movingAverage = new MovingAverage();
		movingAverage.fillValues(amplitude / 2);
		amplitudeAverage = new MovingAverage(5);
		// amplitudeAverage.fillValues(amplitude);
	}

	@Override
	public int getValue() {
		offset = (int) (amplitudeMax
				* Math.sin(Math.toRadians(currentTimeOffset++) * FREQUENCY
						* 2.7777f) / 4 + amplitudeMax / 2);
		amplitude = Math.min(amplitudeMax - offset, offset) - PADDING;
		amplitude += random.nextInt(amplitude);
		amplitudeAverage.addValue(amplitude);
		amplitude = amplitudeAverage.getValue();
		double sin = Math.sin(Math.toRadians(currentTime) * frequency);
		int i = (int) Math.round(amplitude * sin / 2 + offset);
		if (Math.toRadians(currentTime) * frequency >= 2 * Math.PI) {
			currentTime = 0;
			updateFrequency();
		}
		currentTime++;
		movingAverage.addValue(i);
		i = movingAverage.getValue();
		return i;
	}

	private void updateFrequency() {
		final int randomFactor = FREQUENCY_RANDOM_FACTOR * 2;
		frequency = (random.nextInt(randomFactor)) * FREQUENCY
				/ FREQUENCY_RANDOM_FACTOR + FREQUENCY;
	}
}
