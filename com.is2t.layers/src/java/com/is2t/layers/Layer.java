/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.layers;

import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;

public interface Layer {

	/**
	 * Gets the y coordinate.
	 * 
	 * @return the x coordinate.
	 */
	int getX();

	/**
	 * Sets the x coordinate value.
	 * 
	 * @param x
	 *            the x to set.
	 */
	void setX(int x);

	/**
	 * Gets the x coordinate.
	 * 
	 * @return the y coordinate.
	 */
	int getY();

	/**
	 * Sets the y coordinate value.
	 * 
	 * @param y
	 *            the y to set.
	 */
	void setY(int y);

	Image getImage();

	void setImage(Image image);

	int getTransparencyLevel();

	void setTransparencyLevel(int transparency);

	int getAnchor();

	void setAnchor(int anchor);
	
	void stack(AbstractLayersManager layersManager);

}
