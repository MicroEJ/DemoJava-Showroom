/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.util;

import java.io.IOException;

import ej.microui.io.Image;

public class SimpleImageLoader implements ImageLoader {
	
	@Override
	public Image load(String imageName) {
		try {
			return Image.createImage("/images/" + imageName + ".png", Image.PNG);
		} catch (IOException e) {
			return null;
		}
	}
}
