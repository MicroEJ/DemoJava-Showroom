/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.IText;
import com.is2t.mwt.widgets.contracts.Controller;
import com.is2t.mwt.widgets.keyboard.IKeyboard;

import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Keyboard;
import ej.microui.io.Pointer;
import ej.mwt.Widget;

/**
 * <p>This is a single line text field.</p>
 */
public class TextField extends Widget implements IText, Listener {

	/**
	 * Creates a new textfield with an empty text.<br>
	 * Equivalent to {@link #TextField(String)} passing an empty string (<code>""</code>) as argument.
	 */
	public TextField() {
		this("");
	}

	/**
	 * Creates a new textfield specifying its text.<br>
	 * A textfield is enabled by default.<br>
	 *
	 * @param text
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public TextField(String text) {
		super();
		setText(text);
		setEnabled(true);
	}

	public String getText() {
		return buffer.toString();
	}

	/**
	 * Sets the content text.
	 *
	 * @param text
	 *            the text to set
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public void setText(String text) {
		buffer = new StringBuffer(text);
		setCaret(text.length());
		repaint();
	}

	public void back() {
		if (!removeSelection()) {
			int caret = getCaret();
			if (caret > 0) {
				buffer.deleteCharAt(caret - 1);
				setCaret(caret - 1);
				notifyListener();
			}
		}
	}

	public void insert(String text) {
		removeSelection();
		int caret = getCaret();
		buffer.insert(caret, text);
		setCaret(caret + text.length());
		notifyListener();
	}

	/**
	 *
	 */
	public void insert(char c) {
		removeSelection();
		int caret = getCaret();
		buffer.insert(caret, c);
		setCaret(caret + 1);
		notifyListener();
	}

	/**
	 * Returns the part of the text that is selected.<br>
	 * The selection is the part of text between selection start
	 * @return the selected text
	 */
	public String getSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		char[] selectedChars = new char[selectionEnd - selectionStart + 1];
		buffer.getChars(selectionStart, selectionStart, selectedChars, 0);
		return new String(selectedChars);
	}

	/**
	 * Returns the caret position in the text.
	 * @return the caret position
	 */
	public int getCaret() {
		return caretEnd & 0xff;
	}

	/**
	 * Returns the start index of the selection.
	 *
	 * @return the start index of the selection
	 */
	public int getSelectionStart() {
		return Math.min(caretStart & 0xff, caretEnd & 0xff);
	}

	/**
	 * Returns the end index of the selection.
	 *
	 * @return the end index of the selection
	 */
	public int getSelectionEnd() {
		return Math.max(caretStart & 0xff, caretEnd & 0xff);
	}

	public void setSelection(int start, int end) {
		int length = buffer.length();
		start = Math.min(Math.max(start, 0), length);
		end = Math.min(Math.max(end, 0), length);
		this.caretStart = (byte) start;
		this.caretEnd = (byte) end;
		repaint();
	}

	/**
	 * Sets the caret position in the text.
	 *
	 * @param position
	 *            the position to set
	 */
	public void setCaret(int position) {
		setSelection(position, position);
	}

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * Defines which {@link Listener} to associates to the text field.<br>
	 * <ul>
	 * <li>Each time the text is modified, {@link Listener#performAction()} will be called on the
	 * defined listener.</li>
	 * <li>Each time the text selection is modified, {@link Listener#performAction(int)} will be
	 * called passing the caret on the defined listener.</li>
	 * </ul>
	 *
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected StringBuffer buffer;
	// unsigned bytes: 256 positions available
	// must be read with a (& 0xff)
	protected byte caretStart;
	protected byte caretEnd;
	private Listener listener;

	protected boolean removeSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd) {
			buffer.delete(selectionStart, selectionEnd);
			setCaret(selectionStart);
			return true;
		}
		return false;
	}

	protected void setSelectionStop(int stop) {
		caretEnd = (byte) stop;
		repaint();
	}

	protected void notifyListener() {
		if (listener != null) {
			listener.performAction();
		}
	}

	protected void notifyListener(int caret) {
		if (listener != null) {
			listener.performAction(caret);
		}
	}

	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.RELEASED:
				//caret changed
				notifyListener(getCaret());
				return true;
			}
		} else if (type == Event.KEYBOARD) {
			Keyboard keyboard = (Keyboard) Event.getGenerator(event);
			if (keyboard.getAction(event) == Keyboard.TEXT_INPUT) {
				char c = keyboard.getNextChar(event);
				if (c == IKeyboard.BACKSPACE) {
					back();
				} else {
					insert(c);
				}
			}
			return true;
		}

		Controller renderer = (Controller) getRenderer();
		return renderer.handleEvent(this, event) == Controller.HANDLED;
	}

	public void performAction() {
	}

	public void performAction(int value, Object object) {
	}

	public void performAction(int value) {
		handleEvent(value);
	}
}
