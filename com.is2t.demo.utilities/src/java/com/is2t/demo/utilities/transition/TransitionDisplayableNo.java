/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.transition;

import ej.microui.io.Displayable;

public class TransitionDisplayableNo implements TransitionDisplayable {

	private Displayable newDisplayable;


	public void start() {
		newDisplayable.show();
	}

	@Override
	public void setForward(boolean forward) {
		// Nothing to do.
	}

	@Override
	public void setNewDisplayable(Displayable displayable) {
		this.newDisplayable = displayable;
	}
}
