/*
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

import ej.microui.io.Pointer;

public class Link {
	
	public static final int LINKS_SOFTNESS = 2;

	private Ball ball;
	private Pointer pointer;
	private Point vector;
	
	public Link(Ball ball, Pointer pointer) {
		this.ball = ball;
		this.pointer = pointer;
		this.vector = new Point();
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public Pointer getPointer() {
		return pointer;
	}
	
	public int getX1() {
		return ball.getX();
	}
	
	public int getY1() {
		return ball.getY();
	}
	
	public int getX2() {
		return pointer.getX();
	}
	
	public int getY2() {
		return pointer.getY();
	}
	
	public Point getVector() {
		int x1 = ball.getX();
		int y1 = ball.getY();
		int x2 = pointer.getX();
		int y2 = pointer.getY();
		vector.update(x2 - x1, y2 - y1);
		return vector;
	}
	
}
