/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label;

public class TitleLabel extends MultiLineLabel {

	public TitleLabel(String text, int maxLineCount) {
		super(text, maxLineCount);
	}

	public TitleLabel(String text) {
		super(text);
	}

	@Override
	public boolean isTransparent() {
		return true;
	}
}
