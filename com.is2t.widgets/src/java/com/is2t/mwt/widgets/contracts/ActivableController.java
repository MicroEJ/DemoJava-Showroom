/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.contracts;

import com.is2t.mwt.widgets.IWidget;

public interface ActivableController extends Controller {

	/**
	 * Indicates that the event activate the widget.
	 */
	int ACTIVATED = 0x2;

	void activate(IWidget widget);

}
