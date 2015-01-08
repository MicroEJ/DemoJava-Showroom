/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.circularprogressbar;

import ej.microui.Colors;
import ej.microui.IntHolder;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.io.View;

public class CircularView extends View {

	protected static final int START_ANGLE = 90;
	protected static final int BACKGROUND_COLOR = 0x202020;
	protected static final int LINE_COLOR = 0x404040;
	protected static final int CURSOR_COLOR = Colors.WHITE;
	protected static final int TEXT_COLOR = Colors.WHITE;

	protected final Image image;
	private final DisplayFont font;
	private final String name;

	public CircularView(Image image, String name, IntHolder model, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.name = name;
		setModel(model);
		this.image = image;
		this.font = DisplayFont.getFont(DisplayFont.LATIN, 40, DisplayFont.STYLE_PLAIN);
	}

	@Override
	public void paint(GraphicsContext g) {
		int thisWidth = this.getWidth();
		int thisHeight = this.getHeight();

		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, thisWidth, thisHeight);

		IntHolder model = (IntHolder) getModel();
		int value = model.get();
		int angle = value * 360 / CircularProgressBar.MAX;
		drawAngle(g, this.image, angle);
		drawEnd(g, angle);

		int halfWidth = thisWidth / 2;

		g.setColor(TEXT_COLOR);
		g.setFont(this.font);
		g.drawString(String.valueOf(value), halfWidth, thisHeight / 3, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);

		g.drawString(this.name, halfWidth, thisHeight * 2 / 3, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	protected void drawEnd(GraphicsContext g, int angle) {
	}

	private void drawAngle(GraphicsContext g, Image image, int angle) {
		int thisWidth = this.getWidth();
		int thisHeight = this.getHeight();
		int halfDisplayWidth = thisWidth / 2;
		int halfDisplayHeight = thisHeight / 2;
		int encompassingSize = image.getHeight();
		int halfEncompassingSize = encompassingSize / 2;
		int startAngle;
		int arcAngle;
		if (angle > 270) {
			// draw full image
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			// hide
			startAngle = START_ANGLE;
			arcAngle = 360 - angle;
		} else if (angle > 180) {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, thisHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			g.setClip(0, halfDisplayHeight, thisWidth, halfDisplayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			startAngle = START_ANGLE + 90;
			arcAngle = 270 - angle;
		} else if (angle > 90) {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, thisHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			startAngle = START_ANGLE + 180;
			arcAngle = 180 - angle;
		} else {
			g.setClip(halfDisplayWidth + 1, 0, halfDisplayWidth, halfDisplayHeight);
			g.drawImage(image, halfDisplayWidth, halfDisplayHeight, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			startAngle = START_ANGLE + 270;
			arcAngle = 90 - angle;
		}
		g.setClip(0, 0, thisWidth, thisHeight);
		g.setColor(BACKGROUND_COLOR);
		g.fillArc(halfDisplayWidth - halfEncompassingSize - 1, halfDisplayHeight - halfEncompassingSize - 1,
				encompassingSize + 2, encompassingSize + 2, startAngle, arcAngle);
	}

}
