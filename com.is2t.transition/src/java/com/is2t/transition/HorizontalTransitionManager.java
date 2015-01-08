/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionManager;

/**
 * Defines a horizontal translation transition. Both panels move: the old one goes out, the new one comes in.
 */
public class HorizontalTransitionManager extends TranslationTransitionManager implements TransitionManager {

	@Override
	public int getBound(MWTFlowManager<?, ?> flowManager, boolean forward) {
		int desktopWidth = flowManager.getDesktop().getWidth();
		return isReversed() ? -desktopWidth : desktopWidth;
	}

	@Override
	public void step(MWTFlowManager<?, ?> flowManager, int value) {
		int xOldPanel;
		int xNewPanel;
		int xPreviousPanel = isOver() ? 0 : value + getShift();
		if (isForward()) {
			xOldPanel = xPreviousPanel;
			xNewPanel = value;
		} else {
			xOldPanel = value;
			xNewPanel = xPreviousPanel;
		}
		move(flowManager, xOldPanel, 0, xNewPanel, 0, value);
	}

}
