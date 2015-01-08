/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import com.is2t.mwt.widgets.spinner.stringicon.StringIconElement;


public class BasicStringIconElement implements StringIconElement {

	private final String text;
	private final Picto picto;
	
	public BasicStringIconElement(String text, Picto picto) {
		this.text = text;
		this.picto = picto;
	}
	
	@Override
	public String getName() {
		return text;
	}

	@Override
	public Picto getIcon() {
		return picto;
	}
}
