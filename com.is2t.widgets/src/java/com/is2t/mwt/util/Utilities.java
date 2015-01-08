/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

import ej.bon.Timer;

/**
 * Various utilities
 */
public class Utilities {

	private static Timer timer;
	public static Timer getTimer() {
		if(timer == null) {
			timer = new Timer();
		}
		return timer;
	}

	public static void stopTimer() {
		if (timer != null)
			timer.cancel();
	}
}
