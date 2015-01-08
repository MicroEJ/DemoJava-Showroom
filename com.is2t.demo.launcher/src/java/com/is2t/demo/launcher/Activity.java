/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.launcher;

import ej.microui.io.Displayable;

public interface Activity {

	void start();

	void pause();

	void resume();

	void stop();
	
	Displayable getDisplayable();
	
	void setLauncher(Launcher launcher);
}
