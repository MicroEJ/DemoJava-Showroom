/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.label.TitleLabel;
import com.is2t.mwt.widgets.spinner.MultipleSpinner;
import com.is2t.mwt.widgets.spinner.Value;
import com.is2t.mwt.widgets.spinner.int_.EndlessIntegerValue;
import com.is2t.mwt.widgets.spinner.int_.IntegerWheel;

import ej.mwt.Composite;
import ej.mwt.MWT;

public class SecurityPage extends SettingsPage {

	@Override
	public AppPage getType() {
		return AppPage.SecurityPage;
	}

	@Override
	protected String getTitle() {
		return "Security";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.Security;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Composite createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();
		
		TitleLabel titleLabel = new TitleLabel("Set your code");
		titleLabel.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.addAt(titleLabel, MWT.TOP);
		
		MultipleSpinner codeSpinner = new MultipleSpinner();
		codeSpinner.add(createCodeDigit(1));
		codeSpinner.add(createCodeDigit(9));
		codeSpinner.add(createCodeDigit(0));
		codeSpinner.add(createCodeDigit(2));
		mainContentLayout.add(codeSpinner);
		
		return mainContentLayout;
	}
	
	private IntegerWheel createCodeDigit(int initialValue) {
		Value<Integer> codeDigit = new EndlessIntegerValue(0, 9, 1, initialValue, "");
		IntegerWheel codeDigitWheel = new IntegerWheel();
		codeDigitWheel.setModel(codeDigit);
		return codeDigitWheel;
	}
}
