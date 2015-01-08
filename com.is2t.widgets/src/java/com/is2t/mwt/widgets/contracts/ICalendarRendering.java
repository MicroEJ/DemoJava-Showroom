/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.contracts;

import com.is2t.mwt.widgets.tiny.SimpleCalendar;


/**
 * The contract that must be offered by any renderer that wishes to render {@link SimpleCalendar} widgets.
 */
public interface ICalendarRendering {
	
	public static final int INVALID = -1;
	public static final int PREVIOUS_MONTH_COMMAND = -3;
	public static final int NEXT_MONTH_COMMAND = -2;

	/**
	 * Returns the day of month in range <code>[0..daysInMonth-1]</code>, or
	 * {@link #PREVIOUS_MONTH_COMMAND} to go to the previous month, or {@link #NEXT_MONTH_COMMAND}
	 * to go to the next month, or {@link #INVALID} otherwise.
	 * @param calendar the {@link SimpleCalendar} widget
	 * @param x the x position, relative to the widget bounds, of the pointer
	 * @param y the y position, relative to the widget bounds, of the pointer
	 * @return the date selection index or a command or {@link #INVALID}
	 */
	public int getSelection(SimpleCalendar calendar, int x, int y);

}
