/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import java.util.Calendar;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.widgets.spinner.MultipleSpinner;
import com.is2t.mwt.widgets.spinner.Value;
import com.is2t.mwt.widgets.spinner.ValueListener;
import com.is2t.mwt.widgets.spinner.int_.EndlessIntegerValue;
import com.is2t.mwt.widgets.spinner.int_.IntegerWheel;

import ej.bon.Util;
import ej.mwt.Composite;
import ej.mwt.MWT;

public class DateTimePage extends SettingsPage {

	private Value<Integer> day;
	private Value<Integer> month;
	private Value<Integer> year;

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Composite createMainContent() {
		BorderComposite dateLayout = new BorderComposite(MWT.VERTICAL);

		MultipleSpinner spinner = new MultipleSpinner();
		Calendar calendar = Calendar.getInstance();
		ValueListener<Integer> listener = new ValueListener<Integer>() {
			
			@Override
			public void valueChanged(Integer value) {
				Calendar time = Calendar.getInstance();
				time.set(year.getValue(), month.getValue() - 1, day.getValue()); // Calendar months start at 0.
				Util.setCurrentTimeMillis(time.getTimeInMillis());
			}
		};

		day = new EndlessIntegerValue(1, 31, 1, calendar.get(Calendar.DAY_OF_MONTH), "");
		day.addListener(listener);
		IntegerWheel daysWheel = new IntegerWheel();
		daysWheel.setModel(day);
		spinner.add(daysWheel);

		month = new EndlessIntegerValue(1, 12, 1, calendar.get(Calendar.MONTH) + 1, ""); // Calendar months start at 0.
		month.addListener(listener);
		IntegerWheel monthsWheel = new IntegerWheel();
		monthsWheel.setModel(month);
		spinner.add(monthsWheel);

		year = new EndlessIntegerValue(1900, 3000, 1, calendar.get(Calendar.YEAR), "");
		year.addListener(listener);
		IntegerWheel yearsWheel = new IntegerWheel();
		yearsWheel.setModel(year);
		spinner.add(yearsWheel);

		dateLayout.add(spinner);

		return dateLayout;
	}

	@Override
	protected String getTitle() {
		return "Date & time";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.DateAndTime;
	}

	@Override
	public AppPage getType() {
		return AppPage.DateTimePage;
	}
}
