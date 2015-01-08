/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import com.is2t.layers.Layer;
import com.is2t.layers.LayersHandler;
import com.is2t.layers.Transparency;

import ej.microui.Listener;

public class LayersHandlerAdapter implements LayersHandler {

	@Override
	public boolean hasBackgroundColor() {
		return false;
	}

	@Override
	public int getBackgroundColor() {
		return 0;
	}

	@Override
	public Layer[] getLayers() {
		return new Layer[] {};
	}

	@Override
	public int getDisplayTransparencyLevel() {
		return Transparency.TRANSPARENT;
	}

	@Override
	public void addListener(Listener listener) {
	}

	@Override
	public void removeListener(Listener listener) {
	}
}
