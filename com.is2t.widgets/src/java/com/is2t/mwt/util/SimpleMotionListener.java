/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

import ej.motion.util.MotionListener;


public abstract class SimpleMotionListener implements MotionListener {

	@Override
	public void start(int value) {
		step(value);
	}

	@Override
	public void stop(int value) {
		step(value);
	}
}
