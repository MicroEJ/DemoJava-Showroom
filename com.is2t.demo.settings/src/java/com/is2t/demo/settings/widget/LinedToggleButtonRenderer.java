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

public class LinedToggleButtonRenderer extends WidgetRenderer {

	private static final String ON = "ON";
	private static final String OFF = "OFF";
	private static final int INTERNAL_MARGIN = 7;

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return getPictoFont().charWidth(Pictos.SwithOn) + 2 * INTERNAL_MARGIN;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return getPictoFont().getHeight();
	}

	@Override
	public Class<?> getManagedType() {
		return LinedToggleButton.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		LinedToggleButton toggleButton = (LinedToggleButton) renderable;
		int width = toggleButton.getWidth();
		int height = toggleButton.getHeight();

		Look look = getLook();
		int backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);

		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		if (toggleButton.isSelected()) {
			g.setFont(getPictoFont());
			g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT));
			g.drawChar(Pictos.SwithOn, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setColor(backgroundColor);
			g.setFont(getFont());
			g.drawString(ON, width / 2, height / 2, GraphicsContext.RIGHT | GraphicsContext.VCENTER);
		} else {
			g.setFont(getPictoFont());
			g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED));
			g.drawChar(Pictos.SwtichOff, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setFont(getFont());
			g.drawString(OFF, width / 2, height / 2, GraphicsContext.LEFT | GraphicsContext.VCENTER);
		}

		drawLines(g, toggleButton, width, height, false);
	}

	// FIXME: duplicated in com.is2t.widgetextension.button.renderer.PictoButtonRenderer
	protected void drawLines(GraphicsContext g, LinedToggleButton toggleButton, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (toggleButton.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (toggleButton.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}

	private DisplayFont getFont() {
		Look look = getLook();
		return look.getFonts()[look.getProperty(LookExtension.GET_SMALL_FONT_INDEX)];
	}

	private DisplayFont getPictoFont() {
		Look look = getLook();
		return look.getFonts()[look.getProperty(LookExtension.GET_SMALL_PICTO_FONT_INDEX)];
	}
}
