/**
 * Java 1.3
 * 
 * Copyright 2013 IS2T. All rights reserved.
 * Modification and distribution is permitted under certain conditions.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

public interface IRevcounter {

	/**
	 * Initializes widget's fixed properties.
	 * 
	 * @param unit Values unit
	 * @param limitValue Limit value (used to represent value before the latest portion).
	 */
	void initializeCounter(String unit, float limitValue);

	/**
	 * Sets a current value.
	 * 
	 * @param value Current value
	 */
	void setValue(float value);
}
