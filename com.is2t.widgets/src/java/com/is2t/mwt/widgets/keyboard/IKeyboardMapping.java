/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.keyboard;

public interface IKeyboardMapping {

	char[][] getMapping();

	boolean isSpecialKey(char key);

	void handleSpecialKey(char key);

	boolean isModifierKey(char key);

	char applyModifiers(char key, char[] pressedKeys);

	void updateModifiers(char[] pressedKeys);

}
