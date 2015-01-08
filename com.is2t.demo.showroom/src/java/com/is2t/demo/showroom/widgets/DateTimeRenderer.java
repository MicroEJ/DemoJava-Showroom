/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import java.util.Calendar;

import com.is2t.mwt.util.ColorsHelper;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;

public class DateTimeRenderer extends WidgetRenderer {

	private static final String TIME_PATTERN = "00:00";
	private static final String DATE_PATTERN = "December 27, 2014";
	private static final int TIME_MARGIN = 4;

	@Override
	public int getMargin() {
		return TIME_MARGIN;
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_CONTENT)];
		return Math.max(font.stringWidth(TIME_PATTERN), font.stringWidth(DATE_PATTERN));
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_CONTENT)];
		return font.getHeight();
	}

	@Override
	public Class<?> getManagedType() {
		return DateTime.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		DateTime dateTime = (DateTime) renderable;
		int width = dateTime.getWidth();
		int height = dateTime.getHeight();

		Look look = getLook();
		int level = dateTime.getLevel();
		float opacityLevel = (float) level / DateTime.MAX_LEVEL;
		int backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		int foregroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		int mixedColor = ColorsHelper.getMixColor(backgroundColor, foregroundColor, opacityLevel);
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_CONTENT)];
		g.setColor(mixedColor);
		g.setFont(font);

		String text;

		if (dateTime.isTimeVisible()) {
			text = formatTime(Calendar.getInstance());
		} else {
			text = formatDate(Calendar.getInstance());
		}

		int y = (int) (((float) level / DateTime.MAX_LEVEL) * ((height + font.getHeight()) / 2));
		g.drawString(text, width / 2, y, GraphicsContext.HCENTER | GraphicsContext.BOTTOM);
	}

	private String formatTime(Calendar calendar) {
		StringBuffer time = new StringBuffer();
		int numberOfDigitMin = 2;
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		char separator = (second & 0x1) == 0 ? ':' : ' ';

		time.append(formatInteger(hourOfDay, numberOfDigitMin));
		time.append(separator);
		time.append(formatInteger(minutes, numberOfDigitMin));
		return time.toString();
	}

	private String formatDate(Calendar calendar) {
		StringBuffer date = new StringBuffer();
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		date.append(monthToString(month));
		date.append(' ');
		date.append(day);
		date.append(", ");
		date.append(year);
		return date.toString();
	}

	private String monthToString(int month) {
		switch (month) {
		case Calendar.JANUARY:
			return "January";
		case Calendar.FEBRUARY:
			return "February";
		case Calendar.MARCH:
			return "March";
		case Calendar.APRIL:
			return "April";
		case Calendar.MAY:
			return "May";
		case Calendar.JUNE:
			return "June";
		case Calendar.JULY:
			return "July";
		case Calendar.AUGUST:
			return "August";
		case Calendar.SEPTEMBER:
			return "September";
		case Calendar.OCTOBER:
			return "October";
		case Calendar.NOVEMBER:
			return "November";
		case Calendar.DECEMBER:
			return "December";

		default:
			throw new IllegalArgumentException();
		}
	}

	private String formatInteger(int value, int numberOfDigitMin) {
		StringBuffer formattedInteger = new StringBuffer();

		if (value < 0) {
			formattedInteger.append('-');
		}

		String stringValue = String.valueOf(Math.abs(value)); // To remove the sign already took account.
		int numberOfDigitToAdd = Math.max(numberOfDigitMin - stringValue.length(), 0);

		for (int i = 0; i < numberOfDigitToAdd; i++) {
			formattedInteger.append('0');
		}

		formattedInteger.append(stringValue);
		return formattedInteger.toString();
	}

}
