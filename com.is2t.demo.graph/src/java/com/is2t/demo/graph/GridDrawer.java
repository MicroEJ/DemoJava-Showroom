/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import com.is2t.layers.SimpleLayer;

import ej.microui.Colors;
import ej.microui.io.GraphicsContext;

public class GridDrawer {

	// private static final int LIMIT_COLOR = Colors.RED;
	private static final int SCALES_COLOR = 0x606060;
	private static final int FOREGROUND_COLOR = Colors.WHITE;
	private static final int BACKGROUND_COLOR = 0x202020;

	private static final int HORIZONTALS_SCALES = 7;
	private static final int VERTICALS_SCALES = 7;

	// private SimpleLayer layer;

	public GridDrawer(GraphicalInfos graphicalInfos, GraphModel graphModel) {
		// Image image = Image.createImage(graphicalInfos.displayWidth,
		// graphicalInfos.displayHeight);
		// GraphicsContext g = image.getGraphicsContext();
		// g.setFont(graphicalInfos.font);
		// // clean
		// g.setColor(BACKGROUND_COLOR);
		// // AbstractLayersManager.setTransparency(g,
		// Transparency.TRANSPARENT);
		// g.fillRect(0, 0, graphicalInfos.displayWidth,
		// graphicalInfos.displayHeight);
		// // AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
		//
		// draw(g, graphicalInfos, graphModel);
		//
		// layer = new SimpleLayer(0, 0, image);
	}

	public static void draw(GraphicsContext g, GraphicalInfos graphicalInfos,
			GraphModel graphModel) {
		g.setFont(graphicalInfos.font);
		g.setColor(FOREGROUND_COLOR);
		int gridLeft = graphicalInfos.gridLeft;
		int gridTop = graphicalInfos.gridTop;
		int gridHeight = graphicalInfos.gridHeight;
		g.drawVerticalLine(gridLeft, gridTop, gridHeight);
		g.drawVerticalLine(gridLeft - 1, gridTop, gridHeight);

		int gridBottom = graphicalInfos.gridBottom;
		int gridWidth = graphicalInfos.gridWidth;
		g.drawHorizontalLine(gridLeft, gridBottom, gridWidth);
		g.drawHorizontalLine(gridLeft - 1, gridBottom + 1, gridWidth + 1);

		int gridZoneLeft = graphicalInfos.gridZoneLeft;
		for (int i = -1; ++i < HORIZONTALS_SCALES;) {
			g.setColor(FOREGROUND_COLOR);
			int yScale = gridTop + i * gridHeight / HORIZONTALS_SCALES;
			g.drawHorizontalLine(gridLeft, yScale, GraphicalInfos.SCALE_SIZE);
			g.drawHorizontalLine(gridLeft, yScale + 1,
					GraphicalInfos.SCALE_SIZE);
			if ((i & 0x1) == 0) {
				g.drawString(getValue(graphModel, i),
						GraphicalInfos.PADDING / 2, yScale,
						GraphicsContext.LEFT | GraphicsContext.VCENTER);
			}
			g.setColor(SCALES_COLOR);
			int xGrid = GraphicalInfos.SCALE_SIZE * 2;
			g.drawHorizontalLine(gridZoneLeft, yScale, gridWidth - xGrid);
			g.drawHorizontalLine(gridZoneLeft, yScale + 1, gridWidth - xGrid);
		}
		int lastXScale = 0;
		int scaleWidth = graphicalInfos.font.stringWidth("0");
		for (int i = -1; ++i < VERTICALS_SCALES;) {
			int xScale = gridLeft + (gridWidth / (2 * (VERTICALS_SCALES + 1)))
					+ i * gridWidth / VERTICALS_SCALES;
			g.setColor(FOREGROUND_COLOR);
			g.drawVerticalLine(xScale, gridBottom - GraphicalInfos.SCALE_SIZE,
					GraphicalInfos.SCALE_SIZE);
			g.drawVerticalLine(xScale + 1, gridBottom
					- GraphicalInfos.SCALE_SIZE, GraphicalInfos.SCALE_SIZE);
			int yBottom = graphicalInfos.displayHeight - GraphicalInfos.PADDING
					/ 2;
			if (lastXScale + scaleWidth < xScale) {
				g.drawString(getHour(i + 1), xScale + 7, yBottom,
						GraphicsContext.LEFT | GraphicsContext.BOTTOM);
				lastXScale = xScale;
			}
			g.setColor(SCALES_COLOR);
			// g.drawVerticalLine(xScale, gridTop, yBottom - gridTop
			// - graphicalInfos.fontHeight / 2);
			// g.drawVerticalLine(xScale + 1, gridTop, yBottom - gridTop
			// - graphicalInfos.fontHeight / 2);
			g.drawVerticalLine(xScale, gridTop, gridHeight
					- GraphicalInfos.SCALE_SIZE * 4);
			g.drawVerticalLine(xScale + 1, gridTop, gridHeight
					- GraphicalInfos.SCALE_SIZE * 4);
		}
		// g.setColor(LIMIT_COLOR);
		// g.setStrokeStyle(GraphicsContext.DOTTED);
		// int xGrid = GraphicalInfos.SCALE_SIZE * 2;
		// int max = graphModel.getMax();
		// int threshold = graphModel.getThreshold();
		// g.drawHorizontalLine(gridZoneLeft, gridTop + (max - threshold) *
		// gridHeight / max, gridWidth - xGrid);
		// g.drawHorizontalLine(gridZoneLeft, gridTop + (max - threshold) *
		// gridHeight / max + 1, gridWidth - xGrid);
		// g.setStrokeStyle(GraphicsContext.SOLID);
	}

	public SimpleLayer getLayer() {
		// return layer;
		return null;
	}

	private static String getHour(int i) {
		// if (++i < 10) {
		// return "0" + Integer.toString(i);
		// } else {
		return Integer.toString(i);
		// }
	}

	private static String getValue(GraphModel graphModel, int i) {
		int max = graphModel.getMax();
		return Integer.toString(max - (i * max / HORIZONTALS_SCALES));
	}
}
