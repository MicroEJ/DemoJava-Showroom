/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import java.util.List;

import com.is2t.demo.settings.widget.LeftIconLabelButton;
import com.is2t.demo.settings.widget.LinedToggleButton;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.widgets.ListComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.Picto.PictoSize;
import com.is2t.mwt.widgets.label.HeadlineLabel;
import com.is2t.mwt.widgets.label.LeftIconLabel;
import com.is2t.mwt.widgets.scroll.ScrollComposite;
import com.is2t.mwt.widgets.tiny.CheckBox;

import ej.microui.Listener;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;

public abstract class ListSettingsPage extends SettingsPage {

	private ScrollComposite scrollComposite;

	@Override
	protected Composite createMainContent() {
		boolean horizontal = false;
		Composite listComposite = new ListComposite(horizontal);
		List<Widget> listContent = getListElements();

		for (Widget widget : listContent) {
			listComposite.add(widget);
		}

		scrollComposite = new ScrollComposite(listComposite, horizontal, false);
		return scrollComposite;
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

	/**
	 * Gets the elements printed in the list.
	 * 
	 * @return the elements printed in the list.
	 */
	protected abstract List<Widget> getListElements();

	protected HeadlineLabel createHeadlineLabel(String text) {
		HeadlineLabel headlineLabel = new HeadlineLabel(text, "", 2);
		headlineLabel.setHeadlineFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		headlineLabel.setUnderlined(true);
		return headlineLabel;
	}

	protected LeftIconLabel createItem(String text, char picto, boolean overlined) {
		LeftIconLabel item = new LeftIconLabel(text, new Picto(picto, PictoSize.Small));
		item.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		item.setOverlined(overlined);
		return item;
	}

	protected LeftIconLabelButton createSelectableItem(String text, char picto, boolean overlined, Listener listener) {
		LeftIconLabelButton item = new LeftIconLabelButton(text, new Picto(picto, PictoSize.Small));
		item.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		item.setOverlined(overlined);
		item.setListener(listener);
		return item;
	}

	protected LeftIconLabel createItem(String text, boolean overlined) {
		LeftIconLabel item = new LeftIconLabel(text, null);
		item.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		item.setOverlined(overlined);
		return item;
	}

	protected Widget createItemWithToggleButton(String text, char picto, boolean overlined, boolean initial) {
		BorderComposite layout = new BorderComposite();

		LeftIconLabel item = createItem(text, picto, overlined);
		layout.add(item);

		LinedToggleButton toggleButton = new LinedToggleButton();
		toggleButton.setOverlined(overlined);

		if (initial) {
			toggleButton.toggleSelection();
		}

		layout.addAt(toggleButton, MWT.EAST);

		return layout;
	}

	protected Widget createItemWithCheckbox(String text, char picto, boolean overlined, boolean initial) {
		BorderComposite layout = new BorderComposite();

		LeftIconLabel item = createItem(text, picto, overlined);
		layout.add(item);

		CheckBox checkbox = new CheckBox();
		// checkbox.setOverlined(overlined);

		if (initial) {
			checkbox.toggleSelection();
		}

		layout.addAt(checkbox, MWT.EAST);

		return layout;
	}
}
