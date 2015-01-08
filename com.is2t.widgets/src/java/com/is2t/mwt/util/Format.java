/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

/**
 * <p>
 * Provides some tools to format information such as float into string.
 * </p>
 */
public class Format {

	/**
	 * Default digits separator
	 */
	private static final String DEFAULT_DIGIT_SEPARATOR = ".";

	/**
	 * Convert float number into a String object.
	 * 
	 * @param value Float number
	 * @param precision Number of decimals
	 * @return String object
	 */
	public static String floatToStringWithPrecision(float value, int precision) {
		return floatToStringWithPrecision(value, precision, DEFAULT_DIGIT_SEPARATOR);
	}

	/**
	 * 
	 * 
	 * @param value
	 * @param precision
	 * @param digitSeparator
	 * @return
	 */
	public static String floatToStringWithPrecision(float val, int precision, String digitSeparator) {
		float value = Math.abs(val);
		StringBuffer buffer = new StringBuffer();
		if (val < 0) {
			buffer.append('-');
		}
		int factor = 1;
		float prec = (float) 0.01;
		while (--precision >= 0) {
			factor *= 10;
			prec /= 10;
		}
		if (value > 0) {
			value += prec;
		} else {
			value -= prec;
		}
		int a = (int) (value * factor);
		int b = a / factor;
		buffer.append(b);
		boolean appendDigitSeparator = false;
		if (factor > 1) {
			a = a - b * factor;
			factor /= 10;
			while (factor >= 1) {
				b = a / factor;
				if (!appendDigitSeparator) {
					appendDigitSeparator = true;
					buffer.append(digitSeparator);
				}
				buffer.append(b);
				a = a - b * factor;
				factor /= 10;
			}
		}
		return buffer.toString();
	}

}
