/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.plain.util;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.GraphicsContext;

public class PlainDrawer implements Drawer {

	/**
	 * The margin outside the widget's bounds. Default is 3.
	 */
	public static final int MARGIN = 3;
	/**
	 * The margin between the widget's bounds and its content. Default is 4.
	 */
	public static final int PADDING = 4;

	public void fillBackground(GraphicsContext g, int width, int height, LookSettings coloring) {
		g.setColor(coloring.backgroundColor);
		g.fillRect(0, 0, width, height);
	}

	public void drawText(GraphicsContext g, String text, int width, int height, int padding, LookSettings coloring, boolean content) {
		g.setColor(coloring.foregroundColor);
		g.setFont(coloring.font);
		if (content) {
			g.drawString(text, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		} else {
			g.drawString(text, padding, padding, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

	public void drawOutline(GraphicsContext g, int width, int height, int padding, LookSettings coloring) {
		Drawings.drawOutline(g, 0, 0, width, height, padding, coloring.borderColor);
	}

	public void fillBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring) {
		Drawings.fillRoundBorder(g, 0, 0, width, height, 0, coloring.backgroundColor);
	}

	public void drawBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring) {
		Drawings.drawRoundBox(g, 0, 0, width, height, 0, coloring.backgroundColor, coloring.borderColor);
	}

	public void drawHalfBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring, boolean upIsRound) {
		Drawings.drawHalfRoundBox(g, 0, 0, width, height, 0, coloring.backgroundColor, coloring.borderColor, upIsRound);
	}

	public void drawBox(GraphicsContext g, int size, boolean isChecked, LookSettings coloring) {
		// draw check
		Drawings.drawRoundBox(g, 0, 0, size, size, 0, coloring.backgroundColor, coloring.borderColor);
		if (isChecked) {
			int halfBoxSize = size / 2;
			g.setColor(coloring.foregroundColor);
			g.drawLine(2, halfBoxSize, halfBoxSize - 2, size - 4);
			g.drawLine(halfBoxSize - 2, size - 3, size - 2, 3);
			g.drawLine(3, halfBoxSize, halfBoxSize - 1, size - 4);
			g.drawLine(halfBoxSize - 1, size - 3, size - 2, 3);
		}
	}

	public void drawRadio(GraphicsContext g, int size, boolean isSelected, LookSettings coloring) {
		g.setColor(coloring.backgroundColor);
		g.fillCircle(0, 0, size);
		if (isSelected) {
			g.setColor(coloring.foregroundColor);
			g.fillCircle(2, 2, size - 4);
		}
		g.setColor(coloring.borderColor);
		g.drawCircle(0, 0, size);
	}

	public int getMargin() {
		return MARGIN;
	}

	public int getPadding() {
		return PADDING;
	}

}
