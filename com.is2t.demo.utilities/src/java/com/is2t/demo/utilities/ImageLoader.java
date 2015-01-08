/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities;

import java.io.IOException;

import ej.microui.io.Display;
import ej.microui.io.Image;

public class ImageLoader {

	private final String resolutionFolder;

	public ImageLoader(Display display) {
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		resolutionFolder = "d" + displayWidth + "x" + displayHeight + "/";
	}

	public Image load(String imageName) throws IOException {
		return Image.createImage("/images/" + resolutionFolder + imageName
				+ ".png", Image.PNG);
	}

}
