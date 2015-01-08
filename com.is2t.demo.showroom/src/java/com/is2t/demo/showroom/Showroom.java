/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom;

import java.util.Calendar;

import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.bon.Util;
import ej.components.RegistryFactory;
import ej.microui.MicroUI;

public class Showroom {

	private static final int STARTUP_YEAR = 2014;
	private static final int STARTUP_MONTH = Calendar.OCTOBER;
	private static final int STARTUP_DAY = 27;
	private static final int STARTUP_HOUR = 9;
	private static final int STARTUP_MINUTE = 42;

	public static final String COMPONENTS = System.getProperty("components");

	public static void main(String[] args) {
		MicroUI.errorLog(true);
		updateCurrentTime();
		String components = COMPONENTS == null ? "/components/components.properties"
				: COMPONENTS;
		RegistryFactory.startupRegistry(RegistryFactory.getRegistry(),
				components);
		MonitoringLayersManager.setTimer(RegistryFactory.getRegistry()
				.getService(Timer.class));
	}

	private static void updateCurrentTime() {
		Calendar time = Calendar.getInstance();
		time.set(STARTUP_YEAR, STARTUP_MONTH, STARTUP_DAY, STARTUP_HOUR,
				STARTUP_MINUTE);
		Util.setCurrentTimeMillis(time.getTimeInMillis());
	}

}
