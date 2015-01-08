/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.keyboard;

import com.is2t.mwt.widgets.contracts.Controller;

import ej.microui.io.Keyboard;
import ej.mwt.Widget;

public class VKeyboard extends Widget implements IKeyboard {

	/**
	 * <p>
	 * Creates a virtual keyboard.
	 * </p>
	 *
	 * @param keyboard
	 *            the keyboard generator that receive the events
	 * @param keyboardMapping
	 *            the initial keyboard mapping
	 */
	public VKeyboard(Keyboard keyboard, IKeyboardMapping keyboardMapping) {
		super();
		this.keyboard = keyboard;
		this.keyboardMapping = keyboardMapping;
	}

	public Keyboard getEventGenerator() {
		return keyboard;
	}

	public void setEventGenerator(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	public IKeyboardMapping getKeyboardMapping() {
		return this.keyboardMapping;
	}

	public void setKeyboardMapping(IKeyboardMapping keyboardMapping) {
		this.keyboardMapping = keyboardMapping;
	}

	public boolean isModifierKey(char key) {
		return key == SHIFT_IN || keyboardMapping.isModifierKey(key);
	}

	public boolean handleEvent(int event) {
		Controller renderer = (Controller) getRenderer();
		return renderer.handleEvent(this, event) == Controller.HANDLED;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected Keyboard keyboard;
	protected IKeyboardMapping keyboardMapping;

}
