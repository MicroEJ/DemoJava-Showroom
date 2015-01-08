/*
 * Java
 * 
 * Copyright 2014 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.demo.utilities.automaton.impl;

import com.is2t.demo.utilities.RunnableTask;
import com.is2t.demo.utilities.activity.ActivityListener;
import com.is2t.demo.utilities.activity.ActivitySupervisor;
import com.is2t.demo.utilities.activity.EventsActivitySupervisor;
import com.is2t.demo.utilities.automaton.Automaton;
import com.is2t.demo.utilities.automaton.AutomatonFactory;
import com.is2t.demo.utilities.automaton.AutomatonManager;
import com.is2t.demo.utilities.errors.ErrorHandler;

import ej.bon.Timer;
import ej.bon.TimerTask;

/**
 * Starts an automaton that simulates user interaction.
 * <p>
 * It can be started immediately of after a period of inactivity.
 */
public class AutomatonManagerImpl<R extends Automaton> implements AutomatonManager<R> {

	private ActivitySupervisor activitySupervisor;
	private AutomatonFactory<R> automatonFactory;
	private Timer timer;
	private ErrorHandler errorHandler;

	private TimerTask task;
	private R automaton;

	/**
	 * Default constructor. Useful for dependency injection.
	 */
	public AutomatonManagerImpl() {
	}

	/**
	 * Utility constructor that creates an automaton manager with an {@link EventsActivitySupervisor}.
	 * 
	 * @param timer
	 *            the timer to schedule on.
	 * @param automatonFactory
	 *            the automaton factory that create and stop the automaton.
	 * @param inactivityDuration
	 *            the minimum inactivity duration before the automaton is started.
	 */
	public AutomatonManagerImpl(Timer timer, AutomatonFactory<R> automatonFactory, long inactivityDuration) {
		setTimer(timer);
		setAutomatonFactory(automatonFactory);
		setActivitySupervisor(new EventsActivitySupervisor(timer, inactivityDuration));
	}

	@Override
	public void setActivitySupervisor(ActivitySupervisor activitySupervisor) {
		if (this.activitySupervisor != null) {
			// stop and unregister
			this.activitySupervisor.stop();
			this.activitySupervisor.setActivityListener(null);
		}
		this.activitySupervisor = activitySupervisor;
		if (activitySupervisor != null) {
			activitySupervisor.setActivityListener(new ActivityListener() {
				@Override
				public void active() {
					stopAutomaton();
				}

				@Override
				public void inactive() {
					startAutomaton();
				}
			});
		}
	}

	@Override
	public void setAutomatonFactory(AutomatonFactory<R> automatonFactory) {
		this.automatonFactory = automatonFactory;
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void stop() {
		setActivitySupervisor(null);
		stopAutomaton();
	}

	public void arm() {
		if (activitySupervisor != null) {
			activitySupervisor.arm();
		} else {
			startAutomaton();
		}
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	private synchronized void startAutomaton() {
		if (automaton != null) {
			// already started
			return;
		}

		try {
			this.automaton = automatonFactory.createAutomaton();
			this.task = new RunnableTask(automaton, errorHandler);
			timer.schedule(task, 0, automaton.getPeriod());
		} catch (Throwable t) {
			uncaughtException(t);
		}
	}

	private synchronized void stopAutomaton() {
		if (automaton != null) {
			try {
				automatonFactory.stopAutomaton(automaton);
			} catch (Throwable t) {
				uncaughtException(t);
			}
			automaton = null;
		}
		TimerTask t = task;
		this.task = null;
		if (t != null) {
			t.cancel();
		}
	}

	private void uncaughtException(Throwable t) {
		if (errorHandler != null) {
			errorHandler.uncaughtException(t);
		}
	}

}
