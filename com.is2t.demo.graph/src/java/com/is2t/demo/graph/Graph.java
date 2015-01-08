/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import com.is2t.demo.launcher.Launcher;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.Displayable;
import ej.mwt.rendering.Look;

public class Graph {

	private GraphDMA graphDMA;
	private LayersManager layersManager;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		Graph graph = new Graph(display, timer, null);
		graph.getDisplayable().show();
	}

	public Graph(Display display, Timer timer, Launcher launcher) {
		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(
				display, false);
		MonitoringLayersManager.setLocation(0, 0);
		MonitoringLayersManager.setMonitoringLook(new Look() {

			@Override
			public int getProperty(int resource) {
				switch (resource) {
				case GET_FOREGROUND_COLOR_DEFAULT:
					return 0x0;

				case GET_BACKGROUND_COLOR_DEFAULT:
					return 0xffffff;

				default:
					return 0;
				}
			}

			@Override
			public DisplayFont[] getFonts() {
				return null;
			}
		});
		layersManager = monitoringLayersManager;
		graphDMA = new GraphDMA(display, timer, launcher);
		layersManager.setLayersHandler(graphDMA);
		layersManager.show();
	}

	public Displayable getDisplayable() {
		return graphDMA;
	}

	public void destroy() {
		graphDMA.hide();
		layersManager.hide();
		graphDMA.destroy();
	}
}
