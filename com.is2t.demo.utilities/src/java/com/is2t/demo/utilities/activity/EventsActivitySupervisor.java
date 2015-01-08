/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

import java.util.List;

import com.is2t.demo.utilities.events.EventHandler;
import com.is2t.demo.utilities.events.EventsWatcher;
import com.is2t.demo.utilities.events.EventsWatcherSingleton;

import ej.bon.Timer;
import ej.microui.EventGenerator;

/**
 * Useful to supervise activity on event generators.
 */
public class EventsActivitySupervisor extends AbstractActivitySupervisor implements ActivitySupervisor, EventHandler {

	/**
	 * Supervises all the available generators.
	 * 
	 * @param timer
	 *            the timer to schedule on.
	 * @param delay
	 *            the minimum duration of inactivity before the listener is notified.
	 * @see EventsWatcher#getAllGenerators()
	 * @see EventsWatcher#watchAll(List, EventHandler)
	 */
	public EventsActivitySupervisor(Timer timer, long delay) {
		this(timer, delay, EventsWatcherSingleton.getEventsWatcher().getAllGenerators());
	}

	/**
	 * Supervises the given generators.
	 * 
	 * @param timer
	 *            the timer to schedule on.
	 * @param delay
	 *            the minimum duration of inactivity before the listener is notified.
	 * @see EventsWatcher#watchAll(List, EventHandler)
	 */
	public EventsActivitySupervisor(Timer timer, long delay, List<EventGenerator> generators) {
		super(timer, delay);

		EventsWatcher eventsWatchingHelper = EventsWatcherSingleton.getEventsWatcher();
		eventsWatchingHelper.watchAll(generators, this);
	}

	@Override
	public boolean handleEvent(int event) {
		activate();

		return false;
	}

}
