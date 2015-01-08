/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.foundation;

import com.is2t.mwt.widgets.extended.DefaultExtendedStyle;
import com.is2t.mwt.widgets.extended.IExtendedStyle;
import com.is2t.mwt.widgets.extended.IExtendedWidget;

public class Label extends com.is2t.mwt.widgets.tiny.Label implements IExtendedWidget {

	private final IExtendedStyle extendedStyle;

	public Label() {
		this("");
	}

	public Label(String text) {
		super(text);
		this.extendedStyle = new DefaultExtendedStyle(this);
	}

	public IExtendedStyle getExtendedStyle() {
		return extendedStyle;
	}

	public int getStyle() {
		return extendedStyle.getStyle();
	}

	public boolean isTransparent() {
		return ExtendedWidgetHelper.isTransparent(this);
	}

	public boolean contains(int x, int y) {
		return ExtendedWidgetHelper.contains(this, x, y);
	}

	public boolean superContains(int x, int y) {
		return super.contains(x, y);
	}

}
