/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.util.Utilities;
import com.is2t.mwt.widgets.IImageAnimation;

import ej.bon.TimerTask;
import ej.microui.Listener;
import ej.microui.io.Image;
import ej.mwt.Widget;

public class ImageAnimation extends Widget implements IImageAnimation {

	private final Image[] frames;
	private int period;

	private int currentFrame;
	private TimerTask task;
	private Listener listener;

	public ImageAnimation(Image[] frames, int period) {
		this.frames = frames;
		this.period = period;
		// this.currentFrame = 0; VM_DONE
	}

	public int getCurrentFrameIndex() {
		return currentFrame;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Image[] getFrames() {
		return frames;
	}
	
	public int getFramesCount() {
		return frames.length;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public Listener getListener() {
		return listener;
	}

	public synchronized void play() {
		if (task == null) {
			task = new TimerTask() {
				public void run() {
					currentFrame = (currentFrame + 1) % frames.length;
					currentFrameUpdated();
				}
			};
			Utilities.getTimer().scheduleAtFixedRate(task, period, period);
		}
	}

	public synchronized void pause() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

	public synchronized void stop() {
		pause();
		this.currentFrame = 0;
		currentFrameUpdated();
	}
	
	protected void currentFrameUpdated() {
		if (listener != null) {
			listener.performAction(currentFrame, this);
		}
		repaint();
	}
}
