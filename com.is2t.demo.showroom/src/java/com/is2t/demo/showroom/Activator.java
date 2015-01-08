/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom;

import com.is2t.demo.launcher.Launcher;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.components.util.ActivatorAdapter;
import ej.microui.io.Display;

public class Activator extends ActivatorAdapter {

	private Launcher desktopLauncher;

	@Override
	public void initialize(String parameters) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		this.desktopLauncher = new DesktopLauncherWidgets(display, timer);
		RegistryFactory.getRegistry().register(Launcher.class, desktopLauncher);
		RegistryFactory.getRegistry().register(Timer.class, timer);
	}

	@Override
	public void start(String parameters) {
		desktopLauncher.start();
	}
}
