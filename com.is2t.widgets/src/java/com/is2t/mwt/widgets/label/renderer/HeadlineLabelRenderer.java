/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.label.HeadlineLabel;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class HeadlineLabelRenderer extends MultiLineLabelRenderer {

	private static final int INTERNAL_MARGIN = 0;

	@Override
	public Class<?> getManagedType() {
		return HeadlineLabel.class;
	}

	@Override
	public int getPadding() {
		return 11;
	}

	@Override
	protected int getPreferredContentWidth(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		HeadlineLabel label = (HeadlineLabel) widget;
		return Math.max(super.getPreferredContentWidth(widget, font, lines, visibleLineCount), super
				.getPreferredContentWidth(widget, getHeadlineFont(label), label.getHeadlineLines(), visibleLineCount));
	}

	@Override
	protected int getPreferredContentHeight(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		HeadlineLabel label = (HeadlineLabel) widget;
		String[] headlines = label.getHeadlineLines();
		int visibleHeadLineCount = getVisibleLineCount(headlines.length, label.getMaxLineCount());
		int headlineHeight = getHeadlineFont(label).getHeight() * visibleHeadLineCount;
		int textHeight = super.getPreferredContentHeight(widget, font, lines, visibleLineCount);
		return headlineHeight + INTERNAL_MARGIN + textHeight;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		HeadlineLabel label = (HeadlineLabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();

		int maxLineCount = label.getMaxLineCount();

		Look look = getLook();

		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT));
		g.fillRect(0, 0, width, height);

		// Headline on the top left aligned.
		DisplayFont font = getHeadlineFont(label);
		int fontHeight = font.getHeight();
		String[] lines = label.getHeadlineLines();
		int visibleLineCount = getVisibleLineCount(lines.length, maxLineCount);
		int padding = getPadding();
		int textX = padding;
		int textY = padding;

		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_CONTENT));
		g.setFont(font);

		for (int i = 0; i < visibleLineCount; i++) {
			g.drawString(lines[i], textX, textY, GraphicsContext.LEFT | GraphicsContext.TOP);
			textY += fontHeight;
		}

		int yText = textY + INTERNAL_MARGIN;
		g.translate(0, yText);
		drawText(g, label, width, height - yText, false, false);
		g.translate(0, -yText);

		drawLines(g, label, width, height, true);
	}

	public DisplayFont getHeadlineFont(HeadlineLabel label) {
		Look look = getLook();
		int fontIndex = look.getProperty(label.getHeadlineFontSize());
		return look.getFonts()[fontIndex];
	}
}
