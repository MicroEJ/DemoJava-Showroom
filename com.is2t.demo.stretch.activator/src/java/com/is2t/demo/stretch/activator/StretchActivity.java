/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch.activator;


import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.stretch.Stretch;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class StretchActivity implements Activity {

	private Display display;
	private Stretch stretch;
	private Launcher launcher;
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	@Override
	public void start() {
		stretch = new Stretch(display,  RegistryFactory.getRegistry().getService(Timer.class), launcher);
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
		stretch.destroy();
		stretch = null;
	}

	@Override
	public Displayable getDisplayable() {
		return stretch.getViewable();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
