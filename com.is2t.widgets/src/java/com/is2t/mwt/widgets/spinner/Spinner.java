/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner;

public interface Spinner<T extends Comparable<T>> {

	void setModel(Value<T> model);

	T getMinimumValue();

	T getMaximumValue();

	T getStepSize();

	void nextValue();

	void previousValue();

	T getValue();

	void setValue(T value);

	String[] getWindow(int windowSize);

	String getUnit();
}
