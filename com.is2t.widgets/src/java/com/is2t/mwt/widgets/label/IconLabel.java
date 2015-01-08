/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label;

import com.is2t.mwt.widgets.Picto;

public class IconLabel extends MultiLineLabel {

	private Picto icon;
	
	public IconLabel(String text, int maxLineCount, Picto icon) {
		super(text, maxLineCount);
		this.icon = icon;
	}

	public IconLabel(String text, Picto icon) {
		super(text);
		this.icon = icon;
	}
	
	public Picto getIcon() {
		return icon;
	}

	public void setIcon(Picto icon) {
		this.icon = icon;
	}
	
	@Override
	public boolean isTransparent() {
		return true;
	}
}
