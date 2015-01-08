/*
 * Java
 *
 * Copyright 2012-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

import com.is2t.demo.showroom.common.HomeButtonRenderer;
import com.is2t.demo.utilities.Button;
import com.is2t.demo.utilities.ImageLoader;
import com.is2t.demo.utilities.mosaic.BackgroundImageHelper;
import com.is2t.demo.utilities.mosaic.QuarterMosaic;

import ej.microui.Colors;
import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;
import ej.microui.io.View;

public class ElasticView extends View {

	private static final String BALL = "spider";
	private static final String BACKGROUND = "spiderweb";
	private static final String BACKGROUND_TILE = "spiderweb_quarter_tile";
	private static final int BALL_COLOR = 0x948e8e;
	private static final int BACKGROUND_COLOR = Colors.BLACK;
	private static final int LINK_COLOR = 0x948e8e;

	private Image ballImage;
	private BackgroundImageHelper backgroundImageHelper;

	public ElasticView(int x, int y, int width, int height, ImageLoader imageLoader) {
		super(x, y, width, height);
		try {
			ballImage = imageLoader.load(BALL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		backgroundImageHelper = new BackgroundImageHelper(Display.getDefaultDisplay(), new QuarterMosaic(), BACKGROUND, BACKGROUND_TILE);
	}

	public void paint(GraphicsContext g) {
		
		backgroundImageHelper.drawBackground(g, getWidth(), getHeight(), BACKGROUND_COLOR);

		ElasticModel world = (ElasticModel) getModel();

		// draw links
		Link[] links = world.getLinks();
		for (int i = links.length; --i >= 0;) {
			drawLink(g, links[i]);
		}

		// draw ball
		Ball ball = world.getBall();
		drawBall(g, ball);

		// Home button.
		Button homeButton = world.getHomeButton();
		new HomeButtonRenderer().paint(g, homeButton);
	}

	private void drawLink(GraphicsContext g, Link link) {
		g.setColor(LINK_COLOR);
		int x1 = link.getX1();
		int y1 = link.getY1();
		int x2 = link.getX2();
		int y2 = link.getY2();

		g.drawLine(x1 - 1, y1, x2 - 1, y2);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x1 + 1, y1, x2 + 1, y2);
	}

	private void drawBall(GraphicsContext g, Ball ball) {
		if (ballImage == null) {
			g.setColor(BALL_COLOR);
			g.fillCircle(ball.getX() - Ball.BALL_RADIUS, ball.getY() - Ball.BALL_RADIUS, Ball.BALL_RADIUS << 1);
		} else {
			g.drawImage(ballImage, ball.getX(), ball.getY(), GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}
	}

}
