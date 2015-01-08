/**
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import com.is2t.mwt.widgets.renderers.LabelRenderer;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;
import com.is2t.mwt.widgets.tiny.Label;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

/**
 * Renders a {@link MultiLinesLabel}.
 */
public class MultiLinesLabelRenderer extends LabelRenderer {

	/**
	 * Creates a multi-lines label renderer.
	 * 
	 * @param drawer
	 *            the drawer used for drawings.
	 */
	public MultiLinesLabelRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class<?> getManagedType() {
		return MultiLinesLabel.class;
	}

	public int getPreferredContentHeight(Widget widget) {
		// compute the size of the lines
		Label label = (Label) widget;
		int linesCount = ((MultiLinesLabel) label).getLinesCount();
		return getLookSettings(getLook(), label).font.getHeight() * linesCount;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		Label label = (Label) renderable;
		int width = label.getWidth();
		int height = label.getHeight();
		Look look = getLook();

		// get rendering properties
		LookSettings coloring = getLookSettings(look, label);
		drawer.fillBackground(g, width, height, coloring);
		String text = label.getText();
		int padding = getPadding();
		// split text on '\n'
		MultiLinesLabel multiLinesLabel = (MultiLinesLabel) label;
		int linesCount = multiLinesLabel.getLinesCount();
		int lineHeight = coloring.font.getHeight() + padding;
		// the lines are centered if there is enough space, otherwise aligned on top
		int currentY = Math.max(0, (height - (linesCount * lineHeight + padding)) / 2);
		int lastIndex = 0;
		for (int i = -1; ++i < linesCount;) {
			// get line
			int newLineIndex = text.indexOf('\n', lastIndex);
			String line;
			if (newLineIndex == -1) {
				line = text.substring(lastIndex);
			} else {
				line = text.substring(lastIndex, newLineIndex);
			}
			// draw line
			g.translate(0, currentY);
			drawLine(g, multiLinesLabel, i, line, width, lineHeight, padding, coloring);
			g.translate(0, -currentY);

			if (newLineIndex == -1) {
				// no more text
				break;
			}
			// update text and screen next positions
			lastIndex = newLineIndex + 1;
			currentY += lineHeight;
		}
	}

	/**
	 * Draws a single line.
	 * 
	 * @param g
	 *            the graphics context to be used to draw the renderable.
	 * @param label
	 *            the multi-lines label.
	 * @param lineNumber
	 *            the index of the line.
	 * @param line
	 *            the line.
	 * @param width
	 *            the width available for the line.
	 * @param height
	 *            the height available for the line.
	 * @param padding
	 *            the padding.
	 * @param coloring
	 *            the look informations.
	 */
	protected void drawLine(GraphicsContext g, MultiLinesLabel label, int lineNumber, String line, int width,
			int height, int padding, LookSettings coloring) {
		drawer.drawText(g, line, width, height, padding, coloring, false);
	}

}
