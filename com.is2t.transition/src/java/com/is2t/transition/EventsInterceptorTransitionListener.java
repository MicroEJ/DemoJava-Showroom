/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import com.is2t.demo.utilities.events.EventHandler;
import com.is2t.demo.utilities.events.EventsWatcher;
import com.is2t.demo.utilities.events.EventsWatcherSingleton;

import ej.flow.mwt.TransitionListener;

public class EventsInterceptorTransitionListener implements TransitionListener {

	private EventHandler eventHandler;

	@Override
	public void start() {
		eventHandler = new EventHandler() {
			@Override
			public boolean handleEvent(int event) {
				return false;
			}
		};
		EventsWatcher eventsWatcher = EventsWatcherSingleton.getEventsWatcher();
		eventsWatcher.captureAll(eventsWatcher.getAllGenerators(), eventHandler);
	}

	@Override
	public void stop() {
		EventsWatcherSingleton.getEventsWatcher().uncapture(eventHandler);
	}

}
