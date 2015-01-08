/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.launcher;

import ej.microui.io.Display;

public interface Launcher extends Activity {

	void launch(Activity activity);
	
	void add(Activity activity, ActivityMetaData metaData);
	
	void remove(Activity activity);
	
	void add(Background background);
	
	void remove(Background background);
	
	Display getDisplay();
}
