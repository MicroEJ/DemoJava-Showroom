/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.scroll;

import com.is2t.mwt.util.ColorsHelper;

import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class ScrollBarRenderer extends WidgetRenderer {

	private static final int PERCENT_RATIO = 100;
	private static final float MIX_LEVEL_FACTOR = 0.6f;
	private static final int MAX_STEPS = 20000;
	private static final int SIZE = 3;
	private static final int MIN_SIZE = 10 * SIZE;

	@Override
	public int getPadding() {
		return SIZE;
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return SIZE;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return SIZE;
	}

	@Override
	public Class<?> getManagedType() {
		return Scrollbar.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Scrollbar scrollbar = (Scrollbar) renderable;
		int visibilityLevel = scrollbar.getVisibilityLevel();

		if ((!scrollbar.isVisible()) || (visibilityLevel == 0)) {
			// nothing to paint
			return;
		}

		int renderableWidth = renderable.getWidth();
		int renderableHeight = renderable.getHeight();
		int padding = getPadding();

		int currentStep = scrollbar.getCurrentStep();
		int maxStep = scrollbar.getMaxStep();

		boolean horizontal = scrollbar.isHorizontal();
		int barX;
		int barY;
		int barWidth;
		int barHeight;
		if (horizontal) {
			int maxSize = renderableWidth - 2 * padding;
			barWidth = maxSize - MIN_SIZE - maxStep * (maxSize - 2 * MIN_SIZE) / MAX_STEPS;
			barX = padding + currentStep * (renderableWidth - barWidth) / maxStep;
			barHeight = SIZE;
			barY = (renderableHeight - barHeight) / 2;
		} else {
			int maxHeight = renderableHeight - 2 * padding;
			barHeight = maxHeight - MIN_SIZE - maxStep * (maxHeight - 2 * MIN_SIZE) / MAX_STEPS;
			barY = padding + currentStep * (maxHeight - barHeight) / maxStep;
			barWidth = SIZE;
			barX = (renderableWidth - barWidth) / 2;
		}

		Display display = g.getDisplay();
		float mixLevel = (float) visibilityLevel / PERCENT_RATIO;
		boolean maxVisibility = visibilityLevel == PERCENT_RATIO;
		int borderColor = getLook().getProperty(Look.GET_BORDER_COLOR_DEFAULT);
		if (horizontal) {
			if (maxVisibility) {
				int leftPixelColor = readPixel(display, g, barX, barY + barHeight / 2);
				int rightPixelColor = readPixel(display, g, barX + barWidth - 1, barY + barHeight / 2);
				g.setColor(borderColor);
				g.fillRect(barX, barY + 1, barWidth, barHeight - 2);
				g.drawHorizontalLine(barX + 1, barY, barWidth - 3);
				g.drawHorizontalLine(barX + 1, barY + barHeight - 1, barWidth - 3);
				int color = ColorsHelper.getMixColor(leftPixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
				g.setColor(color);
				g.drawPixel(barX, barY);
				g.drawPixel(barX, barY + barHeight - 1);
				color = ColorsHelper.getMixColor(rightPixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
				g.setColor(color);
				g.drawPixel(barX + barWidth - 1, barY);
				g.drawPixel(barX + barWidth - 1, barY + barHeight - 1);
			} else {
				for (int i = -1; ++i < barWidth;) {
					int currentX = barX + i;
					int pixelColor = readPixel(display, g, currentX, barY + barHeight / 2);
					int color = ColorsHelper.getMixColor(pixelColor, borderColor, mixLevel);
					g.setColor(color);
					g.drawVerticalLine(currentX, barY, barHeight - 1);
					if (i == 0 || i == barWidth - 1) {
						color = ColorsHelper.getMixColor(pixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
						g.setColor(color);
						g.drawPixel(currentX, barY);
						g.drawPixel(currentX, barY + barHeight - 1);
					}
				}
			}
		} else {
			if (maxVisibility) {
				int topPixelColor = readPixel(display, g, barX + barWidth / 2, barY);
				int bottomPixelColor = readPixel(display, g, barX + barWidth / 2, barY + barHeight - 1);
				g.setColor(borderColor);
				g.fillRect(barX + 1, barY, barWidth - 2, barHeight);
				g.drawVerticalLine(barX, barY + 1, barHeight - 3);
				g.drawVerticalLine(barX + barWidth - 1, barY + 1, barHeight - 3);
				int color = ColorsHelper.getMixColor(topPixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
				g.setColor(color);
				g.drawPixel(barX, barY);
				g.drawPixel(barX + barWidth - 1, barY);
				color = ColorsHelper.getMixColor(bottomPixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
				g.setColor(color);
				g.drawPixel(barX, barY + barHeight - 1);
				g.drawPixel(barX + barWidth - 1, barY + barHeight - 1);
			} else {
				for (int i = -1; ++i < barHeight;) {
					int currentY = barY + i;
					int pixelColor = readPixel(display, g, barX + barWidth / 2, currentY);
					int color = ColorsHelper.getMixColor(pixelColor, borderColor, mixLevel);
					g.setColor(color);
					g.drawHorizontalLine(barX, currentY, barWidth - 1);
					if (i == 0 || i == barHeight - 1) {
						color = ColorsHelper.getMixColor(pixelColor, borderColor, mixLevel * MIX_LEVEL_FACTOR);
						g.setColor(color);
						g.drawPixel(barX, currentY);
						g.drawPixel(barX + barWidth - 1, currentY);
					}
				}
			}
		}
	}

	private int readPixel(Display display, GraphicsContext g, int x, int y) {
		if (x + g.getTranslateX() >= display.getWidth() || x + g.getTranslateX() < 0
				|| y + g.getTranslateY() >= display.getHeight() || y + g.getTranslateY() < 0) {
			return 0x0;
		}
		// try {
		return g.readPixel(x, y);
		// } catch (Throwable e) {
		// System.out.println("x=" + x);
		// System.out.println("y=" + y);
		// System.out.println("g.getClipX=" + g.getClipX());
		// System.out.println("g.getClipY=" + g.getClipY());
		// System.out.println("g.getClipWidth=" + g.getClipWidth());
		// System.out.println("g.getClipHeight=" + g.getClipHeight());
		// System.out.println("g.getTranslateX=" + g.getTranslateX());
		// System.out.println("g.getTranslateY=" + g.getTranslateY());
		// System.out.println("display.getWidth=" + display.getWidth());
		// System.out.println("display.getHeight=" + display.getHeight());
		// return 0x0;
		// }
	}

}
