/*
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

import ej.microui.io.Display;

public class DisplayContext {

	private static final Display display;
	private static Thread displayThread;

	static {
		display = Display.getDefaultDisplay();
		display.callSerially(new Runnable() {
			public void run() {
				displayThread = Thread.currentThread();
			}
		});
	}

	public static void call(Runnable runnable) {
		if (Thread.currentThread() == displayThread) {
			runnable.run();
		} else {
			display.callSerially(runnable);
		}
	}

	private DisplayContext() {
	}

}
