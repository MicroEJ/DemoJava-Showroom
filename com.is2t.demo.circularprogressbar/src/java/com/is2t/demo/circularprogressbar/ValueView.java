/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar;

import ej.microui.IntHolder;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public class ValueView extends CircularView {

	public ValueView(Image image, String name, IntHolder model, int x, int y, int width, int height) {
		super(image, name, model, x, y, width, height);
	}

	@Override
	protected void drawEnd(GraphicsContext g, int angle) {
		int thisWidth = this.getWidth();
		int thisHeight = this.getHeight();
		int halfDisplayWidth = thisWidth / 2;
		int halfDisplayHeight = thisHeight / 2;

		double valueAngleRadians = Math.toRadians(START_ANGLE + angle);
		int valueThickness = 8;
		int circleSize = this.image.getHeight();
		int circleRadius = circleSize / 2;

		g.setColor(BACKGROUND_COLOR);
		double cos = Math.cos(valueAngleRadians);
		double sin = Math.sin(valueAngleRadians);
		int externalRadius = circleRadius + 1;
		int internalRadius = circleRadius - valueThickness * 2 - 1;
		int xExternal = halfDisplayWidth - (int) (cos * externalRadius);
		int yExternal = halfDisplayHeight - (int) (sin * externalRadius);
		int xInternal = halfDisplayWidth - (int) (cos * internalRadius);
		int yInternal = halfDisplayHeight - (int) (sin * internalRadius);
		g.drawLine(xInternal, yInternal, xExternal, yExternal);
		g.drawLine(xInternal + 1, yInternal, xExternal + 1, yExternal);
		g.drawLine(xInternal - 1, yInternal, xExternal - 1, yExternal);

		int arcSize = circleSize - valueThickness * 2;
		int halfArcSize = arcSize / 2;
		int xArc = halfDisplayWidth - halfArcSize;
		int yArc = halfDisplayHeight - halfArcSize;
		g.setColor(LINE_COLOR);
		g.drawArc(xArc, yArc, arcSize, arcSize, START_ANGLE, 360 - angle);
	}

}
