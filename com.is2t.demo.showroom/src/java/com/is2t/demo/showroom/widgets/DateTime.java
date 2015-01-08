/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.mwt.util.Utilities;

import ej.bon.TimerTask;
import ej.microui.io.Display;
import ej.mwt.Widget;

public class DateTime extends Widget {

	public static final int MAX_LEVEL = 15;
	private int level;
	private boolean appear;
	private boolean timeVisible;
	private TimerTask task;
	private final Runnable empty;

	public DateTime() {
		level = MAX_LEVEL;
		appear = true;
		timeVisible = true;
		empty = new Runnable() {

			@Override
			public void run() {
			}
		};
	}

	public void showNotify() {
		task = new TimerTask() {

			@Override
			public void run() {
				if (appear) {
					if (level < MAX_LEVEL) {
						level++;
						// repaint();
						improvedRepaint();
					}
				} else {
					if (level > 0) {
						level--;
						// repaint();
						improvedRepaint();
					} else {
						appear = true;
						level = 0;
						timeVisible = !timeVisible;
					}
				}

			}
		};
		Utilities.getTimer().schedule(this.task, 0, 50);
	}

	// Avoid graphic artifact.
	private void improvedRepaint() {
		Display display = Display.getDefaultDisplay();
		display.callSerially(empty);
		repaint();
		display.callSerially(empty);
	}

	public void hideNotify() {
		this.task.cancel();
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	public void switchTo() {
		appear = false;
	}

	public int getLevel() {
		return level;
	}

	public boolean isTimeVisible() {
		return timeVisible;
	}
}
