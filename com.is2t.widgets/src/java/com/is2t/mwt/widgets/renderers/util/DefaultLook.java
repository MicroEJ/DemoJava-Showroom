/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.util;

import ej.mwt.rendering.Look;

public class DefaultLook extends LookSettings {

	public DefaultLook(Look look, boolean isEnabled, boolean isPressed) {
		if (!isEnabled) {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DISABLED)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DISABLED);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_DISABLED);
		} else if (isPressed) {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_FOCUSED)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_FOCUSED);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_FOCUSED);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_FOCUSED);
		} else {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_DEFAULT);
		}
	}

}
