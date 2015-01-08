/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IProgress;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.Progress;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class ProgressBarRenderer extends DefaultWidgetRenderer {

	public ProgressBarRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IProgress.class;
	}

	public int getPreferredContentWidth(Widget widget) {
		Progress progressBar = (Progress) widget;
		String text = computeLabel(progressBar, 100);
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_CONTENT);
		DisplayFont font = look.getFonts()[index];
		return font.stringWidth(text);
	}

	public int getPreferredContentHeight(Widget widget) {
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_CONTENT);
		DisplayFont font = look.getFonts()[index];
		return font.getHeight();
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IProgress bar = (IProgress) renderable;

		Look look = getLook();
		int width = bar.getWidth();
		int height = bar.getHeight();

		int selection = bar.getValue();
		int max = bar.getMaxValue();
		int selectionX = selection * width / max;

		LookSettings settings = getLookSettings(look, bar, false);
		drawBar(g, bar, width, height, selectionX, getLookSettings(look, bar, true), settings);

		String text = computeLabel(bar, selection * 100 / max);
		drawer.drawText(g, text, width, height, getPadding(), settings, true);
	}

	protected void drawBar(GraphicsContext g, IProgress bar, int width, int height, int selectionX,
			LookSettings selectionLookSettings, LookSettings lookSettings) {
		g.setClip(selectionX, 0, width - selectionX, height);
		drawer.drawBorderedBox(g, width, height, lookSettings);

		g.setClip(0, 0, selectionX, height);
		drawer.drawBorderedBox(g, width, height, selectionLookSettings);

		// reset clip
		g.setClip(0, 0, width, height);
	}

	protected LookSettings getLookSettings(Look look, IWidget widget, boolean isSelection) {
		return new ContentLook(look, true, isSelection);
	}

	protected String computeLabel(IProgress bar, int percent) {
		return percent + "%";
	}

}
