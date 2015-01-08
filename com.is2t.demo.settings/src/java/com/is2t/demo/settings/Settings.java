/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.settings.page.AppPage;
import com.is2t.demo.settings.page.SettingsPage;
import com.is2t.demo.settings.page.TransitionsHelper;
import com.is2t.demo.settings.theme.SettingsTheme;
import com.is2t.layers.LayersManager;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionDesktop;
import ej.flow.stacked.StackedFlowManager;
import ej.flow.stacked.cached.CachedStackManager;
import ej.microui.io.Display;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Theme;

public class Settings {

	public static Launcher launcher;
	private LayersManager layersManager;
	private TransitionDesktop desktop;
	private MWTFlowManager<AppPage, SettingsPage> mwtFlowManager;
	private Theme theme;

	public static void main(String[] args) {
		Display display = Display.getDefaultDisplay();
		Timer timer = new Timer();
		MonitoringLayersManager.setTimer(timer);
		Settings settings = new Settings(display, timer);
		settings.getDesktop().show();
	}

	public Settings(Display display, Timer timer) {
		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(display);
		MonitoringLayersManager.setLocation(display.getWidth() - MonitoringLayersManager.getWidth() - 70, 0);

		theme = new SettingsTheme();
		Look look = theme.getLook();
		MWT.RenderingContext.add(theme);
		TransitionsHelper.setLook(look);
		mwtFlowManager = TransitionsHelper.getMWTFlowManager();
		StackedFlowManager<AppPage, SettingsPage> stackedFlowManager = new StackedFlowManager<AppPage, SettingsPage>();
		stackedFlowManager.setStackManager(new CachedStackManager<AppPage, SettingsPage>());
		mwtFlowManager.setFlowManager(stackedFlowManager);
		MonitoringLayersManager.setMonitoringLook(look);
		layersManager = monitoringLayersManager;

		desktop = new TransitionDesktop(display);
		mwtFlowManager.setDesktop(desktop);
		mwtFlowManager.setTransitionListener(desktop);
		mwtFlowManager.setTimer(timer);
		mwtFlowManager.goTo(AppPage.MainPage);
		layersManager.show();
	}

	public void destroy() {
		layersManager.hide();
		mwtFlowManager = null;
		MWT.RenderingContext.remove(theme);
	}

	public Desktop getDesktop() {
		return desktop;
	}
}
