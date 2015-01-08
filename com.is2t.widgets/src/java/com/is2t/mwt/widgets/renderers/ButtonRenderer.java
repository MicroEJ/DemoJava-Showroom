/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.util.Utilities;
import com.is2t.mwt.widgets.IButton;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.ActivableController;
import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.Button;

import ej.bon.TimerTask;
import ej.bon.WeakHashtable;
import ej.microui.Event;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Pointer;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;

public class ButtonRenderer extends LabelRenderer implements ActivableController {

	private WeakHashtable cache; // IButton -> ButtonInfos

	public ButtonRenderer(Drawer drawer) {
		super(drawer);
		cache = new WeakHashtable();
	}

	public Class getManagedType() {
		return IButton.class;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IButton button = (IButton) renderable;
		Look look = getLook();
		int padding = getPadding();
		int width = button.getWidth();
		int height = button.getHeight();

		// get rendering properties
		LookSettings coloring = getLookSettings(look, button);

		drawer.drawBorderedBox(g, width, height, coloring);

		// draws an outline dotted border to figure out the focus state.
		if (button.hasFocus()) {
			drawer.drawOutline(g, width, height, getPadding(), coloring);
		}
		drawer.drawText(g, button.getText(), width, height, padding, coloring, true);
	}

	protected LookSettings getLookSettings(Look look, IWidget widget) {
		Button button = (Button) widget;
		return new DefaultLook(look, button.isEnabled(), isPressed(button));
	}

	protected boolean isPressed(IButton button) {
		return getInfos(button).isPressed(button);
	}

	public void activate(final IWidget widget) {
		final IButton button = (IButton) widget;
		final ButtonInfos infos = getInfos(button);
		infos.setPressed(button, true);
		Utilities.getTimer().schedule(new TimerTask() {
			public void run() {
				// TODO synchronize
				infos.setPressed(button, false);
			}
		}, 100);
	}

	public int handleEvent(IWidget widget, int event) {
		int type = Event.getType(event);
		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			IButton button = (IButton) widget;
			ButtonInfos infos = getInfos(button);
			switch (action) {
			case Pointer.ENTERED:
				infos.setHovered(button, true);
				break;
			case Pointer.EXITED:
				infos.setHovered(button, false);
				break;
			case Pointer.PRESSED:
				infos.setPressed(button, true);
				break;
			case Pointer.RELEASED:
				infos.setPressed(button, false);
				if (infos.isHovered(button)) {
					return ACTIVATED;
				} // else fall down
			}
			return HANDLED;
		}
		return NOT_HANDLED;
	}

	protected ButtonInfos getInfos(IButton button) {
		ButtonInfos result = (ButtonInfos) cache.get(button);
		if (result == null) {
			result = new ButtonInfos();
			cache.put(button, result);
		}
		return result;
	}

	protected class ButtonInfos {
		// do not store IScale instance
		// FIXME use bits-map
		private boolean pressed;
		private boolean hovered;

		ButtonInfos() {
		}

		void setPressed(IButton button, boolean pressed) {
			this.pressed = pressed;
			if (pressed) {
				setHovered(button, true);
			}
			button.repaint();
		}

		public boolean isPressed(IButton button) {
			return pressed;
		}

		void setHovered(IButton button, boolean hovered) {
			this.hovered = hovered;
		}

		public boolean isHovered(IButton button) {
			return hovered;
		}

	}
}


