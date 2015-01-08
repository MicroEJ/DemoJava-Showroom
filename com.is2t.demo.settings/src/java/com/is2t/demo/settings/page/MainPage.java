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
import com.is2t.mwt.util.ListenerAdapter;
import com.is2t.mwt.widgets.label.HeadlineLabel;

import ej.mwt.Widget;

public class MainPage extends ListSettingsPage {

	@Override
	protected String getTitle() {
		return "Settings";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.Settings;
	}

	@Override
	protected boolean canGoBack() {
		return false;
	}

	@Override
	protected List<Widget> getListElements() {
		List<Widget> widgets = new ArrayList<Widget>();

		widgets.add(createSelectableItem("About IS2T", Pictos.About, false, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.AboutPage, true, true);
			}
		}));

		HeadlineLabel systemlHeadline = createHeadlineLabel("SYSTEM");
		widgets.add(systemlHeadline);

		widgets.add(createSelectableItem("Date & time", Pictos.DateAndTime, false, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.DateTimePage, true, true);
			}
		}));
		widgets.add(createSelectableItem("Volume", Pictos.Volume, true, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.VolumePage, true, true);
			}
		}));
		widgets.add(createSelectableItem("Profile", Pictos.LanguageAndInput, true, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.Profile, true, true);
			}
		}));

		HeadlineLabel wirelessAndNetworkHeadline = createHeadlineLabel("WIRELESS & NETWORKS");
		widgets.add(wirelessAndNetworkHeadline);

		widgets.add(createItemWithToggleButton("Wi-Fi", Pictos.WiFi, false, true));
		widgets.add(createItemWithToggleButton("Bluetooth", Pictos.Bluetooth, true, false));

		HeadlineLabel personalHeadline = createHeadlineLabel("PERSONAL");
		widgets.add(personalHeadline);

		widgets.add(createItemWithCheckbox("Location data", Pictos.Location, false, true));
		widgets.add(createSelectableItem("Security", Pictos.Security, true, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.SecurityPage, true, true);
			}
		}));
		widgets.add(createSelectableItem("Battery profile", Pictos.BatteryProfile, true, new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				TransitionsHelper.goTo(AppPage.BatteryProfilePage, true, true);
			}
		}));

		return widgets;
	}

	@Override
	public AppPage getType() {
		return AppPage.MainPage;
	}
}
