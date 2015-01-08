/*
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

public class Point {

	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
