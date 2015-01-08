/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.renderer;

import com.is2t.mwt.util.ColorsHelper;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.spinner.Wheel;

import ej.microui.Colors;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class WheelRenderer extends WidgetRenderer {

	private static final int START_GRADIENT_COLOR = 0x969aa2;
	private static final int END_GRADIENT_COLOR = Colors.BLACK;
	private static final int[] GRADIENT = ColorsHelper.getGradient(START_GRADIENT_COLOR, END_GRADIENT_COLOR);

	@Override
	public Class<?> getManagedType() {
		return Wheel.class;
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		Wheel wheel = (Wheel) widget;
		DisplayFont font = getBiggerFont();
		int minValue = font.stringWidth(wheel.getStringValue(wheel.getMinimumValue()));
		int maxValue = font.stringWidth(wheel.getStringValue(wheel.getMaximumValue()));
		int maxAbs = Math.max(minValue, maxValue);
		return (int) (maxAbs + font.stringWidth(wheel.getUnit())
				* (wheel.isHorizontal() ? wheel.getVisibleItemsCount() : 1) * 0.75f);
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return (int) (((Wheel) widget).getVisibleItemsCount() * getBiggerFont().getHeight() * 0.75f);
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Wheel wheel = (Wheel) renderable;
		int width = wheel.getWidth();
		int height = wheel.getHeight();
		int spinOffset = wheel.getSpinOffset();
		int visibleItems = wheel.getVisibleItemsCount();
		int maxVisibleItems = visibleItems + 2;
		int windowOffset = ((maxVisibleItems - visibleItems) / 2);

		String unit = wheel.getUnit();
		String[] window = wheel.getWindow(maxVisibleItems);
		DisplayFont font = getBiggerFont();
		g.setFont(font);

		if (wheel.isHorizontal()) {
			int valueWidth = width / visibleItems;
			int y = height / 2;

			int x = spinOffset - windowOffset * valueWidth + valueWidth / 2;
			for (int i = 0; i < maxVisibleItems; i++) {
				// we draw only visible elements
				if (x > -valueWidth && x < width + valueWidth) {
					if(window[i] != null){
						float fontRatio = computeFontRatio(x, width);
						font.setRatios(fontRatio, fontRatio);
						int color = GRADIENT[(int) (fontRatio * (GRADIENT.length - 1))];
						g.setColor(color);
						g.drawString(window[i] + unit, x, y, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}
				}

				x += valueWidth;
			}
		} else {
			int valueHeight = height / visibleItems;
			int x = width / 2;

			int y = spinOffset - windowOffset * valueHeight + valueHeight / 2;
			for (int i = 0; i < maxVisibleItems; i++) {
				// we draw only visible elements
				if (y > -valueHeight && y < height + valueHeight) {
					if(window[i] != null){
						float fontRatio = computeFontRatio(y, height);
						font.setRatios(fontRatio, fontRatio);
						int color = GRADIENT[(int) (fontRatio * (GRADIENT.length - 1))];
						g.setColor(color);
						g.drawString(window[i] + unit, x, y, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}
				}

				y += valueHeight;
			}
		}
		font.resetRatios();
	}

	private float computeFontRatio(int y, int height) {
		int distance = Math.abs(y - height / 2);
		return 1.0f - (float) distance / height;
	}

	protected DisplayFont getBiggerFont() {
		Look look = getLook();
		int fontIndex = look.getProperty(LookExtension.GET_MEDIUM_FONT_INDEX);
		return look.getFonts()[fontIndex];
	}

}
