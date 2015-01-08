/**
 * Java
 * 
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.composites;

import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;


/**
 * A grid composite is a {@link Composite} that lays out its children in a grid.
 * All columns have the same width, and all rows have the same height. The width
 * of a grid cell is the width of the composite's parent divided by the
 * number of columns. The height of a row is the maximum of the
 * preferred heights of the children.
 */
public class GridComposite extends Composite {

	/**
	 * <p>
	 * Creates a new horizontal grid composite specifying the number of columns.<br>
	 * Identical to calling {@link #GridComposite(int)} with {@link MWT#HORIZONTAL} as direction.
	 * </p>
	 * @param columns
	 *            the number of columns
	 * @throws IllegalArgumentException
	 *             if columns is negative or equals to zero
	 */
	public GridComposite(int columns) {
		this(MWT.HORIZONTAL, columns);
	}

	/**
	 * <p>
	 * Creates a new grid composite specifying the number of columns/rows and the direction.<br>
	 * The direction can be {@link MWT#HORIZONTAL} or {@link MWT#VERTICAL}.<br>If {@link MWT#HORIZONTAL}
	 * then the <code>count</code> specifies the number of columns, and children are laid out across the
	 * top row until it is full and then on to the next row and so on.<br>If {@link MWT#VERTICAL}
	 * then the <code>count</code> specifies the number of rows, and children are laid out down the
	 * left column until it is full and then on to the next column and so on.
	 * </p>
	 * @param count
	 * the number of columns or rows
	 * @param direction
	 *            the direction of the grid
	 * @throws IllegalArgumentException
	 *             if count is negative or equals to zero
	 * @since 1.0
	 */
	public GridComposite(int direction, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException();
		}
		this.count = count;
		this.horizontal = (direction & MWT.VERTICAL) == 0;
	}

	/**
	 * <p>
	 * Lays out children in a grid.
	 * </p>
	 * <p>
	 * The size of each child is set to the size of a grid cell.<br>
	 * </p>
	 * <p>
	 * The parameters defines the maximum size available for this composite, or {@link MWT#NONE} if there is no constraint. <br>
	 * After this call the preferred size will have been established.
	 * </p>
	 * @param widthHint
	 *            the width available for this widget or {@link MWT#NONE}
	 * @param heightHint
	 *            the height available for this widget or {@link MWT#NONE}
	 */
	public void validate(int widthHint, int heightHint) {
		if (!isVisible()) {
			// optim: do not validate its hierarchy
			setPreferredSize(0, 0);
			return;
		}

		Widget[] contents = getWidgets();
		int length = contents.length;
		if (length == 0) {
			// nothing to do
			return;
		}

		boolean horizontal = this.horizontal;

		//get composite renderer and padding
		Renderer cRenderer = getRenderer();
		int cPadding = cRenderer != null ? cRenderer.getPadding() : 0;
		int doubleCPadding = cPadding << 1;

		//compute cells size
		int count = this.count;
		int otherCount = (int) Math.ceil((double) length / count);
		int columns;
		int rows;
		if (horizontal) {
			columns = count;
			rows = otherCount;
		} else {
			rows = count;
			columns = otherCount;
		}

		// if size is not yet defined -> compute it !
		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;

		int maxCellWidth = computeWidth ? 0 : (widthHint - doubleCPadding) / columns;
		int maxCellHeight = computeHeight ? 0 : (heightHint - doubleCPadding) / rows;
		int cellWidth = 0;
		int cellHeight = 0;

		// keep an array of margins for the second pass
		int[] margins = new int[length];

		for(int i = length; --i >= 0;) {
			Widget widget = (Widget)contents[i];

			//get widget renderer and margin
			Renderer renderer = widget.getRenderer();
			int margin;
			if (renderer == null) {
				margin = 0;
			} else {
				margins[i] = margin = renderer.getMargin();
			}
			int doubleMargin = margin << 1;

			int widgetWidthHint = computeWidth ? 0 : maxCellWidth - doubleMargin;
			int widgetHeightHint = computeHeight ? 0 : maxCellHeight - doubleMargin;

			widget.validate(widgetWidthHint, widgetHeightHint);

			int ww = widget.getPreferredWidth() + doubleMargin;
			int wh = widget.getPreferredHeight() + doubleMargin;

			//compute bigger bounds
			if(ww > cellWidth) {
				cellWidth = ww;
			}
			if(wh > cellHeight) {
				cellHeight = wh;
			}
		}

		if (computeWidth) {
			widthHint = cellWidth * columns + doubleCPadding;
		} else {
			cellWidth = maxCellWidth;
		}

		if (computeHeight) {
			heightHint = cellHeight * rows + doubleCPadding;
		} else {
			cellHeight = maxCellHeight;
		}

		int cptLines = 0;
		int currentX = cPadding;
		int currentY = cPadding;
		for(int i = -1; ++i < length;) {
			Widget widget = (Widget) contents[i];
			int margin = margins[i];
			int doubleMargin = margin << 1;

			widget.setBounds(currentX + margin, currentY + margin, cellWidth - doubleMargin, cellHeight - doubleMargin);

			if (++cptLines == count) {
				cptLines = 0;
				if (horizontal) {
					currentX = cPadding;
					currentY += cellHeight;
				} else {
					currentY = cPadding;
					currentX += cellWidth;
				}
			} else {
				if (horizontal) {
					currentX += cellWidth;
				} else {
					currentY += cellHeight;
				}
			}
		}

		this.otherCount = otherCount;

		setPreferredSize(widthHint, heightHint);

	}

	/**
	 */
	public boolean requestFocus(int direction) {
		boolean horizontal = this.horizontal;
		if (direction == (horizontal ? MWT.UP : MWT.LEFT)) {
			int widgetsCount = getWidgetsCount();
			if (widgetsCount == 0 || !isEnabled()) {
				return false;
			}
			// select the widget of the last full line
			int columns = this.count;
			// compute the number of items on the last line
			int lastLineCount = columns - (otherCount * columns) + widgetsCount;
			// compute the column of the item to focus
			int i = widgetsCount - 1;
			if (lastLineCount != columns && widgetsCount > columns) {
				i -= lastLineCount;
			}
			return requestFocusFrom(i, direction);
		}
		return super.requestFocus(direction);
	}

	/**
	 * @since 1.0
	 */
	public int getNext(int from, int direction) {
		boolean horizontal = this.horizontal;
		int widgetsCount = getWidgetsCount();
		switch (direction) {
		case MWT.UP:
		case MWT.LEFT:
			return getPrevious(horizontal, direction == MWT.LEFT, from, widgetsCount);
		case MWT.DOWN:
		case MWT.RIGHT:
			return getNext(horizontal, direction == MWT.RIGHT, from, widgetsCount);
		default:
			// invalid direction
			throw new IllegalArgumentException();
		}
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	private final boolean horizontal;

	private final int count;
	private int otherCount; // computed

	private int getNext(boolean horizontal, boolean horizontally, int from, int widgetsLength) {
		if (horizontally != horizontal) {
			int columns = this.count;
			if ((from += columns) >= widgetsLength) {
				// the old focused was on the last line/column (or penultimate if the last is not full)
				from = from % columns;
				if (++from == columns) {
					// the last widget of the last full line/column was focused,
					// get out of the grid composite
					return MWT.EMPTY;
				}
			}
			return from;
		} else {
			if (++from >= getWidgetsCount()) {
				return MWT.EMPTY;
			}
			return from;
		}
	}

	private int getPrevious(boolean horizontal, boolean horizontally, int from, int widgetsLength) {
		if (horizontally != horizontal) {
			if (from == 0) {
				// the first widget was focused, get out of the grid composite
				return MWT.EMPTY;
			}
			int columns = this.count;
			if ((from -= columns) < 0) {
				// the old focused was on the first line/column
				// compute the number of items on the last line/column
				int lastLineCount = columns - (otherCount * columns) + widgetsLength;
				// compute the column/line of the item to focus
				if (from + columns <= lastLineCount) { // on the last line/column (not the penultimate)
					from += columns;
				}
				if (widgetsLength > columns) { // not only one line
					from += widgetsLength - lastLineCount;
				}
				--from; // does not need to check: --0 = MWT.EMPTY
			}
			return from;
		} else {
			return --from; // does not need to check: --0 = MWT.EMPTY
		}
	}

}
