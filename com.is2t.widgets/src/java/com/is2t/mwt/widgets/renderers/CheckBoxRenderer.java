/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IToggle;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;

public class CheckBoxRenderer extends BoxButtonRenderer {

	public CheckBoxRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IToggle.class;
	}

	protected void drawBox(GraphicsContext g, Renderable renderable, int boxSize, LookSettings coloring) {
		IToggle check = (IToggle) renderable;
		drawer.drawBox(g, boxSize, check.isSelected(), coloring);
	}

}
