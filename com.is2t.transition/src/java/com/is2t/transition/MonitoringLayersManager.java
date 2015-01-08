/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import com.is2t.demo.utilities.activity.Monitoring;
import com.is2t.layers.LayersManager;
import com.is2t.layers.Transparency;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;
import ej.motion.Motion;
import ej.motion.ease.EaseInMotion;
import ej.motion.ease.EaseOutMotion;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.rendering.Look;

public class MonitoringLayersManager extends LayersManager {

	private static final int APPARITION_DURATION = 800;
	private static final int PERIOD = 50;
	private static final int TIMES_BEFORE_UDPATE = 10;

	private static final int PADDING = 5;
	private static final boolean CPULOAD_PRINTED = System.getProperty("com.is2t.demo.CPULoadPrinted") != null;
	private static final boolean FPS_PRINTED = System.getProperty("com.is2t.demo.FPSPrinted") != null;
	private static final boolean LAYERS_DISABLED = System.getProperty("com.is2t.demo.NoLayer") != null;

	private static Timer timer;
	private static DisplayFont font;
	private static int width;
	private static int height;
	private static Image image;
	private static GraphicsContext g;
	private static boolean Animating = false;
	private static boolean IsShown = true;
	private static int Shift = 0;
	private static boolean needToFlush;

	private static int x;
	private static int y;
	private static Look look;

	private TimerTask task;
	/**
	 * Null if no monitoring implementation available. In this case, this layer is not shown.
	 */
	private Monitoring monitoring;
	private final boolean autoRefresh;

	@Override
	public boolean flush() {
		// If layers are disabled, the code must not be called.
		if (LAYERS_DISABLED) {
			return false;
		}

		return super.flush();
	}

	@Override
	public void show() {
		// If layers are disabled, the code must not be called.
		if (!LAYERS_DISABLED) {
			super.show();
		}
	}

	public static void setMonitoringLook(Look look) {
		MonitoringLayersManager.look = look;

		if (image != null) {
			update(0, 0);
		}
	}

	static {
		font = DisplayFont.getFont(DisplayFont.LATIN, 15, DisplayFont.STYLE_PLAIN);
		computeImageSize();

		if (width > 0 && height > 0) {
			image = Image.createImage(width, height);
			g = image.getGraphicsContext();
			AbstractLayersManager.setTransparency(g, Transparency.TRANSPARENT);
			g.fillRect(0, 0, width, height);
		}
	}

	private static void computeImageSize() {
		if (FPS_PRINTED && CPULOAD_PRINTED) {
			width = Math.max(font.stringWidth("000% CPU"), font.stringWidth("100 FPS")) + 2 * PADDING;
			height = 2 * font.getHeight() + 3 * PADDING;
		} else if (CPULOAD_PRINTED) {
			width = font.stringWidth("000% CPU") + 2 * PADDING;
			height = font.getHeight() + 2 * PADDING;
		} else if (FPS_PRINTED) {
			width = font.stringWidth("100 FPS") + 2 * PADDING;
			height = font.getHeight() + 2 * PADDING;
		} else {
			width = 0;
			height = 0;
		}
	}

	public MonitoringLayersManager(Display display, boolean autoRefresh) {
		super(display);
		this.autoRefresh = autoRefresh;
		try {
			this.monitoring = ServiceLoaderFactory.getServiceLoader().getService(Monitoring.class);
		} catch (RuntimeException e) {
			// no valid implementation for this service
		}
	}

	public MonitoringLayersManager(Display display) {
		this(display, true);
	}

	public static void setTimer(Timer timer) {
		MonitoringLayersManager.timer = timer;
	}

	private static synchronized void update(int cpuLoad, int fps) {
		int height = MonitoringLayersManager.height;
		int width = MonitoringLayersManager.width;
		GraphicsContext g = MonitoringLayersManager.g;
		g.setColor(look.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT));
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE / 2);
		g.fillRect(2, 0, width - 2, height);
		g.drawVerticalLine(1, 1, height - 3);
		g.drawVerticalLine(0, 2, height - 5);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE / 2);
		g.drawPixel(0, 1);
		g.drawPixel(1, 0);
		g.drawPixel(0, height - 2);
		g.drawPixel(1, height - 1);
		AbstractLayersManager.setTransparency(g, Transparency.OPAQUE);
		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
		g.setFont(font);

		if (FPS_PRINTED && CPULOAD_PRINTED) {
			g.drawString(cpuLoad + "% CPU", width - PADDING, PADDING, GraphicsContext.RIGHT | GraphicsContext.TOP);
			g.drawString(fps + " FPS", width - PADDING, height - PADDING, GraphicsContext.RIGHT
					| GraphicsContext.BOTTOM);
		} else if (CPULOAD_PRINTED) {
			g.drawString(cpuLoad + "% CPU", width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		} else {
			g.drawString(fps + " FPS", width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}
	}

	protected void updated() {
		flush();
	}

	public static void setLocation(int x, int y) {
		MonitoringLayersManager.x = x;
		MonitoringLayersManager.y = y;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setShift(int shift) {
		Shift = shift;
	}

	@Override
	public void showNotify() {
		super.showNotify();
		if (monitoring != null && image != null) {
			task = new TimerTask() {
				int cpt = 0;

				@Override
				public void run() {
					if (++cpt == TIMES_BEFORE_UDPATE) {
						Monitoring monitoring = MonitoringLayersManager.this.monitoring;
						if (monitoring != null) {
							update(monitoring.getCPULoad(), monitoring.getFPS());
						}
						needToFlush = true;
						cpt = 0;
					}
					if (needToFlush && autoRefresh) {
						needToFlush = false;
						updated();
					}
				}
			};
			timer.schedule(task, 0, PERIOD);
		}
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		TimerTask task = this.task;
		if (task != null) {
			task.cancel();
		}
	}

	public static void switchInfos() {
		if (Animating) {
			return;
		}
		Animating = true;
		Motion motion = IsShown ? new EaseInMotion(Shift, width, APPARITION_DURATION) : new EaseOutMotion(Shift, 0,
				APPARITION_DURATION);
		MotionAnimator animator = new MotionAnimator(motion, new MotionListener() {
			@Override
			public void start(int value) {
				step(value);
			}

			@Override
			public void step(int value) {
				Shift = value;
				needToFlush = true;
			}

			@Override
			public void stop(int value) {
				step(value);
				IsShown = !IsShown;
				Animating = false;
			}
		});
		animator.start(timer, PERIOD);
	}

	@Override
	protected void stackLayers() {
		super.stackLayers();
		if (monitoring != null && image != null) {
			addLayer(image, 0xff, x + Shift, y, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

}
