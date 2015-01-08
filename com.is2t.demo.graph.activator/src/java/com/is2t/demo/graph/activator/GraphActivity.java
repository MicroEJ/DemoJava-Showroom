/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph.activator;


import com.is2t.demo.graph.Graph;
import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.Launcher;

import ej.bon.Timer;
import ej.components.RegistryFactory;
import ej.microui.io.Display;
import ej.microui.io.Displayable;

public class GraphActivity implements Activity {

	private Display display;
	private Graph graph;
	private Launcher launcher;

	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void start() {
		graph = new Graph(display, RegistryFactory.getRegistry().getService(Timer.class), launcher);
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
		graph.destroy();
		graph = null;
	}

	@Override
	public Displayable getDisplayable() {
		return graph.getDisplayable();
	}

	@Override
	public void setLauncher(Launcher launcher) {
		this.launcher = launcher;
	}
}
