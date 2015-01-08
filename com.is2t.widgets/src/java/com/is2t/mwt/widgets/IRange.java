/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

public interface IRange extends IWidget, IObservable {

	int getMinValue();

	void setMinValue(int min);

	int getMaxValue();

	void setMaxValue(int max);

	void setBoundsValues(int min, int max);

	int getValue();

	void setValue(int value);

}
