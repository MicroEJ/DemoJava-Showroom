/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.widget;

import com.is2t.mwt.widgets.IButton;
import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.label.LeftIconLabel;

import ej.microui.Command;
import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;

public class LeftIconLabelButton extends LeftIconLabel implements IButton {
	
	protected Listener selectionListener;
	private boolean pressed;

	public LeftIconLabelButton(String text, int maxLineCount, Picto icon) {
		super(text, maxLineCount, icon);
		setEnabled(true);
	}

	public LeftIconLabelButton(String text, Picto icon) {
		super(text, icon);
		setEnabled(true);
	}
	

	protected void notifyListener() {
		if (selectionListener != null) {
			selectionListener.performAction(0, this);
		}
	}

	@Override
	public Listener getListener() {
		return selectionListener;
	}

	@Override
	public void setListener(Listener listener) {
		this.selectionListener = listener;
	}

	@Override
	public void select() {
		if (!isEnabled()) {
			return;
		}
		notifyListener();
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
		} else if(type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				pressed = true;
				break;
			case Pointer.RELEASED:
				if (pressed) {
					pressed = false;
					select();
				}
				break;
			case Pointer.DRAGGED:
				break;
			}
		}
		return false;
	}
}
