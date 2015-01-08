/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.HomeButton;
import com.is2t.demo.showroom.common.HomeButtonRenderer;
import com.is2t.demo.utilities.Button;
import com.is2t.demo.utilities.ListenerAdapter;
import com.is2t.layers.Layer;
import com.is2t.layers.LayersHandler;
import com.is2t.layers.Transparency;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.bon.XMath;
import ej.microui.Colors;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Display;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Pointer;

public class GraphDMA extends Displayable implements LayersHandler, Listener {

	private GraphModel graphModel;
	private DataGenerator dataGenerator;
	private InputGenerator inputGenerator;

	private Display display;
	private int displayWidth;
	private int displayHeight;

	// private Pointer pointer;

	private GraphicalInfos graphicalInfos;
	private GridDrawer gridDrawer;
	private LineDrawer lineDrawer;
	private InfoDrawer infoDrawer;
	// private AlarmDrawer alarmDrawer;
	private CursorDrawer cursorDrawer;

	// private volatile boolean alarm;
	private volatile boolean pressedInfo;
	private volatile boolean pressedData;
	private volatile boolean xChanged;
	private int previousX;

	private TimerTask updateTask;
	private Timer timer;
	private Button homeButton;
	private HomeButtonRenderer homeButtonRenderer;

	/**
	 * Null if no monitoring implementation available. In this case, this layer
	 * is not shown.
	 */

	private List<Listener> listeners;

	public GraphDMA(Display display, Timer timer, Launcher launcher) {
		super(display);
		this.display = display;
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		listeners = new ArrayList<Listener>();

		createHomeButton(launcher);

		// pointer = (Pointer) EventGenerator.get(Pointer.class, 0);
		// pointer.setListener(this);
		//
		final int max = 70;
		final int threshold = max * 2 / 3;
		graphicalInfos = new GraphicalInfos(displayWidth, displayHeight, max);

		graphModel = new GraphModel(graphicalInfos.gridZoneWidth
				/ GraphicalInfos.SHIFT, threshold, max);
		// dataGenerator = new RandomGenerator(max);
		dataGenerator = new SinusGenerator(max);
		inputGenerator = new InputGenerator();
		graphModel.addData(dataGenerator.getValue());

		gridDrawer = new GridDrawer(graphicalInfos, graphModel);
		infoDrawer = new InfoDrawer(graphicalInfos, graphModel);
		lineDrawer = new LineDrawer(graphicalInfos, graphModel);
		// alarmDrawer = new AlarmDrawer(graphicalInfos, graphModel);
		cursorDrawer = new CursorDrawer(graphicalInfos, graphModel);
		this.timer = timer;
	}

	private void startUpdateTask() {
		updateTask = new TimerTask() {

			@Override
			public void run() {
				// if (!pressed) {
				GraphDMA.this.display.callSerially(new Runnable() {

					@Override
					public void run() {
						boolean changed = false;
						if (pressedInfo) {
							if (xChanged) {
								changed = true;
							}
						} else if (pressedData) {
							int value = inputGenerator.getValue();
							graphModel.addData(value);
							changed = true;
						} else {
							// update model
							int value = dataGenerator.getValue();
							graphModel.addData(value);
							inputGenerator.setValue(value);

							// int threshold = graphModel.getThreshold();
							// alarm = value > threshold;

							changed = true;
						}
						int max = graphModel.getMax();
						int value = graphModel.getLastData();
						int cursorY = graphicalInfos.gridTop + (max - value)
								* graphicalInfos.gridHeight / max;
						cursorDrawer.setY(cursorY);
						if (changed) {
							changed();
						}
					}

				});
				// }
			}
		};
		timer.scheduleAtFixedRate(updateTask, 0, GraphicalInfos.SHIFT_PERIOD);
	}

	private void createHomeButton(final Launcher launcher) {
		int buttonWidth = HomeButton.Width;
		int buttonHeight = HomeButton.Height;
		int buttonX = displayWidth - buttonWidth;
		int buttonY = 0;
		homeButtonRenderer = new HomeButtonRenderer();
		homeButton = new Button(buttonX, buttonY, buttonWidth, buttonHeight);
		homeButton.setListener(new ListenerAdapter() {

			@Override
			public void performAction() {
				launcher.start();
			}
		});

		if (launcher == null) {
			homeButton = new Button(0, 0, 0, 0);
		}
	}

	private void changed() {
		for (Listener listener : listeners) {
			listener.performAction();
		}
	}

	@Override
	public boolean hasBackgroundColor() {
		return false;
	}

	@Override
	public int getBackgroundColor() {
		return Colors.WHITE;
	}

	@Override
	public Layer[] getLayers() {
		// if (alarm) {
		// return new Layer[] { gridDrawer.getLayer(),
		// lineDrawer.getFirstLayer(), lineDrawer.getSecondLayer(),
		// infoDrawer.getLayer(),
		// alarmDrawer.getLayer(), loadInfoDrawer.getLayer() };
		// } else {
		return new Layer[] { /* gridDrawer.getLayer(), */
		lineDrawer.getFirstLayer(), lineDrawer.getSecondLayer(),
				cursorDrawer.getLayer(), infoDrawer.getLayer() /*
																 * ,
																 * loadInfoDrawer
																 * .getLayer()
																 */};
		// }
	}

	@Override
	public int getDisplayTransparencyLevel() {
		return Transparency.OPAQUE;
	}

	@Override
	public void performAction(int event) {
		int action = Pointer.getAction(event);
		Pointer pointer = (Pointer) Event.getGenerator(event);
		final int x = pointer.getX();

		if (homeButton.handleEvent(event)) {
			return;
		}

		switch (action) {
		case Pointer.RELEASED:
			pressedInfo = false;
			pressedData = false;
			break;
		case Pointer.PRESSED:
			if (x > graphicalInfos.gridLeft + graphicalInfos.gridWidth) {
				pressedData = true;
			} else {
				pressedInfo = true;
			}
			// fall down
		case Pointer.DRAGGED:
			if (pressedInfo) {
				if (x != previousX && x != previousX - 1 && x != previousX + 1) {
					infoDrawer.setX(x);
					xChanged = true;
					previousX = x;
				}
			} else {
				int y = pointer.getY();
				int value = graphModel.getMax()
						- ((y - graphicalInfos.gridTop) * graphModel.getMax() / graphicalInfos.gridHeight);
				value = XMath.limit(value, 0, graphModel.getMax());
				inputGenerator.setValue(value);
			}
			break;
		}
	}

	@Override
	public void performAction() {
	}

	@Override
	public void performAction(int value, Object object) {
	}

	public void destroy() {
		if (updateTask != null) {
			updateTask.cancel();
			updateTask = null;
		}
	}

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	@Override
	public void paint(GraphicsContext g) {
		g.setColor(0x202020);
		g.fillRect(0, 0, displayWidth, displayHeight);
		gridDrawer.draw(g, graphicalInfos, graphModel);
		homeButtonRenderer.paint(g, homeButton);
	}

	@Override
	protected void showNotify() {
		super.showNotify();
		startUpdateTask();
	}

	@Override
	protected void hideNotify() {
		super.hideNotify();
		destroy();
	}
}
