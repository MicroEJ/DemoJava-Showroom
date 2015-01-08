/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.ActivityMetaData;
import com.is2t.demo.launcher.Launcher;

import ej.microui.Event;
import ej.microui.io.DisplayFont;
import ej.microui.io.Pointer;
import ej.mwt.Widget;

public class ActivityLauncher extends Widget {

	public static final int DOTS_COUNT = 18;

	private final String name;
	private final DisplayFont font;
	private final char picto;
	private final Activity activity;
	private final Launcher launcher;

	private boolean rotating;
	private int enlightenCount;
	private float offset;

	public ActivityLauncher(Launcher launcher, Activity activity,
			ActivityMetaData metaData, DisplayFont pictoFont) {
		this.launcher = launcher;
		this.activity = activity;
		this.name = metaData.getName();
		this.font = pictoFont;
		this.picto = metaData.getPicto();
		enlightenCount = DOTS_COUNT;
	}

	public void showNotify() {
	}

	public void hideNotify() {
		stopRotateSmallCircles();
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the font
	 */
	public DisplayFont getFont() {
		return this.font;
	}

	/**
	 * @return the picto
	 */
	public char getPicto() {
		return this.picto;
	}

	/**
	 * @return the offset
	 */
	public float getOffset() {
		return this.offset;
	}

	public int getEnlightenCount() {
		return enlightenCount;
	}

	public boolean isRotating() {
		return rotating;
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Event.POINTER) {
			switch (Pointer.getAction(event)) {
			case Pointer.ENTERED:
				rotateSmallCircles();
				break;
			case Pointer.RELEASED:
				launcher.launch(this.activity);
				return true;
			case Pointer.EXITED:
				stopRotateSmallCircles();
				break;
			}
		}
		return false;
	}

	private synchronized void stopRotateSmallCircles() {
		rotating = false;
		repaint();
	}

	private synchronized void rotateSmallCircles() {
		rotating = true;
		repaint();
	}

}
