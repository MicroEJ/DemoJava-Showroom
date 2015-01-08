/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers.util;

import com.is2t.mwt.widgets.extended.IExtendedStyle;
import com.is2t.mwt.widgets.renderers.util.ContentLook;

import ej.mwt.rendering.Look;

public class ContentStyleLook extends ContentLook {

	public ContentStyleLook(Look look, IExtendedStyle style, boolean isEnabled) {
		super(look, isEnabled);
		// FIXME apply enabled and pressed filters on chosen colors
		int styleBackgroundColor = style.getBackgroundColor();
		if (styleBackgroundColor != IExtendedStyle.DEFAULT_COLOR) {
			backgroundColor = styleBackgroundColor;
		}
		int styleForegroundColor = style.getForegroundColor();
		if (styleForegroundColor != IExtendedStyle.DEFAULT_COLOR) {
			foregroundColor = styleForegroundColor;
		}
	}

}
