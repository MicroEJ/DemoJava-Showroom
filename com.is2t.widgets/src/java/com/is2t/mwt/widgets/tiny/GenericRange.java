/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.model.RangeHolder;

import ej.microui.Listener;
import ej.mwt.Widget;

/**
 * A range holds a value between a minimum and a maximum value. The underlying model is a {@link RangeHolder}.
 */
public abstract class GenericRange extends Widget implements Listener {

	/**
	 * Creates a new range with the specified bounds.<br>
	 * A range is enabled by default.<br>
	 */
	public GenericRange(int boundMin, int boundMax) {
		this(new RangeHolder(boundMin, boundMax));
	}

	/**
	 * Creates a new range with the specified {@link RangeHolder} as its model.<br>
	 * A range is enabled by default.<br>
	 */
	public GenericRange(RangeHolder model) {
		super();
		this.model = model;
		setEnabled(true);

		// repaint when data change
		this.model.addListener(this);
	}

	/**
	 * Gets the model that holds the value for this widget.
	 * @return the model of this widget
	 */
	public RangeHolder getModel() {
		return model;
	}

	/**
	 * Gets the lower bound.
	 * @return the lower bound
	 */
	public int getMinValue() {
		return this.model.getMinValue();
	}

	/**
	 * Gets the upper bound.
	 * @return the upper bound
	 */
	public int getMaxValue() {
		return this.model.getMaxValue();
	}

	public void setMinValue(int min) {
		this.model.setMinValue(min);
	}

	public void setMaxValue(int max) {
		this.model.setMaxValue(max);
	}

	public void setBoundsValues(int min, int max) {
		this.model.setMinMaxValues(min, max);
	}

	/**
	 * Gets the current value.
	 * @return the current value
	 */
	public int getValue() {
		return this.model.getValue();
	}

	/**
	 * Updates the current value with an increment.
	 * @param increment the increment to apply
	 */
	public void updateValue(int increment) {
		this.model.updateValue(increment);
	}

	/**
	 * Sets the current value.
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.model.setValue(value);
	}

	/**
	 * Defines which {@link Listener} to associate with the widget.<br>
	 * If the defined listener is not null, each scaling action on an
	 * enabled scale will call {@link Listener#performAction(int, Object)}
	 * passing (value, this), where value is the current value of the scale.
	 * @param listener the listener associated to the button, can be null.
	 */
	public void setListener(Listener listener) {
		this.updateListener = listener;
	}

	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return updateListener;
	}

	public void performAction() {
	}

	public void performAction(int value) {
		// the model has changed
		repaint();
		notifyListener();
	}

	public void performAction(int value, Object object) {
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	private Listener updateListener;
	private RangeHolder model;

	protected void notifyListener() {
		if (updateListener != null) {
			updateListener.performAction(getValue(), this);
		}
	}

}
