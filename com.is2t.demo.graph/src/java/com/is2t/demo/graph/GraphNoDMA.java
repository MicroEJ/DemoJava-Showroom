/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import java.util.Timer;
import java.util.TimerTask;

import com.is2t.layers.Layer;

import ej.microui.Colors;
import ej.microui.EventGenerator;
import ej.microui.MicroUI;
import ej.microui.io.Display;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Pointer;

public class GraphNoDMA extends Displayable {

	public static void main(String[] args) {
		MicroUI.errorLog(true);
		Display display = Display.getDefaultDisplay();
		GraphNoDMA graph = new GraphNoDMA(display);
		graph.show();
	}

	private GraphModel graphModel;
	private DataGenerator dataGenerator;

	private Display display;
	private int displayWidth;
	private int displayHeight;

	private Pointer pointer;

	private GraphicalInfos graphicalInfos;
	private GridDrawer gridDrawer;
	private LineDrawer lineDrawer;
	// private InfoDrawer infoDrawer;
	// private AlarmDrawer alarmDrawer;

	private volatile boolean alarm;
	private volatile boolean pressed;

	private boolean infoVisible;
	private int infoX;

	public GraphNoDMA(Display display) {
		super(display);
		this.display = display;
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();

		pointer = (Pointer) EventGenerator.get(Pointer.class, 0);

		final int max = 150;
		final int threshold = max * 2 / 3;
		graphicalInfos = new GraphicalInfos(displayWidth, displayHeight, max);

		graphModel = new GraphModel(graphicalInfos.gridZoneWidth / GraphicalInfos.SHIFT, threshold, max);
		// dataGenerator = new RandomGenerator(max);
		dataGenerator = new SinusGenerator(max);
		graphModel.addData(dataGenerator.getValue());

		gridDrawer = new GridDrawer(graphicalInfos, graphModel);
		// infoDrawer = new InfoDrawer(graphicalInfos, graphModel);
		lineDrawer = new LineDrawer(graphicalInfos, graphModel);
		// alarmDrawer = new AlarmDrawer(graphicalInfos, graphModel);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (!pressed) {
					GraphNoDMA.this.display.callSerially(new Runnable() {

						@Override
						public void run() {
							if (!pressed) {
								// update model
								int value = dataGenerator.getValue();
								graphModel.addData(value);

								if (infoVisible) {
									infoX -= GraphicalInfos.SHIFT;
									if (infoX < graphicalInfos.gridZoneLeft) {
										infoVisible = false;
									}
								}
								int threshold = graphModel.getThreshold();
								alarm = value > threshold;

								repaint();
							}
						}
					});
				}
			}
		}, 0, GraphicalInfos.SHIFT_PERIOD);
	}

	@Override
	public void paint(GraphicsContext g) {
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, displayWidth, displayHeight);

		drawLayer(g, gridDrawer.getLayer());
		// GridDrawer.draw(g, graphicalInfos, graphModel);
		drawLayer(g, lineDrawer.getFirstLayer());
		// LineDrawer.draw(g, graphicalInfos, graphModel);
		if (infoVisible) {
			InfoDrawer.drawInfo(g, graphicalInfos, graphModel, infoX);
		} else {
			InfoDrawer.drawMessage(g, graphicalInfos, graphModel);
		}
		if (alarm) {
			AlarmDrawer.draw(g, graphicalInfos, graphModel);
		}

		// drawLayer(g, lineDrawer.getLayer());
		// drawLayer(g, infoDrawer.getLayer());
	}

	private void drawLayer(GraphicsContext g, Layer layer) {
		g.drawImage(layer.getImage(), layer.getX(), layer.getY(), GraphicsContext.LEFT | GraphicsContext.TOP);
	}

	@Override
	public void performAction(int value) {
		int action = Pointer.getAction(value);
		switch (action) {
		case Pointer.RELEASED:
			pressed = false;
			break;
		case Pointer.PRESSED:
			pressed = true;
			// fall down
		case Pointer.DRAGGED:
			final int x = pointer.getX();
			// display.callSerially(new Runnable() {
			// @Override
			// public void run() {
			// infoDrawer.setX(x);
			this.infoX = x;
			this.infoVisible = true;
			repaint();
			// }
			// });
			break;
		}
	}

}
