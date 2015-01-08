/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.extended.renderers;

import com.is2t.mwt.widgets.IWidget;
import com.is2t.mwt.widgets.extended.foundation.Button;
import com.is2t.mwt.widgets.extended.renderers.util.DefaultStyleLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.mwt.rendering.Look;

public class ButtonRenderer extends com.is2t.mwt.widgets.renderers.ButtonRenderer {

	public ButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return Button.class;
	}

	protected LookSettings getLookSettings(Look look, IWidget widget) {
		Button button = (Button) widget;
		return new DefaultStyleLook(look, button.getExtendedStyle(), button.isEnabled(), isPressed(button));
	}

}
