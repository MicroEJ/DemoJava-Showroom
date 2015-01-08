/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.mwt.Widget;

public class Filler extends Widget {

	private int width;
	private int height;
	private boolean underlined;
	private boolean overlined;

	public Filler(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public int getPreferredWidth() {
		return width;
	}

	@Override
	public int getPreferredHeight() {
		return height;
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

	@Override
	public void validate(int widthHint, int heightHint) {
		// nothing to do
	}

}
