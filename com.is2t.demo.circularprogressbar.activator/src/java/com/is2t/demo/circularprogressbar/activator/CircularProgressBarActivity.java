/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar.activator;

import java.io.IOException;

import com.is2t.demo.circularprogressbar.CircularProgressBar;
import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class CircularProgressBarActivity implements Activity {

	private Display display;
	private CircularProgressBar heater;
	private Launcher launcher;

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void start() {
		try {
			this.heater = new CircularProgressBar(this.display, RegistryFactory.getRegistry().getService(Timer.class), launcher);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		this.heater.destroy();
		this.heater = null;
	}

	@Override
	public Displayable getDisplayable() {
		return this.heater.getDisplayable();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
