/*
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

public class Ball {
	
	public static final float BALL_MOTION = 0.8f;
	public static final int BALL_RADIUS = 12;

	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;
		
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSpeedX() {
		return xSpeed;
	}
	
	public int getSpeedY() {
		return ySpeed;
	}
	
	public void move(int x, int y, int xSpeed, int ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.x = x;
		this.y = y;
	}
}
