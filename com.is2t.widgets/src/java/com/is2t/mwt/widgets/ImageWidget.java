/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.mwt.widgets;

import ej.microui.io.Image;
import ej.mwt.Widget;

public class ImageWidget extends Widget {

	private Image image;
	private boolean fit;

	public ImageWidget(Image image) {
		this(image, false);
	}

	public ImageWidget(Image image, boolean fit) {
		this.image = image;
		this.fit = fit;
	}

	/**
	 * @param image
	 *            the image to set.
	 */
	public void setImage(Image image) {
		this.image = image;
		repaint();
	}

	/**
	 * @return the image.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return <code>true</code> if the image is fitted on the given space, <code>false</code> otherwise.
	 */
	public boolean isFitted() {
		return fit;
	}
}
