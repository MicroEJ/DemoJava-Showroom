/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.extended.IExtendedWidget;
import com.is2t.mwt.widgets.extended.ProgressStyles;
import com.is2t.mwt.widgets.extended.foundation.Progress;
import com.is2t.mwt.widgets.extended.renderers.util.ContentStyleLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.mwt.rendering.Look;

public class ProgressBarRenderer extends com.is2t.mwt.widgets.renderers.ProgressBarRenderer {

	public ProgressBarRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return Progress.class;
	}

	public int getManagedStyle() {
		return ProgressStyles.HORIZONTAL_SCALE | ProgressStyles.VERTICAL_SCALE;
	}

	protected LookSettings getLookSettings(Look look, IWidget widget) {
		return new ContentStyleLook(look, ((IExtendedWidget) widget).getExtendedStyle(), widget.isEnabled());
	}

}
