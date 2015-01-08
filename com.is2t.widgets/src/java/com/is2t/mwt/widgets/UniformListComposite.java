/*
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.is2t.mwt.widgets;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.widgets.scroll.ScrollContent;

import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;


/**
 * A list composite is a {@link Composite} that lays out its children in a list.
 */
public class UniformListComposite extends Composite implements ScrollContent {

	private final boolean horizontal;
	private final List<Widget> childrenList;
	private int lastFirstVisible;

	public UniformListComposite(boolean horizontal) {
		this.horizontal = horizontal;
		this.childrenList = new ArrayList<>();
	}

	@Override
	public void add(Widget widget) {
		childrenList.add(widget);
	}

	private Widget[] getChildren() {
		return childrenList.toArray(new Widget[childrenList.size()]);
	}

	@Override
	public int getWidgetsCount() {
		return childrenList.size();
	}

	/**
	 * <p>
	 * Lays out children in a grid.
	 * </p>
	 * <p>
	 * The size of each child is set to the size of a grid cell.<br>
	 * </p>
	 * <p>
	 * The parameters defines the maximum size available for this composite, or {@link MWT#NONE} if there is no
	 * constraint. <br>
	 * After this call the preferred size will have been established.
	 * </p>
	 *
	 * @param widthHint
	 *            the width available for this widget or {@link MWT#NONE}
	 * @param heightHint
	 *            the height available for this widget or {@link MWT#NONE}
	 */
	@Override
	public void validate(int widthHint, int heightHint) {
		if (!isVisible()) {
			// optim: do not validate its hierarchy
			setPreferredSize(0, 0);
			return;
		}

		Widget[] contents = getChildren();
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

		// if size is not yet defined -> compute it !
		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;

		int maxCellWidth = widthHint;// computeWidth ? 0 : (widthHint - doubleCPadding) / columns;
		int maxCellHeight = heightHint;// computeHeight ? 0 : (heightHint - doubleCPadding) / rows;
		// int cellWidth = 0;
		// int cellHeight = 0;
		int totalCellWidth = 0;
		int totalCellHeight = 0;

		int widgetWidthHint = computeWidth ? 0 : maxCellWidth;
		int widgetHeightHint = computeHeight ? 0 : maxCellHeight;

		// keep an array of margins for the second pass
		int marginsTotal = 0;

		Widget widgetRef = contents[0];

		// get widget renderer and margin
		Renderer renderer = widgetRef.getRenderer();
		int margin;
		if (renderer == null) {
			margin = 0;
		} else {
			margin = renderer.getMargin();
			marginsTotal += margin * length;
		}
		int halfMargin = margin >> 1;

		widgetRef.validate(widgetWidthHint - margin, widgetHeightHint - margin);

		int widgetPreferredWidth = widgetRef.getPreferredWidth();
		int widgetPreferredHeight = widgetRef.getPreferredHeight();

		totalCellWidth += (widgetPreferredWidth + margin) * length;
		totalCellHeight += (widgetPreferredHeight + margin) * length;

		float xRatio = 1.0f;
		float yRatio = 1.0f;

		int marginMedian = marginsTotal / length;
		int halfMarginMedian = marginMedian >> 1;
		if (computeWidth) {
			widthHint = totalCellWidth + doubleCPadding;
			xRatio = 1.0f;
		} else if (horizontal) {
			xRatio = (float) (widthHint - doubleCPadding) / totalCellWidth;
			// System.out.println("width: " + totalCellWidth + " " + (widthHint - doubleCPadding));
		}

		if (computeHeight) {
			heightHint = totalCellHeight + doubleCPadding;
			yRatio = 1.0f;
		} else if (!horizontal) {
			yRatio = (float) (heightHint - doubleCPadding) / totalCellHeight;
			// System.out.println("height: " + totalCellHeight + " " + (heightHint - doubleCPadding));
		}

		// System.out.println("ratios: " + xRatio + " " + yRatio);

		int currentX = cPadding + halfMarginMedian;
		int currentY = cPadding + halfMarginMedian;
		if (horizontal) {
			int ww = (int) (widgetPreferredWidth * xRatio);
			currentY += halfMargin;
			for (int i = -1; ++i < length;) {
				Widget widget = contents[i];

				widget.setBounds(currentX + halfMargin, currentY, ww, heightHint);

				currentX += ww + margin;
			}
		} else {
			int wh = (int) (widgetPreferredHeight * yRatio);
			currentX += halfMargin;
			for (int i = -1; ++i < length;) {
				Widget widget = contents[i];

				widget.setBounds(currentX, currentY + halfMargin, widthHint, wh);

				currentY += wh + margin;
			}
		}

		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void updateViewport(int x, int y, int width, int height) {
		Widget[] oldVisibleChildren = getWidgets();
		int length = oldVisibleChildren.length;
		// remove no more visible items
		for (int i = -1; ++i < length;) {
			Widget child = oldVisibleChildren[i];
			int childX = child.getX();
			int childY = child.getY();
			if (horizontal ? (childX + x > width || childX + child.getWidth() < -x) : (childY + y > height || childY
					+ child.getHeight() < -y)) {
				remove(child);
			}
		}
		// add newly visible items
		int size = childrenList.size();
		// System.out.print("ListComposite.updateViewport()");
		// System.out.print(" x=" + x);
		// System.out.print(", y=" + y);
		// System.out.print(", width=" + width);
		// System.out.println(", height=" + height);
		// System.out.println("lastFirstVisible=" + lastFirstVisible);
		int firstVisible = getFirstVisible(x, y, width, height);
		// System.out.println("firstVisible=" + firstVisible);
		for (int i = firstVisible - 1; ++i < size;) {
			Widget child = childrenList.get(i);
			int childX = child.getX();
			int childY = child.getY();
			if (horizontal ? (childX + x > width) : (childY + y > height)) {
				break;
			} else if (horizontal ? (childX + child.getWidth() > -x) : (childY + child.getHeight() > -y)) {
				if (!contains(oldVisibleChildren, child)) {
					super.add(child);
				}
			}
		}
	}

	private int getFirstVisible(int x, int y, int width, int height) {
		int index = lastFirstVisible;
		Widget lastFirstVisibleChild = childrenList.get(index);
		if (horizontal) {
			int childX = lastFirstVisibleChild.getX();
			int childWidth = lastFirstVisibleChild.getWidth();
			boolean stillFirst = childX <= -x && childX + childWidth >= -x;
			if (!stillFirst) {
				boolean searchForward = childX + childWidth < -x;
				int size = childrenList.size();
				for (int i = lastFirstVisible; searchForward ? ++i < size : --i >= 0;) {
					Widget child = childrenList.get(i);
					int candidateX = child.getX();
					int candidateWidth = child.getWidth();
					if (searchForward ? candidateX <= -x : candidateX + candidateWidth >= -x) {
						lastFirstVisible = i;
						break;
					}
				}
			}
		} else {
			int childY = lastFirstVisibleChild.getY();
			int childHeight = lastFirstVisibleChild.getHeight();
			// System.out.println("childY=" + childY);
			// System.out.println("childHeight=" + childHeight);
			boolean stillFirst = childY <= -y && childY + childHeight >= -y;
			// System.out.println("childY <= -y=" + (childY <= -y));
			// System.out.println("childY + childHeight >= -y=" + (childY + childHeight >= -y));
			// System.out.println("stillFirst=" + stillFirst);
			if (!stillFirst) {
				boolean searchForward = childY + childHeight < -y;
				// System.out.println("searchForward=" + searchForward);
				int size = childrenList.size();
				int firstCandidate = searchForward ? size - 1 : 0;
				for (int i = lastFirstVisible; searchForward ? ++i < size : --i >= 0;) {
					Widget child = childrenList.get(i);
					int candidateY = child.getY();
					int candidateHeight = child.getHeight();
					// System.out.println("i=" + i);
					// System.out.println("candidateY=" + candidateY);
					// System.out.println("candidateHeight=" + candidateHeight);
					if (candidateY <= -y && candidateY + candidateHeight > -y) {
						firstCandidate = i;
						break;
					}
				}
				lastFirstVisible = firstCandidate;
			}
		}

		return lastFirstVisible;
	 }

	private boolean contains(Widget[] widgets, Widget widget) {
		for (Widget candidate : widgets) {
			if (candidate == widget) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void revalidate() {
		// DO NOT REVALIDATE
		repaint();
	}

}
