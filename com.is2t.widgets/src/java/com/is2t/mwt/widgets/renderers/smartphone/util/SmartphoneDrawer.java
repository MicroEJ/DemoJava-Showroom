/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.smartphone.util;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.GraphicsContext;

public class SmartphoneDrawer implements Drawer {

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
		int backgroundColor = coloring.backgroundColor;
		int lightBackgroundColor = Drawings.lightenColor(backgroundColor, 2);
		int darkBackgroundColor = Drawings.darkenColor(backgroundColor, 2);
		SmartphoneDrawings.fillBox(g, 0, 0, width, height, 0, backgroundColor, lightBackgroundColor,
				darkBackgroundColor);
	}

	public void drawBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring) {
		int backgroundColor = coloring.backgroundColor;
		int lightBackgroundColor = Drawings.lightenColor(backgroundColor, 2); // FIXME lighter factor
		int darkBackgroundColor = Drawings.darkenColor(backgroundColor, 2); // FIXME darker factor
		SmartphoneDrawings.drawRoundBox(g, 0, 0, width, height, 0, backgroundColor, lightBackgroundColor, darkBackgroundColor, coloring.borderColor);
	}

	public void drawHalfBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring, boolean upIsRound) {
		int backgroundColor = coloring.backgroundColor;
		int lightBackgroundColor = Drawings.lightenColor(backgroundColor, 2); // FIXME lighter factor
		int darkBackgroundColor = Drawings.darkenColor(backgroundColor, 2); // FIXME darker factor
		SmartphoneDrawings.drawHalfRoundBox(g, 0, 0, width, height, 0, backgroundColor, lightBackgroundColor,
				darkBackgroundColor, coloring.borderColor, upIsRound);
	}

	public void drawBox(GraphicsContext g, int size, boolean isChecked, LookSettings coloring) {
		// draw check
		Drawings.drawRoundBox(g, 0, 0, size, size, 0, coloring.backgroundColor, coloring.borderColor);
		if (isChecked) {
			g.setColor(coloring.foregroundColor);
			g.drawLine(3, 3, size - 4, size - 4);
			g.drawLine(size - 4, 3, 3, size - 4);
		}
	}

	public void drawRadio(GraphicsContext g, int size, boolean isSelected, LookSettings coloring) {
		g.setColor(coloring.backgroundColor);
		g.fillCircle(0, 0, size - 1); // -1 to fit drawRect and ensures consistency between radio and checkboxes
		if (isSelected) {
			g.setColor(coloring.foregroundColor);
			g.fillCircle(2, 2, size - 5); // idem
		}
		g.setColor(coloring.borderColor);
		g.drawCircle(0, 0, size - 1); // idem
	}

	public int getMargin() {
		return MARGIN;
	}

	public int getPadding() {
		return PADDING;
	}

}
