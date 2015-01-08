/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.mwt.Dialog;
import ej.mwt.Renderable;
import ej.mwt.rendering.Renderer;

public class DialogRenderer extends Renderer {

	private final Drawer drawer;

	public DialogRenderer(Drawer drawer) {
		this.drawer = drawer;
	}

	public Class getManagedType() {
		return Dialog.class;
	}

	public int getPadding() {
		return super.getMargin() + 5; // FIXME constant
	}

	public int getMargin() {
		return Display.getDefaultDisplay().getWidth() / 5;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		// get rendering properties
		LookSettings coloring = new DefaultLook(getLook(), false, false);
		int width = renderable.getWidth();
		int height = renderable.getHeight();
		drawer.drawBorderedBox(g, width, height, coloring);
	}

}
