/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.domotic.activator;

import com.is2t.demo.domotic.Domotic;
import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class DomoticActivity implements Activity {

	private Display display;
	private Domotic domotic;
	private Launcher launcher;

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void start() {
		domotic = new Domotic(display, RegistryFactory.getRegistry().getService(Timer.class), launcher);
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
		domotic.stop();
		domotic = null;
	}

	@Override
	public Displayable getDisplayable() {
		return domotic.getDisplayable();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
