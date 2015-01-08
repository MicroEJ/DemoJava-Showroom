/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.LookExtension;

public class HeadlineLabel extends MultiLineLabel {

	private String headline;
	private int headlineFontSize;
	private String[] headlineLines;

	public HeadlineLabel(String headline, String text, int maxLineCount) {
		super(text, maxLineCount);
		this.headline = headline;
		headlineLines = Drawings.splitStringInLines(headline);
		this.headlineFontSize = LookExtension.GET_SMALL_FONT_INDEX;
	}

	public HeadlineLabel(String headline, String text) {
		this(headline, text, MultiLineLabel.DEFAULT_MAX_LINES_COUNT);
	}

	/**
	 * One of {@link LookExtension} id.
	 * 
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setHeadlineFontSize(int fontSize) {
		this.headlineFontSize = fontSize;
	}

	/**
	 * @return the fontSize
	 */
	public int getHeadlineFontSize() {
		return headlineFontSize;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String[] getHeadlineLines() {
		return headlineLines;
	}
}
