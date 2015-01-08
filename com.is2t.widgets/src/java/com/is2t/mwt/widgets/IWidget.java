/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;


public interface IWidget {

	boolean hasFocus();

	boolean isEnabled();

	void setEnabled(boolean enabled);

	boolean isTransparent();

	void setTransparent(boolean transparent);

	void repaint();

	void revalidate();

	int getRelativeX(int x);

	int getRelativeY(int y);

	int getWidth();

	int getHeight();

}
