/*
 * Java
 *
 * Copyright 2014 IS2T.
 * This Software has been designed or modified by IS2T S.A. 
 * IS2T licensed Delta Dore the right to freely use, modify and distribute this Software.
 */
package com.is2t.demo.utilities.transition;

import com.is2t.demo.utilities.DrawHierarchy;

import ej.bon.Timer;
import ej.microui.io.Display;
import ej.microui.io.Displayable;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.io.Viewable;
import ej.microui.layer.AbstractLayersManager;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.ease.EaseMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.Desktop;

public class SimpleTransitionDisplayable extends Displayable implements TransitionDisplayable, MotionListener {

	private static final int MOVING_TIME = 1000;
	private static final int ANIMATION_PERIOD = 20;
	private Displayable newDisplayable;
	private int currentY;
	private Image oldScreenshot;
	private Image newScreenshot;
	private boolean forward;
	private Timer timer;
	private MotionAnimator animator;

	public SimpleTransitionDisplayable(Display display, Timer timer) {
		super(display);
		this.timer = timer;
	}

	public void start() {
		oldScreenshot = AbstractLayersManager.createImage(getDisplay());
		show();
		MotionManager motionManager = new EaseMotionManager();
		Motion motion = motionManager.easeOut(0, getDisplay().getHeight(), MOVING_TIME);
		animator = new MotionAnimator(motion, this);
		animator.start(timer, ANIMATION_PERIOD);
	}
	
	@Override
	public void start(int value) {
		step(value);
	}

	@Override
	public void step(int value) {
		this.currentY = value;
		repaint();
	}

	@Override
	public void stop(int value) {
		newDisplayable.show();
	}
	
	@Override
	protected void hideNotify() {
		super.hideNotify();
		animator.stop();
		animator = null;
	}
	
	@Override
	public void paint(GraphicsContext g) {
		int displayHeight = getDisplay().getHeight();
		int y = forward ? currentY : -currentY;
		int offsetY = forward ? -displayHeight : displayHeight;
		g.drawImage(oldScreenshot, 0, y, 0);
		g.drawImage(newScreenshot, 0, y + offsetY, 0);
	}

	@Override
	public void performAction(int event) {
		// nothing to do
	}

	@Override
	public void setForward(boolean forward) {
		this.forward = forward;
	}

	@Override
	public void setNewDisplayable(Displayable displayable) {
		this.newDisplayable = displayable;
		Display display = getDisplay();
		newScreenshot = Image.createImage(display.getWidth(), display.getHeight());

		GraphicsContext gc = newScreenshot.getGraphicsContext();

		if (newDisplayable instanceof Desktop) {
			Desktop desktop = (Desktop) newDisplayable;
			desktop.validate();
			DrawHierarchy.draw(gc, desktop);
		} else if (newDisplayable instanceof Viewable) {
			DrawHierarchy.draw(gc, (Viewable) newDisplayable);
		}else {
			DrawHierarchy.draw(gc, newDisplayable);
		}
	}
}
