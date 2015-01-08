/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities;

public class CalendarHelper {

	// use NLS?
	private static final String[] DAYS_OF_WEEK = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday" };
	private static final String[] SHORT_DAYS_OF_WEEK = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	private static final String[] MONTHS = new String[] { "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December" };

	public static String getDayOfWeek(int dayOfWeek) {
		return DAYS_OF_WEEK[dayOfWeek - 1];
	}

	public static String getShortDayOfWeek(int dayOfWeek) {
		return SHORT_DAYS_OF_WEEK[dayOfWeek - 1];
	}

	public static String getMonth(int month) {
		return MONTHS[month];
	}

}
