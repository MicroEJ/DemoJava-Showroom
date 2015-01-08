/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.button;

import ej.microui.io.Image;

public class ImageButton extends Button {

	private final Image normalImage;
	private final Image pressedImage;

	/**
	 * Creates an ImageButton with an image for each state: normal and pressed.
	 * We assume the two images have the same size.
	 * 
	 * @param normalImage
	 * @param pressedImage
	 */
	public ImageButton(Image normalImage, Image pressedImage) {
		super();
		this.normalImage = normalImage;
		this.pressedImage = pressedImage;
	}

	public Image getNormalImage() {
		return normalImage;
	}

	public Image getPressedImage() {
		return pressedImage;
	}
	
	@Override
	public boolean isTransparent() {
		return true;
	}
}
