/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.util.Utilities;
import com.is2t.mwt.widgets.IScale;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.Controller;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.Scale;

import ej.bon.TimerTask;
import ej.bon.WeakHashtable;
import ej.bon.XMath;
import ej.microui.Command;
import ej.microui.Event;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Pointer;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

/**
 * A {@link Renderer} that renders a {@link Scale} as a horizontal slider.
 */
public class ScaleRenderer extends DefaultWidgetRenderer implements Controller {

	public static final float HANDLER_RATIO = 1.2f;

	private WeakHashtable cache; // IScale -> ScaleInfos

	public ScaleRenderer(Drawer drawer) {
		super(drawer);
		cache = new WeakHashtable();
	}

	public Class getManagedType() {
		return Scale.class;
	}

	public int getPreferredContentWidth(Widget widget) {
		IScale scale = (IScale) widget;
		int range = scale.getMaxValue() - scale.getMinValue(); // 1px by value needed
		return range + (int) (getPreferredContentSize() * HANDLER_RATIO);
	}

	public int getPreferredContentHeight(Widget widget) {
		return getPreferredContentSize();
	}

	private int getPreferredContentSize() {
		// fit button size
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_CONTENT);
		return look.getFonts()[index].getHeight();
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IScale scale = (IScale) renderable;
		Look look = getLook();

		// get properties
		int width = scale.getWidth();
		int height = scale.getHeight();
		int padding = getPadding();

		int min = scale.getMinValue();
		int max = scale.getMaxValue();
		int value = scale.getValue();

		// compute handler properties
		int handlerHeight = height;
		int handlerWidth = getHandlerWidth(handlerHeight);
		int handlerX = getHandlerX(value, min, max, width, handlerWidth);

		// fill background
		g.setColor(look.getProperty(Look.GET_BACKGROUND_COLOR_CONTENT));
		g.fillRect(0, 0, width, height);

		drawScale(g, scale, width, height, padding, handlerX, handlerWidth, handlerHeight, getBarLookSettings(look,
				scale, true), getBarLookSettings(look, scale, false), getHandlerLookSettings(look, scale,
				isSelected(scale)));

	}

	protected void drawScale(GraphicsContext g, IScale scale, int width, int height, int padding, int handlerX,
			int handlerWidth, int handlerHeight, LookSettings selectedBarLookSettings, LookSettings barLookSettings,
			LookSettings handlerLookSettings) {
		int min = scale.getMinValue();
		int max = scale.getMaxValue();
		int value = scale.getValue();

		int halfHandlerWidth = handlerWidth / 2;

		// left part
		if (value != min) {
			g.translate(halfHandlerWidth, padding);
			drawer.drawBorderedBox(g, handlerX, height - 2 * padding, selectedBarLookSettings);
			g.translate(-halfHandlerWidth, -padding);
		}

		// right part
		if (value != max) {
			g.translate(handlerX + halfHandlerWidth, padding);
			drawer.drawBorderedBox(g, width - handlerX - handlerWidth, height - 2 * padding, barLookSettings);
			g.translate(-handlerX - halfHandlerWidth, -padding);
		}

		// handler
		g.translate(handlerX, 0);
		drawer.drawBorderedBox(g, handlerWidth, handlerHeight, handlerLookSettings);
		g.translate(-handlerX, 0);

		if (scale.hasFocus()) {
			g.translate(handlerX, 0);
			drawer.drawOutline(g, handlerWidth, handlerHeight, padding, handlerLookSettings);
			g.translate(-handlerX, 0);
		}

		// handler decoration <=====[|||]===>
		g.setColor(handlerLookSettings.borderColor);
		int decorationHeight = handlerHeight / 2 - 1;
		int decorationY = (height - decorationHeight) / 2;
		int handlerCenterX = handlerX + handlerWidth / 2;
		int decorationSpacingX = handlerWidth / 4;
		g.drawVerticalLine(handlerCenterX, decorationY, decorationHeight);
		g.drawVerticalLine(handlerCenterX - decorationSpacingX, decorationY, decorationHeight);
		g.drawVerticalLine(handlerCenterX + decorationSpacingX, decorationY, decorationHeight);
	}

	protected int getHandlerWidth(int handlerHeight) {
		return (int) (handlerHeight * HANDLER_RATIO);
	}

	protected int getHandlerX(int value, int min, int max, int width, int handlerWidth) {
		return (value - min) * (width - handlerWidth) / (max - min);
	}

	protected LookSettings getBarLookSettings(Look look, IWidget widget, boolean isSelection) {
		return new ContentLook(look, widget.isEnabled(), isSelection);
	}

	protected LookSettings getHandlerLookSettings(Look look, IWidget widget, boolean isPressed) {
		return new DefaultLook(look, widget.isEnabled(), isPressed);
	}

	private int getValue(IScale scale, int x, int y, boolean checkHandle) {
		// get properties
		int width = scale.getWidth();
		int height = scale.getHeight();

		int min = scale.getMinValue();
		int max = scale.getMaxValue();
		int value = scale.getValue();

		// compute handler properties
		int handlerHeight = height;
		int handlerWidth = getHandlerWidth(handlerHeight);
		int handlerX = getHandlerX(value, min, max, width, handlerWidth);

		if (checkHandle && x > handlerX && x < handlerX + handlerWidth) {
			// on the handler, return current value
			return value;
		}

		// return bounded value
		int result = ((x - handlerWidth / 2) * (max - min) / (width - handlerWidth)) + min;
		return XMath.limit(result, min, max);
	}

	public int handleEvent(IWidget widget, int event) {
		IScale scale = (IScale) widget;
		int type = Event.getType(event);
		if (type == Event.COMMAND) {
			int cmd = Event.getData(event);
			switch (cmd) {
			case Command.LEFT:
			case Command.NEXT:
				scale.increment(false);
				return HANDLED;
			case Command.RIGHT:
			case Command.PREVIOUS:
				scale.increment(true);
				return HANDLED;
			}
		} else if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int x = scale.getRelativeX(pointer.getX());
			int y = scale.getRelativeY(pointer.getY());
			int currentValue = scale.getValue();
			ScaleInfos infos = getInfos(scale);
			switch (action) {
			case Pointer.PRESSED:
				if (getValue(scale, x, y, true) == currentValue) {
					infos.setSelected(scale, true);
				} else {
					infos.startGoPage(scale, getValue(scale, x, y, false) > currentValue);
				}
				break;
			case Pointer.RELEASED:
				if (infos.isSelected(scale)) {
					scale.setValue(getValue(scale, x, y, false));
				}
				infos.stopAll(scale);
				break;
			case Pointer.DRAGGED:
				if (infos.isSelected(scale)) {
					scale.setValue(getValue(scale, x, y, false));
				}
				break;
			}
			return HANDLED;
		}
		// do not manage this event
		return NOT_HANDLED;
	}

	protected boolean isSelected(IScale scale) {
		return getInfos(scale).isSelected(scale);
	}

	protected ScaleInfos getInfos(IScale scale) {
		ScaleInfos result = (ScaleInfos) cache.get(scale);
		if (result == null) {
			result = new ScaleInfos();
			cache.put(scale, result);
		}
		return result;
	}

}

class ScaleInfos {
	// do not store IScale instance
	private boolean selected;
	private ScalePageTask pageTask;

	void setSelected(IScale scale, boolean selected) {
		this.selected = selected;
		scale.repaint();
	}

	boolean isSelected(IScale scale) {
		return selected;
	}

	synchronized void startGoPage(IScale scale, boolean forward) {
		stopAll(scale);
		pageTask = new ScalePageTask(scale, forward);
	}

	synchronized void stopAll(IScale scale) {
		setSelected(scale, false);
		if (pageTask != null) {
			pageTask.cancel();
			pageTask = null;
		}
	}

}

class ScalePageTask extends TimerTask {
	private final IScale scale;
	private final boolean forward;

	ScalePageTask(IScale scale, boolean forward) {
		this.scale = scale;
		this.forward = forward;
		Utilities.getTimer().schedule(this, 0, 200);
	}

	public void run() {
		scale.pageIncrement(forward);
		// if (scale.getValue() == (forward ? scale.getMaxValue() : scale.getMinValue())) {
		// do not stop it
		// }
	}

}
