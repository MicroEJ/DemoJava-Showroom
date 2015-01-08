/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.util;

import ej.mwt.rendering.Look;

public class ContentLook extends LookSettings {

	public ContentLook(Look look, boolean isEnabled) {
		this(look, isEnabled, false);
	}

	public ContentLook(Look look, boolean isEnabled, boolean isSelected) {
		if (!isEnabled) {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DISABLED)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_DISABLED);
		} else if (isSelected) {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_SELECTION)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_SELECTION);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_SELECTION);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_SELECTION);
		} else {
			font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_CONTENT)];
			backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT);
			foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_CONTENT);
			borderColor = look.getProperty(Look.GET_BORDER_COLOR_CONTENT);
		}
	}

}
