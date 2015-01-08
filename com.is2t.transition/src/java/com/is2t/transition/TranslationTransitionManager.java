/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import com.is2t.layers.Layer;
import com.is2t.layers.LayersHandler;
import com.is2t.layers.Transparency;

import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionManager;
import ej.microui.Listener;
import ej.microui.io.Display;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.layer.AbstractLayersManager;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.mwt.Composite;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

/**
 * Defines transitions by moving panels.
 */
public abstract class TranslationTransitionManager extends MonitoringLayersManager implements TransitionManager {

	/**
	 * Default period between two successive steps of the transition. O
	 * 
	 * @see #setPeriod(long)
	 */
	public static final long DEFAULT_PERIOD = 100;
	/**
	 * Default transition duration.
	 * 
	 * @see #setDuration(long)
	 */
	public static final long DEFAULT_DURATION = 800;
	protected static final boolean USE_FADEOUT = true; // require layers
	protected static Image VEIL;
	private static final boolean USE_DISPLAYABLE = false; // displayable |
															// layers

	// private Image veil;
	protected Image current, veil;
	protected MWTFlowManager<?, ?> currentFlowManager;

	protected boolean forward;
	private long period;
	private long duration;
	private MotionManager motionManager;
	private boolean reverse;
	private boolean over;

	private int shift;

	private int xOldPanel;
	private int yOldPanel;
	private int xNewPanel;
	private int yNewPanel;
	private float percent;

	private Look look;
	private AbstractLayersManager oldLayersManager;

	/**
	 * Creates a transition manager with the {@link #DEFAULT_PERIOD} and {@link #DEFAULT_DURATION}.
	 * <p>
	 * The over and reverse flags are not set.
	 */
	public TranslationTransitionManager() {
		super(Display.getDefaultDisplay());
		this.period = DEFAULT_PERIOD;
		this.duration = DEFAULT_DURATION;

		current = createImage();
		// if (USE_FADEOUT) {
		// veil = createVeil();
		// }
		// add an empty layers handler
		setLayersHandler(new LayersHandler() {
			@Override
			public void removeListener(Listener listener) {
			}

			@Override
			public boolean hasBackgroundColor() {
				return false;
			}

			@Override
			public Layer[] getLayers() {
				return null;
			}

			@Override
			public int getDisplayTransparencyLevel() {
				return Transparency.TRANSPARENT;
			}

			@Override
			public int getBackgroundColor() {
				return 0;
			}

			@Override
			public void addListener(Listener listener) {
			}
		});
	}

	@Override
	protected void updated() {
		// do not flush here
	}

	private Image createVeil() {
		if (VEIL == null) {
			VEIL = createImage();
			GraphicsContext gc = VEIL.getGraphicsContext();
			gc.setColor(getLook().getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT));
			gc.fillRect(0, 0, display.getWidth(), display.getHeight());
		}
		return VEIL;
	}

	private Image createImage() {
		Display display = this.display;
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		return Image.createImage(displayWidth, displayHeight);
	}

	@Override
	public long getPeriod() {
		return this.period;
	}

	@Override
	public void setPeriod(long period) {
		this.period = period;
	}

	@Override
	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public void setMotionManager(MotionManager motionManager) {
		this.motionManager = motionManager;
	}

	/**
	 * Sets the transition inverse flag.
	 * 
	 * @param reverse
	 *            the reverse flag to set.
	 */
	public void reverseTransition(boolean reverse) {
		this.reverse = reverse;
	}

	/**
	 * Gets whether or not the reverse flag is set.
	 * 
	 * @return the reverse flag.
	 */
	public boolean isReversed() {
		return reverse;
	}

	/**
	 * Sets the transition over flag.
	 * 
	 * @param over
	 *            the over flag to set.
	 */
	public void setOver(boolean over) {
		this.over = over;
	}

	/**
	 * Gets whether or not the over flag is set.
	 * <p>
	 * If {@code true}, the pages will be superimposed during transition. If {@code false}, the pages will be
	 * side-by-side.
	 * 
	 * @return the over flag.
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Gets the motion for the translation.
	 * <p>
	 * Uses {@link MotionManager#easeOut(int, int, long)} by default.
	 * 
	 * @param start
	 *            the start value.
	 * @param stop
	 *            the stop value.
	 * @param forward
	 *            {@code true} if going forward, {@code false} otherwise.
	 * @return the move for the translation.
	 */
	protected Motion getMotion(int start, int stop, boolean forward) {
		return motionManager.easeOut(start, stop, this.duration);
	}

	@Override
	public Motion getMotion(MWTFlowManager<?, ?> flowManager, boolean forward) {
		this.forward = forward;
		int bound = getBound(flowManager, forward);
		shift = -bound;
		int start;
		int stop;
		if (forward) {
			start = bound;
			stop = 0;
		} else {
			start = 0;
			stop = bound;
		}
		return getMotion(start, stop, forward);
	}

	/**
	 * Gets translation amplitude.
	 * <p>
	 * Used by {@link #getMotion(MWTFlowManager, boolean)} to:
	 * <ul>
	 * <li>compute shift between pages for no over transitions,</li>
	 * <li>create the bounds of the motion (depending on the way).</li>
	 * </ul>
	 * 
	 * @param flowManager
	 *            the flow manager that requests the animation.
	 * @param forward
	 *            {@code true} if going forward, {@code false} otherwise.
	 * @return the expected amplitude.
	 * @see #isOver()
	 * @see #getShift()
	 */
	protected abstract int getBound(MWTFlowManager<?, ?> flowManager, boolean forward);

	private void drawTree(GraphicsContext gc, Widget widget) {
		Renderer r = widget.getRenderer();

		if (r != null) {
			r.render(gc, widget);
		}

		if (widget instanceof Composite) {
			Composite c = (Composite) widget;

			Widget[] widgetsArray = c.getWidgets();

			// draw children
			for (int j = widgetsArray.length; --j >= 0;) {

				Widget w = widgetsArray[j];
				if (!w.isVisible()) {
					continue;
				}

				// set translation
				gc.translate(w.getX(), w.getY());
				int cx = gc.getClipX();
				int cy = gc.getClipY();
				int cw = gc.getClipWidth();
				int ch = gc.getClipHeight();
				gc.clipRect(0, 0, w.getWidth(), w.getHeight());
				// gc.setClip(0, 0, w.getWidth(), w.getHeight());
				try {
					drawTree(gc, w);
				} finally {
					// restore translation
					gc.setClip(cx, cy, cw, ch);
					gc.translate(-w.getX(), -w.getY());
				}
			}
		}
	}

	@Override
	public void start(MWTFlowManager<?, ?> flowManager, int value) {
		final Panel currentPanel = flowManager.getCurrentPanel();

		currentFlowManager = flowManager;
		if (USE_DISPLAYABLE) {
			final Image old = AbstractLayersManager.createImage(display);
			Displayable displayable = new Displayable(display) {

				@Override
				public void performAction(int event) {
					//
				}

				@Override
				public void paint(GraphicsContext g) {
					g.drawImage(current, xNewPanel, yNewPanel, 0);
					try {
						g.drawImage(old, xOldPanel, yOldPanel, 0);
					} catch (NullPointerException e) {
						// image removed
					}
				}

				@Override
				protected void showNotify() {
					createCurrentImage(currentPanel);
				}
			};

			displayable.show();

			if (USE_FADEOUT) {
				show();
			}
		} else {
			show();
		}

		step(flowManager, value);
	}

	@Override
	public void show() {
		oldLayersManager = getLayersManager(display);
		super.show();
	}

	@Override
	public void showNotify() {
		super.showNotify();
		MWTFlowManager<?, ?> currentFlowManager = this.currentFlowManager;
		if (currentFlowManager != null) {
			createCurrentImage(currentFlowManager.getCurrentPanel());
		}
	}

	@Override
	public void hide() {
		if (oldLayersManager != null) {
			oldLayersManager.show();
		}
		// super.hide();
	}

	/**
	 *
	 */
	protected void createCurrentImage(Panel currentPanel) {
		// do not need to be executed in display thread: already asynchronous
		currentPanel.validate(display.getWidth(), display.getHeight());
		Renderer r = currentPanel.getRenderer();
		GraphicsContext currentGC = current.getGraphicsContext();
		if (r != null) {
			r.render(currentGC, currentPanel);
		}
		drawTree(currentGC, currentPanel.getWidget());

	}

	@Override
	public void stop(MWTFlowManager<?, ?> flowManager, int value) {
		// !USE_DISPLAYABLE || USE_FADEOUT

		final Panel oldPanel = flowManager.getOldPanel();
		final Panel currentPanel = flowManager.getCurrentPanel();

		Desktop desktop = oldPanel.getDesktop();
		currentPanel.show(desktop, true);
		if (USE_DISPLAYABLE) {
			desktop.show();
		}
		hide();
	}

	/**
	 * Gets whether the current transition is forward or backward.
	 * 
	 * @return {@code true} if going forward, {@code false} otherwise.
	 * @see #getMotion(MWTFlowManager, boolean)
	 */
	protected boolean isForward() {
		return forward;
	}

	/**
	 * Gets the current transition shift between pages for no over transitions.
	 * 
	 * @return the shift.
	 */
	protected int getShift() {
		return shift;
	}

	/**
	 * Moves the panels.
	 * 
	 * @param flowManager
	 *            the flow manager responsible of the animation.
	 * @param xOldPanel
	 *            the x coordinate to move the old panel to.
	 * @param yOldPanel
	 *            the y coordinate to move the old panel to.
	 * @param xNewPanel
	 *            the x coordinate to move the new panel to.
	 * @param yNewPanel
	 *            the y coordinate to move the new panel to.
	 */
	protected void move(final MWTFlowManager<?, ?> flowManager, final int xOldPanel, final int yOldPanel,
			final int xNewPanel, final int yNewPanel, final int value) {
		final Desktop desktop = flowManager.getDesktop();
		desktop.getDisplay().callSerially(new Runnable() {
			@Override
			public void run() {
				TranslationTransitionManager.this.xOldPanel = xOldPanel;
				TranslationTransitionManager.this.yOldPanel = yOldPanel;
				TranslationTransitionManager.this.xNewPanel = xNewPanel;
				TranslationTransitionManager.this.yNewPanel = yNewPanel;
				TranslationTransitionManager.this.percent = (float) value / getBound(flowManager, forward);

				if (USE_DISPLAYABLE) {
					desktop.getDisplay().getDisplayable().repaint();
				} else {
					flush();
				}
			}
		});
	}

	@Override
	protected void stackLayers() {
		if (!USE_DISPLAYABLE) {
			int w = display.getWidth();
			int h = display.getHeight();

			if (forward) {
				addLayer(Transparency.OPAQUE, -xOldPanel, -yOldPanel, w + xOldPanel, h + yOldPanel, 0, 0, 0);
				addLayer(current, Transparency.OPAQUE, 0, 0, w - xNewPanel, h - yNewPanel, xNewPanel, yNewPanel, 0);
			} else {
				addLayer(Transparency.OPAQUE, 0, 0, w - xOldPanel, h - yOldPanel, xOldPanel, yOldPanel, 0);
				addLayer(current, Transparency.OPAQUE, -xNewPanel, -yNewPanel, w + xNewPanel, h + yNewPanel, 0, 0, 0);
			}
		} else {
			addLayer(Transparency.OPAQUE);
		}
		if (USE_FADEOUT) {
			addLayer(veil, (int) ((forward ? (1f - percent) : percent) * Transparency.OPAQUE), xOldPanel, yOldPanel,
					GraphicsContext.LEFT | GraphicsContext.TOP);
			// addLayer(veil, (int) ((forward ? percent : (1f - percent)) *
			// Transparency.OPAQUE), xNewPanel,
			// yNewPanel, GraphicsContext.LEFT | GraphicsContext.TOP);
		}
		super.stackLayers();
	}

	protected void superStackLayers() {
		super.stackLayers();
	}

	@Override
	public void setLook(Look look) {
		this.look = look;

		if (USE_FADEOUT) {
			veil = createVeil();
		}
	}

	@Override
	public Look getLook() {
		return look;
	}
}
