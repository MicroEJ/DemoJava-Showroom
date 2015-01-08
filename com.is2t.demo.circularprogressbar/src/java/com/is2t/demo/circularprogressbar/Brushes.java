/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar;

import ej.microui.io.DisplayFont;
import ej.microui.io.FontBrush;

public class Brushes {
	
	private static final int IDENTIFIER = 81;
	private static final char BRUSH_CHAR = (char) 0x21;

	public static FontBrush getBrush(int size) {
		return new FontBrush(getFont(size), BRUSH_CHAR);
	}

	public static DisplayFont getFont(int size) {
		return DisplayFont.getFont(IDENTIFIER, size, DisplayFont.STYLE_PLAIN);
	}

	public static char getChar() {
		return BRUSH_CHAR;
	}
}
