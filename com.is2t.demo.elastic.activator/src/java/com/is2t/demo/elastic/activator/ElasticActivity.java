/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic.activator;

import com.is2t.demo.elastic.Elastic;
import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class ElasticActivity implements Activity {

	private Display display;
	private Elastic elastic;
	private Launcher launcher;

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void start() {
		elastic = new Elastic(display, RegistryFactory.getRegistry().getService(Timer.class), launcher);
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
		elastic.destroy();
		elastic = null;
	}

	@Override
	public Displayable getDisplayable() {
		return elastic.getViewable();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
