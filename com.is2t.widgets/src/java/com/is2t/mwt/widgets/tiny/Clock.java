/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import java.util.Calendar;
import java.util.Date;

import com.is2t.mwt.widgets.IClock;

import ej.mwt.Widget;

/**
 * A clock is a widget that displays a date.
 */
public class Clock extends Widget implements IClock {

	/**
	 * Creates a new clock with the current time. <br>
	 * Equivalent to {@link #Clock(Date)} passing <code>new Date(System.currentTimeMillis())</code>
	 * as argument.
	 */
	public Clock() {
		this(new Date(System.currentTimeMillis()));
	}

	/**
	 * Creates a new clock specifying the date.<br>
	 * A clock is disabled by default.<br>
	 * 
	 * @param date the date of the clock
	 */
	public Clock(Date date) {
		setEnabled(false);
		calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
	}

	/**
	 * Sets a new date. The clock is not automatically repainted.
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		calendar.setTime(date);
	}

	/**
	 * Gets the date of the clock.
	 * @return the date of the clock
	 */
	public Date getDate() {
		return calendar.getTime();
	}

	/**
	 * Adds an year to the current date.
	 */
	public void addYear() {
		add(java.util.Calendar.YEAR, 1);
	}

	/**
	 * Subtracts an year to the current date.
	 */
	public void subYear() {
		add(java.util.Calendar.YEAR, -1);
	}

	/**
	 * Gets the year of the current date.
	 *
	 * @return the year of the current date
	 */
	public int getYear() {
		return calendar.get(java.util.Calendar.YEAR);
	}

	/**
	 * Adds an month to the current date.
	 */
	public void addMonth() {
		add(java.util.Calendar.MONTH, 1);
	}

	/**
	 * Subtracts an month to the current date.
	 */
	public void subMonth() {
		add(java.util.Calendar.MONTH, -1);
	}

	/**
	 * Gets the month of the current date.
	 *
	 * @return the month of the current date
	 */
	public int getMonth() {
		return calendar.get(java.util.Calendar.MONTH);
	}

	/**
	 * Adds an day to the current date.
	 */
	public void addDay() {
		add(java.util.Calendar.DAY_OF_MONTH, 1);
	}

	/**
	 * Subtracts an day to the current date.
	 */
	public void subDay() {
		add(java.util.Calendar.DAY_OF_MONTH, -1);
	}

	/**
	 * Gets the day of month of the current date.
	 *
	 * @return the day of month of the current date
	 * @see Calendar
	 */
	public int getDayOfMonth() {
		return calendar.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets the day of week of the current date.
	 *
	 * @return the day of week of the current date
	 * @see Calendar
	 */
	public int getDayOfWeek() {
		return calendar.get(java.util.Calendar.DAY_OF_WEEK);
	}

	/**
	 * Adds an hour to the current date.
	 */
	public void addHour() {
		add(java.util.Calendar.HOUR_OF_DAY, 1);
	}

	/**
	 * Subtracts an hour to the current date.
	 */
	public void subHour() {
		add(java.util.Calendar.HOUR_OF_DAY, -1);
	}

	/**
	 * Gets the hour of the current date.
	 *
	 * @return the hour of the current date
	 */
	public int getHour() {
		return calendar.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * Adds a minute to the current date.
	 */
	public void addMinute() {
		add(java.util.Calendar.MINUTE,1);
	}

	/**
	 * Subtracts a minute to the current date.
	 */
	public void subMinute() {
		add(java.util.Calendar.MINUTE,-1);
	}

	/**
	 * Gets the minute of the current date.
	 * @return the minute of the current date
	 */
	public int getMinute() {
		return calendar.get(java.util.Calendar.MINUTE);
	}

	/**
	 * Adds a second to the current date.
	 */
	public void addSecond() {
		add(java.util.Calendar.SECOND,1);
	}

	/**
	 * Subtracts a second to the current date.
	 */
	public void subSecond() {
		add(java.util.Calendar.SECOND,-1);
	}

	/**
	 * Gets the second of the current date.
	 * @return the second of the current date
	 */
	public int getSecond() {
		return calendar.get(java.util.Calendar.SECOND);
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/
	protected java.util.Calendar calendar;

	protected void add(int field, int increment) {
		calendar.set(field, calendar.get(field)+increment);
		repaint();
	}

}
