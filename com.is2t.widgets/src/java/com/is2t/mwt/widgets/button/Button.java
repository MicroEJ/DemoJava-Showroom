/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button;

import com.is2t.mwt.widgets.IButton;
import com.is2t.mwt.widgets.tiny.Label;

import ej.microui.Command;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;

/**
 * <p>A button is a widget which can be pressed. By activating a button, an
 * action is triggered and sent to its potential {@link Listener}.</p>
 * <p>As an extension of a {@link Label}, a button wraps a text which is displayed
 * by its renderer.</p>
 * <p>A button manages its own selection behavior through mouse and event handling.</p>
 */
public class Button extends Label implements IButton {

	protected Listener selectionListener;

	private boolean pressed;

	/**
	 * Creates a new button with an empty text label.<br>
	 * Equivalent to {@link #Button(String)} passing an empty string (<code>""</code>) as argument.
	 */
	public Button() {
		this("");
	}

	/**
	 * Creates a new button with the given string as text label.<br>
	 * A button is enabled by default.<br>
	 *
	 * @param text
	 *            defines the text to display on the button.
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public Button(String text) {
		super(text);
		// override label properties
		setEnabled(true);
	}

	/**
	 * @return the pressed state.
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Triggers a selection on the button. If the button is not enabled, nothing is done.
	 */
	public void select() {
		if (!isEnabled()) {
			return;
		}
		notifyListener(0);
	}

	/**
	 * Gets the defined listener.
	 *
	 * @return the defined listener or <code>null</code>
	 */
	public Listener getListener() {
		return selectionListener;
	}

	/**
	 * Defines which {@link Listener} to associates to the button.<br>
	 * Each selection action on an enabled button will call
	 * {@link Listener#performAction(int, Object)} passing (0, this), if the defined listener is not
	 * <code>null</code>.
	 *
	 * @param listener
	 *            the listener associated to the button, can be <code>null</code>.
	 */
	public void setListener(Listener listener) {
		this.selectionListener = listener;
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Event.COMMAND) {
			int data = Event.getData(event);
			if (data == Command.SELECT) {
				select();
				return true;
			}
		} else if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				pressed = true;
				repaint();
				break;
			case Pointer.RELEASED:
				if (isPressed()) {
					select();
				}
				// fall down
			case Pointer.EXITED:
				pressed = false;
				repaint();
				break;
			}
		}
		return false;
	}

	protected void notifyListener(int event) {
		if (selectionListener != null) {
			selectionListener.performAction(event, this);
		}
	}

}
