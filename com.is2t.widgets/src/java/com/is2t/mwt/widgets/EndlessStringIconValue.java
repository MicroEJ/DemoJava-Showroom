/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.widgets.spinner.stringicon.StringIconElement;
import com.is2t.mwt.widgets.spinner.stringicon.StringIconValue;
import com.is2t.mwt.widgets.spinner.stringicon.StringIconValueListener;


public class EndlessStringIconValue implements StringIconValue {

	private final List<StringIconValueListener> listeners = new ArrayList<StringIconValueListener>();
	private final StringIconElement[] elements;
	private int currentIndex;

	public EndlessStringIconValue(StringIconElement[] elements) {
		this.elements = elements;
		currentIndex = 0;
	}

	@Override
	public void nextValue() {
		currentIndex++;

		if (currentIndex > elements.length - 1) {
			currentIndex = 0;
		}
		
		valueChanged(getValue());
	}

	@Override
	public void previousValue() {
		currentIndex--;

		if (currentIndex < 0) {
			currentIndex = elements.length - 1;
		}
		
		valueChanged(getValue());
	}

	@Override
	public int getElementCount() {
		return elements.length;
	}

	@Override
	public StringIconElement getValue() {
		return elements[currentIndex];
	}

	@Override
	public void addListener(StringIconValueListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(StringIconValueListener listener) {
		listeners.remove(listener);
	}
	
	protected void valueChanged(StringIconElement newValue) {
		for(StringIconValueListener listener : listeners) {
			listener.valueChanged(newValue);
		}
	}
}
