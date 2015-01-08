/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class ActivityLauncherRenderer extends WidgetRenderer {

	private static final float TWO_PI = (float) (2 * Math.PI);
	private static final float ANGLE_RADIAN_INCREMENT = (float) (TWO_PI / ActivityLauncher.DOTS_COUNT);

	@Override
	public Class<ActivityLauncher> getManagedType() {
		return ActivityLauncher.class;
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		ActivityLauncher picto = (ActivityLauncher) widget;
		Look look = getLook();
		DisplayFont messageFont = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		DisplayFont pictoFont = picto.getFont();
		return messageFont.getHeight() + pictoFont.getHeight() * 2;
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		ActivityLauncher picto = (ActivityLauncher) widget;
		Look look = getLook();
		char c = picto.getPicto();
		DisplayFont messageFont = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		DisplayFont pictoFont = picto.getFont();
		return Math.max(pictoFont.charWidth(c), messageFont.stringWidth(picto.getName()));
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		ActivityLauncher activityLauncher = (ActivityLauncher) renderable;
		Look look = getLook();
		int width = activityLauncher.getWidth();
		int height = activityLauncher.getHeight();
		int centerX = width / 2;
		int centerY = height / 2;

		DisplayFont messageFont = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		DisplayFont pictoFont = activityLauncher.getFont();
		int pictoHeight = pictoFont.getHeight();
		int messageHeight = messageFont.getHeight();

		int radius = pictoHeight;
		float offset = activityLauncher.getOffset();
		boolean rotating = activityLauncher.isRotating();
		int enlightenCount = activityLauncher.getEnlightenCount();
		int pictoCenterY = centerY - messageHeight / 2;
		
		int i = 0;
		for (float angle = 0; angle + (ANGLE_RADIAN_INCREMENT / 2) < TWO_PI; angle += ANGLE_RADIAN_INCREMENT) {
			float angleWithOffset = angle + offset;
			int smallCircleX = (int) (radius * Math.cos(angleWithOffset));
			int smallCircleY = (int) (radius * Math.sin(angleWithOffset));

			if (rotating && i <= enlightenCount) {
				drawBlueSmallCircle(g, centerX + smallCircleX, pictoCenterY + smallCircleY);
			} else {
				drawBlackSmallCircle(g, centerX + smallCircleX, pictoCenterY + smallCircleY);
			}
			i++;
		}

		g.setFont(pictoFont);
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		g.drawChar(activityLauncher.getPicto(), centerX, pictoCenterY, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);

		g.setFont(messageFont);
		g.drawString(activityLauncher.getName(), centerX, centerY + pictoHeight * 3 / 4, GraphicsContext.HCENTER
				| GraphicsContext.TOP);
	}

	private void drawBlackSmallCircle(GraphicsContext g, int x, int y) {
		g.setColor(0x1b1b1b);
		g.drawPixel(x - 1, y - 1);
		g.setColor(0x020202);
		g.drawPixel(x, y - 1);
		g.setColor(0x1b1b1b);
		g.drawPixel(x + 1, y - 1);

		g.setColor(0x151515);
		g.drawPixel(x - 1, y);
		g.setColor(0x0);
		g.drawPixel(x, y);
		g.setColor(0x151515);
		g.drawPixel(x + 1, y);

		g.setColor(0x303030);
		g.drawPixel(x - 1, y + 1);
		g.setColor(0x464646);
		g.drawPixel(x, y + 1);
		g.setColor(0x303030);
		g.drawPixel(x + 1, y + 1);
	}

	private void drawBlueSmallCircle(GraphicsContext g, int x, int y) {
		g.setColor(0x2d6689);
		g.drawPixel(x - 1, y - 1);
		g.setColor(0x359fdf);
		g.drawPixel(x, y - 1);
		g.setColor(0x29495d);
		g.drawPixel(x + 1, y - 1);

		g.setColor(0x44c4ff);
		g.drawPixel(x - 1, y);
		g.setColor(0x47b9fe);
		g.drawPixel(x, y);
		g.setColor(0x3a9fdc);
		g.drawPixel(x + 1, y);

		g.setColor(0x5f94b4);
		g.drawPixel(x - 1, y + 1);
		g.setColor(0xb9eeff);
		g.drawPixel(x, y + 1);
		g.setColor(0x38627c);
		g.drawPixel(x + 1, y + 1);
	}

	@Override
	public int getPadding() {
		return super.getPadding() / 2;
	}

}
