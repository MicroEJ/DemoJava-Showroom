/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

public interface Monitoring {

	/**
	 * Returns the CPU load in percent.
	 */
	public int getCPULoad();
	
	/**
	 * Returns the number of frames per second.
	 */
	public int getFPS();
	
}
