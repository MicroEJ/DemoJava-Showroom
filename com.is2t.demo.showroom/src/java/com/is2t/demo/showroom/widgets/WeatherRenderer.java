/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import java.util.Calendar;

import com.is2t.demo.showroom.common.Pictos;
import com.is2t.demo.showroom.weather.Day;
import com.is2t.mwt.util.ColorsHelper;
import com.is2t.mwt.widgets.LookExtension;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class WeatherRenderer extends WidgetRenderer {

	private static final int MILLISECONDS_IN_SECOND = 1000;
	private static final int SECONDS_IN_MINUTE = 60;
	private static final long SECONDS_ON_CLOCK = SECONDS_IN_MINUTE
			* MILLISECONDS_IN_SECOND;
	private static final int PADDING = 20;
	private static final int INNER_PADDING = 5;
	private static final int CLOCK_ARC_COLOR = 0x47b9fe;

	@Override
	public Class<?> getManagedType() {
		return Weather.class;
	}

	@Override
	public int getPadding() {
		return PADDING;
	}

	// @Override
	// public int getMargin() {
	// return 30;
	// }

	@Override
	public int getPreferredContentWidth(Widget widget) {
		Weather weather = (Weather) widget;
		Look look = getLook();
		DisplayFont pictoFont = weather.getFont();
		DisplayFont dayFont = look.getFonts()[look
				.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		DisplayFont temperatureFont = look.getFonts()[look
				.getProperty(Look.GET_FONT_INDEX_FOCUSED)];
		int pictoWidth = pictoFont.charWidth(weather.getCurrentChar());
		Day day = weather.getCurrentDay();
		String dayName = day.getName();
		int dayWidth = dayFont.stringWidth(dayName);
		int temperatureWidth = temperatureFont.stringWidth(day.getTemperature()
				+ "°C");
		return Math.max(Math.max(pictoWidth, dayWidth), temperatureWidth);
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		Weather weather = (Weather) widget;
		Look look = getLook();
		DisplayFont dayFont = look.getFonts()[look
				.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		DisplayFont temperatureFont = look.getFonts()[look
				.getProperty(Look.GET_FONT_INDEX_FOCUSED)];
		DisplayFont pictoFont = weather.getFont();
		return pictoFont.getHeight() + dayFont.getHeight()
				+ temperatureFont.getHeight() + INNER_PADDING * 2;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		Weather weather = (Weather) renderable;
		int width = weather.getWidth();
		int height = weather.getHeight();
		Look look = getLook();

		int level = weather.getLevel();
		float opacityLevel = (float) level / Weather.MAX_LEVEL;
		int backgroundColor = look
				.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		int foregroundColor = look
				.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		int mixedColor = ColorsHelper.getMixColor(backgroundColor,
				foregroundColor, opacityLevel);
		g.setColor(mixedColor);
		int shiftX = (Weather.MAX_LEVEL - level) * width * 3 / 2
				/ Weather.MAX_LEVEL;

		if (weather.isOnboot()) {
			DisplayFont pictoFont = look.getFonts()[look
					.getProperty(LookExtension.GET_BIG_PICTO_FONT_INDEX)];
			g.setFont(pictoFont);
			g.setColor(mixedColor);
			int pictoX = width / 2;
			int pictoY = (int) (((float) level / Weather.MAX_LEVEL) * (height + pictoFont
					.getHeight()));
			g.drawChar(Pictos.IS2T, pictoX, pictoY, GraphicsContext.HCENTER
					| GraphicsContext.BOTTOM);
		} else if (weather.isOnClockMode()) {
			int clockSize = Math.min(width, height) - INNER_PADDING;
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			int millisecond = calendar.get(Calendar.MILLISECOND);

			StringBuilder firstPartSB = new StringBuilder();
			getDigit(hour, firstPartSB);
			if ((second & 0x1) == 0) {
				firstPartSB.append(':');
			} else {
				firstPartSB.append(' ');
			}
			getDigit(minute, firstPartSB);
			String firstPartValue = firstPartSB.toString();
			DisplayFont bigFont = DisplayFont.getFont(DisplayFont.LATIN, 80,
					DisplayFont.STYLE_PLAIN);
			g.setFont(bigFont);
			g.setColor(mixedColor);
			g.drawString(firstPartValue, width / 2 + shiftX, height / 2,
					GraphicsContext.HCENTER | GraphicsContext.VCENTER);

			int mixedArcColor = ColorsHelper.getMixColor(backgroundColor,
					CLOCK_ARC_COLOR, opacityLevel);
			g.setColor(mixedArcColor);
			long totalSeconds = second * MILLISECONDS_IN_SECOND + millisecond;
			int secondAngle = getAngle(totalSeconds, SECONDS_ON_CLOCK);
			int x = shiftX;
			int y = (height - clockSize) / 2;
			g.drawArc(x, y, clockSize, clockSize, 90, -secondAngle);
			g.drawArc(x + 1, y + 1, clockSize - 2, clockSize - 2, 90,
					-secondAngle);
		} else {
			DisplayFont bigFont = look.getFonts()[look
					.getProperty(Look.GET_FONT_INDEX_FOCUSED)];
			int bigFontHeight = bigFont.getHeight();
			DisplayFont pictoFont = weather.getFont();
			DisplayFont dayFont = look.getFonts()[look
					.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
			int pictoFontHeight = pictoFont.getHeight();
			int dayFontHeight = dayFont.getHeight();
			int totalFontHeight = pictoFontHeight + bigFontHeight
					+ dayFontHeight;
			int innerPadding = (height - totalFontHeight) / 4;

			char currentChar = weather.getCurrentChar();
			Day day = weather.getCurrentDay();
			g.setFont(pictoFont);
			int pictoX = width / 2 + shiftX;
			int pictoY = innerPadding;

			g.drawChar(currentChar, pictoX, pictoY, GraphicsContext.HCENTER
					| GraphicsContext.TOP);

			int temperatureX = width / 2 - shiftX;
			int temperatureY = pictoY + pictoFontHeight + innerPadding;
			g.setFont(bigFont);
			g.drawString(Integer.toString(day.getTemperature()) + "°C",
					temperatureX, temperatureY
					/* + (height - pictoFontHeight - dayFontHeight) / 2 */,
					GraphicsContext.HCENTER | GraphicsContext.TOP);

			int dayY = temperatureY + bigFontHeight + innerPadding
					+ (Weather.MAX_LEVEL - level) * (height / 2)
					/ Weather.MAX_LEVEL;
			g.setFont(dayFont);
			g.drawString(day.getName(), width / 2, dayY,
					GraphicsContext.HCENTER | GraphicsContext.TOP);
		}
	}

	private int getAngle(long value, long max) {
		return (int) (value * 360 / max);
	}

	private StringBuilder getDigit(int value, StringBuilder stringBuilder) {
		if (value < 10) {
			stringBuilder.append('0');
		}
		stringBuilder.append(Integer.toString(value));
		return stringBuilder;
	}
}
