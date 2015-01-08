/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.smartphone.theme;

import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

public class SmartphoneLook implements Look {

	private static final DisplayFont FONT_CONTENT;
	private static final DisplayFont FONT_SELECTION;
	private static final DisplayFont[] FONTS;

	private static final int BORDER_COLOR = 0x182428;
	private static final int BORDER_COLOR_FOCUSED = 0x555555;
	private static final int BORDER_COLOR_DISABLED = 0x7c7f80;

	private static final int BACKGROUND_COLOR_DEFAULT = 0x96acb2;
	private static final int BACKGROUND_COLOR_CONTENT = 0xd9e7ef;
	private static final int BACKGROUND_COLOR_FOCUSED = 0xaec6cc;
	private static final int BACKGROUND_COLOR_DISABLED = 0xbec6cc;
	private static final int BACKGROUND_COLOR_SELECTION = 0xf4801f;

	private static final int FOREGROUND_COLOR_DEFAULT = 0xffffff;
	private static final int FOREGROUND_COLOR_CONTENT = 0x222222;
	private static final int FOREGROUND_COLOR_FOCUSED = 0xffffff;
	private static final int FOREGROUND_COLOR_DISABLED = 0x7c7f80;
	private static final int FOREGROUND_COLOR_SELECTION = 0xffffff;

	private static final int FONT_INDEX_CONTENT = 0;
	private static final int FONT_INDEX_SELECTION = 1;

	static {
		FONT_CONTENT = DisplayFont.getFont(DisplayFont.LATIN, 13, DisplayFont.STYLE_PLAIN);
		FONT_SELECTION = DisplayFont.getFont(FONT_CONTENT.getIdentifiers()[0], FONT_CONTENT.getHeight(), FONT_CONTENT.getStyle()
				| DisplayFont.STYLE_BOLD);
		FONTS = new DisplayFont[] { FONT_CONTENT, FONT_SELECTION };
	}

	public DisplayFont[] getFonts() {
		return FONTS;
	}

	public int getProperty(int action) {
		switch(action) {
		case Look.GET_BORDER_COLOR_DEFAULT: // fall down
		case Look.GET_BORDER_COLOR_CONTENT: // fall down
		case Look.GET_BORDER_COLOR_SELECTION: // fall down
			return BORDER_COLOR;
		case Look.GET_BORDER_COLOR_FOCUSED:
			return BORDER_COLOR_FOCUSED;
		case Look.GET_BORDER_COLOR_DISABLED:
			return BORDER_COLOR_DISABLED;

		case Look.GET_BACKGROUND_COLOR_DEFAULT:
			return BACKGROUND_COLOR_DEFAULT;
		case Look.GET_BACKGROUND_COLOR_CONTENT:
			return BACKGROUND_COLOR_CONTENT;
		case Look.GET_BACKGROUND_COLOR_FOCUSED:
			return BACKGROUND_COLOR_FOCUSED;
		case Look.GET_BACKGROUND_COLOR_DISABLED:
			return BACKGROUND_COLOR_DISABLED;
		case Look.GET_BACKGROUND_COLOR_SELECTION:
			return BACKGROUND_COLOR_SELECTION;

		case Look.GET_FOREGROUND_COLOR_DEFAULT:
			return FOREGROUND_COLOR_DEFAULT;
		case Look.GET_FOREGROUND_COLOR_CONTENT:
			return FOREGROUND_COLOR_CONTENT;
		case Look.GET_FOREGROUND_COLOR_FOCUSED:
			return FOREGROUND_COLOR_FOCUSED;
		case Look.GET_FOREGROUND_COLOR_DISABLED:
			return FOREGROUND_COLOR_DISABLED;
		case Look.GET_FOREGROUND_COLOR_SELECTION:
			return FOREGROUND_COLOR_SELECTION;

		case Look.GET_FONT_INDEX_DEFAULT: // fall down
		case Look.GET_FONT_INDEX_CONTENT: // fall down
		case Look.GET_FONT_INDEX_FOCUSED: // fall down
		case Look.GET_FONT_INDEX_DISABLED:
			return FONT_INDEX_CONTENT;
		case Look.GET_FONT_INDEX_SELECTION:
			return FONT_INDEX_SELECTION;

		default:
			throw new IllegalArgumentException();
		}
	}

}
