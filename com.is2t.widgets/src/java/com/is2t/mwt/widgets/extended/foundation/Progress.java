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
import com.is2t.mwt.widgets.extended.ProgressStyles;

public class Progress extends com.is2t.mwt.widgets.tiny.Progress implements IExtendedWidget {

	private final IExtendedStyle extendedStyle;

	public Progress() {
		this(DEFAULT_STEPS, ProgressStyles.HORIZONTAL_SCALE);
	}

	public Progress(int style) {
		this(DEFAULT_STEPS, style);
	}

	public Progress(int steps, int style) {
		super(steps);
		this.extendedStyle = new DefaultExtendedStyle(this);
		this.extendedStyle.setStyle(style);
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
