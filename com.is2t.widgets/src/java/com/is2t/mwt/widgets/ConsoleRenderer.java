/**
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

/**
 * Renders a {@link Console}.
 */
public class ConsoleRenderer extends MultiLinesLabelRenderer {

	private static final int CURSOR_WIDTH = 3;

	/**
	 * Creates a console renderer.
	 *
	 * @param drawer
	 *            the drawer used for drawings.
	 */
	public ConsoleRenderer(Drawer drawer) {
		super(drawer);
	}

	@Override
	public Class<?> getManagedType() {
		return Console.class;
	}

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return super.getPreferredContentWidth(widget) + CURSOR_WIDTH;
	}

	@Override
	protected void drawLine(GraphicsContext g, MultiLinesLabel multiLinesLabel, int lineNumber, String line, int width,
			int height, int padding, LookSettings coloring) {
		super.drawLine(g, multiLinesLabel, lineNumber, line, width, height, padding, coloring);
		Console console = (Console) multiLinesLabel;
		if (console.getCursorCurrentState() == Console.CURSOR_OFF) {
			// nothing to do
			return;
		}
		if (lineNumber != console.getCursorLine()) {
			// not this line
			return;
		}
		int charIndex = console.getCursorColumn();
		int lineLength = line.length();
		if (charIndex == lineLength) {
			DisplayFont font = coloring.font;
			int cursorX = font.stringWidth(line);
			int cursorWidth = font.charWidth(' ');
			invertColors(coloring);

			g.translate(cursorX, 0);
			g.setColor(coloring.backgroundColor);
			g.drawHorizontalLine(padding, height - 2 * padding, cursorWidth);
			g.translate(-cursorX, 0);

			invertColors(coloring);
			return;
		}

		if (charIndex > lineLength) {
			// not existing column
			return;
		}

		// compute cursor bounds
		DisplayFont font = coloring.font;
		int cursorX = font.stringWidth(line.substring(0, charIndex));
		char cursorChar = line.charAt(charIndex);
		int cursorWidth = font.charWidth(cursorChar);
		// invert colors
		invertColors(coloring);
		// draw the cursor background then the selected character with the inverted colors
		g.translate(cursorX, 0);
		g.setColor(coloring.backgroundColor);
		// g.fillRect(padding, 0, cursorWidth, height);

		g.drawHorizontalLine(padding, height - 2 * padding, cursorWidth);

		// g.drawHorizontalLine(font.stringWidth(line), height, CURSOR_WIDTH);

		drawer.drawText(g, String.valueOf(cursorChar), cursorWidth, height, padding, coloring, false);
		g.translate(-cursorX, 0);
		// reset colors
		invertColors(coloring);
	}

	protected LookSettings getLookSettings(Look look, Widget label) {
		return new DefaultLook(look, label.isEnabled(), false);
	}

	/**
	 * Inverts foreground and background of the given {@link LookSettings}.
	 *
	 * @param coloring
	 *            the look settings to change
	 */
	private void invertColors(LookSettings coloring) {
		int tmp = coloring.foregroundColor;
		coloring.foregroundColor = coloring.backgroundColor;
		coloring.backgroundColor = tmp;
	}

}
