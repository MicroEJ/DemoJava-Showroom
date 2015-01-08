/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.model;

import ej.bon.XMath;
import ej.microui.Listener;
import ej.microui.Model;

/**
 * Holds an integer value and its upper and lower limits, defining a range. Designed to be used
 * as a model for widgets. Notifies its listeners when the value or range changes.
 */
public class RangeHolder extends Model {

	private int value;
	private int minValue;
	private int maxValue;

	/**
	 * Value passed to the {@link Listener#performAction(int)} method of the listener if the value was
	 * changed.
	 */
	public static final int EVENT_VALUE_CHANGED = 1;

	/**
	 * Value passed to the {@link Listener#performAction(int)} method of the listener if the minimum and
	 * maximum were changed.
	 */
	public static final int EVENT_MINMAX_CHANGED = 2;

	/**
	 * Creates a new range holder.<br>
	 * Equivalent to {@link #RangeData(int,int,int)} passing (<code>0, 0, 100</code>) as argument.
	 */
	public RangeHolder() {
		this(0, 0, 100);
	}

	/**
	 * Creates a new range holder.<br>
	 * Equivalent to {@link #RangeData(int,int,int)} passing (<code>0, minValue, maxValue</code>) as
	 * argument.
	 *
	 * @param minValue
	 *            the minimum value of the data
	 * @param maxValue
	 *            the maximum value of the data
	 */
	public RangeHolder(int minValue, int maxValue) {
		this(0, minValue, maxValue);
	}

	/**
	 * Creates a new range holder.
	 *
	 * @param value
	 *            the value of the data
	 * @param minValue
	 *            the minimum value of the data
	 * @param maxValue
	 *            the maximum value of the data
	 */
	public RangeHolder(int value, int minValue, int maxValue) {
		super();
		setMinMaxValues(minValue, maxValue);
		setValue(value);
	}

	/**
	 * Returns the current value.
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the current value.<br>
	 * If value is not within range it will be adjusted.<br>
	 * Calls the {@link Listener#performAction(int)} method of the listener with (<code>{@link #EVENT_VALUE_CHANGED}</code>) as parameter.
	 *
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		value = XMath.limit(value, minValue, maxValue);

		if (value != this.value) {
			this.value = value;
			changed(EVENT_VALUE_CHANGED);
		}
	}

	/**
	 * Updates the current value with an increment.<br>
	 * If the resulting value is not within range it will be adjusted.<br>
	 * Calls the {@link Listener#performAction(int)} method of the listener with (<code>{@link #EVENT_VALUE_CHANGED}</code>) as parameter.
	 *
	 * @param increment
	 *            the increment to apply
	 */
	public void updateValue(int increment) {
		setValue(this.value + increment);
	}

	/**
	 * Returns the minimum value.
	 * @return the minValue
	 */
	public int getMinValue() {
		return minValue;
	}

	/**
	 * Returns the maximum value.
	 * @return the maxValue
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * Sets the minimum and maximum values of the RangeData.<br>
	 * If value is not within range it will be adjusted.<br>
	 * Calls the {@link Listener#performAction(int)} method of the listener with (<code>{@link #EVENT_MINMAX_CHANGED}</code>) as parameter.
	 *
	 * @param minValue
	 *            the minimum value to set
	 * @param maxValue
	 *            the maximum value to set
	 * @throws IllegalArgumentException
	 *             if the minimum value is bigger than the maximum value
	 */
	public void setMinMaxValues(int minValue, int maxValue) {
		if (minValue > maxValue)
			throw new IllegalArgumentException();

		if (minValue != this.minValue || maxValue != this.maxValue) {
			this.minValue = minValue;
			this.maxValue = maxValue;

			// update value if necessary
			setValue(this.value);

			changed(EVENT_MINMAX_CHANGED);
		}
	}

	/**
	 * Sets the minimum value of the RangeData.<br>
	 * If value is not within range it will be adjusted.<br>
	 * Calls the {@link Listener#performAction(int)} method of the listener with (<code>{@link #EVENT_MINMAX_CHANGED}</code>) as parameter.
	 *
	 * @param minValue
	 *            the minimum value to set
	 * @throws IllegalArgumentException
	 *             if the minimum value is bigger than the maximum value
	 */
	public void setMinValue(int minValue) {
		setMinMaxValues(minValue, maxValue);
	}

	/**
	 * Sets the maximum values of the RangeData.<br>
	 * If value is not within range it will be adjusted.<br>
	 * Calls the {@link Listener#performAction(int)} method of the listener with (<code>{@link #EVENT_MINMAX_CHANGED}</code>) as parameter.
	 *
	 * @param maxValue
	 *            the maximum value to set
	 * @throws IllegalArgumentException
	 *             if the minimum value is bigger than the maximum value
	 */
	public void setMaxValue(int maxValue) {
		setMinMaxValues(minValue, maxValue);
	}
}
