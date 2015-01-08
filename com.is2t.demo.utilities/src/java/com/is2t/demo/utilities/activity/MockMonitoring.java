/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

import java.util.Random;

/**
 * Implementation that returns random values
 */
public class MockMonitoring implements Monitoring{

	private Random random = new Random();
	
	@Override
	public int getCPULoad() {
		return random.nextInt(101);
	}

	@Override
	public int getFPS() {
		return random.nextInt(75);
	}

}
