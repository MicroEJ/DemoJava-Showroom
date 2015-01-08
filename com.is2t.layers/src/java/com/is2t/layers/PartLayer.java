/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */package com.is2t.layers;

import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;

public class PartLayer extends SimpleLayer {

	int xPart;
	int yPart;
	int widthPart;
	int heightPart;

	public PartLayer(Image image) {
		super(image);
	}

	public PartLayer(int x, int y, Image image) {
		super(x, y, image);
	}

	public PartLayer(int x, int y, Image image, int transparency, int anchor) {
		super(x, y, image, transparency, anchor);
	}

	public void setPart(int x, int y, int width, int height) {
		this.xPart = x;
		this.yPart = y;
		this.widthPart = width;
		this.heightPart = height;
	}

	@Override
	public void stack(AbstractLayersManager layersManager) {
		layersManager.addLayer(image, transparency, xPart, yPart, widthPart, heightPart, x, y, anchor);
	}

}
