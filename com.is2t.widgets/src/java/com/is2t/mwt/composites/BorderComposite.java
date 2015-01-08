/**
 * Java
 * 
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.composites;

import ej.microui.io.CompositeView;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Panel;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;

/**
 * A border composite is a {@link Composite} that lays out its children in five regions: {@link MWT#NORTH},
 * {@link MWT#SOUTH}, {@link MWT#EAST}, {@link MWT#WEST}, {@link MWT#CENTER}. Each region can contain at most one widget.
 */
public class BorderComposite extends Composite {

	/**
	 * Creates a new border composite.<br>
	 * Identical to calling {@link #BorderComposite(int)} with {@link MWT#HORIZONTAL} as direction.
	 */
	public BorderComposite() {
		this(MWT.HORIZONTAL);
	}

	/**
	 * Creates a new border composite specifying the direction.<br>
	 * The direction can be {@link MWT#HORIZONTAL} or {@link MWT#VERTICAL}. Respectively, the children will be laid
	 * out in three rows (NORTH, {WEST CENTER EAST}, SOUTH) or in three columns (WEST, {NORTH CENTER SOUTH}, EAST).
	 * @param direction
	 *            the direction of the layout priority
	 * @since 1.0
	 */
	public BorderComposite(int direction) {
		super();
		this.horizontal = (direction & MWT.VERTICAL) == 0;
		widgetPlaces = new int[POSITIONS_COUNT];
		//init widget places
		reset();
	}

	/**
	 * Creates a new border composite specifying its bounds. Its position is relative to the position of its parent.
	 * @param x
	 *            the relative x coordinate of the composite
	 * @param y
	 *            the relative y coordinate of the composite
	 * @param width
	 *            the width of the composite
	 * @param height
	 *            the height of the composite
	 * @deprecated use {@link #BorderComposite()} and {@link #setBounds(int, int, int, int)}
	 */
	public BorderComposite(int x, int y, int width, int height) {
		this();
		setBounds(x, y, width, height);
	}

	/**
	 * <p>
	 * Lays out children to fit five regions: {@link MWT#NORTH}, {@link MWT#SOUTH}, {@link MWT#EAST}, {@link MWT#WEST} ,
	 * {@link MWT#CENTER}.
	 * </p>
	 * <p>
	 * The parameters define the maximum size available for this composite, or {@link MWT#NONE} if there is no constraint. <br>
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

		boolean horizontal = this.horizontal;

		//get composite renderer and padding
		Renderer borderRenderer = getRenderer();
		int cPadding = (borderRenderer != null) ? borderRenderer.getPadding() : 0;

		// if size is not yet defined -> compute it !
		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;

		int remainingWidth = computeWidth ? 0 : widthHint - (cPadding << 1);
		int remainingHeight = computeHeight ? 0 : heightHint - (cPadding << 1);

		Widget first;
		Widget second;
		Widget third;
		Widget fourth;
		Widget center;
		{
			// get widgets
			Widget[] widgets = getWidgets();
			int[] places = this.widgetPlaces;

			int tmpPlace = places[NORTH_INTERN];
			Widget north = tmpPlace == NO_WIDGET ? null : widgets[tmpPlace];
			tmpPlace = places[SOUTH_INTERN];
			Widget south = tmpPlace == NO_WIDGET ? null : widgets[tmpPlace];
			tmpPlace = places[WEST_INTERN];
			Widget west = tmpPlace == NO_WIDGET ? null : widgets[tmpPlace];
			tmpPlace = places[EAST_INTERN];
			Widget east = tmpPlace == NO_WIDGET ? null : widgets[tmpPlace];
			tmpPlace = places[CENTER_INTERN];
			center = tmpPlace == NO_WIDGET ? null : widgets[tmpPlace];

			if (horizontal) {
				first = north;
				second = south;
				third = west;
				fourth = east;
			} else {
				first = west;
				second = east;
				third = north;
				fourth = south;
			}
		}

		WidgetInfos firstInfos;
		WidgetInfos secondInfos;
		WidgetInfos thirdInfos;
		WidgetInfos fourthInfos;

		// compute widgets sizes and margins
		Sizes sizes = new Sizes(remainingWidth, remainingHeight, 0, 0, 0, 0);
		firstInfos = validate(first, sizes, horizontal, computeWidth, computeHeight, true);
		secondInfos = validate(second, sizes, horizontal, computeWidth, computeHeight, true);
		thirdInfos = validate(third, sizes, horizontal, computeWidth, computeHeight, false);
		fourthInfos = validate(fourth, sizes, horizontal, computeWidth, computeHeight, false);

		int centerMargin;
		if(center != null){
			remainingWidth = sizes.remainingWidth;
			remainingHeight = sizes.remainingHeight;

			//get widget renderer and margin
			Renderer centerRenderer = center.getRenderer();
			centerMargin = (centerRenderer != null) ? centerRenderer.getMargin() : 0;

			int centerWidthHint = computeWidth ? 0 : remainingWidth - (centerMargin << 1);
			int centerHeightHint = computeHeight ? 0 : remainingHeight - (centerMargin << 1);

			center.validate(centerWidthHint, centerHeightHint);
			int wWidth = center.getPreferredWidth();
			int wHeight = center.getPreferredHeight();

			if (computeWidth) {
				sizes.centerSumWidth += wWidth + (centerMargin << 1);
			}
			if (computeHeight) {
				sizes.centerSumHeight += wHeight + (centerMargin << 1);
			}
		} else {
			centerMargin = 0;
		}

		if(computeWidth) {
			widthHint = Math.max(sizes.maxWidth, sizes.centerSumWidth);
		}
		if(computeHeight) {
			heightHint = Math.max(sizes.maxHeight, sizes.centerSumHeight);
		}

		Rectangle rectangle = new Rectangle(cPadding, heightHint - cPadding, cPadding, widthHint - cPadding);
		place(first, firstInfos, rectangle, horizontal, true, true);
		place(second, secondInfos, rectangle, horizontal, true, false);
		place(third, thirdInfos, rectangle, horizontal, false, true);
		place(fourth, fourthInfos, rectangle, horizontal, false, false);

		if (center != null) {
			// the center gets the remaining rectangle
			int top = rectangle.top;
			int bottom = rectangle.bottom;
			int left = rectangle.left;
			int right = rectangle.right;
			center.setBounds(left + centerMargin, top + centerMargin, right - left - (centerMargin << 1), bottom - top
					- (centerMargin << 1));
		}

		setPreferredSize(widthHint, heightHint);
	}

	/**
	 * Adds the specified widget at the {@link MWT#CENTER} position of this composite.
	 * Identical to <code>addAt(widget, {@link MWT#CENTER})</code>.
	 * 
	 * @param widget the widget to add
	 * @throws NullPointerException if the specified widget is null
	 * @throws IllegalArgumentException if the specified widget or one of its children
	 *             is already connected to a {@link Panel}
	 */
	public void add(Widget widget) {
		addAt(widget, MWT.CENTER);
	}

	/**
	 * Adds the specified widget at the specified position in the composite. If there is already a widget at this
	 * position, it is removed from the composite.
	 * @param widget
	 *            the widget to add
	 * @param position
	 *            the position in the composite
	 * @throws NullPointerException
	 *             if the specified widget is null
	 * @throws IllegalArgumentException
	 *             if the specified widget or one of its children is already connected to a {@link Panel}
	 * @throws IllegalArgumentException
	 *             if the specified position is not one of {@link MWT#NORTH}, {@link MWT#SOUTH}, {@link MWT#WEST},
	 *             {@link MWT#EAST}, {@link MWT#CENTER}
	 */
	public void addAt(Widget widget, int position) {
		//FIXME synchronize
		//check position to avoid unstable behavior
		position = getInternalFromPosition(position); //could throw an IllegalArgumentException

		Widget[] widgets = getWidgets();

		int oldIndex = widgetPlaces[position];
		if(oldIndex != NO_WIDGET) {
			Widget oldWidget = widgets[oldIndex];
			//reset array (if widget is null, the index will not be updated)
			widgetPlaces[position] = NO_WIDGET;
			//remove old widget
			remove(oldWidget);
		}

		int place = getWidgetsCount();
		super.add((Widget) widget); // could throw a NullPointerException or a IllegalArgumentException
		//the position of the newly added widget is the end of the previous componentViews array
		widgetPlaces[position] = place;
	}

	/**
	 * @param widget
	 *            the widget to remove
	 * @throws NullPointerException
	 *             if the specified widget is null
	 */
	public void remove(Widget widget) {
		//FIXME synchronize
		Widget[] widgets = getWidgets();
		int index = NO_WIDGET;
		//search the widget index
		int widgetsLength = widgets.length; //max: POSITIONS_COUNT
		for(int i = -1; ++i < widgetsLength;) {
			if(widgets[i] == widget) {
				index = i;
				break;
			}
		}
		if (index == NO_WIDGET) {
			return;//not in the hierarchy
		}
		removeInternal(widget, index);
	}

	/**
	 * @deprecated use {@link #remove(Widget)}
	 */
	public void removeWidget(Widget widget) {
		remove(widget);
	}

	/**
	 * <p>
	 * Removes the widget at the specified position from the list of children of this composite.<br>
	 * If there is no widget at this position in the composite, nothing is done.
	 * </p>
	 * @param position
	 *            the position of the widget to remove
	 */
	public void removeAt(int position) {
		int place = widgetPlaces[position];
		if (place == NO_WIDGET) {
			return;// nothing at this position
		}
		Widget[] widgets = getWidgets();
		Widget widget = widgets[place];
		removeInternal(widget, place);
	}

	/**
	 * @deprecated use {@link #removeAt(int)}
	 */
	public void removeWidget(int position) {
		removeAt(position);
	}

	/**
	 */
	public void removeAllWidgets() {
		//FIXME synchronize
		super.removeAllWidgets();
		//reset all places
		reset();
	}

	/**
	 */
	public boolean requestFocus(int direction) {
		int widgetsCount = getWidgetsCount();
		if (widgetsCount == 0 || !isEnabled()) {
			return false;
		}
		int internPlace;
		boolean horizontally;
		boolean forward;
		switch (direction) {
		case MWT.RIGHT:
			internPlace = WEST_INTERN;
			horizontally = true;
			forward = true;
			break;
		case MWT.DOWN:
			internPlace = NORTH_INTERN;
			horizontally = false;
			forward = true;
			break;
		case MWT.LEFT:
			internPlace = EAST_INTERN;
			horizontally = true;
			forward = false;
			break;
		case MWT.UP:
			internPlace = SOUTH_INTERN;
			horizontally = false;
			forward = false;
			break;
		default:
			return super.requestFocus(direction);
		}
		int place;
		boolean horizontal = this.horizontal;
		do {
			place = widgetPlaces[internPlace];
			if(place != MWT.EMPTY) {
				return requestFocusFrom(place, direction);
			}
			if(forward) {
				internPlace = getNext(horizontal, horizontally, internPlace);
			} else {
				internPlace = getPrevious(horizontal, horizontally, internPlace);
			}
		} while (internPlace != MWT.EMPTY);
		return false;
	}

	/**
	 * @since 1.0
	 */
	public int getNext(int from, int direction) {
		boolean horizontal = this.horizontal;
		int internalPlace = getInternalFromPlace(from);
		switch (direction) {
		case MWT.UP:
		case MWT.LEFT:
			internalPlace = getPrevious(horizontal, direction == MWT.LEFT, internalPlace);
			break;
		case MWT.DOWN:
		case MWT.RIGHT:
			internalPlace = getNext(horizontal, direction == MWT.RIGHT, internalPlace);
			break;
		default:
			// invalid direction
			throw new IllegalArgumentException();
		}
		return (internalPlace == MWT.EMPTY) ? MWT.EMPTY : widgetPlaces[internalPlace];
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected final boolean horizontal;

	// default value when no widget at a #widgetsPlaces position
	private static final int NO_WIDGET = -1;
	// numbers of positions in #widgetsPlaces
	private static final int POSITIONS_COUNT = 5;
	// positions in #widgetsPlaces
	private static final int NORTH_INTERN = 0;
	private static final int SOUTH_INTERN = 1;
	private static final int WEST_INTERN = 2;
	private static final int EAST_INTERN = 3;
	private static final int CENTER_INTERN = 4;

	// used to retrieve index in content array corresponding to a position in the BorderLayout
	private int[] widgetPlaces;

	/**
	 * Cleans the {@link #widgetPlaces} array: all will be set to {@link #NO_WIDGET}.
	 */
	private void reset() {
		//reset all places
		int[] places = this.widgetPlaces;
		for(int i = POSITIONS_COUNT; --i >= 0;) {
			places[i] = NO_WIDGET;
		}
	}

	/**
	 * Removes a widget from this composite:
	 * <ol>
	 * <li>removes from {@link CompositeView} children,</li>
	 * <li>sets matching value in {@link #widgetPlaces} to {@link #NO_WIDGET},</li>
	 * </ol>
	 */
	private void removeInternal(Widget widget, int widgetPlace) {
		int[] places = this.widgetPlaces;
		// reset the widget place
		// and update the ones that have been added after
		for (int i = POSITIONS_COUNT; --i >= 0;) {
			int place = places[i];
			if (place == widgetPlace) {
				places[i] = NO_WIDGET;
			} else if (place > widgetPlace) {
				places[i] = place - 1;
			}
		}
		// FIXME check place not found: internal error
		super.remove(widget);
	}

	/**
	 * Gets the {@link #widgetPlaces} index matching the given position (one of {@link MWT#NORTH}, {@link MWT#SOUTH},
	 * {@link MWT#WEST}, {@link MWT#EAST}, {@link MWT#CENTER}).<br>
	 * If the position is erroneous throw an {@link IllegalArgumentException}.
	 */
	private int getInternalFromPosition(int position) {
		switch(position){
		case MWT.NORTH:
			return NORTH_INTERN;
		case MWT.SOUTH:
			return SOUTH_INTERN;
		case MWT.WEST:
			return WEST_INTERN;
		case MWT.EAST:
			return EAST_INTERN;
		case MWT.CENTER:
			return CENTER_INTERN;
		default :
			throw new IllegalArgumentException("Position corrupted"); // NON-NLS
		}
	}

	/**
	 * Gets the {@link #widgetPlaces} index matching the given place in the {@link CompositeView#componentViews} array.<br>
	 * The place must be valid, otherwise it returns NO_WIDGET
	 */
	private int getInternalFromPlace(int place) {
		int[] places = this.widgetPlaces;
		for (int i = POSITIONS_COUNT; --i >= 0;) {
			if (places[i] == place) {
				return i;
			}
		}
		// not found: should not occur
		return NO_WIDGET;
	}

	/**
	 * Gets the next widget.
	 */
	private int getNext(boolean horizontal, boolean horizontally, int internalPlace) {
		switch (internalPlace) {
		case NORTH_INTERN:
			return horizontally ? (horizontal ? MWT.EMPTY : EAST_INTERN) : CENTER_INTERN;
		case SOUTH_INTERN:
			return (!horizontal && horizontally) ? EAST_INTERN : MWT.EMPTY;
		case WEST_INTERN:
			return horizontally ? CENTER_INTERN : (horizontal ? SOUTH_INTERN : MWT.EMPTY);
		case EAST_INTERN:
			return (horizontal && !horizontally) ? SOUTH_INTERN : MWT.EMPTY;
		case CENTER_INTERN:
			return horizontally ? EAST_INTERN : SOUTH_INTERN;
		default:
			throw new IllegalArgumentException("Position corrupted"); // NON-NLS
		}
	}

	/**
	 * Gets the previous widget.
	 */
	private int getPrevious(boolean horizontal, boolean horizontally, int internalPlace) {
		switch (internalPlace) {
		case NORTH_INTERN:
			return (!horizontal && horizontally) ? WEST_INTERN : MWT.EMPTY;
		case SOUTH_INTERN:
			return horizontally ? (horizontal ? MWT.EMPTY : WEST_INTERN) : CENTER_INTERN;
		case WEST_INTERN:
			return (horizontal && !horizontally) ? NORTH_INTERN : MWT.EMPTY;
		case EAST_INTERN:
			return horizontally ? CENTER_INTERN : (horizontal ? NORTH_INTERN : MWT.EMPTY);
		case CENTER_INTERN:
			return horizontally ? WEST_INTERN : NORTH_INTERN;
		default:
			throw new IllegalArgumentException("Position corrupted"); // NON-NLS
		}
	}

	private class Rectangle {
		int top;
		int bottom;
		int left;
		int right;

		public Rectangle(int top, int bottom, int left, int right) {
			super();
			this.top = top;
			this.bottom = bottom;
			this.left = left;
			this.right = right;
		}
	}

	private class Sizes {
		int remainingWidth;
		int remainingHeight;
		int maxWidth;
		int maxHeight;
		int centerSumWidth;
		int centerSumHeight;

		public Sizes(int remainingWidth, int remainingHeight, int maxWidth, int maxHeight, int centerSumWidth, int centerSumHeight) {
			super();
			this.remainingWidth = remainingWidth;
			this.remainingHeight = remainingHeight;
			this.maxWidth = maxWidth;
			this.maxHeight = maxHeight;
			this.centerSumWidth = centerSumWidth;
			this.centerSumHeight = centerSumHeight;
		}
	}

	private class WidgetInfos {
		int margin;
		int width;
		int height;

		public WidgetInfos(int margin, int width, int height) {
			super();
			this.margin = margin;
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * Validates one widget of the border composite (except the one at {@link MWT#CENTER}).
	 */
	private WidgetInfos validate(Widget widget, Sizes sizes, boolean horizontal, boolean computeWidth, boolean computeHeight,
 boolean allSizeWidget) {
		if (widget == null) {
			return null;
		}
		// get widget renderer and margin
		Renderer firstRenderer = widget.getRenderer();
		int wMargin = (firstRenderer != null) ? firstRenderer.getMargin() : 0;

		boolean fillWidth = !(horizontal ^ allSizeWidget);

		int remainingWidth = sizes.remainingWidth;
		int remainingHeight = sizes.remainingHeight;
		int wWidthHint = computeWidth || !fillWidth ? MWT.NONE : remainingWidth - (wMargin << 1);
		int wHeightHint = computeHeight || fillWidth ? MWT.NONE : remainingHeight - (wMargin << 1);

		widget.validate(wWidthHint, wHeightHint);
		int wWidth = widget.getPreferredWidth();
		int wHeight = widget.getPreferredHeight();

		if (fillWidth) {
			// the widget fill the remaining width
			int totalHeight = wHeight + (wMargin << 1);
			if (!computeHeight) {
				// reduce free space
				sizes.remainingHeight = remainingHeight - totalHeight;
			} else {
				// compute total space
				if (allSizeWidget) {
					sizes.maxHeight = Math.max(sizes.maxHeight, totalHeight);
				}
				sizes.centerSumHeight += totalHeight;
			}
		} else {
			// the widget fill the remaining height
			int totalWidth = wWidth + (wMargin << 1);
			if (!computeWidth) {
				// reduce free space
				sizes.remainingWidth = remainingWidth - totalWidth;
			} else {
				// compute total space
				if (allSizeWidget) {
					sizes.maxWidth = Math.max(sizes.maxWidth, totalWidth);
				}
				sizes.centerSumWidth += totalWidth;
			}
		}
		return new WidgetInfos(wMargin, wWidth, wHeight);
	}

	/**
	 * Sets the bounds of one widget of the border composite (except the one at {@link MWT#CENTER}).
	 */
	private void place(Widget widget, WidgetInfos widgetInfos, Rectangle rectangle, boolean horizontal, boolean allSizeWidget,
			boolean topOrLeft) {
		if (widget == null) {
			return;
		}
		int wWidth;
		int wHeight;
		int wMargin = widgetInfos.margin;
		int top = rectangle.top;
		int bottom = rectangle.bottom;
		int left = rectangle.left;
		int right = rectangle.right;
		if (!(horizontal ^ allSizeWidget)) {
			// the widget fill the remaining width
			wWidth = right - left - (wMargin << 1);
			wHeight = widgetInfos.height;
			// remove height to remaining rectangle
			if (topOrLeft) {
				rectangle.top = top + wHeight + (wMargin << 1);
			} else {
				rectangle.bottom = bottom - wHeight - (wMargin << 1);
			}
		} else {
			// the widget fill the remaining height
			wWidth = widgetInfos.width;
			wHeight = bottom - top - (wMargin << 1);
			// remove width to remaining rectangle
			if (topOrLeft) {
				rectangle.left = left + wWidth + (wMargin << 1);
			} else {
				rectangle.right = right - wWidth - (wMargin << 1);
			}
		}
		// compute widget anchor
		int wLeft;
		int wTop;
		if (topOrLeft) {
			wLeft = left + wMargin;
			wTop = top + wMargin;
		} else {
			wLeft = right - wWidth - wMargin;
			wTop = bottom - wHeight - wMargin;
		}
		// set widget bounds
		widget.setBounds(wLeft, wTop, wWidth, wHeight);
	}

}
