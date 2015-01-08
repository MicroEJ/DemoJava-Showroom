/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.contracts;

import com.is2t.mwt.widgets.IWidget;

public interface Controller {

	/**
	 * Indicates that the event is not handled.
	 */
	int NOT_HANDLED = 0x0;
	/**
	 * Indicates that the event is handled.
	 */
	int HANDLED = 0x1;

	int handleEvent(IWidget widget, int event);

}
