/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.mwt.rendering.WidgetRenderer;

public abstract class DefaultWidgetRenderer extends WidgetRenderer {

	protected final Drawer drawer;

	public DefaultWidgetRenderer(Drawer drawer) {
		this.drawer = drawer;
	}

	/**
	 * Gets the margin for this renderer.
	 * @return margin
	 */
	public int getMargin() {
		return drawer.getMargin();
	}

	/**
	 * Gets the padding for this renderer.
	 * @return padding
	 */
	public int getPadding() {
		return drawer.getPadding();
	}

}
