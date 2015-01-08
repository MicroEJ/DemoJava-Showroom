/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.keyboard;

import com.is2t.mwt.widgets.IWidget;

import ej.microui.io.Keyboard;

public interface IKeyboard extends IWidget {

	public static final char SHIFT_IN = 0x0f;
	public static final char BACKSPACE = '\b';
	public static final char NEW_LINE = '\n';
	public static final char SPACE = ' ';

	Keyboard getEventGenerator();

	void setEventGenerator(Keyboard keyboard);

	IKeyboardMapping getKeyboardMapping();

	void setKeyboardMapping(IKeyboardMapping keyboardMapping);

	boolean isModifierKey(char key);

}
