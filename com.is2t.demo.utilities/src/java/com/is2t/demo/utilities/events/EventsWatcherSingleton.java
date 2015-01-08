/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.events;

import com.is2t.demo.utilities.events.internal.EventsWatcherImpl;

public class EventsWatcherSingleton {

	private static EventsWatcher singleton = new EventsWatcherImpl();

	/**
	 * @return the events watcher instance.
	 */
	public static EventsWatcher getEventsWatcher() {
		return singleton;
	}

}
