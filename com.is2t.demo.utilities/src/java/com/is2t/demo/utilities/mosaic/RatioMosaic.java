/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

/**
 * TODO: add yRatios
 */
public class RatioMosaic extends Mosaic {

	private final float[] xRatios;
	private final int[] xOffsets;
	private Image oldPattern;	// null init
	
	public RatioMosaic(float[] xRatios) {
		this.xRatios = xRatios;
		this.xOffsets = new int[xRatios.length];
	}
	
	@Override
	public void apply(GraphicsContext g, int containerWidth, int contenerHeight, Image pattern) {
		
		int tileWidth = pattern.getWidth();
		int tileHeight = pattern.getHeight();
		
		// get X offsets according the pattern (may be this pattern has been already loaded)
		int[] xOffsets = this.xOffsets;
		int xRatiosLength = xOffsets.length;
		if (oldPattern != pattern) {
			float[] xRatios = this.xRatios;
			for(int i = xRatiosLength; --i>=0;) {
				xOffsets[i] = (int)(xRatios[i] * tileWidth);
			}
			oldPattern = pattern;
		}
		
		int offsetIndex = 0;
		for (int y = 0; y < contenerHeight; y += tileHeight) {
			int x = xOffsets[offsetIndex];
			offsetIndex = (offsetIndex + 1) % xRatiosLength;

			for (; x < containerWidth; x += tileWidth) {
				g.drawImage(pattern, x, y, GraphicsContext.LEFT | GraphicsContext.TOP);
			}
		}
	}
}
