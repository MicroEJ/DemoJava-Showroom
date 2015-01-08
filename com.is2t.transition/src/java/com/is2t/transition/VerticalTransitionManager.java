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
 * Defines a vertical translation transition. Both panels move: the old one goes out, the new one comes in.
 */
public class VerticalTransitionManager extends TranslationTransitionManager implements TransitionManager {

	@Override
	public int getBound(MWTFlowManager<?, ?> flowManager, boolean forward) {
		int desktopHeight = flowManager.getDesktop().getHeight();
		return isReversed() ? -desktopHeight : desktopHeight;
	}

	@Override
	public void step(MWTFlowManager<?, ?> flowManager, int value) {
		int yOldPanel;
		int yNewPanel;
		int yPreviousPanel = isOver() ? 0 : value + getShift();
		if (isForward()) {
			yOldPanel = yPreviousPanel;
			yNewPanel = value;
		} else {
			yOldPanel = value;
			yNewPanel = yPreviousPanel;
		}
		move(flowManager, 0, yOldPanel, 0, yNewPanel, value);
	}

}
