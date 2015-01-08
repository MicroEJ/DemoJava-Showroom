/*
 * Java
 *
 * Copyright 2012-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.HomeButton;
import com.is2t.demo.utilities.Button;
import com.is2t.demo.utilities.ListenerAdapter;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.Model;
import ej.microui.io.Pointer;

public class ElasticModel extends Model {

	public static final float WALL_MOTION = 1.0f;
	private static final int UPDATE_PERIOD = 50;
	private static final float WORLD_HEIGHT_FACTOR = 0.90f;

	private int width;
	private int height;
	private final Ball ball;
	private Link[] links;
	private Button homeButton;

	private TimerTask updateTask;

	public ElasticModel(int width, int height, Timer timer, final Launcher launcher) {
		if(launcher != null) {
			homeButton = new Button(width - HomeButton.Width, 0, HomeButton.Width, HomeButton.Height);
			homeButton.setListener(new ListenerAdapter() {
				
				@Override
				public void performAction() {
					launcher.start();
				}
			});
		}else {
			homeButton = new Button(0, 0, 0, 0);
		}
		
		this.width = width;
		this.height = height;
		this.ball = new Ball();
		this.links = new Link[0];
		timer.schedule(updateTask = new TimerTask() {
			public void run() {
				computeForces();
				changed();
			}
		}, 0, UPDATE_PERIOD);
	}

	public void destroy() {
		if (updateTask != null) {
			updateTask.cancel();
			updateTask = null;
		}
	}

	private void computeForces() {
		Ball ball = this.ball;
		Link[] links = getLinks();

		int xStrength = 0;
		int yStrength = 0;

		int speedX = ball.getSpeedX();
		int speedY = ball.getSpeedY();

		// compute links effect
		int linksLength = links.length;
		for (int i = linksLength; --i >= 0;) {
			Link link = links[i];
			Point vector = link.getVector();
			int linkX = vector.getX();
			int linkY = vector.getY();

			xStrength += linkX >> Link.LINKS_SOFTNESS;
			yStrength += linkY >> Link.LINKS_SOFTNESS;
		}

		// compute new speed
		xStrength = (int) ((xStrength + speedX) * (Ball.BALL_MOTION));
		yStrength = (int) ((yStrength + speedY) * (Ball.BALL_MOTION));

		// update ball position and speed
		int ballX = ball.getX();
		int ballY = ball.getY();

		ballX += xStrength;
		ballY += yStrength;

		// bounce if necessary
		int width = this.width;
		int height = (int) (this.height * WORLD_HEIGHT_FACTOR);

		if (ballX - Ball.BALL_RADIUS < 0) {
			ballX = -ballX + (Ball.BALL_RADIUS << 1);
			xStrength = (int) (-xStrength * WALL_MOTION);
		} else if (ballX + Ball.BALL_RADIUS > width) {
			ballX = -ballX + ((width - Ball.BALL_RADIUS) << 1);
			xStrength = (int) (-xStrength * WALL_MOTION);
		}
		if (ballY - Ball.BALL_RADIUS < 0) {
			ballY = -ballY + (Ball.BALL_RADIUS << 1);
			yStrength = (int) (-yStrength * WALL_MOTION);
		} else if (ballY + Ball.BALL_RADIUS > height) {
			ballY = -ballY + ((height - Ball.BALL_RADIUS) << 1);
			yStrength = (int) (-yStrength * WALL_MOTION);
		}

		ball.move(ballX, ballY, xStrength, yStrength);
	}

	public Ball getBall() {
		return ball;
	}

	public void addLink(Pointer pointer) {
		Link link = new Link(ball, pointer);
		Link[] links = this.links;
		try {
			int length = links.length;
			System.arraycopy(links, 0, links = new Link[length + 1], 0, length);
			links[length] = link;
			this.links = links;
		} catch (NullPointerException e) {
			this.links = new Link[] { link };
		}
		changed();
	}

	public void removeLink(Pointer pointer) {
		Link[] links = this.links;
		int length = links.length;
		for (int i = length; --i >= 0;) {
			if (links[i].getPointer() == pointer) {
				if (length == 1) {
					this.links = new Link[0];
				} else {
					Link[] newLinks;
					System.arraycopy(links, 0, newLinks = new Link[length - 1], 0, i);
					System.arraycopy(links, i + 1, newLinks, i, length - i - 1);
					this.links = newLinks;
				}
				break;
			}
		}
		changed();
	}

	public Link[] getLinks() {
		return links;
	}
	
	public Button getHomeButton() {
		return homeButton;
	}
}
