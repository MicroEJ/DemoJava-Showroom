/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.events.internal;

import com.is2t.demo.utilities.events.EventHandler;

import ej.microui.Listener;

public class EventWrapper implements EventHandler {

	private final Listener listener;

	public EventWrapper(Listener listener) {
		this.listener = listener;
	}

	@Override
	public boolean handleEvent(int event) {
		listener.performAction(event);
		return false;
	}
}
