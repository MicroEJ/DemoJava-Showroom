/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.weather;

import ej.microui.io.DisplayFont;

public interface WeatherPictos {

	DisplayFont FONT = DisplayFont.getFont(81, 80, DisplayFont.STYLE_PLAIN);

	char[] SUNNY = new char[] { 0x21, 0x22 };
	char[] RAINY = new char[] { 0x31, 0x32 };
	char[] SNOWY = new char[] { 0x41, 0x42 };
	char[] CLOUDY = new char[] { 0x51, 0x52 };

}
