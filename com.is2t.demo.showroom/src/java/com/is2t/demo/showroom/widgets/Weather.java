/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.widgets;

import com.is2t.demo.showroom.weather.Day;
import com.is2t.mwt.util.Utilities;

import ej.bon.TimerTask;
import ej.microui.Listener;
import ej.microui.io.DisplayFont;
import ej.mwt.Widget;

public class Weather extends Widget {

	public static final int MAX_LEVEL = 40;

	private final DisplayFont font;
	private final Day[] days;
	private int level;
	private boolean appearing;
	private boolean onClockMode;
	private int currentDayIndex;
	private int currentCharIndex;
	private int cptAnim;
	private boolean onboot;
	private TimerTask task;
	private Listener clockAppearListener;

	public Weather(DisplayFont font, Day[] days, boolean onboot) {
		this.font = font;
		this.days = days;
		this.onboot = onboot;
		setEnabled(false);
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	public void showNotify() {
		this.currentCharIndex = 0;
		this.level = 0;
		this.appearing = true;
		this.task = new TimerTask() {
			@Override
			public void run() {
				if (Weather.this.appearing) {
					if (Weather.this.level < MAX_LEVEL) {
						++Weather.this.level;
					}
				} else {
					if (Weather.this.level > 0) {
						--Weather.this.level;
					} else {
						Weather.this.appearing = true;
						if (Weather.this.currentDayIndex == Weather.this.days.length - 1) {
							Weather.this.currentDayIndex = 0;
						}
						// The clock is printed every 2 days.
						else if (Weather.this.currentDayIndex % 2 == 0) {
							if (!onClockMode) {
								onClockMode = true;
								notifyClockAppearListener();
							} else {
								onClockMode = false;
								++Weather.this.currentDayIndex;
							}
						} else {
							++Weather.this.currentDayIndex;
						}
					}
				}
				++Weather.this.cptAnim;
				if ((Weather.this.cptAnim & 3) == 0) {
					if (Weather.this.currentCharIndex == Weather.this.days[Weather.this.currentDayIndex].getChars().length - 1) {
						Weather.this.currentCharIndex = 0;
					} else {
						++Weather.this.currentCharIndex;
					}
				}
				
				if(onboot){
					// IS2T is printed half as long as the weather.
					if (Weather.this.cptAnim == MAX_LEVEL) {
						Weather.this.cptAnim = 0;
						Weather.this.appearing = true;
						Weather.this.level = 0;
						onboot = false;
					}
				}
				else if(onClockMode) {
					// The clock is printed twice as long as the weather.
					if (Weather.this.cptAnim == MAX_LEVEL * 6) {
						Weather.this.cptAnim = 0;
						Weather.this.appearing = false;
						notifyClockAppearListener();
					}
				}else {
					if (Weather.this.cptAnim == MAX_LEVEL * 3) {
						Weather.this.cptAnim = 0;
						Weather.this.appearing = false;
					}
				}
	
				repaint();
			}
		};
		Utilities.getTimer().schedule(this.task, 0, 50);
	}

	public void hideNotify() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

	/**
	 * @return the font
	 */
	public DisplayFont getFont() {
		return font;
	}

	public char getCurrentChar() {
		return getCurrentDay().getChars()[this.currentCharIndex];
	}

	public int getTemperature() {
		return getCurrentDay().getTemperature();
	}

	public Day getCurrentDay() {
		return this.days[this.currentDayIndex];
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return this.level;
	}

	public boolean isAppearing() {
		return this.appearing;
	}

	public boolean isOnClockMode() {
		return onClockMode;
	}
	
	public boolean isOnboot() {
		return onboot;
	}

	private void notifyClockAppearListener() {
		if (clockAppearListener != null) {
			clockAppearListener.performAction();
		}
	}

	public void setClockAppearListener(Listener clockAppearListener) {
		this.clockAppearListener = clockAppearListener;
	}
}
