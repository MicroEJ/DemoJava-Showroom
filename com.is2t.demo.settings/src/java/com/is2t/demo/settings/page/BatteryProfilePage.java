/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.demo.settings.widget.IconToggleButton;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.util.ListenerAdapter;
import com.is2t.mwt.widgets.ListComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.Picto.PictoSize;
import com.is2t.mwt.widgets.label.TitleLabel;
import com.is2t.mwt.widgets.scroll.ScrollComposite;
import com.is2t.mwt.widgets.tiny.ButtonGroup;

import ej.microui.io.Display;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.rendering.WidgetRenderer;

public class BatteryProfilePage extends SettingsPage {

	private TitleLabel label;
	private ScrollComposite scrollComposite;

	@Override
	public AppPage getType() {
		return AppPage.BatteryProfilePage;
	}

	@Override
	protected String getTitle() {
		return "Battery profile";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.BatteryProfile;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Composite createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		label = new TitleLabel("");
		label.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.addAt(label, MWT.SOUTH);

		ListComposite profilesList = new ListComposite(true);
		ButtonGroup group = new ButtonGroup();

		// Put enough buttons to be able to scroll.
		char[] availablePictos = new char[] { Pictos.Database, Pictos.Box, Pictos.HomeGarage, Pictos.Fan,
				Pictos.BlackLight, Pictos.Lightning };
		int availablePictosCount = availablePictos.length;
		int pictoIndex = 0;

		IconToggleButton profile = createProfile("Eco", availablePictos[pictoIndex], group);
		WidgetRenderer renderer = (WidgetRenderer) profile.getRenderer();
		int buttonWidth = renderer.getPreferredContentWidth(profile);
		int containerWidth = Display.getDefaultDisplay().getWidth(); // can be better
		int buttonCount = containerWidth / buttonWidth + 2;
		profilesList.add(profile);

		for (int i = 0; i < buttonCount; i++) {
			String name = "Profile " + i;
			pictoIndex = (pictoIndex + 1) % availablePictosCount;
			char picto = availablePictos[pictoIndex];
			profilesList.add(createProfile(name, picto, group));
		}

		scrollComposite = new ScrollComposite(profilesList, true, false);
		mainContentLayout.addAt(scrollComposite, MWT.CENTER);
		profile.toggleSelection();

		return mainContentLayout;
	}

	private IconToggleButton createProfile(String name, char picto, ButtonGroup group) {
		IconToggleButton profile = new IconToggleButton(name, new Picto(picto, PictoSize.Big));
		profile.setListener(new ProfileSelector(profile));
		group.add(profile);
		return profile;
	}

	@Override
	public void showNotify() {
		scrollComposite.showNotify();
		super.showNotify();
	}

	@Override
	public void hideNotify() {
		scrollComposite.hideNotify();
		super.hideNotify();
	}

	private class ProfileSelector extends ListenerAdapter {

		ProfileSelector(IconToggleButton button) {
			this.button = button;
		}

		private final IconToggleButton button;

		@Override
		public void performAction(int value, Object object) {
			label.setText(button.getText());
		}
	}

}
