/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.keyboard;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.contracts.Controller;
import com.is2t.mwt.widgets.renderers.DefaultWidgetRenderer;
import com.is2t.mwt.widgets.renderers.util.DefaultLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.bon.WeakHashtable;
import ej.microui.Event;
import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Keyboard;
import ej.microui.io.Pointer;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;

public class VKeyboardRenderer extends DefaultWidgetRenderer implements Controller {

	// use a shift on an integer value instead of a floating value
	private static final int ACCURACY_FACTOR = 5;

	private WeakHashtable cache; // IButton -> ButtonInfos

	public VKeyboardRenderer(Drawer drawer) {
		super(drawer);
		cache = new WeakHashtable();
	}

	public Class getManagedType() {
		return IKeyboard.class;
	}

	public int getPreferredContentHeight(Widget widget) {
		IKeyboard keyboard = (IKeyboard) widget;
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		int padding = getPadding();

		int keyHeight = font.getHeight() + 2 * padding;
		int linesCount = keyboard.getKeyboardMapping().getMapping().length;
		return linesCount * keyHeight + (linesCount - 1) * padding; // number of lines per the height of a key
																	// + padding between keys
	}

	public int getPreferredContentWidth(Widget widget) {
		IKeyboard keyboard = (IKeyboard) widget;
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		int padding = getPadding();
		IKeyboardMapping keyboardMapping = keyboard.getKeyboardMapping();
		char[][] mapping = keyboardMapping.getMapping();

		// compute each line width to fit the larger
		int linesCount = mapping.length;
		int maxLineWidth = 0;
		for (int i = linesCount; --i >= 0;) {
			char[] line = mapping[i];
			int lineKeysCount = line.length;
			// FIXME concat all the chars of the line to get the width in one call
			int lineWidth = 0;
			for (int j = lineKeysCount; --j >= 0;) {
				lineWidth += font.charWidth(line[j]);
			}
			lineWidth += (lineKeysCount * 3 - 1) * padding;
			maxLineWidth = Math.max(maxLineWidth, lineWidth);
		}

		return maxLineWidth;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IKeyboard keyboard = (IKeyboard) renderable;
		Look look = getLook();
		DisplayFont font = look.getFonts()[look.getProperty(Look.GET_FONT_INDEX_DEFAULT)];
		IKeyboardMapping keyboardMapping = keyboard.getKeyboardMapping();
		char[][] mapping = keyboardMapping.getMapping();

		int width = keyboard.getWidth();
		int height = keyboard.getHeight();
		int padding = getPadding();

		LookSettings lookSettings = new DefaultLook(look, true, false);
		g.setColor(lookSettings.backgroundColor);
		g.fillRect(0, 0, width, height);
		g.setColor(lookSettings.borderColor);
		g.drawRect(0, 0, width - 1, height - 1);

		VKeyboardInfos infos = getInfos(keyboard);

		// calculate the maximum number of keys on a line
		int linesCount = mapping.length;
		int maxKeysOnLine = 0;
		for (int i = linesCount; --i >= 0;) {
			maxKeysOnLine = Math.max(maxKeysOnLine, mapping[i].length);
		}

		g.setFont(font);
		int currentY = padding << ACCURACY_FACTOR;
		int lineHeight = ((height - padding) << ACCURACY_FACTOR) / linesCount;
		int keyHeight = (lineHeight >> ACCURACY_FACTOR) - padding;
		for (int i = -1; ++i < linesCount;) {
			char[] line = mapping[i];
			int lineKeysCount = line.length;
			int currentX = padding << ACCURACY_FACTOR;
			int columnWidth = ((width - padding) << ACCURACY_FACTOR) / lineKeysCount;
			int keyWidth = columnWidth - (padding << ACCURACY_FACTOR);
			for (int j = -1; ++j < lineKeysCount;) {
				g.translate(currentX >> ACCURACY_FACTOR, currentY >> ACCURACY_FACTOR);
				char key = line[j];
				// check sibling keys
				int currentKeyWidth = keyWidth;
				boolean isKeyPressed = infos.isPressed(keyboard, i, j);
				while (j + 1 < lineKeysCount && line[j + 1] == key) {
					j++;
					isKeyPressed |= infos.isPressed(keyboard, i, j);
					currentKeyWidth += columnWidth;
				}
				boolean isSpecialKey = keyboardMapping.isSpecialKey(key) || keyboard.isModifierKey(key);
				drawKey(g, key, currentKeyWidth >> ACCURACY_FACTOR, keyHeight, lookSettings, isKeyPressed, isSpecialKey);
				g.translate(-currentX >> ACCURACY_FACTOR, -currentY >> ACCURACY_FACTOR);
				currentX += currentKeyWidth + (padding << ACCURACY_FACTOR);
			}
			currentY += lineHeight;
		}
	}

	private void drawKey(GraphicsContext g, char key, int width, int height, LookSettings lookSettings,
			boolean isPressed, boolean isSpecialKey) {
		if (isPressed) {
			// FIXME
			lookSettings = new DefaultLook(getLook(), true, isPressed);
		}
		if(isSpecialKey) {
			LookSettings specialSettings = new LookSettings() {
			};
			specialSettings.backgroundColor = Drawings.darkenColor(lookSettings.backgroundColor, 3);
			specialSettings.borderColor = lookSettings.borderColor;
			specialSettings.foregroundColor = lookSettings.foregroundColor;
			lookSettings = specialSettings;
		}
		drawer.drawBorderedBox(g, width, height, lookSettings);
		g.setColor(lookSettings.foregroundColor);
		g.drawChar(key, width / 2, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
	}

	public int handleEvent(IWidget widget, int event) {
		int type = Event.getType(event);
		if (type == Event.POINTER) {
			IKeyboard keyboard = (IKeyboard) widget;
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			VKeyboardInfos infos = getInfos(keyboard);
			switch (action) {
			case Pointer.EXITED:
				infos.setHovered(keyboard, pointer, false);
				break;
			case Pointer.PRESSED:
				infos.setPressed(keyboard, pointer, true);
				// fall down
			case Pointer.ENTERED:
				infos.setHovered(keyboard, pointer, true);
				// fall down
			case Pointer.DRAGGED:
			case Pointer.DOUBLE_CLICKED:
				// update pressed key
				int x = keyboard.getRelativeX(pointer.getX());
				int y = keyboard.getRelativeY(pointer.getY());
				int width = keyboard.getWidth();
				int height = keyboard.getHeight();

				if (y >= 0 && y < height && x >= 0 && x < width) {
					// over keyboard
					IKeyboardMapping keyboardMapping = keyboard.getKeyboardMapping();
					char[][] mapping = keyboardMapping.getMapping();

					int line = y * mapping.length / height;
					int column = x * mapping[line].length / width;
					char key = mapping[line][column];
					if (keyboard.isModifierKey(key)) {
						boolean unlocked = unlockFullKey(keyboard, line, column, mapping, infos);
						if (action == Pointer.DOUBLE_CLICKED) {
							infos.lockKey(keyboard, line, column);
						} else if (action == Pointer.PRESSED) {
							if (!unlocked) {
								infos.lockOnceKey(keyboard, line, column);
							}
						}
						infos.setKeyPressed(keyboard, pointer, line, column);
						// update mapping
						char[] pressedKeys = getPressedKeys(keyboard, infos, mapping);
						keyboardMapping.updateModifiers(pressedKeys);
					} else {
						infos.setKeyPressed(keyboard, pointer, line, column);
					}
				} else if (action != Pointer.DOUBLE_CLICKED) {
					// out of keyboard
					infos.setKeyReleased(keyboard, pointer);
				}
				break;
			case Pointer.RELEASED:
				boolean modifier = false;
				IKeyboardMapping keyboardMapping = null;
				char[][] mapping = null;
				if (infos.isHovered(keyboard, pointer)) {
					keyboardMapping = keyboard.getKeyboardMapping();
					mapping = keyboardMapping.getMapping();
					int line = infos.getLine(keyboard, pointer);
					int column = infos.getColumn(keyboard, pointer);
					char key = mapping[line][column];
					if (keyboardMapping.isSpecialKey(key)) {
						keyboardMapping.handleSpecialKey(key);
						infos.unlockAllKeys(keyboard);
					} else if (keyboard.isModifierKey(key)) {
						modifier = true;
					} else {
						// get all pressed keys
						char[] pressedKeys = getPressedKeys(keyboard, infos, mapping);
						key = keyboardMapping.applyModifiers(key, pressedKeys);
						keyboard.getEventGenerator().send(Keyboard.TEXT_INPUT, key);
						infos.unlockOnceKeys(keyboard);
						// update mapping
						pressedKeys = getPressedKeys(keyboard, infos, mapping);
						keyboardMapping.updateModifiers(pressedKeys);
					}
				} // else fall down
				infos.setPressed(keyboard, pointer, false);
				infos.setKeyReleased(keyboard, pointer);
				if (modifier) {
					char[] pressedKeys = getPressedKeys(keyboard, infos, mapping);
					keyboardMapping.updateModifiers(pressedKeys);
				}
			}
			return HANDLED;
		}
		return NOT_HANDLED;
	}

	private char[] getPressedKeys(IKeyboard keyboard, VKeyboardInfos infos, char[][] mapping) {
		Key[] pressedKeysIndices = infos.getAllPressedKeys(keyboard);
		int pressedKeysLength = pressedKeysIndices.length;
		char[] pressedKeys = new char[pressedKeysLength];
		for (int i = pressedKeysLength; --i >= 0;) {
			Key pressedKeyIndices = pressedKeysIndices[i];
			pressedKeys[i] = mapping[pressedKeyIndices.line][pressedKeyIndices.column];
		}
		return pressedKeys;
	}

	private boolean unlockFullKey(IKeyboard keyboard, int line, int column, char[][] mapping, VKeyboardInfos infos) {
		boolean removed = false;
		Stack/* <Key> */stack = new Stack();
		Vector/* <Key> */visited = new Vector();
		stack.push(new Key(line, column));
		do {
			Key key = (Key) stack.pop();
			int keyLine = key.line;
			int keyColumn = key.column;
			addKeyToVisit(stack, visited, keyLine, keyColumn - 1, mapping); // left
			addKeyToVisit(stack, visited, keyLine, keyColumn + 1, mapping); // right
			visited.addElement(key);
			removed |= infos.unlock(keyboard, keyLine, keyColumn);
		} while (!stack.isEmpty());
		return removed;
	}

	private void addKeyToVisit(Stack stack, Vector visited, int line, int column, char[][] mapping) {
		Key key = new Key(line, column);
		if (!visited.contains(key) && !stack.contains(key)
				&& line >= 0 && column >= 0 && line < mapping.length && column < mapping[line].length) {
			stack.addElement(key);
		}
	}

	protected VKeyboardInfos getInfos(IKeyboard keyboard) {
		VKeyboardInfos result = (VKeyboardInfos) cache.get(keyboard);
		if (result == null) {
			result = new VKeyboardInfos();
			cache.put(keyboard, result);
		}
		return result;
	}

}

class Key {
	int line;
	int column;

	public Key() {
		// line = column = 0 VM_DONE
	}
	public Key(int line, int column) {
		super();
		this.line = line;
		this.column = column;
	}

	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			// same object
			return true;
		}
		try {
			Key key = (Key) obj;
			return key.line == line && key.column == column;
		} catch (ClassCastException e) {
			return false;
		}
	}
	public int hashCode() {
		return (line << 16) | (column);
	}
}

class PointerInfos {
	static final int NOT_PRESS = -1;
	Key key;
	boolean isHovered;
	boolean isPressed;

	public PointerInfos() {
		this.key = new Key();
		this.key.line = NOT_PRESS; // FIXME useless ?
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
		if (isPressed) {
			setHovered(true);
		}
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}

}

class VKeyboardInfos {
	private Hashtable/* <Pointer, PointerInfos> */pointersInfos;
	private Vector/* <Key> */lockOnceKeys;
	private Vector/* <Key> */lockKeys;

	VKeyboardInfos() {
		pointersInfos = new Hashtable();
		lockKeys = new Vector();
		lockOnceKeys = new Vector();
	}

	PointerInfos getPointerInfos(Pointer pointer) {
		PointerInfos pointerInfos = (PointerInfos) pointersInfos.get(pointer);
		if (pointerInfos == null) {
			pointerInfos = new PointerInfos();
			pointersInfos.put(pointer, pointerInfos);
		}
		return pointerInfos;
	}

	/**
	 * Returns all pressed keys including the locked ones.
	 */
	Key[] getAllPressedKeys(IKeyboard keyboard) {
		Vector aggregator = new Vector();
		addAll(keyboard, aggregator, lockOnceKeys);
		addAll(keyboard, aggregator, lockKeys);
		Enumeration elements = pointersInfos.elements();
		while (elements.hasMoreElements()) {
			PointerInfos pointerInfos = (PointerInfos) elements.nextElement();
			aggregator.addElement(pointerInfos.key);
		}
		int totalLength = aggregator.size();
		Key[] result = new Key[totalLength];
		aggregator.copyInto(result);
		return result;
	}

	private void addAll(IKeyboard keyboard, Vector aggregator, Vector aggregated) {
		int length = aggregated.size();
		for (int i = length; --i >= 0;) {
			aggregator.addElement(aggregated.elementAt(i));
		}
	}

	void lockKey(IKeyboard keyboard, int line, int column) {
		Key key = new Key(line, column);
		lockKeys.addElement(key);
	}

	void lockOnceKey(IKeyboard keyboard, int line, int column) {
		Key key = new Key(line, column);
		lockOnceKeys.addElement(key);
	}

	boolean isLocked(IKeyboard keyboard, int line, int column) {
		Key key = new Key(line, column);
		return lockKeys.contains(key);
	}

	boolean isOnceLocked(IKeyboard keyboard, int line, int column) {
		Key key = new Key(line, column);
		return lockOnceKeys.contains(key);
	}

	boolean unlock(IKeyboard keyboard, int line, int column) {
		Key key = new Key(line, column);
		return lockKeys.removeElement(key) | lockOnceKeys.removeElement(key);
	}

	void unlockOnceKeys(IKeyboard keyboard) {
		lockOnceKeys.removeAllElements();
	}

	void unlockAllKeys(IKeyboard keyboard) {
		lockOnceKeys.removeAllElements();
		lockKeys.removeAllElements();
	}

	void setPressed(IKeyboard keyboard, Pointer pointer, boolean isPressed) {
		getPointerInfos(pointer).setPressed(isPressed);
		keyboard.repaint();
	}

	void setHovered(IKeyboard keyboard, Pointer pointer, boolean isHovered) {
		getPointerInfos(pointer).setHovered(isHovered);
		keyboard.repaint();
	}

	boolean isHovered(IKeyboard keyboard, Pointer pointer) {
		return getPointerInfos(pointer).isHovered;
	}

	void setKeyPressed(IKeyboard keyboard, Pointer pointer, int line, int column) {
		PointerInfos pointerInfos = getPointerInfos(pointer);
		pointerInfos.key.line = line;
		pointerInfos.key.column = column;
		keyboard.repaint();
	}

	void setKeyReleased(IKeyboard keyboard, Pointer pointer) {
		pointersInfos.remove(pointer);
		keyboard.repaint();
	}

	int getLine(IKeyboard keyboard, Pointer pointer) {
		return getPointerInfos(pointer).key.line;
	}

	int getColumn(IKeyboard keyboard, Pointer pointer) {
		return getPointerInfos(pointer).key.column;
	}

	boolean isPressed(IKeyboard keyboard, int line, int column) {
		if (isLocked(keyboard, line, column) || isOnceLocked(keyboard, line, column)) {
			return true;
		}
		Enumeration elements = pointersInfos.elements();
		while (elements.hasMoreElements()) {
			PointerInfos pointerInfos = (PointerInfos) elements.nextElement();
			if (pointerInfos.isPressed && pointerInfos.key.line == line && pointerInfos.key.column == column) {
				return true;
			}
		}
		return false;
	}

}
