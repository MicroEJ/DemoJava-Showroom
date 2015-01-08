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

public class AlarmDrawer {

	private static final String ALARM = "Alarm !";
	private SimpleLayer layer;

	public AlarmDrawer(GraphicalInfos graphicalInfos, GraphModel graphModel) {
		int alarmWidth = graphicalInfos.font.stringWidth(ALARM) * 2;
		int alarmHeight = graphicalInfos.fontHeight * 2;
		Image image = Image.createImage(alarmWidth, alarmHeight);
		GraphicsContext g = image.getGraphicsContext();
		LayersManager.setTransparency(g, Transparency.TRANSPARENT);
		g.fillRect(0, 0, alarmWidth, alarmHeight);
		LayersManager.setTransparency(g, Transparency.OPAQUE);

		draw(g, graphicalInfos, graphModel);

		layer = new SimpleLayer((graphicalInfos.displayWidth - alarmWidth) / 2,
				(graphicalInfos.displayHeight - alarmHeight) / 2, image);
	}

	public static void draw(GraphicsContext g, GraphicalInfos graphicalInfos, GraphModel graphModel) {
		int alarmWidth = graphicalInfos.font.stringWidth(ALARM) * 2;
		int alarmHeight = graphicalInfos.fontHeight * 2;
		g.setColor(Colors.RED);
		g.fillRoundRect(0, 0, alarmWidth, alarmHeight, 5, 5);
		g.setFont(graphicalInfos.font);
		g.setColor(Colors.WHITE);
		g.drawString(ALARM, alarmWidth / 2, alarmHeight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	public SimpleLayer getLayer() {
		return layer;
	}
}
