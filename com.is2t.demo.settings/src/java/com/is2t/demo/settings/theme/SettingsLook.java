/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.theme;

import com.is2t.demo.showroom.common.HomeButtonRenderer;
import com.is2t.mwt.widgets.LookExtension;

import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

public class SettingsLook implements Look {

	private static final int BORDER_COLOR_DEFAULT = 0x969aa2;
	private static final int BACKGROUND_COLOR_DEFAULT = 0xffffff;
	private static final int BORDER_COLOR_CONTENT = 0x969aa2;
	private static final int BACKGROUND_COLOR_CONTENT = 0x4da5ea;
	private static final int BACKGROUND_COLOR_SELECTION = 0x969aa2;
	private static final int FOREGROUND_COLOR_DEFAULT = 0x0;
	private static final int FOREGROUND_COLOR_CONTENT = 0xffffff;
	private static final int FOREGROUND_COLOR_FOCUSED = 0x0;
	private static final int FOREGROUND_COLOR_DISABLED = 0x524d4d;

	private static final int SMALL_FONT_INDEX = 0;
	private static final int MEDIUM_FONT_INDEX = 1;
	private static final int BIG_FONT_INDEX = 2;
	
	private static final int BOLD_MEDIUM_FONT_INDEX = 3;

	private static final int X_SMALL_PICTO_FONT_INDEX = 4;
	private static final int SMALL_PICTO_FONT_INDEX = 5;
	private static final int MEDIUM_PICTO_FONT_INDEX = 6;
	private static final int BIG_PICTO_FONT_INDEX = 7;

	private static final DisplayFont SMALL_FONT = DisplayFont.getFont(DisplayFont.LATIN, 15, DisplayFont.STYLE_PLAIN);
	private static final DisplayFont MEDIUM_FONT = DisplayFont.getFont(DisplayFont.LATIN, 30, DisplayFont.STYLE_PLAIN);
	private static final DisplayFont BIG_FONT = DisplayFont.getFont(DisplayFont.LATIN, 50, DisplayFont.STYLE_PLAIN);

	private static final DisplayFont BOLD_MEDIUM_FONT = DisplayFont.getFont(DisplayFont.LATIN, 30, DisplayFont.STYLE_BOLD);
	
	private static final int PICTO_FONT_ID = 81;

	private static final DisplayFont X_SMALL_PICTO_FONT = HomeButtonRenderer.FONT;
	private static final DisplayFont SMALL_PICTO_FONT = DisplayFont.getFont(PICTO_FONT_ID, 30, DisplayFont.STYLE_PLAIN);
	private static final DisplayFont MEDIUM_PICTO_FONT = DisplayFont
			.getFont(PICTO_FONT_ID, 50, DisplayFont.STYLE_PLAIN);
	private static final DisplayFont BIG_PICTO_FONT = DisplayFont.getFont(PICTO_FONT_ID, 75, DisplayFont.STYLE_PLAIN);

	private static final DisplayFont[] FONTS;

	static {
		FONTS = new DisplayFont[] { SMALL_FONT, MEDIUM_FONT, BIG_FONT, BOLD_MEDIUM_FONT, X_SMALL_PICTO_FONT, SMALL_PICTO_FONT, MEDIUM_PICTO_FONT,
				BIG_PICTO_FONT, };
	}

	@Override
	public int getProperty(int resource) {
		switch (resource) {
		case Look.GET_BORDER_COLOR_CONTENT: // Fall down.
		case Look.GET_BORDER_COLOR_SELECTION:
			return BORDER_COLOR_CONTENT;
		case Look.GET_BORDER_COLOR_DEFAULT: // Fall down.
		case Look.GET_BORDER_COLOR_FOCUSED: // Fall down.
		case Look.GET_BORDER_COLOR_DISABLED:
			return BORDER_COLOR_DEFAULT;

		case Look.GET_BACKGROUND_COLOR_SELECTION:
			return BACKGROUND_COLOR_SELECTION;

		case Look.GET_BACKGROUND_COLOR_DEFAULT: // Fall down.
		case Look.GET_BACKGROUND_COLOR_FOCUSED: // Fall down.
		case Look.GET_BACKGROUND_COLOR_DISABLED:
			return BACKGROUND_COLOR_DEFAULT;
		case Look.GET_BACKGROUND_COLOR_CONTENT:
			return BACKGROUND_COLOR_CONTENT;

		case Look.GET_FOREGROUND_COLOR_CONTENT:
			return FOREGROUND_COLOR_CONTENT;

		case Look.GET_FOREGROUND_COLOR_DISABLED:
			return FOREGROUND_COLOR_DISABLED;

		case Look.GET_FOREGROUND_COLOR_FOCUSED:
			return FOREGROUND_COLOR_FOCUSED;

		case Look.GET_FOREGROUND_COLOR_DEFAULT: // Fall down.
		case Look.GET_FOREGROUND_COLOR_SELECTION:
			return FOREGROUND_COLOR_DEFAULT;

		case Look.GET_FONT_INDEX_DEFAULT: // fall down
		case Look.GET_FONT_INDEX_CONTENT: // fall down
		case Look.GET_FONT_INDEX_FOCUSED: // fall down
		case Look.GET_FONT_INDEX_DISABLED: // fall down
		case Look.GET_FONT_INDEX_SELECTION:
			return MEDIUM_FONT_INDEX;

		case LookExtension.GET_XXX_SMALL_FONT_INDEX:
			return SMALL_FONT_INDEX;
		case LookExtension.GET_XX_SMALL_FONT_INDEX:
			return SMALL_FONT_INDEX;
		case LookExtension.GET_X_SMALL_FONT_INDEX:
			return SMALL_FONT_INDEX;
		case LookExtension.GET_SMALL_FONT_INDEX:
			return SMALL_FONT_INDEX;
		case LookExtension.GET_MEDIUM_FONT_INDEX:
			return MEDIUM_FONT_INDEX;
		case LookExtension.GET_BIG_FONT_INDEX:
			return BIG_FONT_INDEX;
		case LookExtension.GET_X_BIG_FONT_INDEX:
			return BIG_FONT_INDEX;
		case LookExtension.GET_BOLD_X_SMALL_FONT_INDEX:
			return BOLD_MEDIUM_FONT_INDEX;
		case LookExtension.GET_BOLD_SMALL_FONT_INDEX:
			return BOLD_MEDIUM_FONT_INDEX;
		case LookExtension.GET_X_SMALL_PICTO_FONT_INDEX:
			return X_SMALL_PICTO_FONT_INDEX;
		case LookExtension.GET_SMALL_PICTO_FONT_INDEX:
			return SMALL_PICTO_FONT_INDEX;
		case LookExtension.GET_MEDIUM_PICTO_FONT_INDEX:
			return MEDIUM_PICTO_FONT_INDEX;
		case LookExtension.GET_BIG_PICTO_FONT_INDEX:
			return BIG_PICTO_FONT_INDEX;

		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public DisplayFont[] getFonts() {
		return FONTS;
	}
}
