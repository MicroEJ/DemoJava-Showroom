/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities;


import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.io.Pointer;

public class Button extends Region {
	
	private boolean pressed;
	private String text;
	private Listener listener;
	
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public boolean handleEvent(int value) {
		int type = Event.getType(value);

		if (type == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(value);
			int pointerX = pointer.getX();
			int pointerY = pointer.getY();

			if (Pointer.isPressed(value)) {
				if (contains(pointerX, pointerY)) {
					pressed = true;
					return true;
				}
			} else if (Pointer.isDragged(value)) {
				if (pressed || contains(pointerX, pointerY)) {
					return true;
				}
			} else if (Pointer.isClicked(value)) {
				if (pressed) {
					return true;
				}
			} else if (Pointer.isReleased(value)) {
				if (pressed && contains(pointerX, pointerY)) {
					if(listener != null) {
						listener.performAction();
					}
					return true;
				}

				pressed = false;
			}
		}

		return false;
	}
}
