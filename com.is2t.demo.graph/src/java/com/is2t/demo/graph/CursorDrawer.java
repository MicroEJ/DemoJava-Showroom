/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import com.is2t.layers.LayersManager;
import com.is2t.layers.SimpleLayer;
import com.is2t.layers.Transparency;

import ej.microui.Colors;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public class CursorDrawer {

	private SimpleLayer layer;

	public CursorDrawer(GraphicalInfos graphicalInfos, GraphModel graphModel) {
		int cursorWidth = graphicalInfos.displayWidth - (graphicalInfos.gridLeft + graphicalInfos.gridWidth);
		int cursorHeight = cursorWidth;
		Image image = Image.createImage(cursorWidth, cursorHeight);
		GraphicsContext g = image.getGraphicsContext();
		LayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(0, 0, cursorWidth, cursorHeight);
		LayersManager.setTransparency(g, Transparency.OPAQUE);

		layer = new SimpleLayer(graphicalInfos.displayWidth - cursorWidth, graphicalInfos.gridTop + cursorHeight / 2,
				image);

		draw(g, graphicalInfos, graphModel);
	}

	public void draw(GraphicsContext g, GraphicalInfos graphicalInfos, GraphModel graphModel) {
		int cursorWidth = layer.getImage().getWidth();
		int cursorHeight = layer.getImage().getHeight();
		g.setColor(Colors.SILVER);
		int[] xys = new int[] { 0, cursorHeight / 2, cursorWidth, 0, cursorWidth, cursorHeight };
		g.fillPolygon(xys);
		g.drawPolygon(xys);
		g.setColor(LineDrawer.LINE_COLOR);
		int[] xys2 = new int[] { 0, cursorHeight / 2, cursorWidth / 4, cursorHeight * 3 / 8 + 1, cursorWidth / 4,
				cursorHeight * 5 / 8 };
		g.fillPolygon(xys2);
		g.drawPolygon(xys2);
	}

	public void setY(int y) {
		layer.setY(y - layer.getImage().getHeight() / 2);
	}

	public SimpleLayer getLayer() {
		return layer;
	}
}
