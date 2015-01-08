/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class FillerRenderer extends WidgetRenderer {

	@Override
	public int getPreferredContentWidth(Widget widget) {
		Filler filler = (Filler) widget;
		return filler.getPreferredWidth();
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		Filler filler = (Filler) widget;
		return filler.getPreferredHeight();
	}

	@Override
	public Class<?> getManagedType() {
		return Filler.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Filler filler = (Filler) renderable;
		int width = filler.getWidth();
		int height = filler.getHeight();
		drawLines(g, filler, width, height, false);

	}

	// FIXME: duplicated in com.is2t.widgetextension.button.renderer.PictoButtonRenderer
	protected void drawLines(GraphicsContext g, Filler filler, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (filler.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (filler.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}

}
