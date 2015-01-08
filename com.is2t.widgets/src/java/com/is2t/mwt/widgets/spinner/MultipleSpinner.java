/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.spinner;

import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;

public class MultipleSpinner extends Composite {

	@Override
	public boolean isTransparent() {
		return true;
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		Renderer renderer = getRenderer();
		int padding = renderer.getPadding();

		int totalWidth = 2 * padding;
		int maxHeight = 0;

		Widget[] widgets = getWidgets();
		int widgetsLength = widgets.length;
		for (int i = -1; ++i < widgetsLength;) {
			Widget widget = widgets[i];
			widget.validate(widthHint, heightHint);
			int widgetPreferredWidth = widget.getPreferredWidth();
			int widgetPreferredHeight = widget.getPreferredHeight();
			totalWidth += widgetPreferredWidth + 2 * padding;
			maxHeight = Math.max(maxHeight, widgetPreferredHeight);
		}

		int availableWidth;
		float xRatio;
		int currentX;
		if(widthHint == MWT.NONE) {
			availableWidth = totalWidth;
			xRatio = 1.0f;
			currentX = 2 * padding;
		} else {
			availableWidth = widthHint;
			if (availableWidth > totalWidth) {
				xRatio = 1.0f;
				currentX = (availableWidth - totalWidth) / 2 + 2 * padding;
			} else {
				xRatio = (float) availableWidth / totalWidth;
				currentX = 2 * padding;
			}
		}
		int availableHeight;
		int itemsHeight;
		int currentY;
		if (heightHint == MWT.NONE) {
			availableHeight = maxHeight + 2 * padding;
			itemsHeight = maxHeight;
			currentY = padding;
		} else {
			availableHeight = heightHint;
			if (availableHeight > maxHeight) {
				itemsHeight = maxHeight;
				currentY = (availableHeight - maxHeight) / 2;
			} else {
				itemsHeight = availableHeight - 2 * padding;
				currentY = padding;
			}
		}

		int doublePaddingRatio = (int) (2 * padding * xRatio);
		for (int i = -1; ++i < widgetsLength;) {
			Widget widget = widgets[i];
			int widgetPreferredWidth = widget.getPreferredWidth();
			int widgetWidth = (int) (widgetPreferredWidth * xRatio);
			widget.setBounds(currentX, currentY, widgetWidth, itemsHeight);
			currentX += widgetWidth + doublePaddingRatio;
		}

		setPreferredSize(availableWidth, availableHeight);
	}
}
