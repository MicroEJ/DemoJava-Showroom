/*
 * Java
 * Copyright 2011-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.ToggleButton;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

/**
 * This class is used as a super class for all renderers that need to display a text and a element aside the text.
 */
public abstract class BoxButtonRenderer extends LabelRenderer {

	public BoxButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	public int getPreferredContentWidth(Widget widget) {
		int value = super.getPreferredContentWidth(widget);
		// add some extra width for box
		return value + getTextXOffset((ToggleButton) widget);
	}

	protected int getBoxSize(ToggleButton widget) {
		// use the preferred height as the size of the box
		return getPreferredContentHeight(widget);
	}

	protected int getTextXOffset(ToggleButton widget) {
		return getBoxSize(widget) + getPadding();
	}

	public void render(GraphicsContext g, Renderable renderable) {
		ToggleButton widget = (ToggleButton) renderable;
		Look look = getLook();
		int width = widget.getWidth();
		int height = widget.getHeight();

		// get rendering properties
		LookSettings coloring = new ContentLook(look, widget.isEnabled());

		// drawer.fillBackground(g, width, height, coloring);
		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
		g.fillRect(0, 0, width, height);

		int padding = getPadding();
		int boxSize = getBoxSize(widget);

		int yBoxPosition = (height - boxSize) / 2;
		// draw radio
		g.translate(padding, yBoxPosition);
		drawBox(g, renderable, boxSize, coloring);
		g.translate(-padding, -yBoxPosition);

		int xTextPosition = getTextXOffset(widget);
		g.translate(xTextPosition, 0);
		// drawer.drawText(g, widget.getText(), width - boxSize - padding, height, padding, coloring, false);
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		DisplayFont font = look.getFonts()[look.getProperty(LookExtension.GET_MEDIUM_FONT_INDEX)];
		g.setFont(font);
		int offset = (font.getBaselinePosition() - (font.getHeight()) / 2) / 2;
		g.drawString(widget.getText(), padding, height / 2 + offset, GraphicsContext.LEFT
				| GraphicsContext.VCENTER);
		g.translate(-xTextPosition, 0);

		// draw outline
		if (widget.hasFocus()) {
			// here, the check box cannot be disabled
			drawer.drawOutline(g, width, height, padding, coloring);
		}
	}

	protected abstract void drawBox(GraphicsContext g, Renderable renderable, int boxSize, LookSettings coloring);

}
