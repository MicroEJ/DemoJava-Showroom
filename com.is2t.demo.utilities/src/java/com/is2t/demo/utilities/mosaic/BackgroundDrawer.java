/**
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.mosaic;

import ej.microui.io.GraphicsContext;

/**
 * @deprecated, use {@link BackgroundImageHelper} instead
 */
public interface BackgroundDrawer {

	public void draw(GraphicsContext g, int containerWidth, int contenerheight, int defaultColor);

}
