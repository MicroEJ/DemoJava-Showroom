/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.mwt.widgets.LookExtension;

import ej.microui.Colors;
import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

public class DesktopLauncherLook implements Look {

	private static final int BACKGROUND_COLOR_DEFAULT = 0x292929;
	private static final int BACKGROUND_COLOR_CONTENT = 0x47b9fe;
	private static final int FOREGROUND_COLOR_DEFAULT = Colors.WHITE;
	private static final int FOREGROUND_COLOR_CONTENT = Colors.WHITE;
	private static final int BORDER_COLOR_DEFAULT = 0x505050;
	private static final int FOREGROUND_COLOR_SELECTION = 0x808080;
	private static final int FOREGROUND_COLOR_DISABLED = 0xd0d0d0;

	private static final int FONT_DEFAULT_INDEX = 0;
	private static final int FONT_CONTENT_INDEX = 1;
	private static final int FONT_FOCUSED_INDEX = 2;
	
	private static final int PICTO_FONT_ID = 91;
	private static final int SMALL_PICTO_FONT_INDEX = 3;
	private static final int BIG_PICTO_FONT_INDEX = 4;

	private final DisplayFont[] fonts;

	public DesktopLauncherLook() {
		DisplayFont fontDefault = DisplayFont.getFont(DisplayFont.LATIN, 22, DisplayFont.STYLE_PLAIN);
		DisplayFont fontContent = DisplayFont.getFont(DisplayFont.LATIN, 30, DisplayFont.STYLE_PLAIN);
		DisplayFont fontFocused = DisplayFont.getFont(84, 50, DisplayFont.STYLE_PLAIN);
		DisplayFont smallPictoFont = DisplayFont.getFont(PICTO_FONT_ID, 25, DisplayFont.STYLE_PLAIN);
		DisplayFont bigPictoFont = DisplayFont.getFont(PICTO_FONT_ID,79, DisplayFont.STYLE_PLAIN);
		this.fonts = new DisplayFont[] { fontDefault, fontContent, fontFocused,  smallPictoFont, bigPictoFont};
	}

	@Override
	public int getProperty(int resource) {
		switch (resource) {
		case Look.GET_BACKGROUND_COLOR_DEFAULT:
			return BACKGROUND_COLOR_DEFAULT;
		case Look.GET_BACKGROUND_COLOR_CONTENT:
			return BACKGROUND_COLOR_CONTENT;
		case Look.GET_FOREGROUND_COLOR_DEFAULT:
			return FOREGROUND_COLOR_DEFAULT;
		case Look.GET_FOREGROUND_COLOR_CONTENT:
			return FOREGROUND_COLOR_CONTENT;
		case Look.GET_BORDER_COLOR_CONTENT: // Fall down.
		case Look.GET_BORDER_COLOR_DEFAULT:
			return BORDER_COLOR_DEFAULT;
		case Look.GET_FOREGROUND_COLOR_SELECTION:
			return FOREGROUND_COLOR_SELECTION;
		case Look.GET_FOREGROUND_COLOR_DISABLED:
			return FOREGROUND_COLOR_DISABLED;
			
		case Look.GET_FONT_INDEX_DEFAULT:
			return FONT_DEFAULT_INDEX;
		case Look.GET_FONT_INDEX_CONTENT:
			return FONT_CONTENT_INDEX;
		case Look.GET_FONT_INDEX_FOCUSED:
			return FONT_FOCUSED_INDEX;
			
		case LookExtension.GET_SMALL_FONT_INDEX:
			return FONT_DEFAULT_INDEX;
			
		case LookExtension.GET_X_SMALL_PICTO_FONT_INDEX: // Fall down.
		case LookExtension.GET_SMALL_PICTO_FONT_INDEX:
			return SMALL_PICTO_FONT_INDEX;
			
		case LookExtension.GET_BIG_PICTO_FONT_INDEX:
			return BIG_PICTO_FONT_INDEX;

		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public DisplayFont[] getFonts() {
		return this.fonts;
	}

}
