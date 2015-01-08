/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button.renderer;

import com.is2t.mwt.widgets.button.ImageButton;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.mwt.MWT;
import ej.mwt.Renderable;
import ej.mwt.Widget;

public class ImageButtonRenderer extends ButtonRenderer {

	public ImageButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	@Override
	public Class<?> getManagedType() {
		return ImageButton.class;
	}

	@Override
	public int getMargin() {
		return 10;
	}

	@Override
	public int getPadding() {
		return MWT.NONE;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		ImageButton button = (ImageButton) widget;
		return button.getNormalImage().getHeight();
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		ImageButton button = (ImageButton) widget;
		return button.getNormalImage().getWidth();
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		ImageButton button = (ImageButton) renderable;
		int width = button.getWidth();
		int height = button.getHeight();
		Image image;

		if (button.isPressed()) {
			image = button.getPressedImage();
		} else {
			image = button.getNormalImage();
		}

		g.drawImage(image, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}
}
