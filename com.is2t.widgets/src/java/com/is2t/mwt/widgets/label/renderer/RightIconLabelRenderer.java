/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.label.RightIconLabel;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class RightIconLabelRenderer extends MultiLineLabelRenderer {

	@Override
	public Class<?> getManagedType() {
		return RightIconLabel.class;
	}

	@Override
	protected int getPreferredContentWidth(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		RightIconLabel label = (RightIconLabel) widget;
		Picto icon = label.getIcon();
		int iconWidth = icon == null ? 0 : icon.getPictoFont(getLook()).charWidth(icon.getPicto());
		return iconWidth + super.getPreferredContentWidth(widget, font, lines, visibleLineCount);
	}

	@Override
	protected int getPreferredContentHeight(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		RightIconLabel label = (RightIconLabel) widget;
		Picto icon = label.getIcon();
		int iconHeight = icon == null ? 0 : icon.getPictoFont(getLook()).getHeight();
		return Math.max(iconHeight, super.getPreferredContentHeight(widget, font, lines, visibleLineCount));
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		RightIconLabel label = (RightIconLabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();
		int padding = getPadding();

		// Icon centered vertically at the left.
		int pictoWidth;
		Picto icon = label.getIcon();
		if (icon != null) {
			int iconX = width - padding / 2;
			int iconY = height / 2;
			Look look = getLook();
			DisplayFont pictoFont = icon.getPictoFont(look);
			g.setFont(pictoFont);
			g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
			g.drawChar(icon.getPicto(), iconX, iconY, GraphicsContext.RIGHT | GraphicsContext.VCENTER);
			pictoWidth = pictoFont.charWidth(icon.getPicto());
		} else {
			pictoWidth = 0;
		}

		int xText = padding / 2;
		g.translate(xText, 0);
		drawText(g, label, width - xText, height, false, true);
		g.translate(-xText, 0);

		drawLines(g, label, width, height, true);
	}

}
