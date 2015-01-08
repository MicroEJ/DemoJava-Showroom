/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.layers;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;

public class SimpleLayer implements Layer {

	int anchor;
	int transparency;
	Image image;
	int x;
	int y;

	public SimpleLayer(Image image) {
		this(0, 0, image);
	}
	
	public SimpleLayer(int x, int y, Image image) {
		this(x, y, image, 0xff, GraphicsContext.LEFT | GraphicsContext.TOP);
	}

	public SimpleLayer(int x, int y, Image image, int transparency, int anchor) {
		setLocation(x, y);
		this.image = image;
		this.transparency = transparency;
		this.anchor = anchor;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public int getTransparencyLevel() {
		return transparency;
	}

	@Override
	public void setTransparencyLevel(int transparency) {
		this.transparency = transparency;
	}

	@Override
	public int getAnchor() {
		return anchor;
	}

	@Override
	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}
	
	@Override
	public void stack(AbstractLayersManager layersManager) {
		layersManager.addLayer(image, transparency, x, y, anchor);
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + getImage().getWidth() + " " + getImage().getHeight() + " "+ getTransparencyLevel();
	}

}
