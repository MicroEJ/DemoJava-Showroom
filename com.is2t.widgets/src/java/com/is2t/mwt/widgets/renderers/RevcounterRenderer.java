/**
 * Java 1.3
 * 
 * Copyright 2013 IS2T. All rights reserved.
 * Modification and distribution is permitted under certain conditions.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.util.Format;
import com.is2t.mwt.widgets.tiny.Revcounter;

import ej.microui.Colors;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.WidgetRenderer;

/**
 * 
 * This file contains an {@link Revcounter} renderer.
 * It displays a snapshot value relative to limited one.
 * The range of values is represented by a half-circle, on which a needle shows the current value.
 * Renderer also shows minimum and maximum recorded values.
 */
public class RevcounterRenderer extends WidgetRenderer {

	/**
	 * Counter colors
	 */
	private static final int[] COUNTER_COLORS = { 0x06BF00, 0x82B000, 0xB6C600, 0xEFBA00, 0xCD6200, 0xBF0006 };
	/**
	 * Cursors thickness
	 */
	private static final int CURSOR_THICKNESS = 3;
	/**
	 * Hand polygon apex.
	 */
	private static final int[] HAND_APEX = new int[8];
	/**
	 * Margin
	 */
	private static final int MARGIN = 0;
	/**
	 * Number of displayed decimals
	 */
	public static final int NB_DECIMALS = 1;
	/**
	 * Padding
	 */
	private static final int PADDING = 10;
	/**
	 * Math.PI in degrees.
	 */
	private static final int PI_IN_DEGREES = (int) Math.toDegrees(Math.PI);
	/**
	 * Background color
	 */
	private int bgColor = Colors.GRAY;
	/**
	 * Default font
	 */
	private DisplayFont defaultFont = DisplayFont.getDefaultFont();
	/**
	 * Font used to draw values
	 */
	private DisplayFont valuesFont = DisplayFont.getDefaultFont();

	/**
	 * Draw colored arc from rev-counter center.
	 * Delimit arcs with background colored lines.
	 * Recover arcs with a background colored half-circle.
	 * 
	 * @param g Graphics context
	 * @param centerPoint the widget center
	 * @param counterDiameter Counter diameter
	 */
	private void drawColoredArcs(final GraphicsContext g, final int[] centerPoint, final int counterDiameter) {
		int nbColors = COUNTER_COLORS.length;
		int anglePerColor = PI_IN_DEGREES / nbColors;
		for (int i = nbColors; --i >= 0;) {
			int startAngle = i * anglePerColor;
			g.setColor(COUNTER_COLORS[nbColors - i - 1]);
			g.fillArc(centerPoint[0] - counterDiameter / 2, centerPoint[1] - counterDiameter / 2, counterDiameter, counterDiameter, startAngle,
					anglePerColor);
		}
		int[] counterPoint = { centerPoint[0] - counterDiameter / 2 - 1, centerPoint[1] };
		g.setColor(bgColor);
		for (int i = nbColors; --i >= 0;) {
			if (i != 0) {
				int rotationAngle = i * anglePerColor;
				int[] rotatePoint = Drawings.rotatePoint2D(counterPoint, centerPoint, rotationAngle);
				Drawings.drawThickLine(g, centerPoint[0], centerPoint[1], rotatePoint[0], rotatePoint[1], 3);
			}
		}
		int arcRadius = counterDiameter / 3;
		g.fillArc(centerPoint[0] - arcRadius, centerPoint[1] - arcRadius, 2 * arcRadius, 2 * arcRadius, 0, PI_IN_DEGREES);
	}

	/**
	 * Draw black circle containing current rev-counter value.
	 * 
	 * @param g Graphics context
	 * @param value Current value
	 * @param unit Value unit
	 * @param centerPoint the widget center
	 * @param circleDiameter Circle diameter
	 */
	private void drawCurrentValueCircle(final GraphicsContext g, final float value, final String unit, final int[] centerPoint,
			final int circleDiameter) {
		g.setColor(Colors.BLACK);
		g.fillCircle(centerPoint[0] - circleDiameter / 2, centerPoint[1] - circleDiameter / 2, circleDiameter);
		g.setColor(Colors.WHITE);
		g.drawCircle(centerPoint[0] - circleDiameter / 2, centerPoint[1] - circleDiameter / 2, circleDiameter);
		String currentValue = new StringBuffer(Format.floatToStringWithPrecision(value, NB_DECIMALS)).append(unit).toString();
		g.setFont(valuesFont);
		g.drawString(currentValue, centerPoint[0], centerPoint[1], GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	/**
	 * Draw the rev-counter hand.
	 * Need to rotate hand around origin point for the current value angle.
	 * 
	 * @param g Graphics context
	 * @param origin Rotation center 2D point
	 * @param counterRadius Counter radius
	 * @param circleRadius Circle radius
	 * @param currentValue Current value
	 * @param anglePerValue Angle for each value unity
	 */
	private void drawHand(final GraphicsContext g, final int[] origin, final int counterRadius, final int circleRadius, final float currentValue,
			final double anglePerValue) {
		// Construct default hand (vertical above rev-counter center)
		HAND_APEX[0] = origin[0] - circleRadius / 3;
		HAND_APEX[1] = origin[1];
		HAND_APEX[2] = origin[0] - 1;
		HAND_APEX[3] = origin[1] - (5 * counterRadius / 6);
		HAND_APEX[4] = origin[0] + 1;
		HAND_APEX[5] = origin[1] - (5 * counterRadius / 6);
		HAND_APEX[6] = origin[0] + circleRadius / 3;
		HAND_APEX[7] = origin[1];
		// Rotate each hand points around origin point
		int angle = (int) (currentValue * anglePerValue);
		for (int i = 0; i < HAND_APEX.length - 2; i += 2) {
			int[] point = { HAND_APEX[i], HAND_APEX[i + 1] };
			int[] rotatePoint = Drawings.rotatePoint2D(point, origin, angle - 90);
			HAND_APEX[i] = rotatePoint[0];
			HAND_APEX[i + 1] = rotatePoint[1];
		}
		g.setColor(Colors.WHITE);
		g.fillPolygon(HAND_APEX);
	}

	/**
	 * Draw limited value at the right 5/6 angle of rev-counter.
	 * 
	 * @param g Graphics context
	 * @param limitedValue Limited value
	 * @param unit Value unit
	 * @param origin Origin 2D point
	 * @param counterRadius Counter radius
	 */
	private void drawLimitedValue(final GraphicsContext g, final float limitedValue, final String unit, final int[] origin, final int counterRadius) {
		// Do a rotation of counter point vertically above center around counter center
		int[] point = { origin[0], origin[1] - counterRadius };
		int[] startLine = Drawings.rotatePoint2D(point, origin, (5 * PI_IN_DEGREES / 6) - 90);
		// Line + value string do not have to exceed counter height
		int lineHeight = startLine[1] - (origin[1] - counterRadius) - valuesFont.getHeight();
		g.setColor(Colors.WHITE);
		g.setFont(valuesFont);
		g.drawLine(startLine[0], startLine[1], startLine[0], startLine[1] - lineHeight);
		g.drawString(Format.floatToStringWithPrecision(limitedValue, NB_DECIMALS) + unit, startLine[0], startLine[1] - lineHeight,
				GraphicsContext.HCENTER
				| GraphicsContext.BOTTOM);
	}

	/**
	 * Draw minimum and maximum cursors on rev-counter.
	 * 
	 * @param g Graphics context
	 * @param centerPoint Center point of rev-counter
	 * @param counterDiameter Counter diameter
	 * @param minAngle Minimum value angle
	 * @param maxAngle Maximum value angle
	 */
	private void drawMinAndMaxCursors(final GraphicsContext g, final int[] centerPoint, final int counterDiameter, final int minAngle,
			final int maxAngle) {
		int[] zeroPointUp = { centerPoint[0] - counterDiameter / 2 - 3, centerPoint[1] };
		int[] zeroPointDown = { centerPoint[0] - counterDiameter / 3 + 3, centerPoint[1] };
		g.setColor(Colors.WHITE);
		int[] minPointUp = Drawings.rotatePoint2D(zeroPointUp, centerPoint, minAngle);
		int[] minPointDown = Drawings.rotatePoint2D(zeroPointDown, centerPoint, minAngle);
		Drawings.drawThickLine(g, minPointUp[0], minPointUp[1], minPointDown[0], minPointDown[1], CURSOR_THICKNESS);
		int[] maxPointUp = Drawings.rotatePoint2D(zeroPointUp, centerPoint, maxAngle);
		int[] maxPointDown = Drawings.rotatePoint2D(zeroPointDown, centerPoint, maxAngle);
		Drawings.drawThickLine(g, maxPointUp[0], maxPointUp[1], maxPointDown[0], maxPointDown[1], CURSOR_THICKNESS);
	}

	/**
	 * Draw black rectangles containing minimum and maximum values.
	 * 
	 * @param g Graphics context
	 * @param counterWidth Counter diameter
	 * @param widgetWidth Widget width
	 * @param widgetHeight Widget height
	 * @param minValue Minimum value
	 * @param maxValue Maximum value
	 * @param unit Values unit
	 */
	private void drawMinAndMaxRectangles(final GraphicsContext g, final int currentY, final int counterDiameter, final int widgetWidth,
			final int widgetHeight, final float minValue, final float maxValue, final String unit) {
		int rectMargin = 5;
		int defaultFontHeight = defaultFont.getHeight();
		int valuesFontHeight = valuesFont.getHeight();
		int rectWidth = counterDiameter / 2 - 2 * rectMargin;
		int rectHeight = defaultFontHeight + valuesFontHeight + 2 * rectMargin;
		int rectTopY = currentY + (widgetHeight - currentY) / 2 - rectHeight / 2;
		int rectCenterY = rectTopY + rectHeight / 2;
		// draw minimum rectangle
		int currentX = widgetWidth / 2 - rectMargin - rectWidth;
		g.setColor(Colors.BLACK);
		g.fillRoundRect(currentX, rectTopY, rectWidth, rectHeight, 10, 10);
		currentX += rectWidth / 2;
		g.setColor(Colors.WHITE);
		g.setFont(defaultFont);
		g.drawString("min.", currentX, rectCenterY - defaultFontHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		String minValueStr = new StringBuffer(Format.floatToStringWithPrecision(minValue, NB_DECIMALS)).append(unit).toString();
		g.setFont(valuesFont);
		g.drawString(minValueStr, currentX, rectCenterY + valuesFontHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		// draw maximum rectangle
		currentX += 2 * rectMargin + rectWidth / 2;
		g.setColor(Colors.BLACK);
		g.fillRoundRect(currentX, rectTopY, rectWidth, rectHeight, 10, 10);
		currentX += rectWidth / 2;
		g.setColor(Colors.WHITE);
		g.setFont(defaultFont);
		g.drawString("max.", currentX, rectCenterY - defaultFontHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		String maxValueStr = new StringBuffer(Format.floatToStringWithPrecision(maxValue, NB_DECIMALS)).append(unit).toString();
		g.setFont(valuesFont);
		g.drawString(maxValueStr, currentX, rectCenterY + valuesFontHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.Renderer#getManagedType()
	 */
	public Class getManagedType() {
		return Revcounter.class;
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.Renderer#getMargin()
	 */
	public int getMargin() {
		return MARGIN;
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.Renderer#getPadding()
	 */
	public int getPadding() {
		return PADDING;
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.rendering.WidgetRenderer#getPreferredContentHeight(ej.mwt.Widget)
	 */
	public int getPreferredContentHeight(Widget widget) {
		return 2 * getMargin() + 2 * getPadding();
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.rendering.WidgetRenderer#getPreferredContentWidth(ej.mwt.Widget)
	 */
	public int getPreferredContentWidth(Widget widget) {
		return 2 * getMargin() + 2 * getPadding();
	}

	/*
	 * (non-Javadoc)
	 * @see ej.mwt.Renderer#render(ej.microui.io.GraphicsContext, ej.mwt.Renderable)
	 */
	public void render(final GraphicsContext g, final Renderable renderable) {
		Revcounter revCounter = (Revcounter) renderable;
		int width = revCounter.getWidth();
		int height = revCounter.getHeight();
		// Draw background
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		float currentValue = revCounter.getValue();
		if (!Float.isNaN(currentValue)) {
			float minValue = revCounter.getMinValue();
			float maxValue = revCounter.getMaxValue();
			float limitedValue = revCounter.getLimitedValue();
			String unit = new StringBuffer(" ").append(revCounter.getUnit()).toString();
			int[] centerPoint = { width / 2, height / 2 };
			// Counter diameter equals 9/10 of minimum widget dimension.
			int counterDiameter = (int) (Math.min(width, height) * 0.9);
			int circleDiameter = counterDiameter / 3;
			// 5/6 of half circle represents limited value
			float anglePerValue = (5 * PI_IN_DEGREES) / (6 * limitedValue);
			// Draw colored arcs
			drawColoredArcs(g, centerPoint, counterDiameter);
			// Draw min and max on rev-counter
			drawMinAndMaxCursors(g, centerPoint, counterDiameter, (int) (minValue * anglePerValue), (int) (maxValue * anglePerValue));
			// Draw needle
			drawHand(g, centerPoint, counterDiameter / 2, circleDiameter / 2, currentValue, anglePerValue);
			// Draw black circle containing current value
			drawCurrentValueCircle(g, currentValue, unit, centerPoint, circleDiameter);
			// Draw minimum and maximum values into black rectangles
			drawMinAndMaxRectangles(g, centerPoint[1] + circleDiameter / 2, counterDiameter, width, height, minValue, maxValue, unit);
			// Draw limited value
			drawLimitedValue(g, limitedValue, unit, centerPoint, counterDiameter / 2);
		}
	}

	/**
	 * Sets the background color.
	 * 
	 * @param color New background color.
	 */
	public void setBackgroundColor(final int color) {
		bgColor = color;
	}

	/**
	 * Sets the default font.
	 * 
	 * @param font A Display font
	 */
	public void setDefaultFont(final DisplayFont font) {
		this.defaultFont = font;
	}

	/**
	 * Sets the displayed font to display values.
	 * 
	 * @param font A Display font
	 */
	public void setValuesFont(final DisplayFont font) {
		this.valuesFont = font;
	}
}
