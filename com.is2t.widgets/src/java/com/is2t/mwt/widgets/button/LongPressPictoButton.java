/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button;

import com.is2t.mwt.util.Utilities;
import com.is2t.mwt.widgets.Picto;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.Event;
import ej.microui.io.Pointer;

public class LongPressPictoButton extends PictoButton {

	private static final int TIMER_BEFORE_REPEAT = 500;
	private static final int REPEAT_PERIOD = 180;

	private TimerTask repeatedTask;
	private final Timer timer;

	public LongPressPictoButton(Picto picto) {
		super(picto);
		timer = Utilities.getTimer();
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);

		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				repeat();
				return true;

			case Pointer.RELEASED:
				stopRepeat();
				notifyListener(event);
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private void repeat() {
		repeatedTask = new TimerTask() {

			@Override
			public void run() {
				selectionListener.performAction(0, null);
			}
		};
		timer.schedule(repeatedTask, TIMER_BEFORE_REPEAT, REPEAT_PERIOD);
	}

	private void stopRepeat() {
		if(repeatedTask != null) {
			repeatedTask.cancel();
		}
	}
}
