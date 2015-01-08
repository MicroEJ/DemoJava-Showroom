/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.microui.io.DisplayFont;

public interface Pictos {

	int PICTO_FONT_ID = 97;
	
	DisplayFont MediumFont = DisplayFont.getFont(PICTO_FONT_ID, 30, DisplayFont.STYLE_PLAIN);
	char UncheckedRadio = 0x21;
	char CheckedRadio = 0x22;
}
