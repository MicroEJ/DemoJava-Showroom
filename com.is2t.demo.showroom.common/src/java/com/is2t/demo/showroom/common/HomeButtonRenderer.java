/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.common;

import com.is2t.demo.utilities.Button;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;

public class HomeButtonRenderer {

	public static final DisplayFont FONT = DisplayFont.getFont(91, 25, DisplayFont.STYLE_PLAIN);
	private static final int BUTTON_FOREGROUND_COLOR = 0xffffff;

	public void paint(GraphicsContext gc, Button button) {
		int width = button.getWidth();
		int height = button.getHeight();

		if (width > 0 && height > 0) {
			gc.translate(button.getX(), button.getY());
			paint(gc, width, height);
			gc.translate(-button.getX(), -button.getY());
		}
	}

	public void paint(GraphicsContext gc, int width, int height) {
		gc.setColor(BUTTON_FOREGROUND_COLOR);
		int x = width / 2;
		int y = height / 2;
		gc.setFont(FONT);
		gc.drawChar(Pictos.Home, x, y, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}
}
