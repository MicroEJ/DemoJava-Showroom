/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import com.is2t.layers.Layer;
import com.is2t.layers.SimpleLayer;
import com.is2t.layers.Transparency;
import com.is2t.nls.BasicImmutablesNLS;
import com.is2t.nls.NLS;

import ej.bon.XMath;
import ej.microui.Colors;
import ej.microui.Listener;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;

public class InfoDrawer implements Listener {

	public static final int OUTER_CURSOR_FONT_SIZE = 11;
	public static final int INNER_CURSOR_FONT_SIZE = 5;

	private static final String PERCENT = " %";
	// private static final String PRESS_THE_SCREEN_TO_SEE_MORE = "Press the screen to see more";

	private static final int SHADOW_COLOR = Colors.SILVER;
	private static final int FOREGROUND_COLOR = Colors.BLACK;
	private static final int BOX_COLOR = Colors.WHITE;
	private static final int ARC = 6;
	private static final int VISIBILITY_STEPS = 10;
	private static final int TRANSITION_STEPS = 100;

	private static NLS nls = new BasicImmutablesNLS(GraphMessages.class.getSimpleName());

	private final GraphicalInfos graphicalInfos;
	private final GraphModel graphModel;
	private final GraphicsContext g;
	private final Layer layerInfo;

	private boolean visible;
	private int visibilityLevel;
	private boolean visibilityIncrement;
	private int transtionLevel;
	private int nlsLocaleIndex;
	private Layer layerMessage;

	private int boxHeight;
	private DisplayFont outerCursorFont;
	private DisplayFont innerCursorFont;

	// private BrushPainter brushPainter;

	public InfoDrawer(GraphicalInfos graphicalInfos, GraphModel graphModel) {
		this.graphicalInfos = graphicalInfos;
		this.graphModel = graphModel;

		outerCursorFont = Brushes.getFont(OUTER_CURSOR_FONT_SIZE);
		innerCursorFont = Brushes.getFont(INNER_CURSOR_FONT_SIZE);

		// create info layer
		int maxInfoWidth = graphicalInfos.font.stringWidth(Integer.toString(graphModel.getMax()));
		int infoWidth = maxInfoWidth * 2;
		int infoHeight = graphicalInfos.fontHeight + graphicalInfos.gridHeight + 2 * GraphicalInfos.PADDING;
		Image image = Image.createImage(infoWidth, infoHeight);
		g = image.getGraphicsContext();
		g.setFont(graphicalInfos.font);
		// clean
		AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(0, 0, infoWidth, infoHeight);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);

		boxHeight = graphicalInfos.fontHeight + GraphicalInfos.PADDING / 2;

		layerInfo = new SimpleLayer(0, (graphicalInfos.gridTop - boxHeight) / 2, image);

		updateMessageLayer(graphicalInfos);

		graphModel.addListener(this);
	}

	private void updateMessageLayer(GraphicalInfos graphicalInfos) {
		String message = nls.getMessage(GraphMessages.PRESS_TO_SEE_MORE, nls.getAvailableLocales()[nlsLocaleIndex]);
		// create message layer
		int messageWidth = graphicalInfos.font.stringWidth(message);
		Image imageMessage = Image.createImage(messageWidth, graphicalInfos.fontHeight);
		GraphicsContext g = imageMessage.getGraphicsContext();
		g.setFont(graphicalInfos.font);
		// clean
		AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(0, 0, messageWidth, graphicalInfos.fontHeight);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
		layerMessage = new SimpleLayer((graphicalInfos.displayWidth - messageWidth) / 2,
				(graphicalInfos.gridTop - graphicalInfos.fontHeight) / 2, imageMessage);

		g.setColor(Colors.SILVER);
		g.drawString(message, 0, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
	}

	public Layer getLayer() {
		incrementVisibility();
		return visible ? layerInfo : layerMessage;
	}

	private void incrementVisibility() {
		if (visibilityLevel < VISIBILITY_STEPS) {
			visibilityLevel++;
		} else if (!visibilityIncrement) {
			visibilityLevel = 0;
			visibilityIncrement = true;
			nlsLocaleIndex = (++nlsLocaleIndex) % nls.getAvailableLocales().length;
			updateMessageLayer(graphicalInfos);
		}
		int transparencyLevel = visibilityLevel * 0xff / VISIBILITY_STEPS;
		if (!visibilityIncrement) {
			transparencyLevel = 0xff - transparencyLevel;
		}
		layerMessage.setTransparencyLevel(transparencyLevel);

		if (++transtionLevel == TRANSITION_STEPS) {
			transtionLevel = 0;
			visibilityLevel = 0;
			visibilityIncrement = false;
		}
	}

	public void setX(int x) {
		visible = true;
		int gridZoneLeft = graphicalInfos.gridZoneLeft;
		int gridZoneWidth = graphicalInfos.gridZoneWidth;
		x = XMath.limit(x, gridZoneLeft, gridZoneLeft + gridZoneWidth - graphicalInfos.lineCharWidth);
		Image infoImage = layerInfo.getImage();
		int infoWidth = infoImage.getWidth();
		int halfWidth = infoWidth / 2;
		int xInfo = x - halfWidth;
		layerInfo.setX(xInfo);
		int boxHeight = this.boxHeight;

		char c = Brushes.getChar();
		int[] datas = graphModel.getDatas();
		int datasLength = datas.length;
		float dataPreciseIndex = (float) (x - gridZoneLeft + graphicalInfos.lineCharWidth / 2) * datasLength
				/ (gridZoneWidth);
		int lowIndex = XMath.limit((int) Math.floor(dataPreciseIndex), 0, datasLength - 1);
		int highIndex = XMath.limit((int) Math.ceil(dataPreciseIndex), 0, datasLength - 1);
		int value;
		if (lowIndex == highIndex) {
			value = datas[lowIndex];
		} else {
			int lowValue = datas[lowIndex];
			int highValue = datas[highIndex];
			value = (int) (lowValue + (highValue - lowValue) * (dataPreciseIndex - lowIndex));
		}
		int max = graphModel.getMax();
		int y = (max - value) * graphicalInfos.gridHeight / max;

		int lineHeight = graphicalInfos.gridHeight + 2 * GraphicalInfos.SCALE_SIZE;

		// clean
		AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
		int outerCursorFontHeight = outerCursorFont.getHeight();
		g.fillRect((infoWidth - outerCursorFontHeight) / 2, boxHeight, outerCursorFontHeight + 2, lineHeight
				+ outerCursorFontHeight);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);

		int xData = x - xInfo + 1;
		int yData = y + boxHeight + layerInfo.getY() + 1;

		g.setColor(BOX_COLOR);
		g.fillRoundRect(0, 0, infoWidth, boxHeight, ARC, ARC);
		g.setColor(SHADOW_COLOR);
		g.drawHorizontalLine(ARC / 2, boxHeight - 1, infoWidth - ARC);
		// g.setColor(BOX_COLOR);
		// g.drawVerticalLine(infoWidth / 2, boxHeight, lineHeight);
		// g.drawVerticalLine(infoWidth / 2 + 1, boxHeight, lineHeight);

		g.setColor(FOREGROUND_COLOR);
		g.setFont(graphicalInfos.font);
		g.drawString(Integer.toString(value), infoWidth / 2, boxHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		g.setColor(BOX_COLOR);
		g.drawVerticalLine(infoWidth / 2, yData, lineHeight - (yData - boxHeight));
		g.drawVerticalLine(infoWidth / 2 + 1, yData, lineHeight - (yData - boxHeight));

		g.setColor(BOX_COLOR);
		g.setFont(outerCursorFont);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.setColor(Colors.WHITE - BOX_COLOR);
		g.setFont(innerCursorFont);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	public static void drawInfo(GraphicsContext g, GraphicalInfos graphicalInfos, GraphModel graphModel, int x) {
		// brushPainter = new BrushPainter(g);
		int maxInfoWidth = graphicalInfos.font.stringWidth(Integer.toString(graphModel.getMax()));
		int infoWidth = maxInfoWidth * 2;
		int boxHeight = graphicalInfos.fontHeight + GraphicalInfos.PADDING / 2;

		int gridZoneLeft = graphicalInfos.gridZoneLeft;
		int gridZoneWidth = graphicalInfos.gridZoneWidth;
		x = Math.max(gridZoneLeft, Math.min(gridZoneLeft + gridZoneWidth, x));
		int halfWidth = infoWidth / 2;
		int xInfo = x - halfWidth;

		int yShift = (graphicalInfos.gridTop - boxHeight) / 2;
		int xShift = xInfo;

		char c = Brushes.getChar();
		int[] datas = graphModel.getDatas();
		int datasLength = datas.length;
		float dataPreciseIndex = (float) (x - (gridZoneLeft + Brushes.getFont(GraphicalInfos.LINE_FONT_SIZE).charWidth(
				c) / 2))
				* datasLength / (gridZoneWidth);
		int lowIndex = Math.min(datasLength - 1, (int) Math.floor(dataPreciseIndex));
		int highIndex = Math.min(datasLength - 1, (int) Math.ceil(dataPreciseIndex));
		int value;
		if (lowIndex == highIndex) {
			value = datas[lowIndex];
		} else {
			int lowValue = datas[lowIndex];
			int highValue = datas[highIndex];
			value = (int) (lowValue + (highValue - lowValue) * (dataPreciseIndex - lowIndex));
		}
		int max = graphModel.getMax();
		int y = (max - value) * graphicalInfos.gridHeight / max;

		g.setFont(graphicalInfos.font);
		g.setColor(BOX_COLOR);
		g.fillRoundRect(xShift, yShift, infoWidth, boxHeight, ARC, ARC);
		g.setColor(SHADOW_COLOR);
		g.drawHorizontalLine(xShift + ARC / 2, yShift + boxHeight - 1, infoWidth - ARC);
		g.setColor(BOX_COLOR);
		g.drawVerticalLine(xShift + infoWidth / 2, yShift + boxHeight, graphicalInfos.gridHeight + 2
				* GraphicalInfos.SCALE_SIZE);
		g.drawVerticalLine(xShift + infoWidth / 2 + 1, yShift + boxHeight, graphicalInfos.gridHeight + 2
				* GraphicalInfos.SCALE_SIZE);

		g.setColor(Colors.WHITE);
		g.drawString(value + PERCENT, xShift + infoWidth / 2, yShift + boxHeight / 2, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);

		g.setColor(BOX_COLOR);
		int xData = x - xInfo + 1;
		int yData = y + boxHeight + 1;
		g.setColor(BOX_COLOR);
		g.setFont(Brushes.getFont(OUTER_CURSOR_FONT_SIZE));
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.setColor(Colors.WHITE);
		g.setFont(Brushes.getFont(INNER_CURSOR_FONT_SIZE));
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		g.drawChar(c, xData, yData, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		// final int CIRCLE_RADIUS = 4;
		// int xData = x - xInfo;
		// int yData = y + boxHeight;
		// // brushPainter.drawCircle(MyBrush.getBrushChar(), xData - CIRCLE_RADIUS + 1, yData - CIRCLE_RADIUS + 1,
		// // CIRCLE_RADIUS * 2);
		// g.fillCircle(xShift + xData - CIRCLE_RADIUS, yShift + yData - CIRCLE_RADIUS, CIRCLE_RADIUS * 2);
		// g.setColor(Colors.WHITE);
		// g.fillCircle(xShift + xData - CIRCLE_RADIUS / 2, yShift + yData - CIRCLE_RADIUS / 2, CIRCLE_RADIUS);
	}

	public static void drawMessage(GraphicsContext g, GraphicalInfos graphicalInfos, GraphModel graphModel) {
		String message = nls.getMessage(GraphMessages.PRESS_TO_SEE_MORE);
		int messageWidth = graphicalInfos.font.stringWidth(message);
		g.setFont(graphicalInfos.font);
		g.setColor(Colors.GRAY);
		g.drawString(message, (graphicalInfos.displayWidth - messageWidth) / 2,
				(graphicalInfos.gridTop - graphicalInfos.fontHeight) / 2, GraphicsContext.LEFT | GraphicsContext.TOP);
	}

	@Override
	public void performAction(int value) {
		if (visible) {
			int newX = layerInfo.getX() - GraphicalInfos.SHIFT;
			if (newX + layerInfo.getImage().getWidth() / 2 < graphicalInfos.gridZoneLeft) {
				visible = false;
				visibilityLevel = 0;
				visibilityIncrement = true;
			} else {
				layerInfo.setX(newX);
			}
		}
	}

	@Override
	public void performAction() {
	}

	@Override
	public void performAction(int value, Object object) {
	}

}
