/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom.weather;

public class Day {

	private final String name;
	private final char[] chars;
	private final int temperature;

	public Day(String name, char[] chars, int temperature) {
		super();
		this.name = name;
		this.chars = chars;
		this.temperature = temperature;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the chars
	 */
	public char[] getChars() {
		return chars;
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

}
