/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner;

import com.is2t.mwt.util.ColorsHelper;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class MultipleSpinnerRenderer extends Renderer {

	@Override
	public int getPadding() {
		return 10;
	}

	@Override
	public Class<?> getManagedType() {
		return MultipleSpinner.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		MultipleSpinner dateSpinner = (MultipleSpinner) renderable;
		int width = dateSpinner.getWidth();
		int height = dateSpinner.getHeight();
		int padding = getPadding();

		Look look = getLook();

		int borderColor = look.getProperty(Look.GET_BORDER_COLOR_DEFAULT);
		int innerColor = ColorsHelper.getMixColor(borderColor, g.readPixel(0, 0), 0.5f);
		g.setColor(innerColor);

		Widget[] children = dateSpinner.getWidgets();
		int childrenLength = children.length;
		int ySpinners = height;
		int heightSpinners = 0;
		int xSpinners = width;
		int widthSpinners = 0;
		for (int i = -1; ++i < childrenLength;) {
			Widget child = children[i];
			int childX = child.getX();
			int childWidth = child.getWidth();

			xSpinners = Math.min(xSpinners, childX);
			widthSpinners += childWidth + 2 * padding;
			ySpinners = Math.min(ySpinners, child.getY());
			heightSpinners = Math.max(heightSpinners, child.getHeight());

			g.drawVerticalLine(childX - padding, ySpinners, heightSpinners);
		}

		// Border.
		g.setColor(borderColor);
		g.drawRect(xSpinners - padding, ySpinners - padding, widthSpinners, heightSpinners + 2 * padding);
	}
}
