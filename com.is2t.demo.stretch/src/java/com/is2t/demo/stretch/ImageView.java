/*
 * Java
 *
 * Copyright 2011-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch;

import com.is2t.demo.showroom.common.HomeButtonRenderer;
import com.is2t.demo.utilities.Button;

import ej.microui.io.GraphicsContext;
import ej.microui.io.View;

public class ImageView extends View {

	private static final int BACKGROUND_COLOR = 0x0;

	public ImageView(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void paint(GraphicsContext g) {
		ImageModel model = (ImageModel) getModel();
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawDeformedImage(model.getImage(), model.getX(), model.getY(), model.getPoints(), GraphicsContext.TOP
				| GraphicsContext.LEFT);

		Button homeButton = model.getHomeButton();
		new HomeButtonRenderer().paint(g, homeButton);
	}
}
