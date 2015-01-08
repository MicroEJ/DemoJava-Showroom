/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.spinner.renderer.WheelRenderer;

import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

public class SettingsWheelRenderer extends WheelRenderer {
	
	@Override
	protected DisplayFont getBiggerFont() {
		Look look = getLook();
		int fontIndex = look.getProperty(LookExtension.GET_BIG_FONT_INDEX);
		return look.getFonts()[fontIndex];
	}
}
