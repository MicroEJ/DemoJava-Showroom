/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.layers;

import ej.microui.Listener;

public interface LayersHandler {

	boolean hasBackgroundColor();

	int getBackgroundColor();

	Layer[] getLayers();

	int getDisplayTransparencyLevel();

	void addListener(Listener listener);

	void removeListener(Listener listener);

}
