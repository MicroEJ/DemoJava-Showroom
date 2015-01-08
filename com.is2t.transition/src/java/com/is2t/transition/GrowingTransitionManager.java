/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import com.is2t.layers.Transparency;

import ej.flow.mwt.MWTFlowManager;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;
import ej.motion.Motion;
import ej.mwt.Desktop;
import ej.mwt.Panel;

/**
 * Defines transitions by moving panels.
 */
public class GrowingTransitionManager extends TranslationTransitionManager {

	private Image old;
	private int currentValue;

	@Override
	public Motion getMotion(MWTFlowManager<?, ?> flowManager, boolean forward) {
		this.forward = forward;
		int start;
		int stop;
		int width = flowManager.getDesktop().getWidth();
		if (forward) {
			start = 0;
			stop = width;
		} else {
			start = width;
			stop = 0;
		}
		return getMotion(start, stop, forward);
	}

	@Override
	protected int getBound(MWTFlowManager<?, ?> flowManager, boolean forward) {
		return flowManager.getDesktop().getWidth();
	}

	@Override
	public void start(MWTFlowManager<?, ?> flowManager, int value) {
		final Panel currentPanel = flowManager.getCurrentPanel();

		old = AbstractLayersManager.createImage(display);
		currentFlowManager = flowManager;
		Displayable displayable = new Displayable(display) {

			@Override
			public void performAction(int event) {
				//
			}

			@Override
			public void paint(GraphicsContext g) {
				AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());
				AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
				int xLeft = (display.getWidth() - currentValue) / 2;
				int xRight = xLeft + currentValue;
				int height = currentValue * display.getHeight() / display.getWidth();
				int yTop = (display.getHeight() - height) / 2;
				int yBottom = yTop + height;
				int[] xys = new int[] { xLeft, yTop, xRight, yTop, xRight, yBottom, xLeft, yBottom };
				g.drawDeformedImage(forward ? current : old, 0, 0, xys, 0);
			}

			@Override
			protected void showNotify() {
				createCurrentImage(currentPanel);
			}

			@Override
			protected void hideNotify() {
				super.hideNotify();
				old = null;
			}
		};

		displayable.show();

		if (USE_FADEOUT) {
			show();
		}

		step(flowManager, value);

	}

	@Override
	public void step(MWTFlowManager<?, ?> flowManager, int value) {
		currentValue = value;
		flowManager.getDesktop().getDisplay().getDisplayable().repaint();
	}

	@Override
	public void stop(MWTFlowManager<?, ?> flowManager, int value) {
		final Panel oldPanel = flowManager.getOldPanel();
		final Panel currentPanel = flowManager.getCurrentPanel();

		final Desktop desktop = oldPanel.getDesktop();
		currentPanel.show(desktop, true);
		desktop.getDisplay().callSerially(new Runnable() {
			@Override
			public void run() {
				desktop.show();

				hide();
			}
		});
	}

	@Override
	protected void stackLayers() {
		addLayer(forward ? old : current, Transparency.OPAQUE, 0, 0, display.getWidth(), display.getHeight(), 0, 0,
				GraphicsContext.LEFT | GraphicsContext.TOP);
		if (USE_FADEOUT) {
			float percent = (float) currentValue / display.getWidth();
			addLayer(veil, (int) (percent * Transparency.OPAQUE), 0, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
		addLayer(Transparency.OPAQUE);
		superStackLayers();
	}

}
