/*
 * Java
 *
 * Copyright 2011-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch;

import java.io.IOException;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.utilities.ImageLoader;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.Image;
import ej.microui.io.View;
import ej.microui.io.Viewable;
import ej.mwt.rendering.Look;

public class Stretch {

	private static final String STRETCH_IMAGE = "stretch";

	private Viewable viewable;
	private LayersManager layersManager;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		Stretch stretch = new Stretch(display, timer, null);
		stretch.getViewable().show();
	}

	public Stretch(Display display, Timer timer, Launcher launcher) {
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
		Image image;
		try {
			image = imageLoader.load(STRETCH_IMAGE);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		int intialOffsetX = (width - image.getWidth()) / 2;
		int initialOffsetY = (height - image.getHeight()) / 2;
		StretchModel model = new StretchModel(image, intialOffsetX,
				initialOffsetY, width, height, launcher);
		View view = new ImageView(0, 0, width, height);
		view.setModel(model);
		StretchController controller = new StretchController(model);
		viewable = new Viewable(display);
		viewable.setComponentView(view);
		viewable.setEventListener(controller);
		viewable.show();
		layersManager.show();
	}

	public Viewable getViewable() {
		return viewable;
	}

	public void destroy() {
		layersManager.hide();
	}
}
