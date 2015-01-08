/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button.renderer;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.button.Button;
import com.is2t.mwt.widgets.renderers.LabelRenderer;
import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;

public class ButtonRenderer extends LabelRenderer {

	public ButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	@Override
	public Class<?> getManagedType() {
		return Button.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Button button = (Button) renderable;
		Look look = getLook();
		int padding = getPadding();
		int width = button.getWidth();
		int height = button.getHeight();

		// get rendering properties
		LookSettings coloring = getLookSettings(look, button);

		drawer.drawBorderedBox(g, width, height, coloring);

		// draws an outline dotted border to figure out the focus state.
		if (button.hasFocus()) {
			drawer.drawOutline(g, width, height, getPadding(), coloring);
		}
		drawer.drawText(g, button.getText(), width, height, padding, coloring, true);
	}

	@Override
	protected LookSettings getLookSettings(Look look, IWidget widget) {
		Button button = (Button) widget;
		return new DefaultLook(look, button.isEnabled(), button.isPressed());
	}

}


