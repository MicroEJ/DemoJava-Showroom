/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

/**
 * Layer manager configuration.
 */
public class Layers {

	/**
	 * @return the effective value of software mode.
	 */
	native public static boolean isInSoftwareMode();
	
	/**
	 * Set the mode to hardware or software.
	 * @return the effective value of software mode. May be different from value set if the display does not manage both software and hardware modes.
	 */
	native public static boolean setInSoftwareMode(boolean soft);
	
}
