/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.plain;

import com.is2t.mwt.widgets.IClock;
import com.is2t.mwt.widgets.renderers.DefaultWidgetRenderer;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class DialClockRenderer extends DefaultWidgetRenderer {

	private static final int MINUTES_IN_HOUR = 60;
	private static final int HOURS = 12;

	private static final int CLOCK_SIZE = 75;
	private static final int HAND_STEPS = 6;
	private static final int CENTER_SIZE = 2;

	public DialClockRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IClock.class;
	}

	public int getPreferredContentWidth(Widget widget) {
		return CLOCK_SIZE;
	}

	public int getPreferredContentHeight(Widget widget) {
		return CLOCK_SIZE;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IClock clock = (IClock) renderable;
		int padding = getPadding();
		int clockSize = Math.min(clock.getWidth(), clock.getHeight()) - 2 * padding;

		g.setColor(getLook().getProperty(Look.GET_BACKGROUND_COLOR_CONTENT));
		g.fillRect(0, 0, clock.getWidth(), clock.getHeight());

		int hour = clock.getHour();
		int minute = clock.getMinute();
		int second = clock.getSecond();

		int borderColor = getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT);
		g.setColor(borderColor);
		g.drawCircle(padding, padding, clockSize);
		int center = padding + clockSize / 2;

		int radius = clockSize / 2;

		// draw tick marks
		for (int i = 0; i < 60; i+=5) {
			double angle = i * Math.PI * 2 / 60;
			int xOuter = center + (int) (radius * Math.sin(angle));
			int yOuter = center - (int) (radius * Math.cos(angle));
			int xInner = center + (int) ((radius-HAND_STEPS/2) * Math.sin(angle));
			int yInner = center - (int) ((radius - HAND_STEPS / 2) * Math.cos(angle));
			g.drawLine(xInner, yInner, xOuter, yOuter);
		}
		g.setColor(getLook().getProperty(Look.GET_FOREGROUND_COLOR_CONTENT));

		//draw seconds hand
		double secondsInMinute = (double) second / MINUTES_IN_HOUR;
		renderHand(g, center, radius, secondsInMinute);
		//decrease the radius, the minute hand is smaller than the second one
		radius -= HAND_STEPS;
		//draw minutes
		double minutesInHour = (double) (minute + secondsInMinute) / MINUTES_IN_HOUR;
		renderHand(g, center, radius, minutesInHour);
		//decrease the radius, the hour hand is smaller than the minute one
		radius -= HAND_STEPS;
		//draw hours
		double hours = (double) (hour + minutesInHour) / HOURS;
		renderHand(g, center, radius, hours);

		//draw center
		int centerPosition = (clockSize-CENTER_SIZE)/2;
		g.fillCircle(padding+centerPosition, padding+centerPosition, CENTER_SIZE);
	}

	private void renderHand(GraphicsContext g, int center, int radius, double hourFraction) {
		double sAngle = hourFraction * Math.PI * 2;
		int xHand = center + (int) (radius * Math.sin(sAngle));
		int yHand = center - (int) (radius * Math.cos(sAngle));
		g.drawLine(center, center, xHand, yHand);
	}
}
