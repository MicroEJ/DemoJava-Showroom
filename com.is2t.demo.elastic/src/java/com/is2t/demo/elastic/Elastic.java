/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.utilities.ImageLoader;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.microui.Listener;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.View;
import ej.microui.io.Viewable;
import ej.mwt.rendering.Look;

public class Elastic {

	private ElasticModel model;
	private Viewable viewable;
	private LayersManager layersManager;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		Elastic elastic = new Elastic(display, timer, null);
		elastic.getViewable().show();
	}

	public Elastic(Display display, Timer timer, Launcher launcher) {
		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(
				display);
		MonitoringLayersManager.setLocation(0, 0);
		MonitoringLayersManager.setMonitoringLook(new Look() {

			@Override
			public int getProperty(int resource) {
				switch (resource) {
				case GET_FOREGROUND_COLOR_DEFAULT:
					return 0xffffff;

				case GET_BACKGROUND_COLOR_DEFAULT:
					return 0x0;

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

		int width = display.getWidth();
		int height = display.getHeight();
		ImageLoader imageLoader = new ImageLoader(display);

		model = new ElasticModel(width, height, timer, launcher);
		model.getBall().setCoordinates(width / 2, height / 2);
		View view = new ElasticView(0, 0, width, height, imageLoader);
		view.setModel(model);
		Listener controller = new ElasticController(model);
		viewable = new Viewable(display);
		viewable.setComponentView(view);
		viewable.setEventListener(controller);

		layersManager.show();
	}

	public void destroy() {
		model.destroy();
		layersManager.hide();
	}

	public Viewable getViewable() {
		return viewable;
	}
}
