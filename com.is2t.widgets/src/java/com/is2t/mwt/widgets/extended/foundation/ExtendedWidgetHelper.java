/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.foundation;

import com.is2t.mwt.widgets.extended.IExtendedWidget;
import com.is2t.mwt.widgets.extended.contracts.TransparencyManager;

public class ExtendedWidgetHelper {

	public static boolean isTransparent(IExtendedWidget widget) {
		try {
			TransparencyManager renderer = (TransparencyManager) widget.getRenderer();
			return renderer.isTransparent();
		} catch (ClassCastException e) {
			// not transparency manager
			return false;
		} catch (NullPointerException e) {
			// no renderer
			return true;
		}
	}

	public static boolean contains(IExtendedWidget widget, int x, int y) {
		try {
			TransparencyManager renderer = (TransparencyManager) widget.getRenderer();
			return renderer.contains(x, y);
		} catch (ClassCastException e) {
			// not transparency manager
		} catch (NullPointerException e) {
			// no renderer
		}
		return widget.superContains(x, y);
	}

}
