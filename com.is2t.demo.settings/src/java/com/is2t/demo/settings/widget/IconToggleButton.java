/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.tiny.RadioButton;

public class IconToggleButton extends RadioButton {

	private Picto icon;

	public IconToggleButton(String text, Picto icon) {
		super(text);
		this.icon = icon;
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	public Picto getIcon() {
		return icon;
	}

	public void setIcon(Picto icon) {
		this.icon = icon;
	}
}
