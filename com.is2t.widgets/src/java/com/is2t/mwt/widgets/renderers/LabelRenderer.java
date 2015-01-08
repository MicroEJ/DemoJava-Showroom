/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.ILabel;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class LabelRenderer extends DefaultWidgetRenderer {

	public LabelRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return ILabel.class;
	}

	public int getPreferredContentHeight(Widget widget) {
		ILabel label = (ILabel) widget;
		return getLookSettings(getLook(), label).font.getHeight();
	}

	public int getPreferredContentWidth(Widget widget) {
		ILabel label = (ILabel) widget;
		String text = label.getText();
		DisplayFont font = getLookSettings(getLook(), label).font;
		return font.stringWidth(text);
	}

	public void render(GraphicsContext g, Renderable renderable) {
		ILabel label = (ILabel) renderable;
		int width = label.getWidth();
		int height = label.getHeight();
		Look look = getLook();

		// get rendering properties
		LookSettings coloring = getLookSettings(look, label);
		drawer.fillBackground(g, width, height, coloring);
		String text = label.getText();
		int padding = getPadding();
		drawer.drawText(g, text, width, height, padding, coloring, false);
	}

	protected LookSettings getLookSettings(Look look, IWidget button) {
		return new ContentLook(look, true);
	}

}
