/**
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import com.is2t.mwt.util.Utilities;

import ej.bon.TimerTask;
import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

/**
 * The console widget displays a multi-lines text and a cursor.
 */
public class Console extends MultiLinesLabel {

	/**
	 * The cursor OFF state.
	 */
	public static final int CURSOR_OFF = 0;
	/**
	 * The cursor ON state.
	 */
	public static final int CURSOR_ON = 1;
	/**
	 * The cursor BLINK state.
	 */
	public static final int CURSOR_BLINK = 2;

	private static final long BLINK_PERIOD = 500;

	private int cursorState;
	private int cursorCurrentState;
	private int cursorLine;
	private int cursorColumn;
	private TimerTask blinkTask;

	/**
	 * Creates a console.
	 * 
	 * @param linesCount
	 *            the number of lines displayed
	 */
	public Console(int linesCount) {
		super(linesCount);
	}

	/**
	 * Update the cursor parameters.
	 * <p>
	 * The console is repaint.
	 * 
	 * @param state
	 *            the new state.
	 * @param line
	 *            the new line.
	 * @param column
	 *            the new column.
	 */
	public void updateCursor(int state, int line, int column) {
		cursorState = state;
		synchronized (this) {
			if (state == CURSOR_BLINK) {
				if (blinkTask == null) {
					// start blinking
					blinkTask = new BlinkTask();
					Utilities.getTimer().schedule(blinkTask, BLINK_PERIOD, BLINK_PERIOD);
				}
			} else {
				if (blinkTask != null) {
					// stop blinking
					blinkTask.cancel();
					blinkTask = null;
				}
				cursorCurrentState = state;
			}
		}
		// update position
		cursorLine = line;
		cursorColumn = column;
		repaint();
	}

	/**
	 * Returns the cursor state: one of {@link #CURSOR_OFF}, {@link #CURSOR_ON}, {@link #CURSOR_BLINK}.
	 * 
	 * @return the cursor state
	 */
	public int getCursorState() {
		return cursorState;
	}

	/**
	 * Returns {@link #CURSOR_ON} if the cursor is on, {@link #CURSOR_OFF} otherwise.
	 * 
	 * @return the cursor current state
	 */
	public int getCursorCurrentState() {
		return cursorCurrentState;
	}

	/**
	 * @return the cursor line
	 */
	public int getCursorLine() {
		return cursorLine;
	}

	/**
	 * @return the cursor column
	 */
	public int getCursorColumn() {
		return cursorColumn;
	}

	void switchCursorCurrentState() {
		cursorCurrentState = 1 - cursorCurrentState; // 0 -> 1, 1 -> 0, ...
		repaint();
	}

	/**
	 * Switch the cursor state.
	 */
	class BlinkTask extends TimerTask {
		@Override
		public void run() {
			switchCursorCurrentState();
		}
	}

	public int getMaxTextWidth(int characterCount) {
		StringBuilder text = new StringBuilder();

		for (int i = 0; i < characterCount; i++) {
			text.append('W'); // A big character.
		}

		Look look = getRenderer().getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		return font.stringWidth(text.toString());
	}
}
