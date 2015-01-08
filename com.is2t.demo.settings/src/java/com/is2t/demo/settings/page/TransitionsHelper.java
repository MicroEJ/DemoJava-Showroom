/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import java.util.Stack;

import com.is2t.transition.HorizontalTransitionManager;
import com.is2t.transition.MWTFlowManagerImpl;
import com.is2t.transition.TranslationTransitionManager;
import com.is2t.transition.VerticalTransitionManager;

import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionManager;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.mwt.rendering.Look;

public class TransitionsHelper {

	private static final int DURATION = 800;
	private static final int PERIOD = 50;
	private static final boolean ANIMATIONS_DISABLED = System.getProperty("com.is2t.demo.NoAnimation") != null;
	private static final boolean LAYERS_DISABLED = System.getProperty("com.is2t.demo.NoLayer") != null;

	private static final TransitionManager HORIZONTAL_MANAGER;
	private static final TransitionManager VERTICAL_MANAGER;
	private static final Stack<TransitionInfo> TRANSITIONS_INFOS;
	private static final MWTFlowManager<AppPage, SettingsPage> MWTFLOWMANAGER;

	static {
		TRANSITIONS_INFOS = new Stack<>();
		MotionManager motionManager = null;

		if (!ANIMATIONS_DISABLED) {
			motionManager = new EaseMotionManager();
		}

		if (LAYERS_DISABLED) {
			HORIZONTAL_MANAGER = new ej.flow.mwt.translation.HorizontalTransitionManager();
			HORIZONTAL_MANAGER.setMotionManager(motionManager);
			HORIZONTAL_MANAGER.setDuration(DURATION);
			HORIZONTAL_MANAGER.setPeriod(PERIOD);
			VERTICAL_MANAGER = new VerticalTransitionManager();
			VERTICAL_MANAGER.setMotionManager(motionManager);
			VERTICAL_MANAGER.setDuration(DURATION);
			VERTICAL_MANAGER.setPeriod(PERIOD);
			MWTFLOWMANAGER = new ej.flow.mwt.impl.MWTFlowManagerImpl<>();
		} else {
			HORIZONTAL_MANAGER = new HorizontalTransitionManager();
			HORIZONTAL_MANAGER.setMotionManager(motionManager);
			HORIZONTAL_MANAGER.setDuration(DURATION);
			HORIZONTAL_MANAGER.setPeriod(PERIOD);
			VERTICAL_MANAGER = new VerticalTransitionManager();
			VERTICAL_MANAGER.setMotionManager(motionManager);
			VERTICAL_MANAGER.setDuration(DURATION);
			VERTICAL_MANAGER.setPeriod(PERIOD);
			MWTFLOWMANAGER = new MWTFlowManagerImpl<>();
		}
	}

	private TransitionsHelper() {
	}

	public static MWTFlowManager<AppPage, SettingsPage> getMWTFlowManager() {
		return MWTFLOWMANAGER;
	}

	public static SettingsPage goTo(AppPage appPage, boolean horizontal, boolean forward) {
		MWTFLOWMANAGER.setTransitionManager(getTransitionManager(horizontal, forward));
		// flowManager.clearHistory();
		TRANSITIONS_INFOS.push(new TransitionInfo(horizontal, forward));
		SettingsPage goTo = MWTFLOWMANAGER.goTo(appPage);
		return goTo;
	}

	public static void back() {
		TransitionInfo transitionInfo;

		if (TRANSITIONS_INFOS.isEmpty()) {
			transitionInfo = new TransitionInfo(true, false);
		} else {
			transitionInfo = TRANSITIONS_INFOS.pop();
		}

		MWTFLOWMANAGER.setTransitionManager(getTransitionManager(transitionInfo));
		MWTFLOWMANAGER.back();
	}

	private static TransitionManager getTransitionManager(TransitionInfo transitionInfo) {
		return getTransitionManager(transitionInfo.horizontal, transitionInfo.forward);
	}

	private static TransitionManager getTransitionManager(boolean horizontal) {
		if (horizontal) {
			return HORIZONTAL_MANAGER;
		} else {
			return VERTICAL_MANAGER;
		}
	}

	private static TransitionManager getTransitionManager(boolean horizontal, boolean forward) {
		TransitionManager transitionManager = getTransitionManager(horizontal);

		if (transitionManager instanceof TranslationTransitionManager) {
			((TranslationTransitionManager) transitionManager).reverseTransition(!forward);
		} else {
			((ej.flow.mwt.translation.TranslationTransitionManager) transitionManager).reverseTransition(!forward);
		}

		return transitionManager;
	}

	public static void setLook(Look look) {
		HORIZONTAL_MANAGER.setLook(look);
		VERTICAL_MANAGER.setLook(look);
	}

	private static class TransitionInfo {

		boolean horizontal;
		boolean forward;

		TransitionInfo(boolean horizontal, boolean forward) {
			this.horizontal = horizontal;
			this.forward = forward;
		}
	}
}
