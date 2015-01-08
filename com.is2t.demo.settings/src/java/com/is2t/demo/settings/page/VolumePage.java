/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.demo.settings.widget.LinedScale;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.widgets.label.LeftIconLabel;

import ej.mwt.MWT;
import ej.mwt.Widget;

public class VolumePage extends ListSettingsPage {

	@Override
	public AppPage getType() {
		return AppPage.VolumePage;
	}

	@Override
	protected String getTitle() {
		return "Volume";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.Volume;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected List<Widget> getListElements() {
		List<Widget> widgets = new ArrayList<Widget>();
		
		widgets.add(createItemWithScale("Game", Pictos.Game, true, 50));
		widgets.add(createItemWithScale("Alarm", Pictos.Alarm, true, 15));
		widgets.add(createItemWithScale("Ring", Pictos.Security, true, 33));
		
		return widgets;
	}
	
	private Widget createItemWithScale(String text, char picto, boolean overlined, int initial) {
		BorderComposite layout = new BorderComposite();
		
		LeftIconLabel item = createItem(text, picto, overlined);
		layout.addAt(item, MWT.CENTER);
		
		LinedScale scale = new LinedScale(0, 100);
		scale.setValue(initial);
		scale.setOverlined(overlined);
		layout.addAt(scale, MWT.EAST);
		
		return layout;
	}
}
