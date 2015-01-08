/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.tiny.CheckBox;

public class LinedToggleButton extends CheckBox {

	private boolean underlined;
	private boolean overlined;
	
	public LinedToggleButton() {
		super();
	}
	
	/**
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return underlined;
	}

	/**
	 * @param overlined
	 *            the overlined to set
	 */
	public void setOverlined(boolean overlined) {
		this.overlined = overlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isOverlined() {
		return overlined;
	}
}
