/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers.smartphone.theme;

import com.is2t.mwt.widgets.extended.renderers.ButtonRenderer;
import com.is2t.mwt.widgets.extended.renderers.LabelRenderer;
import com.is2t.mwt.widgets.renderers.smartphone.theme.SmartphoneTheme;
import com.is2t.mwt.widgets.renderers.smartphone.util.SmartphoneDrawer;
import com.is2t.mwt.widgets.renderers.util.Drawer;

public class ExtendedSmartphoneTheme extends SmartphoneTheme {

	protected void populate() {
		// TODO create a Drawing subclass to manage alignment and anchor
		Drawer drawer = new SmartphoneDrawer();
		add(new LabelRenderer(drawer));
		add(new ButtonRenderer(drawer));
		super.populate();
	}

}
