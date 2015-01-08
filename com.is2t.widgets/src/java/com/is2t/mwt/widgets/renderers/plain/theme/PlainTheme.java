/**
 * Java
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.plain.theme;

import com.is2t.mwt.widgets.renderers.plain.DialClockRenderer;
import com.is2t.mwt.widgets.renderers.plain.util.PlainDrawer;
import com.is2t.mwt.widgets.renderers.theme.WidgetsTheme;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class PlainTheme extends WidgetsTheme {

	private Look defaultLook = new PlainLook();

	public String getName() {
		return "Classic";
	}

	public Look getDefaultLook() {
		return this.defaultLook;
	}

	public boolean isStandard() {
		return true;
	}

	protected Renderer newClockRenderer(Drawer drawer) {
		return new DialClockRenderer(drawer);
	}

	protected Drawer newDrawer() {
		return new PlainDrawer();
	}

}
