/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.widgets.contracts.UpDownRendererContract;

import ej.microui.Command;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;
import ej.mwt.MWT;


/**
 * A combo is a widget that permit to choose an item from a list of items.
 */
public class Combo extends GenericList {

	/**
	 * Creates a new combo. It is enabled an not transparent.
	 */
	public Combo() {
		super();
	}

	/**
	 * @see com.is2t.mwt.widgets.tiny.GenericList#addItem(java.lang.Object)
	 */
	public void addItem(String item) {
		super.addItem(item);
		// automatically select it if the list was empty
		if (model.getSelection() == MWT.EMPTY) {
			setSelection(0);
		}
	}

	/**
	 * @see com.is2t.mwt.widgets.tiny.GenericList#addItems(Object[])
	 */
	public void addItems(String[] items) {
		super.addItems(items);
		// automatically select the first if the list was empty
		if (model.getSelection() == MWT.EMPTY) {
			setSelection(0);
		}
	}

	/**
	 * Consume next, previous and pointer events to update the selection.
	 * @see ej.mwt.Widget#handleEvent(int)
	 */
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		handleEvent: {
			if(type == Event.COMMAND) {
				int cmd = Event.getData(event);
				int currentSelection = model.getSelection();
				switch (cmd) {
				case Command.NEXT:
					currentSelection--;
					break;
				case Command.PREVIOUS:
					currentSelection++;
					break;
				default:
					//not handled
					break handleEvent;
				}
				if (model.isInBounds(currentSelection)) {
					setSelection(currentSelection);
					return true;
				}
				return false;
			} else if(type == Event.POINTER) {
				int action = Pointer.getAction(event);
				switch (action) {
				case Pointer.RELEASED:
					Pointer pointer = (Pointer) Event.getGenerator(event);
					int x = pointer.getX();
					int y = pointer.getY();
					UpDownRendererContract renderer = (UpDownRendererContract) getRenderer();
					int zone = renderer.getCommand(this, x - getAbsoluteX(), y - getAbsoluteY());
					int currentSelection = model.getSelection();
					switch(zone) {
					case UpDownRendererContract.DECREASE_COMMAND:
						if (model.isInBounds(--currentSelection)) {
							setSelection(currentSelection);
						}
						break;
					case UpDownRendererContract.INCREASE_COMMAND:
						if (model.isInBounds(++currentSelection)) {
							setSelection(currentSelection);
						}
						break;
					}
					break;
				}
				return true;
			}
		}
		return super.handleEvent(event);
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public Listener getListener() {
		return listener;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	private Listener listener;

	public boolean isLastElementSelected() {
		return getSelection() == getItems().length - 1;
	}

	public boolean isFirstElementSelected() {
		return getSelection() == 0;
	}

}
