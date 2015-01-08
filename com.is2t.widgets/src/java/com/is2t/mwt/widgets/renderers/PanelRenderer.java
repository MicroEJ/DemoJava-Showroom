/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import ej.microui.io.GraphicsContext;
import ej.mwt.Panel;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class PanelRenderer extends Renderer { // /!\ a Panel is not a Widget but we can share the same behavior

	public Class getManagedType() {
		return Panel.class;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		int width = renderable.getWidth();
		int height = renderable.getHeight();
		int color = getLook().getProperty(Look.GET_BACKGROUND_COLOR_CONTENT);

		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}

}
