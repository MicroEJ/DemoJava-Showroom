/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.mwt.widgets.label.renderer.MultiLineLabelRenderer;

import ej.microui.io.Display;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

public class DesktopLauncherTheme extends Theme {

	private final Display display;

	public DesktopLauncherTheme(Display display) {
		this.display = display;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	protected void populate() {
		add(new ActivityLauncherRenderer());
		add(new DateTimeRenderer());
		add(new WeatherRenderer());
		add(new PanelRenderer(display));
		add(new MultiLineLabelRenderer());
		add(new LeftIconLabelRenderer());
	}

	@Override
	public Look getDefaultLook() {
		return new DesktopLauncherLook();
	}

	@Override
	public boolean isStandard() {
		return false;
	}

}
