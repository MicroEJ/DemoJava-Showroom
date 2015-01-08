/*
 * Java
 * Copyright 2009-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;

/**
 * <p>
 * Provides some drawing facilities.
 * </p>
 */
public class Drawings {

	// use a shift on an integer value instead of a floating value
	public static final int ACCURACY_FACTOR = 5;

	private static final int ALPHA_SHIFT = 24;
	private static final int RED_SHIFT = 16;
	private static final int GREEN_SHIFT = 8;
	private static final int BLUE_SHIFT = 0;

	private static final int ALPHA_MASK = 0xff << ALPHA_SHIFT;
	private static final int RED_MASK = 0xff << RED_SHIFT;
	private static final int GREEN_MASK = 0xff << GREEN_SHIFT;
	private static final int BLUE_MASK = 0xff << BLUE_SHIFT;

	private static final int LIGHT_FACTOR = 0x10;
	
	private static final char SPACE = ' ';
	private static final char NEW_LINE = '\n';

	public static int darkenColor(int color, int factor) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		factor = Math.abs(factor) * LIGHT_FACTOR;

		red = Math.max(0x0, red - factor);
		green = Math.max(0x0, green - factor);
		blue = Math.max(0x0, blue - factor);

		return getColor(red, green, blue);
	}

	public static final void drawHalfRoundBorder(GraphicsContext g, int x, int y, int width, int height, int padding, int borderColor,
			boolean upIsRound) {
		int doublePadding = padding * 2;
		int wMDP = width - doublePadding;
		int hMDP = height - doublePadding;

		x += padding;
		y += padding;

		int yVertical;
		int yFullHorizontal;
		int yPartialHorizontal;
		int yPixels;
		if (upIsRound) {
			yPartialHorizontal = y;
			yFullHorizontal = y + hMDP - 1;
			yPixels = y + 1;
			yVertical = y + 2;
		} else {
			yFullHorizontal = y;
			yPartialHorizontal = y + hMDP - 1;
			yPixels = y + hMDP - 2;
			yVertical = y;
		}
		g.setColor(borderColor);
		g.drawPixel(x + 1, yPixels);
		g.drawPixel(x + wMDP - 2, yPixels);
		g.drawHorizontalLine(x, yFullHorizontal, wMDP);
		g.drawHorizontalLine(x + 2, yPartialHorizontal, wMDP - 5);
		g.drawVerticalLine(x, yVertical, hMDP - 3);
		g.drawVerticalLine(x + wMDP - 1, yVertical, hMDP - 3);
	}

	public static final void drawHalfRoundBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor,
			int borderColor, boolean upIsRound) {
		int doublePadding = padding * 2;
		int wMDP = width - doublePadding;
		int hMDP = height - doublePadding;

		x += padding;
		y += padding;

		g.setColor(backgroundColor);
		g.fillRect(x + 1, y + 1, wMDP - 2, hMDP - 2);

		int yVertical;
		int yFullHorizontal;
		int yPartialHorizontal;
		int yPixels;
		if (upIsRound) {
			yPartialHorizontal = y;
			yFullHorizontal = y + hMDP - 1;
			yPixels = y + 1;
			yVertical = y + 2;
		} else {
			yFullHorizontal = y;
			yPartialHorizontal = y + hMDP - 1;
			yPixels = y + hMDP - 2;
			yVertical = y;
		}
		g.setColor(borderColor);
		g.drawPixel(x + 1, yPixels);
		g.drawPixel(x + wMDP - 2, yPixels);
		g.drawHorizontalLine(x, yFullHorizontal, wMDP);
		g.drawHorizontalLine(x + 2, yPartialHorizontal, wMDP - 5);
		g.drawVerticalLine(x, yVertical, hMDP - 3);
		g.drawVerticalLine(x + wMDP - 1, yVertical, hMDP - 3);
	}

	public static void drawOutline(GraphicsContext g, int x, int y, int width, int height, int padding, int color) {
		g.setStrokeStyle(GraphicsContext.DOTTED);
		g.setColor(color);
		int doublePadding = padding * 2;
		g.drawRect(x + padding - 1, y + padding - 1, width - doublePadding + 1, height - doublePadding + 1);
		g.setStrokeStyle(GraphicsContext.SOLID);
	}

	public static final void drawRoundBorder(GraphicsContext g, int x, int y, int width, int height, int padding, int color) {
		int doublePadding = padding * 2;
		int wMDP = width - doublePadding;
		int hMDP = height - doublePadding;

		x += padding;
		y += padding;

		// border
		g.setColor(color);
		// top
		g.drawHorizontalLine(x + 2, y, wMDP - 5);
		// bottom
		g.drawHorizontalLine(x + 2, y + hMDP - 1, wMDP - 5);
		// left
		g.drawVerticalLine(x, y + 2, hMDP - 5);
		// right
		g.drawVerticalLine(x + wMDP - 1, y + 2, hMDP - 5);

		// top/left
		g.drawPixel(x + 1, y + 1);
		// bottom/left
		g.drawPixel(x + 1, y + hMDP - 2);
		// bottom/right
		g.drawPixel(x + wMDP - 2, y + hMDP - 2);
		// top/right
		g.drawPixel(x + wMDP - 2, y + 1);
	}

	public static final void drawRoundBox(GraphicsContext g, int x, int y, int width, int height, int padding, int backgroundColor, int borderColor) {
		fillRoundBorder(g, x, y, width, height, padding, backgroundColor);
		drawRoundBorder(g, x, y, width, height, padding, borderColor);
	}

	/**
	 * Draw a thick line
	 */
	public static void drawThickLine(GraphicsContext g, int x1, int y1, int x2, int y2, int thickness) {
		if (thickness == 1) {
			g.drawLine(x1, y1, x2, y2);
		} else {
			// FIXME use bresenham + murphy algorithm
			int gdx = x2 - x1;
			int gdy = y2 - y1;
			double length = Math.sqrt(gdx * gdx + gdy * gdy);
			double shiftX = gdy * thickness / (2 * length);
			double shiftY = -gdx * thickness / (2 * length);
			int[] xys = new int[] { (int) (x1 - shiftX), (int) (y1 - shiftY), (int) (x1 + shiftX), (int) (y1 + shiftY), (int) (x2 + shiftX),
					(int) (y2 + shiftY), (int) (x2 - shiftX), (int) (y2 - shiftY), };
			g.fillPolygon(xys);
		}
	}

	public static final void fillRoundBorder(GraphicsContext g, int x, int y, int width, int height, int padding, int color) {
		int doublePadding = padding * 2;
		int wMDP = width - doublePadding;
		int hMDP = height - doublePadding;

		x += padding;
		y += padding;

		// background
		g.setColor(color);
		g.fillRect(x + 1, y + 1, wMDP - 2, hMDP - 2);
	}

	public static final void fillVLinearGradient(GraphicsContext g, int x, int y, int width, int height, int startColor, int stopColor, boolean horizontal) {
		int currentRed = getRed(startColor) << ACCURACY_FACTOR;
		int currentGreen = getGreen(startColor) << ACCURACY_FACTOR;
		int currentBlue = getBlue(startColor) << ACCURACY_FACTOR;

		int length = horizontal ? height : width;

		int stepRed = (currentRed - (getRed(stopColor) << ACCURACY_FACTOR)) / length;
		int stepGreen = (currentGreen - (getGreen(stopColor) << ACCURACY_FACTOR)) / length;
		int stepBlue = (currentBlue - (getBlue(stopColor) << ACCURACY_FACTOR)) / length;

		do {
			g.setColor(getColor(currentRed >>> ACCURACY_FACTOR, currentGreen >>> ACCURACY_FACTOR, currentBlue >>> ACCURACY_FACTOR));
			if(horizontal) g.drawHorizontalLine(x, y++, width);
			else g.drawVerticalLine(x++, y, height);
			//step
			currentRed -= stepRed;
			currentGreen -= stepGreen;
			currentBlue -= stepBlue;
		} while(--length >= 0);
	}

	public static final void fillVRadialGradient(GraphicsContext g, int x, int y, int width, int height, int size, int startColor, int stopColor, int startAngle, int arcAngle) {
		int currentRed = getRed(startColor) << ACCURACY_FACTOR;
		int currentGreen = getGreen(startColor) << ACCURACY_FACTOR;
		int currentBlue = getBlue(startColor) << ACCURACY_FACTOR;

		int stepRed = (currentRed - (getRed(stopColor) << ACCURACY_FACTOR)) / size;
		int stepGreen = (currentGreen - (getGreen(stopColor) << ACCURACY_FACTOR)) / size;
		int stepBlue = (currentBlue - (getBlue(stopColor) << ACCURACY_FACTOR)) / size;

		do {
			g.setColor(getColor(currentRed >>> ACCURACY_FACTOR, currentGreen >>> ACCURACY_FACTOR, currentBlue >>> ACCURACY_FACTOR));
			g.fillArc(x++, y++, width, height, startAngle, arcAngle);
			//step
			currentRed -= stepRed;
			currentGreen -= stepGreen;
			currentBlue -= stepBlue;
			width -= 2;
			height -= 2;
		} while(--size >= 0);
	}

	public static int getBlue(int color) {
		return (color & BLUE_MASK) >>> BLUE_SHIFT;
	}

	public static int getColor(int red, int green, int blue) {
		return (red << RED_SHIFT) & RED_MASK | (green << GREEN_SHIFT) & GREEN_MASK | (blue << BLUE_SHIFT) & BLUE_MASK;
	}

	public static int getGreen(int color) {
		return (color & GREEN_MASK) >>> GREEN_SHIFT;
	}

	public static int getRed(int color) {
		return (color & RED_MASK) >>> RED_SHIFT;
	}

	public static int invertColor(int color) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		red = 0xff - red;
		green = 0xff - green;
		blue = 0xff - blue;

		return getColor(red, green, blue);
	}

	public static int lightenColor(int color, int factor) {
		int red = getRed(color);
		int green = getGreen(color);
		int blue = getBlue(color);

		factor = Math.abs(factor) * LIGHT_FACTOR;

		red = Math.min(0xff, red + factor);
		green = Math.min(0xff, green + factor);
		blue = Math.min(0xff, blue + factor);

		return getColor(red, green, blue);
	}

	/**
	 * Does a rotation around a point.
	 * 
	 * @param point2D Point to rotate
	 * @param origin2D Rotation origin
	 * @param angle Rotation angle
	 * @return Point after rotation.
	 */
	public static int[] rotatePoint2D(final int[] point2D, final int[] origin2D, final int angle) {
		final double rad = Math.toRadians(angle);
		final double cosAlpha = Math.cos(rad);
		final double sinAlpha = Math.sin(rad);
		final int offsetX = point2D[0] - origin2D[0];
		final int offsetY = point2D[1] - origin2D[1];
		final int nX = (int) (cosAlpha * offsetX - sinAlpha * offsetY);
		final int nY = (int) (sinAlpha * offsetX + cosAlpha * offsetY);
		final int[] res = new int[2];
		res[0] = origin2D[0] + nX;
		res[1] = origin2D[1] + nY;
		return res;
	}
	
	public static String[] splitString(String string, DisplayFont font, int width) {
		Vector<String> lines = new Vector<String>();

		char[] chars = string.toCharArray();
		int length = chars.length;
		int index = 0;
		int lastSpaceIndex = 0;
		int lastSplitIndex = 0;

		do {
			split: {
				boolean isSpace = false;
				if (index < length) {
					// in the string
					char c = chars[index];
					if (c == SPACE) {
						// find word
						isSpace = true;
					} else if (c != NEW_LINE) {
						// do not need to try to split here
						break split;
					} // else find manual break
				} else if (index != length) {
					// stop loop
					break;
				} // else end of the string

				int charsWidth = font.charsWidth(chars, lastSplitIndex, index - lastSplitIndex);
				if (charsWidth > width) {
					// the last word exceed the max width
					if (lastSpaceIndex > lastSplitIndex) {
						// split to previous word
						lines.addElement(string.substring(lastSplitIndex, lastSpaceIndex - 1));
						lastSplitIndex = lastSpaceIndex;
					}
				}
				if (isSpace) {
					// do not split for spaces, just save the last index
					lastSpaceIndex = index + 1;
				} else {
					// split here
					lines.addElement(string.substring(lastSplitIndex, index));
					lastSplitIndex = index + 1;
				}
			}
			index++;
		} while (true);

		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Splits the given string in its lines. It removes the '\n' character.
	 *
	 * @param string
	 *            the string to split.
	 * @return the list of lines contained in the given string.
	 */
	public static String[] splitStringInLines(String string) {
		if (string.isEmpty()) {
			return new String[0];
		}
		List<String> lines = new ArrayList<String>();
		int newLineIndex = 0;

		do {
			int endOfLineIndex = string.indexOf(NEW_LINE, newLineIndex);

			if (endOfLineIndex == -1) {
				// End of string reached.
				lines.add(string.substring(newLineIndex));
				break;
			} else {
				lines.add(string.substring(newLineIndex, endOfLineIndex));
				newLineIndex = endOfLineIndex + 1;
			}

		} while (true);

		return lines.toArray(new String[lines.size()]);
	}
}
