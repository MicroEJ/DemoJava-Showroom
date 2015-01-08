/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.tiny.CheckBox;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class CheckboxRenderer extends WidgetRenderer {

	private static final int BOX_SIZE = 30;
	private static final int INTERNAL_MARGIN = 15;
	
	@Override
	public int getPreferredContentWidth(Widget widget) {
		return Math.max(BOX_SIZE, getPictoFont().charWidth(Pictos.Check)) + 2 * INTERNAL_MARGIN;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return Math.max(BOX_SIZE, getPictoFont().charWidth(Pictos.Check));
	}

	@Override
	public Class<?> getManagedType() {
		return CheckBox.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		CheckBox checkBox = (CheckBox) renderable;
		int width = checkBox.getWidth();
		int height = checkBox.getHeight();
		
		Look look = getLook();
		int backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		int foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		int x = (width - getPreferredContentWidth(checkBox)) / 2 + INTERNAL_MARGIN;
		int y = (height - getPreferredContentHeight(checkBox)) / 2;
		
		g.translate(x, y);
		drawBorder(g, BOX_SIZE, BOX_SIZE, 2, foregroundColor);
		g.translate(-x, -y);
		
		if(checkBox.isSelected()) {
			g.setFont(getPictoFont());
			g.drawChar(Pictos.Check, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}
	}
	
	private void drawBorder(GraphicsContext g, int width, int height, int borderSize, int color) {
		g.setColor(color);

		for (int i = 1; i <= borderSize; i++) {
			g.drawRect(-i, -i, width + 2 * i, height + 2 * i);
		}
	}
	
	private DisplayFont getPictoFont() {
		Look look = getLook();
		return look.getFonts()[look.getProperty(LookExtension.GET_SMALL_PICTO_FONT_INDEX)];
	}
}
