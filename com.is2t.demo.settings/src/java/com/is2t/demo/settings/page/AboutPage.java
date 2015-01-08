/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.settings.page;

import com.is2t.demo.settings.theme.Pictos;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.util.Drawings;
import com.is2t.mwt.widgets.ListComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.label.MultiLineLabel;
import com.is2t.mwt.widgets.label.TitleLabel;
import com.is2t.mwt.widgets.scroll.ScrollComposite;

import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

public class AboutPage extends SettingsPage {

	private static final String HEADLINE_TEXT = "Designing embedded software has never been this easy!";
	private static final String TEXT = "IS2T is the editor of the highly-scalable MicroEJ solution to prototype, design, and deploy software applications on various hardware configurations.";

	private ScrollComposite scrollComposite;

	@Override
	public AppPage getType() {
		return AppPage.AboutPage;
	}

	@Override
	protected Composite createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		ListComposite listComposite = new ListComposite(false);

		// Put enough text to be able to scroll.
		Display display = Display.getDefaultDisplay();

		MultiLineLabel headline = new MultiLineLabel("");
		Renderer renderer = headline.getRenderer();
		int containerWidth = display.getWidth() - renderer.getPadding() * 2;
		Look look = renderer.getLook();
		int headlineFontIndex = LookExtension.GET_BOLD_SMALL_FONT_INDEX;
		DisplayFont headlineFont = look.getFonts()[look.getProperty(headlineFontIndex)];
		headline.setFontSize(headlineFontIndex);
		String[] availableHeadlines = Drawings.splitString(HEADLINE_TEXT, headlineFont, containerWidth);
		StringBuffer text = new StringBuffer();

		for (String line : availableHeadlines) {
			text.append(line);
			text.append('\n');
		}
		headline.setText(text.toString());

		listComposite.add(headline);

		MultiLineLabel textLabel = new MultiLineLabel("");
		int textFontIndex = LookExtension.GET_MEDIUM_FONT_INDEX;
		DisplayFont textFont = look.getFonts()[look.getProperty(textFontIndex)];
		String[] availableTextlines = Drawings.splitString(TEXT, textFont, containerWidth);
		int containerHeight = display.getHeight() - 2 * SettingsPage.TOP_BAR_HEIGHT - headlineFont.getHeight(); // can
																												// be
																												// better.
		int lineCount = containerHeight / textFont.getHeight() - 1;
		int textLineCount = Math.min(lineCount, availableTextlines.length);
		text = new StringBuffer();

		for (int i = 0; i < textLineCount; i++) {
			text.append(availableTextlines[i]);
			text.append('\n');
		}

		for (int i = textLineCount; i < lineCount; i++) {
			text.append('\n');
		}

		textLabel.setText(text.toString());
		textLabel.setFontSize(textFontIndex);
		listComposite.add(textLabel);

		scrollComposite = new ScrollComposite(listComposite, false, true);
		mainContentLayout.addAt(scrollComposite, MWT.CENTER);

		TitleLabel visitIs2tLabel = new TitleLabel("Visit us at www.is2t.com");
		visitIs2tLabel.setFontSize(LookExtension.GET_BIG_FONT_INDEX);
		visitIs2tLabel.setOverlined(true);
		mainContentLayout.addAt(visitIs2tLabel, MWT.BOTTOM);

		return mainContentLayout;
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

	@Override
	protected String getTitle() {
		return "About IS2T";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.About;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}
}
