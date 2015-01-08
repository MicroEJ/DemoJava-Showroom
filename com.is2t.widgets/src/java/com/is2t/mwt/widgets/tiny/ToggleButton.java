/*
 * Java
 *
 * Copyright 2012-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.IObservable;
import com.is2t.mwt.widgets.IToggle;

import ej.microui.Command;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;

/**
 * <p>
 * Common behavior for two-state widgets.
 * </p>
 */
public abstract class ToggleButton extends Label implements IToggle, IObservable {

	/**
	 * TODO <br>
	 * 
	 * TODO default selection state<br>
	 * 
	 * It is enabled by default.<br>
	 * 
	 * @param text
	 *            defines the text to display.
	 * @throws NullPointerException
	 *             if the given text is null
	 */
	public ToggleButton(String text) {
		super(text);
		// override label properties
		setEnabled(true);
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

	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Event.COMMAND) {
			int data = Event.getData(event);
			if (data == Command.SELECT) {
				toggleSelection(); // trigger a repaint
				return true;
			}
			// fall down
		} else if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			if (action == Pointer.RELEASED) {
				Pointer pointer = (Pointer) Event.getGenerator(event);
				// retrieve coordinates of pointer relative to this widget parent (Composite or Panel)
				int x = pointer.getX() - getAbsoluteX() + getX();
				int y = pointer.getY() - getAbsoluteY() + getY();
				if (contains(x, y)) {
					toggleSelection(); // trigger a repaint
				}
			}
			// returned value doesn't matter, pointer events are not propagated by system to hierarchies
			// fall down
		}
		return false; // doesn't consume the event
	}

	public boolean isSelected() {
		return selected;
	}
	
	@Override
	public void setSelected(boolean selected) {
		if(this.selected != selected) {
			this.selected = selected;
			notifySelectionChange();
			repaint();
		}
	}

	/**
	 * <p>
	 * Inverts the selection state, notifies listener and triggers a repaint.
	 * </p>
	 */
	public void toggleSelection() {
		setSelected(!selected);
	}
	
	@Override
	public void setGroupListener(Listener listener) {
		groupListener = listener;
	}
	
	@Override
	public Listener getGroupListener() {
		return groupListener;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected boolean selected;
	protected Listener selectionListener;
	protected Listener groupListener;

	/**
	 * <p>
	 * Notifies potential listener of a selection change.
	 * </p>
	 */
	protected void notifySelectionChange() {
		if(groupListener != null && selected) {
			groupListener.performAction(0, this);
		}
		
		if (selectionListener != null) {
			selectionListener.performAction(0, this);
		}
	}
}
