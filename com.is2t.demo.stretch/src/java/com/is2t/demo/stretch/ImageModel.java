/*
 * Java
 *
 * Copyright 2011-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.stretch;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.HomeButton;
import com.is2t.demo.utilities.Button;
import com.is2t.demo.utilities.ListenerAdapter;

import ej.microui.Model;
import ej.microui.io.Image;

public abstract class ImageModel extends Model {
	protected Image image;
	protected int x, y;
	protected int[] points;
	private Button homeButton;

	public ImageModel(Image image, int intialOffsetX, int initialOffsetY, int availableWidth, int availableHeight, final Launcher launcher) {
		if (launcher != null) {
			homeButton = new Button(availableWidth - HomeButton.Width, 0, HomeButton.Width, HomeButton.Height);
			homeButton.setListener(new ListenerAdapter() {

				@Override
				public void performAction() {
					launcher.start();
				}
			});
		} else {
			homeButton = new Button(0, 0, 0, 0);
		}

		this.image = image;
		this.x = 0;
		this.y = 0;
		int imgWidth = image.getWidth();
		int imgHeight = image.getHeight();
		points = new int[] { intialOffsetX, initialOffsetY, imgWidth - 1 + intialOffsetX, initialOffsetY,
				imgWidth - 1 + intialOffsetX, imgHeight - 1 + initialOffsetY, intialOffsetX,
				imgHeight - 1 + initialOffsetY };
	}

	public Image getImage() {
		return image;
	}

	public int[] getPoints() {
		return points;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Button getHomeButton() {
		return homeButton;
	}
}
