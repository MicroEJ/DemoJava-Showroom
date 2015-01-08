/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.button.ListEntry;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class ListEntryRenderer extends MultiLineLabelRenderer {

	@Override
	public Class<?> getManagedType() {
		return ListEntry.class;
	}

	@Override
	public int getPreferredContentWidth(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		ListEntry label = (ListEntry) widget;
		Object value = label.getValue();
		int lineWidth = font.stringWidth(value.toString());
		return lineWidth + super.getPreferredContentWidth(widget, font, lines, visibleLineCount);
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		ListEntry label = (ListEntry) renderable;
		int width = label.getWidth();
		int height = label.getHeight();
		int padding = getPadding();

		Object value = label.getValue();
		String valueString = value.toString();
		int valueX = width - 2 * padding;
		int valueY = height - padding;
		Look look = getLook();
		int color;
		DisplayFont valueFont = getFont(label);
		if (label.isEnabled()) {
			valueFont = getBoldFont(valueFont);
			color = look.getProperty(Look.GET_FOREGROUND_COLOR_FOCUSED);
		} else {
			color = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		}
		g.setFont(valueFont);
		g.setColor(color);
		g.drawString(valueString, valueX, valueY, GraphicsContext.RIGHT | GraphicsContext.BOTTOM);
		int valueWidth = valueFont.stringWidth(valueString);

		drawText(g, label, width - (valueWidth + 2 * padding), height, false, true);

		drawLines(g, label, width, height, false);
	}

	private DisplayFont getBoldFont(DisplayFont displayFont) {
		return DisplayFont.getFont(displayFont.getIdentifiers()[0], displayFont.getHeight(), DisplayFont.STYLE_BOLD);
	}

}
