/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers;

import com.is2t.mwt.widgets.IImageAnimation;
import com.is2t.mwt.widgets.renderers.util.ContentLook;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;

public class ImageAnimationRenderer extends DefaultWidgetRenderer {

	public ImageAnimationRenderer(Drawer drawer) {
		super(drawer);
	}

	public Class getManagedType() {
		return IImageAnimation.class;
	}

	public int getPreferredContentWidth(Widget widget) {
		IImageAnimation label = (IImageAnimation) widget;
		return label.getFrames()[0].getWidth();
	}

	public int getPreferredContentHeight(Widget widget) {
		IImageAnimation label = (IImageAnimation) widget;
		return label.getFrames()[0].getHeight();
	}

	public void render(GraphicsContext g, Renderable renderable) {
		IImageAnimation animation = (IImageAnimation) renderable;
		int width = animation.getWidth();
		int height = animation.getHeight();

		drawer.fillBackground(g, width, height, new ContentLook(getLook(), true));

		// draw centered frame
		g.drawImage(animation.getFrames()[animation.getCurrentFrameIndex()], width / 2, height / 2, GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);
	}

}
