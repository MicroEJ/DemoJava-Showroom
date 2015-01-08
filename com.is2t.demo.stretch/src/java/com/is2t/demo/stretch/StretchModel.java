/*
 * Java
 *
 * Copyright 2011-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch;

import com.is2t.demo.launcher.Launcher;

import ej.microui.io.Image;

public class StretchModel extends ImageModel {

	public StretchModel(Image image, int x, int y, int availableWidth, int availableHeight, Launcher launcher) {
		super(image, x, y, availableWidth, availableHeight, launcher);
	}

	public void stretchTo(int x, int y) {
		int[] points = this.points;
		// update the nearest corner coordinates
		int minIndex = -1;
		int minDistance = Integer.MAX_VALUE;
		for (int i = -1; ++i < 4;) {
			int distance = distance(x, y, points[i * 2], points[i * 2 + 1]);
			if (distance < minDistance) {
				minIndex = i;
				minDistance = distance;
			}
		}

		if (minDistance != 0) {
			points[minIndex * 2] = x;
			points[minIndex * 2 + 1] = y;

			// keep the image rectangular (for speed reason)
			int tlx = points[0]; // tlx
			int tly = points[1]; // tly
			int trx = points[2]; // trx
			int trY = points[3]; // try
			int brx = points[4]; // brx
			int bry = points[5]; // bry
			int blx = points[6]; // blx
			int bly = points[7]; // bly
			// get average of coordinates
			int lx = (blx + tlx) / 2;
			int rx = (brx + trx) / 2;
			int ty = (tly + trY) / 2;
			int by = (bly + bry) / 2;

			points[0] = lx; // tlx
			points[1] = ty; // tly
			points[2] = rx; // trx
			points[3] = ty; // try
			points[4] = rx; // brx
			points[5] = by; // bry
			points[6] = lx; // blx
			points[7] = by; // bly

			changed();
		}
	}

	protected int distance(int x1, int y1, int x2, int y2) {
		int height = Math.abs(y2 - y1);
		int width = Math.abs(x2 - x1);
		return (int) Math.sqrt(height * height + width * width);
	}
}
