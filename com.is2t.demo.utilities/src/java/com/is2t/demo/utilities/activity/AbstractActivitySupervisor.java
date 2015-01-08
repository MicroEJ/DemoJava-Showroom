/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

import java.util.List;

import com.is2t.demo.utilities.errors.ErrorHandler;
import com.is2t.demo.utilities.events.EventHandler;
import com.is2t.demo.utilities.events.EventsWatcher;

import ej.bon.Timer;
import ej.bon.TimerTask;

public abstract class AbstractActivitySupervisor implements ActivitySupervisor {

	private final long delay;
	private final Timer timer;

	private ActivityListener activityListener;
	private TimerTask task;
	private boolean interrupted;
	private boolean activity;
	private ErrorHandler errorHandler;

	/**
	 * Supervise some activity.
	 *
	 * @param timer
	 *            the timer to schedule on.
	 * @param delay
	 *            the minimum duration of inactivity before the listener is notified.
	 * @see EventsWatcher#getAllGenerators()
	 * @see EventsWatcher#watchAll(List, EventHandler)
	 */
	public AbstractActivitySupervisor(Timer timer, long delay) {
		this.timer = timer;
		this.delay = delay;
	}

	@Override
	public void setActivityListener(ActivityListener activityListener) {
		this.activityListener = activityListener;
	}

	@Override
	public synchronized void arm() {
		if (task == null) {
			interrupted = false;
			activity = false;
			task = new TimerTask() {
				@Override
				public void run() {
					if (!interrupted) {
						interrupted = true;
						activity = false;
						try {
							activityListener.inactive();
						} catch (Throwable t) {
							AbstractActivitySupervisor.this.uncaughtException(t);
						}
					}
					if (activity) {
						interrupted = false;
					}
				}
			};
			timer.schedule(task, delay, delay);
		}
	}

	@Override
	public synchronized void stop() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

	/**
	 * Called when an activity occurs.
	 */
	protected void activate() {
		interrupted = true;
		activity = true;
		if (task != null) {
			try {
				activityListener.active();
			} catch (Throwable t) {
				uncaughtException(t);
			}
		}
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private void uncaughtException(Throwable t) {
		if (errorHandler != null) {
			errorHandler.uncaughtException(t);
		}
	}

}
