/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.layers;

import ej.microui.Listener;
import ej.microui.io.Display;
import ej.microui.layer.AbstractLayersManager;

public class LayersManager extends AbstractLayersManager implements Listener {

	private LayersHandler layersHandler;
	private boolean displayAtLast;

	public LayersManager(Display display) {
		super(display);
	}

	public void setLayersHandler(LayersHandler layersHandler) {
		if (this.layersHandler != null) {
			this.layersHandler.removeListener(this);
		}
		this.layersHandler = layersHandler;
		layersHandler.addListener(this);
	}

	@Override
	protected void stackLayers() {
		if (layersHandler != null) {
			try {
				if (layersHandler.hasBackgroundColor()) {
					setBackgroundColor(layersHandler.getBackgroundColor());
				}
				if(!displayAtLast) {
					addLayer(layersHandler.getDisplayTransparencyLevel());
					stackHandlerLayers();
				}else {
					stackHandlerLayers();
					addLayer(layersHandler.getDisplayTransparencyLevel());
				}
			} catch (NullPointerException e) {
				return; // too late to show this layer
			}
		} else {
			// always add display
			addLayer(Transparency.OPAQUE);
		}
	}

	private void stackHandlerLayers() {
		Layer[] layers = layersHandler.getLayers();
		int layersLength = layers.length;
		for (int i = -1; ++i < layersLength;) {
			Layer layer = layers[i];
			layer.stack(this);
		}
	}

	@Override
	public void performAction() {
		flush();
	}

	@Override
	public void performAction(int value) {
		flush();
	}

	@Override
	public void performAction(int value, Object object) {
		flush();
	}
	
	public void setDisplayAtLast(boolean displayAtLast) {
		this.displayAtLast = displayAtLast;
	}
}
