/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import java.util.Date;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.contracts.ICalendarRendering;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.tiny.SimpleCalendar;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class CalendarRenderer extends DefaultWidgetRenderer implements ICalendarRendering {

	protected static final int INTERNAL_PADDING = 2; // white space around text in each cell

	protected static final String REPRESENTATIVE_ITEM_STRING = "WW";// arbitrary 2-char string for stringWidth

	public CalendarRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return SimpleCalendar.class;
	}

	public int getPreferredContentWidth(Widget widget) {
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_DEFAULT);
		DisplayFont font = look.getFonts()[index];
		//each item has quite the same size and at most two characters size
		int dayWidth = font.stringWidth(REPRESENTATIVE_ITEM_STRING);
		int numberOfGridLines = SimpleCalendar.DAYS_BY_WEEK + 1;
		return ((dayWidth + 2*INTERNAL_PADDING)*SimpleCalendar.DAYS_BY_WEEK) + numberOfGridLines;
	}

	public int getPreferredContentHeight(Widget widget) {
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_DEFAULT);
		DisplayFont font = look.getFonts()[index];
		int numberOfRows = ((SimpleCalendar) widget).getNumberOfLines() + 2;
		int numberOfGridLines = numberOfRows + 1;
		return (numberOfRows * (font.getHeight() + 2*INTERNAL_PADDING)) + numberOfGridLines;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		SimpleCalendar cal = (SimpleCalendar)renderable;
		Look look = getLook();
		int width = cal.getWidth();
		int height = cal.getHeight();

		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT));
		g.fillRect(0, 0, width, height);

		int padding = getPadding();
		int focusedDay = cal.hasFocus() ? cal.getFocus() : INVALID;
		int nLines = cal.getNumberOfLines();
		int itemWidth = getCellWidth(width, padding);
		int itemHeight = getCellHeight(height, nLines, padding);

		g.translate(padding, padding);

		renderTopRow(g, cal, width, itemWidth, itemHeight, focusedDay);
		int fontIndex = look.getProperty(Look.GET_FONT_INDEX_DEFAULT);
		g.setFont(look.getFonts()[fontIndex]);
		renderSecondRow(g, cal, itemWidth, itemHeight);
		renderDayCells(g, cal, itemWidth, itemHeight, focusedDay);
		renderGridLines(g, nLines, itemWidth, itemHeight);
	}

	private void renderTopRow(GraphicsContext g, SimpleCalendar cal, int widgetWidth, int itemWidth, int itemHeight, int focusedDay) {
		//display the calendar title (month year)
		Look look = getLook();
		int fontIndex = look.getProperty(Look.GET_FONT_INDEX_SELECTION);
		g.setFont(look.getFonts()[fontIndex]);
		int bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		g.setColor(bgColor);
		g.fillRect(0, 0, 7 * itemWidth + 1, itemHeight);
		int color = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
		g.setColor(color);
		g.drawString(SimpleCalendar.MONTHS[cal.getMonth()] + " " + cal.getYear(), widgetWidth / 2, 1 + itemHeight / 2, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);
		//display month navigation buttons
		drawCell(g, "<", 0, 0, itemWidth, itemHeight, bgColor, color, focusedDay == PREVIOUS_MONTH_COMMAND);
		drawCell(g, ">", itemWidth * (SimpleCalendar.DAYS_BY_WEEK - 1), 0, itemWidth, itemHeight, bgColor, color, focusedDay == NEXT_MONTH_COMMAND);
	}

	private void renderSecondRow(GraphicsContext g, SimpleCalendar cal, int itemWidth, int itemHeight) {
		//displays days name
		Look look = getLook();
		int bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_SELECTION);
		int color = look.getProperty(Look.GET_FOREGROUND_COLOR_FOCUSED);
		int x = 0;
		for(int i = -1; ++i < SimpleCalendar.DAYS_BY_WEEK;) {
			drawCell(g, cal.getDayName(i), x, itemHeight, itemWidth, itemHeight, bgColor, color, false);
			x += itemWidth;
		}
	}

	private void renderDayCells(GraphicsContext g, SimpleCalendar cal, int itemWidth, int itemHeight, int focusedDay) {
		Look look = getLook();
		int column = 0;
		int y = itemHeight * 2; // ready for third row
		int x = 0;

		//draw previous month days cells
		int bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DISABLED);
		int color = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);
		int previousMonthDaysCount = cal.getPreviousMonthDaysCount();
		int previousMonthLastDay = cal.getPreviousMonthLastDay();
		for(int i = -1; ++i < previousMonthDaysCount;) {
			drawCell(g, Integer.toString((previousMonthLastDay-(previousMonthDaysCount-i)+1)), x, y, itemWidth, itemHeight, bgColor, color, false);
			x += itemWidth;
			++column;
		}

		//draw current month days cells
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		int todayDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		boolean todayMonthOk =
			calendar.get(java.util.Calendar.MONTH) == cal.getMonth() &&
			calendar.get(java.util.Calendar.YEAR) == cal.getYear();

		int currentMonthDaysCount = cal.getCurrentMonthDaysCount();
		for(int i = 0; ++i <= currentMonthDaysCount;) {
			if(i == cal.getSelection()) {
				// selected day
				bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_FOCUSED);
				color = look.getProperty(Look.GET_FOREGROUND_COLOR_FOCUSED);
			} else if(todayMonthOk && todayDay == i) {
				// current day
				bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_SELECTION);
				color = look.getProperty(Look.GET_FOREGROUND_COLOR_SELECTION);
			} else {
				// default
				bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
				color = look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
			}

			drawCell(g, Integer.toString(i), x, y, itemWidth, itemHeight, bgColor, color, i == focusedDay);
			x += itemWidth;
			if(++column >= SimpleCalendar.DAYS_BY_WEEK) {
				column = 0;
				//new line
				y += itemHeight;
				x = 0;
			}
		}

		//draw next month days cells
		bgColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DISABLED);
		color = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);
		int nextMonthDaysCount = cal.getNextMonthDaysCount();
		for(int i = 0; ++i <= nextMonthDaysCount;) {
			drawCell(g, Integer.toString(i), x, y, itemWidth, itemHeight, bgColor, color, false);
			x += itemWidth;
			if(++column >= SimpleCalendar.DAYS_BY_WEEK) {
				column = 0;
				//new line
				y += itemHeight;
				x = 0;
			}
		}
	}

	private void renderGridLines(GraphicsContext g, int numberOfLines, int itemWidth, int itemHeight) {
		int color = getLook().getProperty(Look.GET_BORDER_COLOR_DEFAULT);
		g.setColor(color);
		int x = 0;
		int y = itemHeight;
		int arrayHeight = itemHeight * (numberOfLines+1);
		for(int i = SimpleCalendar.DAYS_BY_WEEK+1; --i >= 0;) {
			g.drawVerticalLine(x, itemHeight, arrayHeight);
			x += itemWidth;
		}
		x = 0;
		int arrayWidth = itemWidth * SimpleCalendar.DAYS_BY_WEEK;
		for(int i = numberOfLines+2; --i >= 0;) {
			g.drawHorizontalLine(x, y, arrayWidth);
			y += itemHeight;
		}
	}

	/*
	 * the width and height include space for a gridline, so offset by 1,1 to draw the content
	 */
	private void drawCell(GraphicsContext g, String content, int x, int y, int width, int height, int backColor, int foreColor, boolean outline) {
		//fill background
		g.setColor(backColor);
		g.fillRect(x+1, y+1, width-1, height-1);
		//draw text
		g.setColor(foreColor);
		g.drawString(content, x + 1 + (width-1)/2, y + 1 + (height-1) / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		//draw outline if necessary
		if(outline) {
			Drawings.drawOutline(g, x, y, width, height, 4, foreColor);
		}
	}

	private int getCellHeight(int widgetHeight, int nLines, int padding) {
		// remember that we need space for one extra grid line, hence the "-1"
		return (widgetHeight-2*padding-1)/(nLines+2);
	}

	private int getCellWidth(int widgetWidth, int padding) {
		// remember that we need space for one extra grid line, hence the "-1"
		return (widgetWidth-2*padding-1)/SimpleCalendar.DAYS_BY_WEEK;
	}

	public int getSelection(SimpleCalendar cal, int x, int y) {
		int padding = getPadding();
		x -= padding;
		y -= padding;
		int nLines = cal.getNumberOfLines();
		int cellWidth = getCellWidth(cal.getWidth(), padding);
		int cellHeight = getCellHeight(cal.getHeight(), nLines, padding);

		int column = x / cellWidth;
		int line = y / cellHeight;

		int previousMonthDaysCount = cal.getPreviousMonthDaysCount();
		if(line == 0) {
			if(column == 0) {
				return PREVIOUS_MONTH_COMMAND;
			} else if(column == SimpleCalendar.DAYS_BY_WEEK-1) {
				return NEXT_MONTH_COMMAND;
			} else {
				return INVALID;
			}
		} else if(line == 1) {
			return INVALID;
		} else if(line == 2 && column < previousMonthDaysCount) {
			return PREVIOUS_MONTH_COMMAND;
		} else if(line == nLines+1 && column > SimpleCalendar.DAYS_BY_WEEK - cal.getNextMonthDaysCount() - 1) {
			return NEXT_MONTH_COMMAND;
		} else {
			return (line-2)*SimpleCalendar.DAYS_BY_WEEK + column - previousMonthDaysCount+1;
		}
	}

}
