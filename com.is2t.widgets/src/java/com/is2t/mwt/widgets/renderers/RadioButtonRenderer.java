/*
 * Java
 * Copyright 2009-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IRadio;
import com.is2t.mwt.widgets.Pictos;
import com.is2t.mwt.widgets.renderers.util.Drawer;
import com.is2t.mwt.widgets.renderers.util.LookSettings;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;

public class RadioButtonRenderer extends BoxButtonRenderer {

	public RadioButtonRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IRadio.class;
	}

	protected void drawBox(GraphicsContext g, Renderable renderable, int boxSize, LookSettings coloring) {
		IRadio radio = (IRadio) renderable;
		char picto;
		
		if(radio.isSelected()) {
			picto = Pictos.CheckedRadio;
		}else {
			picto = Pictos.UncheckedRadio;
		}
		
		int halfBox = boxSize / 2;
		g.setColor(coloring.backgroundColor);
		g.setFont(Pictos.MediumFont);
		g.drawChar(picto, halfBox, halfBox, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		
//		g.fillRect(0, 0, boxSize, boxSize);
	}

}
