/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.mwt.widgets.LookExtension;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class LinedScaleRenderer extends WidgetRenderer {

	private static final int INTERNAL_MARGIN = 5;
	private static final int BAR_HEIGHT = 3;
	private static final int FULL_COLOR = 0x4da5ea;

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return 200 + 2 * INTERNAL_MARGIN + getPictoFont().charWidth(Pictos.Circle);
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return getPictoFont().getHeight();
	}

	@Override
	public Class<?> getManagedType() {
		return LinedScale.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		LinedScale scale = (LinedScale) renderable;
		int width = scale.getWidth();
		int height = scale.getHeight();
		int preferredWidth = getPreferredContentWidth(scale) - getPictoFont().charWidth(Pictos.Circle) - 2
				* INTERNAL_MARGIN;
		int preferredHeight = BAR_HEIGHT;

		Look look = getLook();
		DisplayFont pictoFont = getPictoFont();
		int backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		int onColor = look.getProperty(Look.GET_FOREGROUND_COLOR_SELECTION);
		int offColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);

		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		int x = (width - preferredWidth) / 2;
		int y = (height - preferredHeight) / 2;

		g.setColor(offColor);
		g.fillRect(x, y, preferredWidth, preferredHeight);

		float fullFactor = Math.abs(scale.getValue() / (float) (scale.getMaxValue() - scale.getMinValue()));
		int fullWidth = (int) (fullFactor * preferredWidth);
		g.setColor(FULL_COLOR);
		g.fillRect(x, y, fullWidth, preferredHeight);
		g.setColor(onColor);
		g.setFont(pictoFont);
		g.drawChar(Pictos.Circle, x + fullWidth, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		drawLines(g, scale, width, height, false);
	}

	// FIXME: duplicated in com.is2t.widgetextension.button.renderer.PictoButtonRenderer
	protected void drawLines(GraphicsContext g, LinedScale scale, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (scale.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (scale.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}

	private DisplayFont getPictoFont() {
		Look look = getLook();
		return look.getFonts()[look.getProperty(LookExtension.GET_SMALL_PICTO_FONT_INDEX)];
	}
}
