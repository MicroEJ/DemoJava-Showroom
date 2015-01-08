/*
 * Java
 * Copyright 2009-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.IRadio;

import ej.microui.Listener;

/**
 * <p>A radio button is a widget that holds a selection state. By modifying the state, an
 * action is triggered and sent to its potential {@link Listener}.</p>
 */
public class RadioButton extends ToggleButton implements IRadio {

	/**
	 * Creates a new radiobutton with an empty text label.<br>
	 * Equivalent to {@link #RadioButton(String)} passing an empty string (<code>""</code>) as argument.
	 */
	public RadioButton() {
		this("");
	}

	/**
	 * Creates a new radiobutton with the given string as text label.<br>
	 * A {@link RadioButton} is enabled by default.<br>
	 * 
	 * @param text
	 *            defines the text to display on the radiobutton.
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public RadioButton(String text) {
		super(text);
	}

	/**
	 * Triggers a selection on the radio button. If the radio button is not enabled, nothing is
	 * done.
	 */
	public void toggleSelection() {
		if (!isEnabled() || isSelected()) {
			// Nothing to do.
			return;
		}
		
		super.toggleSelection();
	}
}
