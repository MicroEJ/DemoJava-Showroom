/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.smartphone.util;

import com.is2t.mwt.util.Drawings;

import ej.microui.io.GraphicsContext;

/**
 * <p>
 * Provides some drawing facilities.
 * </p>
 */
public class SmartphoneDrawings {

	public static final void drawHalfRoundBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor,
			int lightBackgroundColor, int darkBackgroundColor, int borderColor, boolean upIsRound) {
		Drawings.drawHalfRoundBox(g, x, y, width, height, padding, backgroundColor, borderColor, upIsRound);
		int halfHeight = height / 2;
		Drawings.fillRoundBorder(g, x, y + halfHeight, width, halfHeight - 1, 0, darkBackgroundColor);
		g.setColor(lightBackgroundColor);
		g.drawHorizontalLine(x + (upIsRound ? 2 : 1), y + 1, width - (upIsRound ? 5 : 3));
	}

	public static final void drawRoundBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor,
			int lightBackgroundColor, int darkBackgroundColor, int borderColor) {
		int halfHeight = height / 2;
		Drawings.fillRoundBorder(g, x, y, width, height, 0, backgroundColor);
		Drawings.fillRoundBorder(g, x, y + halfHeight, width, halfHeight - 1, 0, darkBackgroundColor);
		g.setColor(lightBackgroundColor);
		g.drawHorizontalLine(x + 2, y + 1, width - 5);
		Drawings.drawRoundBorder(g, x, y, width, height, 0, borderColor);
	}

	public static final void drawBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor,
			int lightBackgroundColor, int darkBackgroundColor, int borderColor) {
		int halfHeight = height / 2;
		g.setColor(backgroundColor);
		g.fillRect(x + 1, y + 1, width - 2, height - 2);
		Drawings.fillRoundBorder(g, x, y + halfHeight, width, halfHeight - 1, 0, darkBackgroundColor);
		g.setColor(lightBackgroundColor);
		g.drawHorizontalLine(x + 1, y + 1, width - 4);
		g.setColor(borderColor);
		g.drawRect(x, y, width - 1, height - 1);
	}

	public static final void fillBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor,
			int lightBackgroundColor, int darkBackgroundColor) {
		int halfHeight = height / 2;
		g.setColor(backgroundColor);
		g.fillRect(x, y, width, height);
		Drawings.fillRoundBorder(g, x, y + halfHeight, width, halfHeight - 1, 0, darkBackgroundColor);
		g.setColor(lightBackgroundColor);
		g.drawHorizontalLine(x, y, width - 2);
	}

}
