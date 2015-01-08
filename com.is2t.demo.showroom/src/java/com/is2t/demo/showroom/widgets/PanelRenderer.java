/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.demo.utilities.mosaic.BackgroundImageHelper;
import com.is2t.demo.utilities.mosaic.RatioMosaic;

import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.mwt.Panel;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class PanelRenderer extends Renderer {

	private final BackgroundImageHelper backgroundImageHelper;

	public PanelRenderer(Display display) {
		this.backgroundImageHelper = new BackgroundImageHelper(display, new RatioMosaic(
				new float[] { 0, -0.66f, -0.33f }), "background", "background_tile");
	}

	@Override
	public Class<?> getManagedType() {
		return Panel.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		this.backgroundImageHelper.drawBackground(g, renderable.getWidth(), renderable.getHeight(), getLook()
				.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
	}

}
