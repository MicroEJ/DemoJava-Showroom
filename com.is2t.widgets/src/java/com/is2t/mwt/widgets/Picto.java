/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;

public class Picto {

	public enum PictoSize {
		XSmall(LookExtension.GET_X_SMALL_PICTO_FONT_INDEX), Small(LookExtension.GET_SMALL_PICTO_FONT_INDEX), Medium(
				LookExtension.GET_MEDIUM_PICTO_FONT_INDEX), Big(LookExtension.GET_BIG_PICTO_FONT_INDEX);

		private final int index;

		private PictoSize(int index) {
			this.index = index;
		}

		public int getResourceIndex() {
			return index;
		}

		public DisplayFont getPictoFont(Look look) {
			int fontIndex = look.getProperty(index);
			return look.getFonts()[fontIndex];
		}

	}

	private final PictoSize size;
	private char picto;

	public Picto(char picto, PictoSize size) {
		this.picto = picto;
		this.size = size;
	}

	public void setPicto(char picto) {
		this.picto = picto;
	}

	public char getPicto() {
		return picto;
	}

	public PictoSize getSize() {
		return size;
	}

	public DisplayFont getPictoFont(Look look) {
		return size.getPictoFont(look);
	}
}
