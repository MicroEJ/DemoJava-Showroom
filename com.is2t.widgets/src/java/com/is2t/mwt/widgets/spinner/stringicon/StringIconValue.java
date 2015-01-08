/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner.stringicon;



public interface StringIconValue {

	void nextValue();

	void previousValue();
	
	int getElementCount();
	
	StringIconElement getValue();
	
	/**
	 * Adds a listener to the model.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	void addListener(StringIconValueListener listener);

	/**
	 * Removes a listener from the model.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	void removeListener(StringIconValueListener listener);
}
