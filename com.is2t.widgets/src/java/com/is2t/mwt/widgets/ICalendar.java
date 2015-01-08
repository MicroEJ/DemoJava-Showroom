/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import java.util.Date;

public interface ICalendar extends IWidget, IObservable {

	Date getSelection();

	int getDayOfMonth();

	int getDayOfWeek();

	int getMonth();

	int getYear();

	void select(Date date);

}
