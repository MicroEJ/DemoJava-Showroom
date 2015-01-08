/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.util;

import ej.microui.io.GraphicsContext;

public interface Drawer {

	public abstract void fillBackground(GraphicsContext g, int width, int height, LookSettings coloring);

	public abstract void drawText(GraphicsContext g, String text, int width, int height, int padding, LookSettings coloring, boolean content);

	public abstract void drawOutline(GraphicsContext g, int width, int height, int padding, LookSettings coloring);

	public abstract void fillBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring);

	public abstract void drawBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring);

	public abstract void drawHalfBorderedBox(GraphicsContext g, int width, int height, LookSettings coloring,
			boolean upIsRound);

	public abstract void drawBox(GraphicsContext g, int size, boolean isChecked, LookSettings coloring);

	public abstract void drawRadio(GraphicsContext g, int size, boolean isSelected, LookSettings coloring);

	public abstract int getMargin();

	public abstract int getPadding();

}
