/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.Picto;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class IconToggleButtonRenderer extends WidgetRenderer {

	@Override
	public int getPreferredContentWidth(Widget widget) {
		IconToggleButton button = (IconToggleButton) widget;
		Picto icon = button.getIcon();
		return icon.getPictoFont(getLook()).charWidth(icon.getPicto());
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		IconToggleButton button = (IconToggleButton) widget;
		Picto icon = button.getIcon();
		return icon.getPictoFont(getLook()).getHeight();
	}

	@Override
	public Class<?> getManagedType() {
		return IconToggleButton.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		IconToggleButton button = (IconToggleButton) renderable;
		int width = button.getWidth();
		int height = button.getHeight();

		Look look = getLook();

		int pictoColor;

		if (button.isSelected()) {
			pictoColor = look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT);
		} else {
			pictoColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		}

		Picto icon = button.getIcon();

		g.setFont(icon.getPictoFont(look));
		g.setColor(pictoColor);
		g.drawChar(icon.getPicto(), width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

}
