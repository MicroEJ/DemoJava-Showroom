/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.ILabel;

import ej.mwt.Widget;

/**
 * <p>A label is a simple widget which holds a text {@link String}.</p>
 * <p>It is a single line text. There is no behavior associated to it.</p>
 */
public class Label extends Widget implements ILabel {

	/**
	 * Creates a new label with an empty text.<br>
	 * Equivalent to {@link #Label(String)} passing an empty string <code>""</code> as argument.
	 */
	public Label() {
		this("");
	}

	/**
	 * Creates a new label with the given string as text.<br>
	 * A label is disabled by default.
	 * 
	 * @param text
	 *            the text to display.
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public Label(String text) {
		super();
		setEnabled(false);
		setTextInternal(text);
	}

	/**
	 * Sets the text to display.
	 *
	 * @param text
	 *            the text to display
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public void setText(String text) {
		setTextInternal(text);
		revalidate();
	}

	/**
	 * Returns the displayed text.
	 *
	 * @return the displayed text.
	 */
	public String getText() {
		return text;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected String text;

	private void setTextInternal(String text) {
		if(text == null) {
			throw new NullPointerException();
		}
		this.text = text;
	}

}
