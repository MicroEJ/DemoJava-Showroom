/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public abstract class Mosaic {
	
	
	public void apply(Image dest, Image pattern) {
		apply(dest.getGraphicsContext(), dest.getWidth(), dest.getHeight(), pattern);
	}
	
	public abstract void apply(GraphicsContext dest, int containerWidth, int contenerHeight, Image pattern);
	
	/**
	 * Optional default color, does not use it by default
	 */
	public void apply(GraphicsContext dest, Image pattern, int containerWidth, int contenerHeight, int defaultColor) {
		apply(dest, containerWidth, contenerHeight, pattern);
	}
}
