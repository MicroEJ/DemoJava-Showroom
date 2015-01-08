/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

/**
 * @deprecated, use {@link BackgroundImageHelper} instead
 */
public class BackgroundDrawerImpl implements BackgroundDrawer {

	private final ImageProvider imageProvider;

	public BackgroundDrawerImpl(ImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}

	@Override
	public void draw(GraphicsContext g, int containerWidth, int contenerHeight, int defaultColor) {
		Image image = imageProvider.getImage();

		int width = g.getClipWidth();
		int height = g.getClipHeight();

		if (image == null) {
			// use the default color
			g.setColor(defaultColor);
			g.fillRect(0, 0, width, height);
		} else if (image.getWidth() < width || image.getHeight() < height) {
			// draw a pattern
			new SimpleMosaic().apply(g, containerWidth, contenerHeight, image);
		} else {
			// draw the image
			g.drawImage(image, width >> 1, height >> 1, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}
	}

}
