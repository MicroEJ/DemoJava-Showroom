/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.label.MultiLineLabel;
import com.is2t.mwt.widgets.label.TitleLabel;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class TitleLabelRenderer extends MultiLineLabelRenderer {

	@Override
	public Class<?> getManagedType() {
		return TitleLabel.class;
	}

	@Override
	protected int getPreferredContentHeight(Widget widget, DisplayFont font, String[] lines, int visibleLineCount) {
		return super.getPreferredContentHeight(widget, font, lines, visibleLineCount) + 20;
	}

	@Override
	public int getPadding() {
		return 4;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		MultiLineLabel label = (MultiLineLabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();

		int padding = getPadding();
		g.translate(0, padding);
		drawText(g, label, width, height, true, true);
		g.translate(0, -padding);

		drawLines(g, label, width, height, false);
	}

	@Override
	protected void drawLines(GraphicsContext g, MultiLineLabel label, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		int padding = getPadding();
		if (label.isOverlined()) {
			g.drawHorizontalLine((all ? 0 : padding), 0, width - (all ? 0 : 2 * padding));
		}
		if (label.isUnderlined()) {
			g.drawHorizontalLine((all ? 0 : padding), height - 1, width - (all ? 0 : 2 * padding));
		}
	}

}
