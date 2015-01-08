/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended;

import com.is2t.mwt.widgets.IWidget;

import ej.mwt.rendering.Renderer;

public interface IExtendedWidget extends IWidget {

	IExtendedStyle getExtendedStyle();

	int computeScore(Renderer renderer);

	Renderer getRenderer();

	boolean isTransparent();

	boolean contains(int x, int y);

	// sorry, another idea?
	boolean superContains(int x, int y);

}
