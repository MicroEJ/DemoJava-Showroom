/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import ej.microui.io.DisplayFont;

public final class GraphicalInfos {

	public static final int PADDING = 25;
	public static final int SCALE_SIZE = 3;
	public static final int SHIFT = 2;
	public static final int SHIFT_PERIOD = 20;
	public static final int LINE_FONT_SIZE = 5;

	public final int displayWidth;
	public final int displayHeight;

	public final DisplayFont font;
	public final int fontHeight;

	public final int gridWidth;
	public final int gridHeight;
	public final int gridLeft;
	public final int gridTop;
	public final int gridBottom;
	public final int gridZoneWidth;
	public final int gridZoneLeft;
	public final int lineCharWidth;

	public GraphicalInfos(int displayWidth, int displayHeight, int max) {
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
		font = DisplayFont.getFont(DisplayFont.LATIN, 15, DisplayFont.STYLE_PLAIN);
		int leftLegendWidth = font.stringWidth(Integer.toString(max));
		fontHeight = font.getHeight();
		gridLeft = PADDING + leftLegendWidth;
		gridZoneLeft = gridLeft + SCALE_SIZE * 2;
		gridTop = PADDING + fontHeight;
		gridWidth = displayWidth - (2 * PADDING + 2 * leftLegendWidth);
		gridZoneWidth = gridWidth - 2 * SCALE_SIZE;
		gridHeight = displayHeight - (2 * PADDING + 2 * fontHeight);
		gridBottom = gridTop + gridHeight;
		lineCharWidth = Brushes.getFont(LINE_FONT_SIZE).charWidth(Brushes.getChar());
	}

}
