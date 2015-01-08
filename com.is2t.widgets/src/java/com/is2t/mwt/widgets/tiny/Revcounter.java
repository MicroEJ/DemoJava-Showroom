/**
 * Java 1.3
 * 
 * Copyright 2013 IS2T. All rights reserved.
 * Modification and distribution is permitted under certain conditions.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.IRevcounter;

import ej.mwt.Widget;

/**
 * <p>
 * A revcounter is a widget displaying a current value in a relative way to a limit value. It displays also minimum and maximum value since the last
 * initialization call.
 * </p>
 * <p>
 * A revcounter manages its own selection behavior through mouse and event handling.
 * </p>
 */
public class Revcounter extends Widget implements IRevcounter {

	/**
	 * Default constructor.
	 */
	public Revcounter() {
		value = Float.NaN;
	}

	/*
	 * (non-Javadoc)
	 * @see com.is2t.widgets.revcounter.IRevcounter#initializeCounter(java.lang.String, float)
	 */
	public void initializeCounter(String unit, float limitValue) {
		this.unit = unit;
		this.limitValue = limitValue;
		this.minValue = limitValue;
		this.maxValue = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.is2t.widgets.revcounter.IRevcounter#setValue(float)
	 */
	public void setValue(float value) {
		this.value = value;
		if (minValue > value) {
			minValue = value;
		}
		if (maxValue < value) {
			maxValue = value;
		}
		repaint();
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	/**
	 * Current value
	 */
	private float value;
	/**
	 * Minimum recorded value
	 */
	private float minValue;
	/**
	 * Maximum recorded value
	 */
	private float maxValue;
	/**
	 * System limited value
	 */
	private float limitValue;
	/**
	 * Values unit
	 */
	private String unit;

	/**
	 * @return the current value.
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @return The minimum recorded value.
	 */
	public float getMinValue() {
		return minValue;
	}

	/**
	 * @return The maximum recorded value.
	 */
	public float getMaxValue() {
		return maxValue;
	}

	/**
	 * @return The system limited value.
	 */
	public float getLimitedValue() {
		return limitValue;
	}

	/**
	 * @return The value unit.
	 */
	public String getUnit() {
		return unit;
	}
}
