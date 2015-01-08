/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import java.util.Date;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.ICalendarRendering;

import ej.bon.Immortals;
import ej.microui.Command;
import ej.microui.Event;
import ej.microui.io.Pointer;
import ej.mwt.Widget;

/**
 * A calendar is a widget that permit to navigate in the days.
 * It display all the days of the current month, week by week.
 * The navigation is done month by month.
 */
public class SimpleCalendar extends Widget implements IWidget {

	/**
	 * Creates a new calendar. The current month is displayed.
	 * Equivalent to {@link #Calendar(Date)} passing <code>System.currentTimeMillis()</code> as argument.
	 */
	public SimpleCalendar() {
		this(new Date(System.currentTimeMillis()));
	}

	/**
	 * Creates a new calendar. The month of the given date is displayed.<br>
	 * A calendar is enabled by default.<br>
	 * 
	 * @param date the reference date of the calendar
	 */
	public SimpleCalendar(Date date) {
		super();

		setEnabled(true);

		selectionPtr = -1;
		//focusPtr = 0; //vm done

		setFirstDayOfWeek(java.util.Calendar.MONDAY);
		//by default set the date to current one (adjusted to the first day of month)
		setDate(date);

		computeDays();
	}

	/**
	 * Select the day of week on the first column of the calendar.
	 * @param firstDayOfWeek the day to set on the first column
	 */
	public void setFirstDayOfWeek(int firstDayOfWeek) {
		this.firstDayOfWeek = (byte)firstDayOfWeek;
	}

	/**
	 * Returns the day of month of the selected date.
	 * @return the day of month of the selected date
	 */
	public int getDayOfMonth() {
		return calendar.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns the day of week of the selected date.
	 * @return the day of week of the selected date
	 */
	public int getDayOfWeek() {
		return calendar.get(java.util.Calendar.DAY_OF_WEEK);
	}

	/**
	 * Returns the month of the selected date.
	 * @return the month of the selected date
	 */
	public int getMonth() {
		return calendar.get(java.util.Calendar.MONTH);
	}

	/**
	 * Returns the year of the selected date.
	 * @return the year of the selected date
	 */
	public int getYear() {
		return calendar.get(java.util.Calendar.YEAR);
	}

	/**
	 * Returns the selected date.
	 * @return the selected date
	 */
	public Date getSelectedDate() {
		calendar.set(java.util.Calendar.DAY_OF_MONTH, selectionPtr+1);
		Date d = calendar.getTime();
		resetMonth();
		return d;
	}

	/**
	 * Select the day of the given date on the calendar.
	 * The month of the date is displayed.
	 * @param date the date to select
	 */
	public void setDate(Date date) {
		//get old month
		int month = calendar.get(java.util.Calendar.MONTH);
		int oldSelection = selectionPtr;
		//set the new date
		calendar.setTime(date);
		int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		resetMonth();
		//select the day
		selectionPtr = (byte)(dayOfMonth);
		if(month != calendar.get(java.util.Calendar.MONTH)) {
			computeDays();
		}else if(oldSelection != selectionPtr){//same month but changing the selected day
			repaint();
		}
	}

	/**
	 * The calendar consume {@link Command#UP}, {@link Command#DOWN},
	 * {@link Command#LEFT}, {@link Command#RIGHT} except on the bounds
	 * and {@link Command#SELECT} commands.
	 * @param event the event to handle
	 * @return <code>true</code> if the calendar has consumed the event, <code>false</code> otherwise
	 */
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if(type == Event.COMMAND) {
			int cmd = Event.getData(event);
			int focus = focusPtr;
			switch (cmd) {
			case Command.UP:
				if(focus > 0) {
					if(focus <= DAYS_BY_WEEK) { //no day above
						focusPtr = ICalendarRendering.NEXT_MONTH_COMMAND;
					} else {
						focusPtr -= DAYS_BY_WEEK;
					}
				} else if(focus == ICalendarRendering.PREVIOUS_MONTH_COMMAND ||
						focus == ICalendarRendering.NEXT_MONTH_COMMAND) {
					//let the parent manage the focus
					return false;
				} else {
					//no selection before
					focusPtr = 1;
				}
				break;
			case Command.DOWN:
				if(focus > 0) {
					if(focus > currentMonthDaysCount - DAYS_BY_WEEK) { //no day below
						//let the parent manage the focus
						return false;
					} else {
						focusPtr += DAYS_BY_WEEK;
					}
				} else {
					//no selection before
					focusPtr = 1;
				}
				break;
			case Command.LEFT:
				if(focus > 0) {
					if(focus == 1) { //first day
						focusPtr = ICalendarRendering.NEXT_MONTH_COMMAND;
					} else {
						focusPtr -= 1;
					}
				} else if(focus == ICalendarRendering.PREVIOUS_MONTH_COMMAND){
					//let the parent manage the focus
					return false;
				} else if(focus == ICalendarRendering.NEXT_MONTH_COMMAND) {
					focusPtr = ICalendarRendering.PREVIOUS_MONTH_COMMAND;
				} else {
					//no selection before
					focusPtr = 1;
				}
				break;
			case Command.RIGHT:
				if(focus > 0) {
					if(focus == currentMonthDaysCount) { //last day
						//let the parent manage the focus
						return false;
					} else {
						focusPtr += 1;
					}
				} else if(focus == ICalendarRendering.PREVIOUS_MONTH_COMMAND){
					focusPtr = ICalendarRendering.NEXT_MONTH_COMMAND;
//				} else if(focus == NEXT_MONTH_COMMAND) {
//					focusPtr = 1;
				} else {
					//no selection before
					focusPtr = 1;
				}
				break;
			case Command.SELECT:
				if(focus == ICalendarRendering.PREVIOUS_MONTH_COMMAND) {
					showPreviousMonth();
				} else if(focus == ICalendarRendering.NEXT_MONTH_COMMAND) {
					showNextMonth();
				} else {
					selectionPtr = focusPtr;
					repaint();
				}
				return true;
			default:
				return super.handleEvent(event);
			}
			repaint();
			return true;
		} else if(type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				Pointer pointer = (Pointer)Event.getGenerator(event);
				int x = pointer.getX();
				int y = pointer.getY();
				int i = ((ICalendarRendering)getRenderer()).getSelection(this, x - getAbsoluteX(), y - getAbsoluteY());
				if (i != ICalendarRendering.INVALID) {
					if (i == ICalendarRendering.PREVIOUS_MONTH_COMMAND) {
						showPreviousMonth();
						focusPtr = ICalendarRendering.PREVIOUS_MONTH_COMMAND;
					}else if (i == ICalendarRendering.NEXT_MONTH_COMMAND) {
						showNextMonth();
						focusPtr = ICalendarRendering.NEXT_MONTH_COMMAND;
					}else {
						selectionPtr = focusPtr = (byte)i;
						repaint();
					}
				}
				return true;
			}
		}
		return super.handleEvent(event);
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected byte selectionPtr;
	public byte focusPtr;

	private byte firstDayOfWeek;
	//unique calendar used to retrieve date information
	private java.util.Calendar calendar = java.util.Calendar.getInstance();
	//computed fields
	private byte previousMonthDaysCount;
	private byte previousMonthLastDay;
	private byte currentMonthDaysCount;
	private byte nextMonthDaysCount;
	private byte linesCount;

	/**
	 * Public accesses for the renderer
	 */

	//Literals string are in flash (immutable). Could gain the array too...
	//The array is immortal too.
	public static final String[] DAYS = (String[])Immortals.setImmortal(new String[] {"Su","Mo","Tu","We","Th","Fr","Sa"});
	public static final String[] MONTHS = (String[])Immortals.setImmortal(new String[] {"January","February","March","April","May","June","July","August","September","October","November","December"});

	public static final int DAYS_BY_WEEK = 7;

	public int getSelection() {
		return selectionPtr;
	}

	public int getFocus() {
		return focusPtr;
	}

	public String getDayName(int day) {
		if((day += firstDayOfWeek) > DAYS_BY_WEEK) {
			day -= DAYS_BY_WEEK;
		}
		return DAYS[day-1];
	}

	public int getNumberOfLines() {
		return linesCount;
	}
	public int getPreviousMonthDaysCount() {
		return previousMonthDaysCount;
	}
	public int getPreviousMonthLastDay() {
		return previousMonthLastDay;
	}
	public int getCurrentMonthDaysCount() {
		return currentMonthDaysCount;
	}
	public int getNextMonthDaysCount() {
		return nextMonthDaysCount;
	}

	/**
	 * Private intern methods
	 */

	private void add(int field, int increment) {
		int january = java.util.Calendar.JANUARY;
		int december = java.util.Calendar.DECEMBER;

		int value = calendar.get(field);
		int newValue = value+increment;
		if(field == java.util.Calendar.MONTH) {
			int year = calendar.get(java.util.Calendar.YEAR);
			if(newValue > december) {
				newValue = january;
				calendar.set(java.util.Calendar.YEAR,year+1);
			}else if(newValue < january) {
				newValue = december;
				calendar.set(java.util.Calendar.YEAR,year-1);
			}
		}
		calendar.set(field, newValue);
	}

	private int getDayShift() {
		//ensure we are the first day of month
		resetMonth();
		calendar.setTime(calendar.getTime());
		int dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);

		if((dayOfWeek -= firstDayOfWeek) < 0) {
			dayOfWeek += DAYS_BY_WEEK;
		}
		return dayOfWeek;
	}

	private int getNumberOfDays(int month) {
		switch(month) {
			case java.util.Calendar.FEBRUARY:
				int year = calendar.get(java.util.Calendar.YEAR);
				//if leap year 29, 28 otherwise
				return ((year % 4 == 0) && (year % 100 !=0)) || (year % 400 ==0) ? 29 : 28;
			case java.util.Calendar.APRIL:
			case java.util.Calendar.JUNE:
			case java.util.Calendar.SEPTEMBER:
			case java.util.Calendar.NOVEMBER: return 30;
			default: // JAN, MAR, MAY, JUL, AUG, OCT, DEC
				return 31;
		}
	}

	private void resetMonth() {
		calendar.set(java.util.Calendar.DAY_OF_MONTH,1);
	}

	private void computeDays() {
		short selectionPtr = this.selectionPtr;

		int dayShift = getDayShift();

		int month = calendar.get(java.util.Calendar.MONTH);

		//previous month
		int previousMonthNumberOfDays = getNumberOfDays((month-1)%12);

		//current month
		int currentMonthDaysCount = getNumberOfDays(month);

		//try to keep the same day selected
		this.selectionPtr = (byte) (selectionPtr <= currentMonthDaysCount ? selectionPtr : currentMonthDaysCount);

		//next month
		int nItems = dayShift+currentMonthDaysCount;
		int lineCount = (nItems / DAYS_BY_WEEK)+((nItems % DAYS_BY_WEEK == 0) ? 0 : 1);
		int daysToAdd = (lineCount*DAYS_BY_WEEK)-(nItems);

		//update fields
		this.linesCount = (byte)lineCount;
		this.previousMonthDaysCount = (byte)dayShift;
		this.previousMonthLastDay = (byte)previousMonthNumberOfDays;
		this.currentMonthDaysCount = (byte)currentMonthDaysCount;
		this.nextMonthDaysCount = (byte)daysToAdd;

		revalidate();
	}

	private void showPreviousMonth() {
		add(java.util.Calendar.MONTH, -1);
		computeDays();
		repaint();
	}

	private void showNextMonth() {
		add(java.util.Calendar.MONTH, 1);
		computeDays();
		repaint();
	}

}
