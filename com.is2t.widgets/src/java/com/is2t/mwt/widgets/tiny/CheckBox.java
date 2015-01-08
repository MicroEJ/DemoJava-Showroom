/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import ej.microui.Listener;


/**
 * <p>A checkbox is a widget that holds a selection state. By modifying the state, an
 * action is triggered and sent to its potential {@link Listener}.</p>
 */
public class CheckBox extends ToggleButton {

	/**
	 * Creates a new checkbox with an empty text label.<br>
	 * Equivalent to {@link #CheckBox(String)} passing an empty string (<code>""</code>) as argument.
	 */
	public CheckBox() {
		this("");
	}

	/**
	 * Creates a new checkbox with the given string as text label.<br>
	 * A checkbox is enabled and transparent by default.<br>
	 *
	 * @param text
	 *            defines the text to display on the checkbox.
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public CheckBox(String text) {
		super(text);
	}
}
