
/*
 *	Java
 *	
 *	Copyright 2014 IS2T. All rights reserved.
 *	Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */

package com.is2t.demo.utilities.mosaic;

import com.is2t.demo.utilities.ImageLoader;

import ej.microui.io.Display;
import ej.microui.io.GraphicsContext;
import ej.microui.io.Image;

public class BackgroundImageHelper {

	private Image full, tile;
	private Mosaic mosaic;

	/**
	 * Use the specified display's size as expected size. 
	 * @see #BackgroundImageHelper(Display, Mosaic, String, String, int, int)
	 * @param display
	 * @param mosaic
	 * @param fullImageName
	 * @param tileImageName
	 */
	public BackgroundImageHelper(Display display, Mosaic mosaic, String fullImageName, String tileImageName) {
		this(display, mosaic, fullImageName, tileImageName, display.getWidth(), display.getHeight());
	}

	/**
	 * Specific entry point for {@link QuarterMosaic} mosaic: the expected area size
	 * is indexed on the tile area size.
	 * @param display
	 * @param mosaic
	 * @param fullImageName
	 * @param tileImageName
	 */
	public BackgroundImageHelper(Display display, QuarterMosaic mosaic, String fullImageName, String tileImageName) {
		this(display, mosaic, fullImageName, tileImageName, QuarterMosaic.TILE_FACTOR, QuarterMosaic.TILE_FACTOR, true);
	}
	
	/**
	 * Prepare a background drawer. 
	 * <p>
	 * Tries first to load the background image from the ROM memory.
	 * <ul>
	 * <li>If works, this image will be used by {@link #drawBackground(GraphicsContext, int)} method.</li>
	 * <li>If fails, tries to create an image which area size is specified by (expectedWidth, expectedHeight).</li>
	 * <ul>
	 * <li>If works, this image will be used by {@link #drawBackground(GraphicsContext, int)} method.</li>
	 * <li>If fails, the tile image will be used "at runtime".</li>
	 * </ul>
	 * </ul>
	 * @param display the targeted display
	 * @param mosaic the mosaic to apply on the background
	 * @param fullImageName the "big" image name (will be loaded thanks the {@link ImageLoader}
	 * @param tileImageName the "tile" image name (will be loaded thanks the {@link ImageLoader}
	 * @param expectedWidth the background expected width
	 * @param expectedHeight the background expected height
	 */
	public BackgroundImageHelper(Display display, Mosaic mosaic, String fullImageName, String tileImageName, int expectedWidth, int expectedHeight) {
		this(display, mosaic, fullImageName, tileImageName, expectedWidth, expectedHeight, false);
	}

	private BackgroundImageHelper(Display display, Mosaic mosaic, String fullImageName, String tileImageName, int expectedWidth, int expectedHeight, boolean useTileSize) {

		// try first to load the full image
		ImageLoader loader = new ImageLoader(display);
		Image full = loadImage(loader, fullImageName);

		if (full != null) {
			// full image is available: no need to load anything else
			this.full = full;
		}
		else {
			// full background image is not available
			// try to create a dynamic one

			Image tile = loadImage(loader, tileImageName);

			if (tile != null) {
				
				if (useTileSize) {
					// the background size is dependant of the tile image
					// in this case, expectedWidth and expectedHeight are some factors
					expectedWidth *= tile.getWidth();
					expectedHeight *= tile.getHeight();
				}
				// else: use the given size

				Image background;
				
				try {
					background = Image.createImage(display, expectedWidth, expectedHeight);
					mosaic.apply(background, tile);
					this.tile = null;	// useless image
					this.full = background;
					this.mosaic = null;	// useless
				}
				 catch (OutOfMemoryError e){
					 // not enough memory to create a full background image
					 // -> draw mozaic at runtime
					 this.tile = tile;	
					 this.full = null;	// useless image
					 this.mosaic = mosaic;
				 }
				
			}
			// else: the tile image is not available, we will use the background color!
		}
	}
	
	public void drawBackground(GraphicsContext g, int containerWidth, int contenerheight, int defaultColor) {
		
		Image full = this.full;
		Image tile = this.tile;
		
		if (full != null) {
			
			// full image is available: draw it!
			
			int fw = full.getWidth();
			int fh = full.getHeight();
			
			if (fw < containerWidth || fh < contenerheight) {
				// image is smaller than the destination area
				fillBackground(g, containerWidth, contenerheight, defaultColor);
			}
			
			g.drawImage(full, containerWidth / 2, contenerheight / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}
		else if (tile != null){
			// have to draw the mozaic "at runtime"
			mosaic.apply(g, tile, containerWidth, contenerheight, defaultColor);
		}
		else {
			// have to draw with the background color
			fillBackground(g, containerWidth, contenerheight, defaultColor);
		}
	}
	
	private void fillBackground(GraphicsContext g, int gw, int gh, int defaultColor) {
		g.setColor(defaultColor);
		g.fillRect(0, 0, gw, gh);
	}

	private Image loadImage(ImageLoader loader, String name) {
		try {
			return loader.load(name);
		} catch (Exception invalidFullImage) {
			// image is not available
			return null;
		}
	}
}
