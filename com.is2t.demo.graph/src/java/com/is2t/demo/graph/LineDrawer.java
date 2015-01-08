/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import com.is2t.demo.utilities.ListenerAdapter;
import com.is2t.layers.Layer;
import com.is2t.layers.PartLayer;
import com.is2t.layers.Transparency;

import ej.bon.XMath;
import ej.microui.Colors;
import ej.microui.Command;
import ej.microui.EventGenerator;
import ej.microui.Listener;
import ej.microui.io.FontBrush;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;

public class LineDrawer implements Listener {

	public static final int LINE_COLOR = 0x00aeff;

	private final GraphicalInfos graphicalInfos;
	private final GraphModel graphModel;
	private final PartLayer firstLayer;
	private final PartLayer secondLayer;
	private final GraphicsContext g;
	private final FontBrush brush;

	private boolean drawGradient = true;
	private int currentX;

	public LineDrawer(GraphicalInfos graphicalInfos, GraphModel graphModel) {
		this.graphicalInfos = graphicalInfos;
		this.graphModel = graphModel;
		Image image = Image.createImage(graphicalInfos.gridZoneWidth + graphicalInfos.lineCharWidth,
				graphicalInfos.gridHeight);
		g = image.getGraphicsContext();
		// clean
		AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(0, 0, graphicalInfos.gridZoneWidth + graphicalInfos.lineCharWidth, graphicalInfos.gridHeight);
		brush = new FontBrush(Brushes.getFont(GraphicalInfos.LINE_FONT_SIZE), Brushes.getChar());
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);

		firstLayer = new PartLayer(graphicalInfos.gridZoneLeft, graphicalInfos.gridTop, image);
		secondLayer = new PartLayer(graphicalInfos.gridZoneLeft, graphicalInfos.gridTop, image);
		// firstLayer.setPart(0, 0, graphicalInfos.gridZoneWidth, graphicalInfos.gridHeight);

		graphModel.addListener(new ListenerAdapter() {
			@Override
			public void performAction(int value) {
				newValue(value);
			}
		});

		EventGenerator eventGenerator = EventGenerator.get(Command.class, 0);
		eventGenerator.setListener(this);
	}

	public Layer getFirstLayer() {
		return firstLayer;
	}

	public Layer getSecondLayer() {
		return secondLayer;
	}

	@Override
	public void performAction(int event) {
		drawGradient = !drawGradient;
	}

	public void newValue(int newValue) {
		long start = System.currentTimeMillis();
		int gridZoneWidth = graphicalInfos.gridZoneWidth;
		int gridHeight = graphicalInfos.gridHeight;

		int[] datas = graphModel.getDatas();
		int datasLength = datas.length;
		int value = datas[datasLength - 2];
		int max = graphModel.getMax();
		int y = (max - value) * gridHeight / max;
		int newY = (max - newValue) * gridHeight / max;

		int xStart = currentX;
		int xEnd = xStart + GraphicalInfos.SHIFT;
		drawNewLine(xStart, y, xEnd, newY);

		int oldX = currentX - GraphicalInfos.SHIFT;
		int oldValue = datas[datasLength - 3];
		int oldY = (max - oldValue) * gridHeight / max;

		if (oldValue != GraphModel.NO_VALUE) {
			drawSmoothLine(y, newY, xStart, xEnd, oldX, oldY);
		}

		if (xEnd >= gridZoneWidth) {
			xStart -= gridZoneWidth;
			xEnd -= gridZoneWidth;
			drawNewLine(xStart, y, xEnd, newY);
		} else if (xEnd + graphicalInfos.lineCharWidth >= gridZoneWidth) {
			xStart -= gridZoneWidth;
			clean(xStart);
		}
		currentX = xEnd;

		int xSplit = currentX + graphicalInfos.lineCharWidth - 1;
		int firstPartWidth = gridZoneWidth - xSplit;
		firstLayer.setPart(xSplit, 0, firstPartWidth, gridHeight);
		secondLayer.setX(graphicalInfos.gridZoneLeft + firstPartWidth);
		secondLayer.setPart(0, 0, xSplit, gridHeight);
//		System.out.println("newValue " + (System.currentTimeMillis() - start) + "ms");
	}

	private void drawSmoothLine(int y, int newY, int xStart, int xEnd, int oldX, int oldY) {
		int centerX = (xEnd + oldX) / 2;
		int centerY = (newY + oldY) / 2;
		int heightCenterX = (xStart + centerX) / 2;
		int heightCenterY = (y + centerY) / 2;
		int deltaX = XMath.limit(heightCenterX - centerX, -GraphicalInfos.LINE_FONT_SIZE / 2,
				GraphicalInfos.LINE_FONT_SIZE / 2);
		int deltaY = XMath.limit(heightCenterY - centerY, -GraphicalInfos.LINE_FONT_SIZE / 2,
				GraphicalInfos.LINE_FONT_SIZE / 2);

		int smoothX1 = oldX + deltaX;
		int smoothY1 = oldY + deltaY;
		int smoothX2 = xEnd + deltaX;
		int smoothY2 = newY + deltaY;
		g.setColor(LINE_COLOR);
		g.setBrush(brush);
		g.drawLine(smoothX1, smoothY1, smoothX2, smoothY2);
	}

	private void drawNewLine(int xStart, int y, int xEnd, int newY) {
		clean(xStart);
		if (drawGradient) {
			drawGradient(xStart, y);
		}
		g.setColor(LINE_COLOR);
		g.setBrush(brush);
		g.drawLine(xStart, y, xEnd, newY);
	}

	private void drawGradient(int xStart, int y) {
		g.setColor(LINE_COLOR);
		g.setBrush(null);
		int steps = graphicalInfos.gridHeight / 3;
		float currentTransparency = Transparency.OPAQUE / 2;
		float stepTransparency = (float) Transparency.OPAQUE / (2 * (steps - 1));
		int topGradient = graphicalInfos.gridHeight * 2 / 3;
		AbstractLayersManager.setTransparency(g, (int) currentTransparency);

		g.fillRect(xStart, y, GraphicalInfos.SHIFT + 1, topGradient - y);
		int currentY = topGradient;
		for (int i = -1; ++i < steps;) {
			if (currentY > y) {
				AbstractLayersManager.setTransparency(g, (int) currentTransparency);
				g.drawHorizontalLine(xStart, currentY, GraphicalInfos.SHIFT);
			}
			currentY++;
			currentTransparency -= stepTransparency;
		}
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
	}

	private void clean(int xStart) {
		AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(xStart + graphicalInfos.lineCharWidth / 2, 0, GraphicalInfos.SHIFT + graphicalInfos.lineCharWidth
				/ 2, graphicalInfos.gridHeight);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
	}

	public static void draw(GraphicsContext g, GraphicalInfos graphicalInfos, GraphModel graphModel) {
		int[] datas = graphModel.getDatas();
		int datasLength = datas.length;

		g.setColor(Colors.BLACK);
		g.setColor(LINE_COLOR);

		int x = graphicalInfos.gridZoneLeft;
		int yShift = graphicalInfos.gridTop;
		int max = graphModel.getMax();
		int y = (max - datas[0]) * graphicalInfos.gridHeight / max + yShift;
		g.setBrush(new FontBrush(Brushes.getFont(GraphicalInfos.LINE_FONT_SIZE), Brushes.getChar()));
		// long s = System.currentTimeMillis();
		for (int i = 0; ++i < datasLength;) {
			int newY = (max - datas[i]) * graphicalInfos.gridHeight / max + yShift;
			int newX = x + GraphicalInfos.SHIFT;
			g.drawLine(x, y, newX, newY);
			y = newY;
			x = newX;
		}
		// System.out.println("time: " + (System.currentTimeMillis() - s) + "ms");
		g.setBrush(null);
	}

	@Override
	public void performAction() {
	}

	@Override
	public void performAction(int value, Object object) {
	}

}
