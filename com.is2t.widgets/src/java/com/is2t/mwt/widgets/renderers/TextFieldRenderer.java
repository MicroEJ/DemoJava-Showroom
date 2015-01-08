/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IText;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.Controller;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.bon.XMath;
import ej.microui.Command;
import ej.microui.Event;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Pointer;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;

public class TextFieldRenderer extends LabelRenderer implements Controller {

	public TextFieldRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IText.class;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IText text = (IText) renderable;
		Look look = getLook();

		// get sizes
		int width = renderable.getWidth();
		int height = renderable.getHeight();
		int padding = getPadding();
		int halfPadding = padding / 2;

		// get text properties
		int caret = text.getCaret();
		int selectionStart = text.getSelectionStart();
		int selectionEnd = text.getSelectionEnd();
		boolean isEnabled = text.isEnabled();
		int selectionLength = selectionEnd - selectionStart;
		char[] messageArray = text.getText().toCharArray();
		int messageLength = messageArray.length;

		// get font & colors
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		// get rendering properties
		LookSettings coloring = new ContentLook(look, isEnabled);

		// draw back and border
		drawer.drawBorderedBox(g, width, height, coloring);

		g.translate(padding, padding);
		g.setFont(font);

		// draw first part of the text (before selection)
		g.setColor(coloring.foregroundColor);
		int x = 0;
		g.drawChars(messageArray, 0, selectionStart, x, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
		int caretPosition = font.charsWidth(messageArray, 0, selectionStart);
		x += caretPosition;
		if (selectionLength != 0) {
			// draw selection part
			int selectionWidth = font.charsWidth(messageArray, selectionStart, selectionLength);

			int selectionBackgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_SELECTION);
			g.setColor(selectionBackgroundColor);
			g.fillRect(x, -halfPadding, selectionWidth, font.getHeight() + padding);

			int selectionForegroundColor = look.getProperty(Look.GET_FOREGROUND_COLOR_SELECTION);
			g.setColor(selectionForegroundColor);
			g.drawChars(messageArray, selectionStart, selectionLength, x, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
			x += selectionWidth;
			if (caret == selectionEnd) {
				caretPosition += selectionWidth;
			}
		}
		// draw last part of the text (after selection)
		g.setColor(coloring.foregroundColor);
		g.drawChars(messageArray, selectionStart + selectionLength, messageLength - selectionStart - selectionLength,
				x, 0, GraphicsContext.LEFT
				| GraphicsContext.TOP);
		// draw caret
		g.drawVerticalLine(caretPosition - 1, -halfPadding, font.getHeight() + padding - 1);
		g.translate(-padding, -padding);

		if (isEnabled && text.hasFocus()) { // figure out the focus state with an outline border
			drawer.drawOutline(g, width, height, padding, coloring);
		}
	}

	private int getCaret(IText textField, int x, int y) {
		Look look = getLook();
		int index = look.getProperty(Look.GET_FONT_INDEX_DEFAULT);
		DisplayFont font = look.getFonts()[index];

		x -= getPadding();

		char[] text = textField.getText().toCharArray();
		int textLength = text.length;

		if (textLength == 0) {
			// empty text
			return 0;
		}

		// search the character under the given position
		// use dichotomy
		int min = 0;
		int max = textLength;
		while (max - min > 1) {
			int half = (min + max) / 2;
			// compute size of the first part of the text
			int textWidth = font.charsWidth(text, min, half - min);
			// update bounds
			if (textWidth > x) {
				max = half;
			} else {
				min = half;
				// remove excluded zone width
				x -= textWidth;
			}
		}
		// select the right side of the selected character
		char c = text[min];
		int charWidth = font.charWidth(c);
		if (x > charWidth / 2) {
			min++;
		}
		return XMath.limit(min, 0, textLength);
	}

	protected DisplayFont getFont(IWidget widget) {
		Look look = getLook();
		int index = look.getProperty(widget.isEnabled() ? Look.GET_FONT_INDEX_CONTENT : Look.GET_FONT_INDEX_DISABLED);
		return look.getFonts()[index];
	}

	public int handleEvent(IWidget widget, int event) {
		IText text = (IText) widget;
		int type = Event.getType(event);
		if (type == Event.COMMAND) {
			int cmd = Event.getData(event);
			int caret = text.getCaret();
			if (cmd == Command.LEFT) {
				if (caret > 0) {
					text.setCaret(caret - 1);
					return HANDLED;
				}
			} else if (cmd == Command.RIGHT) {
				if (caret < text.getText().length()) {
					text.setCaret(caret + 1);
					return HANDLED;
				}
			}
		} else if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED: {
				int newCaret = getCaret(text, event);
				text.setCaret(newCaret); // reset selection and caret to current position
				break;
			}
			case Pointer.DRAGGED: {
				int newCaret = getCaret(text, event);
				// retrieve selection beginning
				int caret = text.getCaret();
				int start = text.getSelectionStart();
				int end = text.getSelectionEnd();
				int caretStart;
				if (start == caret) {
					caretStart = end;
				} else {
					caretStart = start;
				}
				text.setSelection(caretStart, newCaret);
				break;
			}
			case Pointer.DOUBLE_CLICKED: {
				text.setSelection(0, text.getText().length());
				break;
			}
			}
			return HANDLED;
		}
		return NOT_HANDLED;
	}

	private int getCaret(IText text, int event) {
		Pointer pointer = (Pointer) Event.getGenerator(event);
		int x = text.getRelativeX(pointer.getX());
		int y = text.getRelativeY(pointer.getY());
		return getCaret(text, x, y);
	}
}
