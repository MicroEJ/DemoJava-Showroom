/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers.plain.theme;

import com.is2t.mwt.widgets.extended.renderers.ButtonRenderer;
import com.is2t.mwt.widgets.extended.renderers.LabelRenderer;
import com.is2t.mwt.widgets.renderers.plain.theme.PlainTheme;
import com.is2t.mwt.widgets.renderers.plain.util.PlainDrawer;
import com.is2t.mwt.widgets.renderers.util.Drawer;

public class ExtendedPlainTheme extends PlainTheme {

	protected void populate() {
		// TODO create a Drawing subclass to manage alignment and anchor
		Drawer drawer = new PlainDrawer();
		add(new LabelRenderer(drawer));
		add(new ButtonRenderer(drawer));
		super.populate();
	}

}
