/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.activator;

import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.settings.Settings;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class SettingsActivity implements Activity {

	private Display display;
	private Settings settings;

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void start() {
		settings = new Settings(display, RegistryFactory.getRegistry().getService(Timer.class));
	}

	@Override
	public void pause() {
		stop();
	}

	@Override
	public void resume() {
		start();
	}

	@Override
	public void stop() {
		settings.destroy();
		settings = null;
	}

	@Override
	public Displayable getDisplayable() {
		return settings.getDesktop();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		Settings.launcher = launcher;
	}
}
