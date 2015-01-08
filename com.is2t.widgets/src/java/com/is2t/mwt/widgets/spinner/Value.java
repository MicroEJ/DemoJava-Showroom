/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner;


public interface Value<T extends Comparable<T>> {

	/**
	 * Gets the maximum available value.
	 *
	 * @return the maximum available value.
	 */
	T getMinimumValue();

	/**
	 * Gets the minimum available value.
	 *
	 * @return the minimum available value.
	 */
	T getMaximumValue();

	/**
	 * Gets the size of the step used to increment or decrement the value.
	 * 
	 * @return the size of the step used to increment or decrement the value.
	 */
	T getStepSize();

	/**
	 * Increments the current value of stepSize quantity.
	 */
	void nextValue();

	/**
	 * Decrements the current value of stepSize quantity.
	 */
	void previousValue();

	/**
	 * Increments the current value of stepSize * stepCount quantity.
	 * @param stepCount the number of step to spin relatively to the current value.
	 */
	void nextValue(int stepCount);

	/**
	 * Gets the current value of the model.
	 *
	 * @return he current value of the model.
	 */
	T getValue();

	void setValue(T value);

	String getStringValue(T value);
	
	/**
	 * Computes and returns the next value from the given value.
	 * Returns null if the limit of the value is reached.
	 */
	T computeNextValue(T baseValue, int stepCount);

	/**
	 * Gets the window of the given size centered on the given value.
	 *
	 * @param value
	 *            the window center value.
	 * @param windowSize
	 *            the size of the window, must be odd.
	 * @return the window of the given size centered on the current value.
	 * @see #getWindow(int)
	 */
	String[] getWindow(T value, int windowSize);

	/**
	 * Gets the window of the given size centered on the current value.
	 *
	 * @param windowSize
	 *            the size of the window, must be odd.
	 * @return the window of the given size centered on the current value.
	 */
	String[] getWindow(int windowSize);

	/**
	 * Gets the unit of the value. Can be null.
	 *
	 * @return the unit of the value.
	 */
	String getUnit();

	/**
	 * Adds a listener to the model.
	 *
	 * @param listener
	 *            the listener to add.
	 */
	void addListener(ValueListener<T> listener);

	/**
	 * Removes a listener from the model.
	 *
	 * @param listener
	 *            the listener to remove.
	 */
	void removeListener(ValueListener<T> listener);
}
