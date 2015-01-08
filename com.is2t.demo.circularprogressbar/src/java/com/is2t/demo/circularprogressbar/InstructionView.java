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

public class InstructionView extends CircularView {

	public InstructionView(Image image, String name, IntHolder model, int x, int y, int width, int height) {
		super(image, name, model, x, y, width, height);
	}

	@Override
	protected void drawEnd(GraphicsContext g, int angle) {
		int thisWidth = this.getWidth();
		int thisHeight = this.getHeight();
		int halfDisplayWidth = thisWidth / 2;
		int halfDisplayHeight = thisHeight / 2;

		int instructionThickness = 6;
		int arcSize = this.image.getHeight() - instructionThickness * 2;
		int halfArcSize = arcSize / 2;
		int xArc = halfDisplayWidth - halfArcSize;
		int yArc = halfDisplayHeight - halfArcSize;

		g.setColor(LINE_COLOR);
		g.drawArc(xArc, yArc, arcSize, arcSize, START_ANGLE, 360 - angle);

		g.setBrush(Brushes.getBrush(11));
		double instructionAngleRadians = Math.toRadians(START_ANGLE + angle);
		int xAngle = halfDisplayWidth - (int) (Math.cos(instructionAngleRadians) * halfArcSize);
		int yAngle = halfDisplayHeight - (int) (Math.sin(instructionAngleRadians) * halfArcSize);
		g.setColor(CURSOR_COLOR);
		g.drawCircle(xAngle - 5, yAngle - 5, 10);
		g.setBrush(null);
	}

}
