/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button.renderer;

import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.button.PictoButton;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class PictoButtonRenderer extends ButtonRenderer {

	public PictoButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	@Override
	public Class<?> getManagedType() {
		return PictoButton.class;
	}

	@Override
	public int getMargin() {
		return 0;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		PictoButton pictoButton = (PictoButton) widget;
		return pictoButton.getPicto().getPictoFont(getLook()).getHeight();
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		PictoButton pictoButton = (PictoButton) widget;
		Picto picto = pictoButton.getPicto();
		return picto.getPictoFont(getLook()).charWidth(picto.getPicto());
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		PictoButton pictoButton = (PictoButton) renderable;
		int width = pictoButton.getWidth();
		int height = pictoButton.getHeight();

		Look look = getLook();
		Picto picto = pictoButton.getPicto();

		// Centered vertically and horizontally.
		g.setFont(picto.getPictoFont(look));
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawChar(picto.getPicto(), width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		drawLines(g, pictoButton, width, height, true);
	}

	// FIXME: duplicated in com.is2t.widgetextension.label.renderer.MultiLineLabelRenderer
	protected void drawLines(GraphicsContext g, PictoButton pictoButton, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (pictoButton.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (pictoButton.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}
}
