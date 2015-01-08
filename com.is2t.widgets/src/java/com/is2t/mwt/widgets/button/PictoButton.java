/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button;

import com.is2t.mwt.widgets.Picto;

public class PictoButton extends Button {

	private Picto picto;
	private boolean underlined;
	private boolean overlined;

	public PictoButton(Picto picto) {
		this.picto = picto;
	}

	public Picto getPicto() {
		return picto;
	}
	
	public void setPicto(Picto picto) {
		this.picto = picto;
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
	public boolean isTransparent() {
		return true;
	}
}
