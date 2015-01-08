/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.extended.IExtendedWidget;
import com.is2t.mwt.widgets.extended.foundation.Label;
import com.is2t.mwt.widgets.extended.renderers.util.ContentStyleLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.mwt.rendering.Look;

public class LabelRenderer extends com.is2t.mwt.widgets.renderers.LabelRenderer {

	public LabelRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return Label.class;
	}

	protected LookSettings getLookSettings(Look look, IWidget widget) {
		return new ContentStyleLook(look, ((IExtendedWidget) widget).getExtendedStyle(), true);
	}

}
