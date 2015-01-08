/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended;


public class DefaultExtendedStyle implements IExtendedStyle {

	private final IExtendedWidget widget;
	private int style;
	private int alignment;
	private int anchor;
	private int backgroundColor;
	private int foregroundColor;

	/**
	 * Creates a new extended style linked with a widget.
	 */
	public DefaultExtendedStyle(IExtendedWidget widget) {
		this.widget = widget;
		backgroundColor = DEFAULT_COLOR;
		foregroundColor = DEFAULT_COLOR;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
		widget.revalidate();
	}

	/**
	 * @return the alignment
	 */
	public int getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
		widget.repaint();
	}

	/**
	 * @return the anchor
	 */
	public int getAnchor() {
		return anchor;
	}

	/**
	 * @param anchor
	 *            the anchor to set
	 */
	public void setAnchor(int anchor) {
		this.anchor = anchor;
		widget.repaint();
	}

	/**
	 * @return the backgroundColor
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		widget.repaint();
	}

	/**
	 * @return the foregroundColor
	 */
	public int getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * @param foregroundColor
	 *            the foregroundColor to set
	 */
	public void setForegroundColor(int foregroundColor) {
		this.foregroundColor = foregroundColor;
		widget.repaint();
	}

}
