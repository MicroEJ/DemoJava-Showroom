/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public class QuarterMosaic extends Mosaic {

	public static final int TILE_FACTOR = 2;
	
	private int xOffset, yOffset;	// 0 init
	
	
	@Override
	public void apply(GraphicsContext dest, Image pattern, int containerWidth, int contenerHeight, int defaultColor) {

		int iw = pattern.getWidth();
		int ih = pattern.getHeight();
		
		if (iw * 2 < containerWidth || ih * 2  < contenerHeight) {
			// center the mozaic
			dest.setColor(defaultColor);
			dest.fillRect(0, 0, containerWidth, contenerHeight);
			xOffset = (containerWidth - iw * 2) / 2;
			yOffset = (contenerHeight - ih * 2) / 2;
		}
		else {
			xOffset = yOffset = 0;
		}
		
		apply(dest, containerWidth, contenerHeight, pattern);
	}
	
	@Override
	public void apply(GraphicsContext g, int containerWidth, int contenerHeight, Image pattern) {
		
		int xOffset = this.xOffset;
		int yOffset = this.yOffset;
		
		int tileWidth = pattern.getWidth();
		int tileHeight = pattern.getHeight();
		// Top left quarter.
		g.drawImage(pattern, xOffset, yOffset, GraphicsContext.LEFT | GraphicsContext.TOP);
		// Top right quarter.
		g.drawDeformedImage(pattern, 2 * tileWidth - 1 + xOffset, yOffset, new int[] { 0, 0, -(tileWidth - 1),
				0, -(tileWidth - 1), tileHeight - 1, 0, tileHeight - 1 }, GraphicsContext.LEFT
				| GraphicsContext.TOP);
		// Bottom right quarter.
		g.drawDeformedImage(pattern, xOffset, 2 * tileHeight - 1 + yOffset, new int[] { 0, 0, tileWidth - 1, 0,
				tileWidth - 1, -(tileHeight - 1), 0, -(tileHeight - 1) }, GraphicsContext.LEFT
				| GraphicsContext.TOP);
		// Bottom right quarter.
		g.drawDeformedImage(pattern, 2 * tileWidth - 1 + xOffset, 2 * tileHeight - 1 + yOffset, new int[] { 0, 0,
				-(tileWidth - 1), 0, -(tileWidth - 1), -(tileHeight - 1), 0, -(tileHeight - 1) },
				GraphicsContext.LEFT | GraphicsContext.TOP);
	}
}
