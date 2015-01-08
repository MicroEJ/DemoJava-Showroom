/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.events.internal;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.is2t.demo.utilities.ListenerAdapter;
import com.is2t.demo.utilities.errors.ErrorHandler;
import com.is2t.demo.utilities.events.EventHandler;
import com.is2t.demo.utilities.events.EventsWatcher;

import ej.microui.EventGenerator;
import ej.microui.Listener;

/**
 *
 */
public class EventsWatcherImpl implements EventsWatcher {

	private final Hashtable<EventGenerator, WatchersManager> interceptors;
	private ErrorHandler errorHandler;

	public EventsWatcherImpl() {
		interceptors = new Hashtable<>();
	}

	@Override
	public List<EventGenerator> getAllGenerators() {
		// cache it with soft references?
		EventGenerator[] eventGenerators = EventGenerator
				.get(EventGenerator.class);
		List<EventGenerator> generatorsList = new ArrayList<>();
		for (EventGenerator eventGenerator : eventGenerators) {
			generatorsList.add(eventGenerator);
		}
		return generatorsList;
	}

	@Override
	public void watch(EventGenerator eventGenerator, EventHandler eventHandler) {
		watchAll(toList(eventGenerator), eventHandler, true);
	}

	@Override
	public void watchAll(List<EventGenerator> eventGenerators,
			EventHandler eventHandler) {
		watchAll(eventGenerators, eventHandler, true);
	}

	@Override
	public void capture(EventGenerator eventGenerator, EventHandler eventHandler) {
		watchAll(toList(eventGenerator), eventHandler, false);
	}

	@Override
	public void captureAll(List<EventGenerator> eventGenerators,
			EventHandler eventHandler) {
		watchAll(eventGenerators, eventHandler, false);
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private void watchAll(List<EventGenerator> eventGenerators,
			EventHandler eventHandler, boolean watch) {
		for (EventGenerator eventGenerator : eventGenerators) {
			WatchersManager interceptorsManager = getWatchersManager(eventGenerator);
			interceptorsManager.intercept(eventHandler, watch);
		}
	}

	private WatchersManager getWatchersManager(EventGenerator eventGenerator) {
		WatchersManager interceptorsManager = interceptors.get(eventGenerator);
		if (interceptorsManager == null) {
			interceptorsManager = new WatchersManager(eventGenerator);
			interceptors.put(eventGenerator, interceptorsManager);
		}
		return interceptorsManager;
	}

	private void uncaughtException(Throwable t) {
		if (errorHandler != null) {
			errorHandler.uncaughtException(t);
		}
	}

	private static List<EventGenerator> toList(EventGenerator eventGenerator) {
		List<EventGenerator> list = new ArrayList<>();
		list.add(eventGenerator);
		return list;
	}

	private class WatchersManager extends ListenerAdapter {

		private final List<Reference<EventHandler>> watchers;
		private final List<Reference<EventHandler>> robbers;

		private WatchersManager(EventGenerator eventGenerator) {
			watchers = new ArrayList<>();
			robbers = new ArrayList<>();
			Listener initialListener = eventGenerator.getListener();
			robbers.add(new SimpleReference<EventHandler>(new EventWrapper(
					initialListener)));
			eventGenerator.setListener(this);
		}

		public void intercept(EventHandler eventHandler, boolean watch) {
			List<Reference<EventHandler>> list = watch ? watchers : robbers;
			WeakReference<EventHandler> weakElement = new WeakReference<>(
					eventHandler);
			list.add(weakElement);
			clean();
		}

		private void clean() {
			System.gc();
			clean(watchers);
			clean(robbers);
		}

		private void clean(List<Reference<EventHandler>> list) {
			for (int i = list.size(); --i >= 0;) {
				Reference<EventHandler> weakElement = list.get(i);
				EventHandler eventHandler = weakElement.get();
				if (eventHandler == null) {
					// clean
					list.remove(i);
				}
			}
		}

		@Override
		public void performAction(int value) {
			List<Reference<EventHandler>> list = watchers;

			// browse list by the end to manage the possible removals
			for (int i = list.size(); --i >= 0;) {
				Reference<EventHandler> weakElement = list.get(i);
				EventHandler eventHandler = weakElement.get();
				if (eventHandler == null) {
					// clean
					list.remove(i);
				} else {
					handleEvent(value, eventHandler);
				}
			}

			// get last added (still available) robber
			EventHandler handler;
			while (true) {
				int lastIndex = robbers.size() - 1;
				Reference<EventHandler> reference = robbers.get(lastIndex);
				handler = reference.get();
				if (handler != null) {
					break;
				}
				robbers.remove(lastIndex);
			}
			handleEvent(value, handler);
		}

		private void handleEvent(int value, EventHandler eventHandler) {
			try {
				eventHandler.handleEvent(value);
			} catch (Throwable t) {
				// protect against user errors
				uncaughtException(t);
			}
		}

		public void removeRobber(EventHandler eventHandler) {
			removeHandler(eventHandler, robbers);
		}

		public void removeWatcher(EventHandler eventHandler){
			removeHandler(eventHandler, watchers);
		}

		private void removeHandler(EventHandler eventHandler,
				List<Reference<EventHandler>> list) {
			int index = list.size() - 1;
			EventHandler currentHandler;

			while (index >= 0) {
				currentHandler = list.get(index).get();

				if (currentHandler.equals(eventHandler)) {
					list.remove(index);
					break;
				}

				index--;
			}
		}
	}

	@Override
	public void uncapture(EventHandler eventHandler) {
		for(EventGenerator eventGenerator : interceptors.keySet()){
			WatchersManager interceptorsManager = getWatchersManager(eventGenerator);
			interceptorsManager.removeRobber(eventHandler);
		}
	}

	@Override
	public void unwatch(EventHandler eventHandler) {
		for(EventGenerator eventGenerator : interceptors.keySet()){
			WatchersManager interceptorsManager = getWatchersManager(eventGenerator);
			interceptorsManager.removeWatcher(eventHandler);
		}
	}
}
