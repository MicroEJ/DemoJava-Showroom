/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.label.TopIconLabel;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class TopIconLabelRenderer extends MultiLineLabelRenderer {

	@Override
	public Class<?> getManagedType() {
		return TopIconLabel.class;
	}

	@Override
	public int getPadding() {
		return super.getPadding() - 2;
	}

	@Override
	protected int getPreferredContentWidth(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		TopIconLabel label = (TopIconLabel) widget;
		Picto icon = label.getIcon();
		int iconWidth = icon.getPictoFont(getLook()).charWidth(icon.getPicto());
		return iconWidth; // all top icon labels have the same width if the picto font is monospace
		// return Math.max(iconWidth, super.getPreferredContentWidth(widget, font, lines, visibleLineCount));
	}

	@Override
	protected int getPreferredContentHeight(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		TopIconLabel label = (TopIconLabel) widget;
		int iconHeight = label.getIcon().getPictoFont(getLook()).getHeight();
		return iconHeight + super.getPreferredContentHeight(widget, font, lines, visibleLineCount);
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		TopIconLabel label = (TopIconLabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();

		// Icon horizontally centered on the top.
		Picto icon = label.getIcon();
		Look look = getLook();
		DisplayFont pictoFont = icon.getPictoFont(look);
		g.setFont(pictoFont);

		int color = getColor(label, look);

		g.setColor(color);
		g.drawChar(icon.getPicto(), width / 2, getPadding(), GraphicsContext.HCENTER | GraphicsContext.TOP);

		int pictoHeight = pictoFont.getHeight();
		g.translate(0, pictoHeight);
		drawText(g, label, width, height - pictoHeight, true, false);

		drawLines(g, label, width, height, true);

		g.translate(0, -pictoHeight);
	}

	protected int getColor(TopIconLabel label, Look look) {
		return label.isEnabled() ? look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT) : look
				.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);
	}

}
