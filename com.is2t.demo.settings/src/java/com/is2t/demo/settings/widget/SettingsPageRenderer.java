/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.demo.settings.page.SettingsPage;

import ej.microui.io.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class SettingsPageRenderer extends WidgetRenderer {

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return MWT.NONE;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return MWT.NONE;
	}

	@Override
	public Class<?> getManagedType() {
		return SettingsPage.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		int width = renderable.getWidth();
		int height = renderable.getHeight();
		
		int backgroundColor = getLook().getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
	}
}
