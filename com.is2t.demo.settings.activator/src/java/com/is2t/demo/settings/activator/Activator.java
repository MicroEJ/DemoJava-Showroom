/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.activator;

import com.is2t.demo.launcher.ActivityMetaData;
import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.Pictos;

import ej.components.RegistryFactory;
import ej.components.util.ActivatorAdapter;

public class Activator extends ActivatorAdapter {

	private SettingsActivity settingsActivity;

	@Override
	public void initialize(String parameters) {
		settingsActivity = new SettingsActivity();
	}

	@Override
	public void link(String parameters) {
		Launcher launcher = RegistryFactory.getRegistry().getService(Launcher.class);
		settingsActivity.setLauncher(launcher);
		settingsActivity.setDisplay(launcher.getDisplay());
		launcher.add(settingsActivity, new ActivityMetaData() {

			@Override
			public char getPicto() {
				return Pictos.Settings;
			}

			@Override
			public String getName() {
				return "Settings";
			}
		});
	}
}
