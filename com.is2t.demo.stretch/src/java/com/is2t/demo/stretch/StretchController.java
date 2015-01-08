/*
 * Java
 *
 * Copyright 2011-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch;

import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;

public class StretchController implements Listener {
	private StretchModel model;

	public StretchController(StretchModel model) {
		this.model = model;
	}

	public void performAction(int event) {
		int type = Event.getType(event);

		if (model.getHomeButton().handleEvent(event)) {
			return;
		}

		if (type == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			model.stretchTo(pointer.getX(), pointer.getY());
		}
	}

	public void performAction() {
	}

	public void performAction(int value, Object object) {
	}
}
