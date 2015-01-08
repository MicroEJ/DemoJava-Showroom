/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

/**
 * Utilities for decimal numbers.
 */
public class DecimalNumber {

	private DecimalNumber() {
	}

	/**
	 * Gets the integer part of the given value.
	 *
	 * @param value
	 *            the argument whose integer part is to be determined.
	 * @return the integer part of the given value.
	 */
	public static int getIntegerPart(float value) {
		return (int) Math.abs(value);
	}

	/**
	 * Gets the floating part of the given value.
	 *
	 * @param value
	 *            the argument whose floating part is to be determined.
	 * @return the floating part of the given value.
	 */
	public static float getFloatingPart(float value) {
		return Math.abs(value) - getIntegerPart(value);
	}

	/**
	 * Gets the floating part of the given value with the given number of significative digits.
	 *
	 * @param value
	 *            the argument whose floating part is to be determined.
	 * @param significativeDigits
	 *            the number of significative digits.
	 * @return the floating part of the given value.
	 */
	public static int getFloatingPart(float value, int significativeDigits) {
		value = getFloatingPart(value);
		float factor = (float) Math.pow(10, significativeDigits);
		return (int) (factor * value);
	}

	/**
	 * Gets the rounded value of a float.
	 *
	 * @param appendable
	 *            the appendable on which to append the rounded value.
	 * @param value
	 *            the value to round.
	 * @param significativeDigits
	 *            the number of decimal digits to keep.
	 */
	public static void roundWithSize(StringBuilder appendable, float value, int significativeDigits) {
		if (value < 0) {
			appendable.append('-');
		}
		value = Math.abs(value);
		appendable.append((int) value).append('.').append(getFloatingPart(value, significativeDigits));
	}

	/**
	 * Gets the rounded value of a float.
	 *
	 * @param value
	 *            the value to round.
	 * @param significativeDigits
	 *            the number of decimal digits to keep.
	 * @return the rounded value.
	 */
	public static String roundWithSize(float value, int significativeDigits) {
		StringBuilder appendable = new StringBuilder();
		roundWithSize(appendable, value, significativeDigits);
		return appendable.toString();
	}
}
