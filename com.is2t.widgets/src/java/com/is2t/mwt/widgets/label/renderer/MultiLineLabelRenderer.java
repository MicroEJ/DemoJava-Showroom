/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.label.MultiLineLabel;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

/**
 * Renders a {@link MultiLineLabel}.
 */
public class MultiLineLabelRenderer extends Renderer {

	@Override
	public Class<?> getManagedType() {
		return MultiLineLabel.class;
	}

	@Override
	public int getMargin() {
		return 0;
	}

	@Override
	public int getPadding() {
		return 15;
	}

	protected int getPreferredContentWidth(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		int maxLineWidth = 0;
		for (int i = -1; ++i < visibleLineCount;) {
			int lineWidth = font.stringWidth(lines[i]);
			maxLineWidth = Math.max(lineWidth, maxLineWidth);
		}
		return maxLineWidth;
	}

	protected int getPreferredContentHeight(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		return font.getHeight() * visibleLineCount;
	}

	public void validate(Widget widget, int widthHint, int heightHint) {
		MultiLineLabel label = (MultiLineLabel) widget;
		int doublePadding = getPadding() << 1;
		DisplayFont font = getFont(label);

		String[] lines = label.getLines();
		int visibleLineCount = getVisibleLineCount(lines.length, label.getMaxLineCount());

		int width = getPreferredContentWidth(widget, font, lines, visibleLineCount) + doublePadding;
		int height = getPreferredContentHeight(widget, font, lines, visibleLineCount) + doublePadding;

		widget.setPreferredSize(widthHint == MWT.NONE ? width : Math.min(widthHint, width),
				heightHint == MWT.NONE ? height : Math.min(heightHint, height));
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		MultiLineLabel label = (MultiLineLabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();

		drawText(g, label, width, height, false, false);

		drawLines(g, label, width, height, false);
	}

	// FIXME: duplicated in com.is2t.widgetextension.button.renderer.PictoButtonRenderer
	protected void drawLines(GraphicsContext g, MultiLineLabel label, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (label.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (label.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}

	protected void drawText(GraphicsContext g, MultiLineLabel label, int width, int height, boolean hcentered,
			boolean vcentered) {
		int padding = getPadding();
		DisplayFont font = getFont(label);
		String[] lines = label.getLines();
		int fontHeight = font.getHeight();
		int visibleLineCount = getVisibleLineCount(lines.length, label.getMaxLineCount());
		int textX = hcentered ? width / 2 : padding;
		int textY = vcentered ? (height - fontHeight * visibleLineCount) / 2 : padding;
		Look look = getLook();
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		g.setFont(font);

		for (int i = 0; i < visibleLineCount; i++) {
			g.drawString(lines[i], textX, textY, (hcentered ? GraphicsContext.HCENTER : GraphicsContext.LEFT)
					| GraphicsContext.TOP);
			textY += fontHeight;
		}
	}

	protected DisplayFont getFont(MultiLineLabel label) {
		Look look = getLook();
		int fontIndex = look.getProperty(label.getFontSize());
		return look.getFonts()[fontIndex];
	}

	protected int getVisibleLineCount(int lineCount, int maxLineCount) {
		return maxLineCount < 0 ? lineCount : Math.min(maxLineCount, lineCount);
	}
}
