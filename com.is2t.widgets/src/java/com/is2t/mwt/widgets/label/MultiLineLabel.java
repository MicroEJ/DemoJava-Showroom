/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label;

import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.label.renderer.MultiLineLabelRenderer;
import com.is2t.mwt.widgets.tiny.Label;

/**
 * A multi-line label displays a multi-line text.
 */
public class MultiLineLabel extends Label {

	protected static final int DEFAULT_MAX_LINES_COUNT = -1;

	private final int maxLineCount;
	private int fontSize;
	private boolean underlined;
	private boolean overlined;
	private String[] lines;

	public MultiLineLabel(String text, int maxLineCount) {
		super(text);
		this.maxLineCount = maxLineCount;
		this.fontSize = LookExtension.GET_SMALL_FONT_INDEX;
		lines = Drawings.splitStringInLines(text);
	}

	public MultiLineLabel(String text) {
		this(text, DEFAULT_MAX_LINES_COUNT);
	}

	/**
	 * One of {@link LookExtension} id.
	 * 
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return underlined;
	}

	/**
	 * @param overlined
	 *            the overlined to set
	 */
	public void setOverlined(boolean overlined) {
		this.overlined = overlined;
	}

	/**
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isOverlined() {
		return overlined;
	}

	/**
	 * A negative value means all the lines.
	 * 
	 * @return the lines count
	 */
	public int getMaxLineCount() {
		return maxLineCount;
	}

	@Override
	public String toString() {
		return text;
	}

	public String[] getLines() {
		return lines;
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		lines = Drawings.splitStringInLines(text);
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		try {
			MultiLineLabelRenderer renderer = (MultiLineLabelRenderer) getRenderer();
			renderer.validate(this, widthHint, heightHint);
		} catch (ClassCastException e) {
			super.validate(widthHint, heightHint);
		}
	}
	
	@Override
	public void revalidate() {
		// DO NOT REVALIDATE
		repaint();
	}
}
