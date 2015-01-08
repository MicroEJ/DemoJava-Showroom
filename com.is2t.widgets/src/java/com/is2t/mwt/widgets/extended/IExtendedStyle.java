/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended;


public interface IExtendedStyle {

	int DEFAULT_COLOR = 0xff000000;

	int getStyle();

	void setStyle(int style);

	int getBackgroundColor();

	void setBackgroundColor(int backgroundColor);

	int getForegroundColor();

	void setForegroundColor(int foregroundColor);

	int getAlignment();

	void setAlignment(int alignment);

	int getAnchor();

	void setAnchor(int anchor);

}
