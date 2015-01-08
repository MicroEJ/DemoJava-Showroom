/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.label.renderer;

import com.is2t.mwt.widgets.button.TopIconButton;
import com.is2t.mwt.widgets.label.TopIconLabel;

import ej.mwt.rendering.Look;

public class TopIconButtonRenderer extends TopIconLabelRenderer {

	@Override
	public Class<?> getManagedType() {
		return TopIconButton.class;
	}

	@Override
	protected int getColor(TopIconLabel label, Look look) {
		return ((TopIconButton) label).isActive() ? look.getProperty(Look.GET_FOREGROUND_COLOR_FOCUSED) : look
				.getProperty(Look.GET_FOREGROUND_COLOR_DEFAULT);
	}

}
