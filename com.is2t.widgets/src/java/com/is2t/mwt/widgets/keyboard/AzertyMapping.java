/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.keyboard;

public class AzertyMapping implements IKeyboardMapping {

	public static final char SPECIAL_1 = 0x01;
	public static final char SPECIAL_2 = 0x02;
	public static final char SPECIAL_3 = 0x03;

	// TODO immutables
	private static final char[] AZERTY_LOWER_MAPPING_LINE1 = new char[] { 'a', 'z', 'e', 'r', 't', 'y', 'u', 'i', 'o',
			'p' };
	private static final char[] AZERTY_LOWER_MAPPING_LINE2 = new char[] { 'q', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
			'm' };
	private static final char[] AZERTY_LOWER_MAPPING_LINE3 = new char[] { IKeyboard.SHIFT_IN, IKeyboard.SHIFT_IN, 'w',
			'x', 'c', 'v', 'b', 'n', IKeyboard.BACKSPACE, IKeyboard.BACKSPACE };
	private static final char[] AZERTY_UPPER_MAPPING_LINE1 = new char[] { 'A', 'Z', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
			'P' };
	private static final char[] AZERTY_UPPER_MAPPING_LINE2 = new char[] { 'Q', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
			'M' };
	private static final char[] AZERTY_UPPER_MAPPING_LINE3 = new char[] { IKeyboard.SHIFT_IN, IKeyboard.SHIFT_IN, 'W',
			'X', 'C', 'V', 'B', 'N', IKeyboard.BACKSPACE, IKeyboard.BACKSPACE };
	private static final char[] AZERTY_MAPPING_LINE4 = new char[] { SPECIAL_2, IKeyboard.SPACE, IKeyboard.SPACE,
			IKeyboard.SPACE, IKeyboard.NEW_LINE };

	private static final char[] NUMERIC_MAPPING_LINE = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	private static final char[] NUMERIC_MAPPING_LINE2 = new char[] { '-', '/', ':', ';', '(', ')', '~', '&', '@', '"' };
	private static final char[] NUMERIC_MAPPING_LINE3 = new char[] { SPECIAL_3, SPECIAL_3, '.', ',', '?', '!', '\'',
			'|', IKeyboard.BACKSPACE, IKeyboard.BACKSPACE };
	private static final char[] SYMBOLS_MAPPING_LINE2 = new char[] { '_', '\\', '[', ']', '{', '}', '#', '%', '$', '*' };
	private static final char[] SYMBOLS_MAPPING_LINE3 = new char[] { SPECIAL_2, SPECIAL_2, '<', '>', '`', '+', '=',
			'^', IKeyboard.BACKSPACE, IKeyboard.BACKSPACE };
	private static final char[] MAPPING_LINE4 = new char[] { SPECIAL_1, IKeyboard.SPACE, IKeyboard.SPACE,
			IKeyboard.SPACE, IKeyboard.NEW_LINE };

	private static final char[][] AZERTY_LOWER_MAPPING = new char[][] { AZERTY_LOWER_MAPPING_LINE1,
			AZERTY_LOWER_MAPPING_LINE2, AZERTY_LOWER_MAPPING_LINE3, AZERTY_MAPPING_LINE4 };

	private static final char[][] AZERTY_UPPER_MAPPING = new char[][] { AZERTY_UPPER_MAPPING_LINE1,
			AZERTY_UPPER_MAPPING_LINE2, AZERTY_UPPER_MAPPING_LINE3, AZERTY_MAPPING_LINE4 };

	private static final char[][] NUMERIC_MAPPING = new char[][] { NUMERIC_MAPPING_LINE, NUMERIC_MAPPING_LINE2,
			NUMERIC_MAPPING_LINE3, MAPPING_LINE4 };

	private static final char[][] SYMBOLS_MAPPING = new char[][] { NUMERIC_MAPPING_LINE, SYMBOLS_MAPPING_LINE2,
			SYMBOLS_MAPPING_LINE3, MAPPING_LINE4 };

	private char[][] currentMapping = AZERTY_LOWER_MAPPING;

	public char[][] getMapping() {
		return currentMapping;
	}

	public boolean isSpecialKey(char key) {
		return key <= SPECIAL_3;
	}

	public void handleSpecialKey(char key) {
		switch (key) {
		case SPECIAL_1:
			currentMapping = AZERTY_LOWER_MAPPING;
			break;
		case SPECIAL_2:
			currentMapping = NUMERIC_MAPPING;
			break;
		case SPECIAL_3:
			currentMapping = SYMBOLS_MAPPING;
			break;
		}
	}

	public boolean isModifierKey(char key) {
		return false;
	}

	public char applyModifiers(char key, char[] pressedKeys) {
		// if (currentMapping == AZERTY_MAPPING && key >= 'A' && key <= 'Z') {
		// int pressedKeysLength = pressedKeys.length;
		// for (int i = pressedKeysLength; --i >= 0;) {
		// if (pressedKeys[i] == IKeyboard.SHIFT_IN) {
		// // shift key is pressed
		// return Character.toUpperCase(key);
		// }
		// }
		// return Character.toLowerCase(key);
		// }
		return key;
	}

	public void updateModifiers(char[] pressedKeys) {
		boolean isUpperMapping = currentMapping == AZERTY_UPPER_MAPPING;
		boolean isLowerMapping = currentMapping == AZERTY_LOWER_MAPPING;
		if (isUpperMapping || isLowerMapping) {
			int pressedKeysLength = pressedKeys.length;
			for (int i = pressedKeysLength; --i >= 0;) {
				if (pressedKeys[i] == IKeyboard.SHIFT_IN) {
					currentMapping = AZERTY_UPPER_MAPPING;
					return;
				}
			}
			currentMapping = AZERTY_LOWER_MAPPING;
		}
	}

}
