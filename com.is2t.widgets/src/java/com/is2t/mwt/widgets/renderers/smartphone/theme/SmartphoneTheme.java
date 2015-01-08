/**
 * Java
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.smartphone.theme;

import com.is2t.mwt.widgets.renderers.smartphone.DialClockRenderer;
import com.is2t.mwt.widgets.renderers.smartphone.util.SmartphoneDrawer;
import com.is2t.mwt.widgets.renderers.theme.WidgetsTheme;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class SmartphoneTheme extends WidgetsTheme {

	private Look defaultLook = new SmartphoneLook();

	public String getName() {
		return "Smartphone";
	}

	public Look getDefaultLook() {
		return this.defaultLook;
	}

	public boolean isStandard() {
		return true;
	}

	protected Drawer newDrawer() {
		return new SmartphoneDrawer();
	}

	protected Renderer newClockRenderer(Drawer drawer) {
		return new DialClockRenderer(drawer);
	}
}
