/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.UpDownRendererContract;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.Combo;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class ComboRenderer extends DefaultWidgetRenderer implements UpDownRendererContract {

	private static final int BUTTON_HEIGHT = 11;
	protected static final int ARROW_SIZE = BUTTON_HEIGHT - 5;
	protected static final int[] UP_ARROW = new int[] { 0, ARROW_SIZE, ARROW_SIZE / 2, 0, ARROW_SIZE, ARROW_SIZE };
	protected static final int[] DOWN_ARROW = new int[] { 0, 0, ARROW_SIZE / 2, ARROW_SIZE, ARROW_SIZE, 0 };

	public ComboRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return Combo.class;
	}

	public int getPreferredContentHeight(Widget widget) {
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		int fontHeight = font.getHeight();
		return fontHeight + (2 * BUTTON_HEIGHT);
	}

	public int getPreferredContentWidth(Widget widget) {
		Combo combo = (Combo) widget;
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		int itemWidth = 0;
		Object[] items = combo.getItems();
		for (int i = items.length; --i >= 0;) {
			itemWidth = Math.max(itemWidth, font.stringWidth(items[i].toString()));
		}
		return itemWidth;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		Combo combo = (Combo) renderable;
		Look look = getLook();
		int width = combo.getWidth();
		int height = combo.getHeight();
		int padding = getPadding();
		int itemHeight = height - (2 * BUTTON_HEIGHT);

		// render buttons
		LookSettings upButtonLookSettings = getButtonLookSettings(look, combo, !combo.isFirstElementSelected(), false);
		drawButton(g, 0, 0, width, BUTTON_HEIGHT, upButtonLookSettings, true);
		LookSettings downButtonLookSettings = getButtonLookSettings(look, combo, !combo.isLastElementSelected(), false);
		drawButton(g, 0, itemHeight + BUTTON_HEIGHT, width, BUTTON_HEIGHT, downButtonLookSettings, false);

		// fill item area
		LookSettings itemLookSettings = getItemLookSettings(look, combo);
		drawItemBackground(g, 0, BUTTON_HEIGHT, width, itemHeight, itemLookSettings);

		// draw selected item text
		g.setColor(itemLookSettings.foregroundColor);
		Object selectedItem = combo.getSelectedItem();
		String text = selectedItem == null ? "" : selectedItem.toString();
		drawer.drawText(g, text, width, height, padding, itemLookSettings, true);

		if (combo.hasFocus()) {
			g.translate(0, BUTTON_HEIGHT);
			drawer.drawOutline(g, width, itemHeight, padding, itemLookSettings);
			g.translate(0, -BUTTON_HEIGHT);
		}
	}

	protected void drawButton(GraphicsContext g, int x, int y, int width, int height, LookSettings lookSettings, boolean isUp) {
		g.translate(x, y);
		drawer.drawHalfBorderedBox(g, width, height, lookSettings, isUp);

		int xShift = (width - ARROW_SIZE) / 2;
		int yShift = (height - ARROW_SIZE) / 2;
		g.translate(xShift, yShift);
		g.setColor(lookSettings.foregroundColor);
		g.fillPolygon(isUp ? UP_ARROW : DOWN_ARROW);
		g.translate(-(x + xShift), -(y + yShift));
	}

	protected void drawItemBackground(GraphicsContext g, int x, int y, int width, int height, LookSettings lookSettings) {
		g.translate(x, y);
		drawer.fillBorderedBox(g, width, height, lookSettings);
		g.setColor(lookSettings.borderColor);
		g.drawVerticalLine(0, 0, height - 1);
		g.drawVerticalLine(width - 1, 0, height - 1);
		g.translate(-x, -y);
	}

	protected LookSettings getButtonLookSettings(Look look, IWidget widget, boolean isEnabled, boolean isPressed) {
		return new DefaultLook(look, widget.isEnabled() && isEnabled, isPressed);
	}

	protected LookSettings getItemLookSettings(Look look, IWidget widget) {
		return new ContentLook(look, widget.isEnabled());
	}

	public int getCommand(Widget widget, int x, int y) {
		int width = widget.getWidth();
		int height = widget.getHeight();

		if (x < 0 || x >= width || y < 0 || y >= height) {
			return NO_OP;
		}

		if (y < BUTTON_HEIGHT) {
			return DECREASE_COMMAND;
		} else if (y >= height - BUTTON_HEIGHT) {
			return INCREASE_COMMAND;
		}
		return UpDownRendererContract.NO_OP;
	}

}
