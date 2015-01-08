/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public class SimpleMosaic extends Mosaic {

	@Override
	public void apply(GraphicsContext g, int containerWidth, int contenerHeight, Image pattern) {
		int patternWidth = pattern.getWidth();
		int patternHeight = pattern.getHeight();
		
		int columnCount = containerWidth / patternWidth + 1;
		int lineCount = contenerHeight / patternHeight + 1;

		int x = 0;
		for (int column = columnCount; --column >= 0;) {
			int y = 0;
			for (int line = lineCount; --line >= 0;) {
				g.drawImage(pattern, x, y, GraphicsContext.LEFT | GraphicsContext.TOP);
				y += patternHeight;
			}
			x += patternWidth;
		}
	}
}
