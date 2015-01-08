/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button;

import com.is2t.mwt.widgets.IButton;

public class ListEntry extends ListButton implements IButton {

	protected Object value;

	public ListEntry(String text, int maxLineCount) {
		super(text, maxLineCount);
		setEnabled(true);
	}

	public ListEntry(String text) {
		super(text);
		setEnabled(true);
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
