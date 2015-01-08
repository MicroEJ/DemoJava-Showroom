/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.microui.io.Image;

public interface IImageAnimation extends IWidget {

	Image[] getFrames();

	int getCurrentFrameIndex();

	int getPeriod();

	void setPeriod(int delay);

	void play();

	void pause();

	void stop();

}
