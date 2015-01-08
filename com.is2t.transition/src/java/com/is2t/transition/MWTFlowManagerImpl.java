/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import ej.bon.Timer;
import ej.flow.FlowManager;
import ej.flow.Page;
import ej.flow.PageType;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.MWTPage;
import ej.flow.mwt.TransitionListener;
import ej.flow.mwt.TransitionManager;
import ej.motion.Motion;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.Desktop;
import ej.mwt.Panel;

/**
 * It helps to use the flow manager on MWT. It displays the pages on the screen and provides animations while
 * navigating.
 * 
 * @param <T>
 *            the type of the pages type.
 * @param <P>
 *            the type of pages to manage.
 */
public class MWTFlowManagerImpl<T extends PageType<P>, P extends Page<T> & MWTPage> implements FlowManager<T, P>,
		MWTFlowManager<T, P> {

	private Desktop desktop;
	private Timer timer;
	private FlowManager<T, P> flowManager;
	private TransitionManager transitionManager;
	private TransitionListener transitionListener;

	private Panel currentPanel;
	private Panel oldPanel;
	private MotionAnimator animator;

	@Override
	public void setFlowManager(FlowManager<T, P> flowManager) {
		this.flowManager = flowManager;
	}

	@Override
	public FlowManager<T, P> getFlowManager() {
		return flowManager;
	}

	@Override
	public void setTransitionManager(TransitionManager transitionManager) {
		cancelTransition();
		this.transitionManager = transitionManager;
	}

	@Override
	public void setDesktop(Desktop desktop) {
		this.desktop = desktop;
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public Desktop getDesktop() {
		return desktop;
	}

	/**
	 * Gets the current panel.
	 * 
	 * @return the current panel.
	 */
	@Override
	public Panel getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * Gets the old panel.
	 * 
	 * @return the old panel.
	 */
	@Override
	public Panel getOldPanel() {
		return oldPanel;
	}

	@Override
	public P goTo(T type) {
		cancelTransition();
		startTransitionNotify();

		try {
			P oldPage = getCurrentPage();
			P newPage = flowManager.goTo(type);
			doTransition(oldPage, newPage, true);
			return newPage;
		} catch (IllegalArgumentException e) {
			stopTransitionNotify();
			throw e;
		}
	}

	@Override
	public P back() {
		cancelTransition();
		startTransitionNotify();

		try {
			P oldPage = getCurrentPage();
			P newPage = flowManager.back();
			doTransition(oldPage, newPage, false);
			return newPage;
		} catch (IllegalStateException e) {
			stopTransitionNotify();
			throw e;
		}
	}

	@Override
	public P backUntil(T type) {
		cancelTransition();
		startTransitionNotify();

		try {
			P oldPage = getCurrentPage();
			P newPage = flowManager.backUntil(type);
			doTransition(oldPage, newPage, false);
			return newPage;
		} catch (IllegalArgumentException e) {
			stopTransitionNotify();
			throw e;
		}
	}

	@Override
	public void clearHistory() {
		flowManager.clearHistory();
	}

	@Override
	public P getCurrentPage() {
		return flowManager.getCurrentPage();
	}

	private void doTransition(P from, P to, boolean forward) {
		if (from != null) {
			from.hideNotify();
		}

		Panel newPanel = createPanel(to);

		to.showNotify();

		if (from != null) {
			try {
				synchronized (this) {
					oldPanel = currentPanel;
					currentPanel = newPanel;

					// do the real transition!!
					Motion motion = transitionManager.getMotion(this, forward);
					this.animator = new MotionAnimator(motion, new TransitionMotionListener());

					// start animation
					animator.start(timer, transitionManager.getPeriod());
				}
			} catch (Throwable t) {
				// no transition manager or no motion can be created.
				// no error handler…
				// t.printStackTrace();

				// call show before hide to avoid flickering:
				// (1) the hide asks for a repaint of the desktop after removing old panel,
				// (2) the show asks for a revalidate of the new panel after adding it,
				// (3) the new panel is paint (because of 1) before its validation is done.
				showNewPanel(newPanel);
				cancelTransition();
			}
		} else {
			// first panel
			showNewPanel(newPanel);
			stopTransitionNotify();
		}
	}

	private void showNewPanel(Panel newPanel) {
		currentPanel = newPanel; // no need to synchronize this atomic modification
		newPanel.show(desktop, true);
	}

	private void cancelTransition() {
		synchronized (this) {
			MotionAnimator oldAnimator = this.animator;
			this.animator = null;
			if (oldAnimator != null) {
				oldAnimator.stop();
			}
			stopTransitionNotify();
			Panel oldOldPanel = this.oldPanel;
			this.oldPanel = null;
			if (oldOldPanel != null) {
				oldOldPanel.hide();
				// free the widget
				oldOldPanel.setWidget(null);
			}
		}
	}

	private Panel createPanel(P page) {
		Panel panel = new Panel();
		panel.setWidget(page.getWidget());
		return panel;
	}

	@Override
	public void setTransitionListener(TransitionListener transitionListener) {
		this.transitionListener = transitionListener;
	}

	@Override
	public TransitionListener getTransitionListener() {
		return transitionListener;
	}

	private void startTransitionNotify() {
		TransitionListener transitionListener = this.transitionListener;
		if (transitionListener != null) {
			transitionListener.start();
		}
	}

	private void stopTransitionNotify() {
		TransitionListener transitionListener = this.transitionListener;
		if (transitionListener != null) {
			transitionListener.stop();
		}
	}

	private final class TransitionMotionListener implements MotionListener {

		@Override
		public void start(int value) {
			transitionManager.start(MWTFlowManagerImpl.this, value);
		}

		@Override
		public void step(int value) {
			transitionManager.step(MWTFlowManagerImpl.this, value);
		}

		@Override
		public void stop(int value) {
			transitionManager.stop(MWTFlowManagerImpl.this, value);
			desktop.getDisplay().callSerially(new Runnable() {
				@Override
				public void run() {
					currentPanel.setLocation(0, 0);
					currentPanel.repaint();
				}
			});
			cancelTransition();
		}
	}

}