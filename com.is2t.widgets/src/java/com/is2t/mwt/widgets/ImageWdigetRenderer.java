/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.mwt.widgets;


import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.WidgetRenderer;

public class ImageWdigetRenderer extends WidgetRenderer {

	public int getPreferredContentWidth(Widget widget) {
		try {
			return ((ImageWidget) widget).getImage().getWidth();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	public int getPreferredContentHeight(Widget widget) {
		try {
			return ((ImageWidget) widget).getImage().getHeight();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	public Class<?> getManagedType() {
		return ImageWidget.class;
	}

	public void render(GraphicsContext g, Renderable renderable) {
		ImageWidget imageWidget = (ImageWidget) renderable;
		Image image = imageWidget.getImage();
		int imageWidgetWidth = imageWidget.getWidth();
		int imageWidgetHeight = imageWidget.getHeight();
		
		if (image == null) {
			return;
		}
		
		if (imageWidget.isFitted()) {
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();

			float xRatio = (float) imageWidgetWidth / imageWidth;
			float yRatio = (float) imageWidgetHeight / imageHeight;
			float ratio = Math.min(xRatio, yRatio);
			int width = (int) (imageWidth * ratio);
			int height = (int) (imageHeight * ratio);
			int xLeft = (imageWidgetWidth - width) / 2;
			int xRight = xLeft + width;
			int yTop = (imageWidgetHeight - height) / 2;
			int yBottom = yTop + height;

			int[] xys = new int[] { xLeft, yTop, xRight, yTop, xRight, yBottom, xLeft, yBottom, };
			g.drawDeformedImage(imageWidget.getImage(), 0, 0, xys, 0);
		} else {
			g.drawImage(image, imageWidgetWidth >> 1, imageWidgetHeight >> 1, GraphicsContext.HCENTER
					| GraphicsContext.VCENTER);
		}
	}
}
