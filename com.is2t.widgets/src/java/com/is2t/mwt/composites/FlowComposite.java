/**
 * Java
 * 
 * Copyright 2009-2013 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.composites;

import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.mwt.rendering.Renderer;

/**
 * A flow composite is a {@link Composite} that lays out its children in rows or columns.
 */
public class FlowComposite extends Composite {

	/**
	 * Lay out direction.
	 */
	protected final boolean horizontal;

	/**
	 * Creates a new flow composite.<br>
	 * Identical to calling {@link #FlowComposite(int)} with {@link MWT#HORIZONTAL} as direction.
	 */
	public FlowComposite() {
		this(MWT.HORIZONTAL);
	}

	/**
	 * Creates a new flow composite specifying the direction.<br>
	 * The direction can be {@link MWT#HORIZONTAL} or {@link MWT#VERTICAL}.
	 * Respectively, the children will be laid out in rows or in columns.
	 * 
	 * @param direction the direction of the layout priority
	 */
	public FlowComposite(int direction) {
		super();
		this.horizontal = (direction & MWT.VERTICAL) == 0;
	}

	/**
	 * <p>
	 * Lays out children in rows.
	 * </p>
	 * <p>
	 * If the width (resp. height) of the row (resp. column) is too small for all the components, it creates several lines (resp. columns). The size
	 * of each child is set to the child's preferred size.
	 * </p>
	 * <p>
	 * The parameters define the maximum size available for this composite, or {@link MWT#NONE} if there is no constraint. <br>
	 * After this call the preferred size will have been established.
	 * </p>
	 * 
	 * @param widthHint the width available for this widget or {@link MWT#NONE}
	 * @param heightHint the height available for this widget or {@link MWT#NONE}
	 */
	public void validate(int widthHint, int heightHint) {
		if (!isVisible()) {
			// optim: do not validate its hierarchy
			setPreferredSize(0, 0);
			return;
		}
		Widget[] contents = getWidgets();
		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;
		//get composite renderer and padding
		Renderer cRenderer = getRenderer();
		int cPadding = cRenderer != null ? cRenderer.getPadding() : 0;
		int currentX = cPadding;
		int currentY = cPadding;
		if (!computeWidth) {
			widthHint -= cPadding << 1;
		}
		if (!computeHeight) {
			heightHint -= cPadding << 1;
		}
		int maxWidth = 0;
		int maxHeight = 0;
		int currentMaxHeight = 0;
		int currentMaxWidth = 0;
		int length = contents.length;
		for (int i = -1; ++i < length;) {
			Widget widget = (Widget) contents[i];
			//get widget renderer and margin
			Renderer renderer = widget.getRenderer();
			int margin = renderer != null ? renderer.getMargin() : 0;
			int doubleMargin = margin << 1;
			int widgetWidthHint = computeWidth ? 0 : widthHint - doubleMargin;
			int widgetHeightHint = computeHeight ? 0 : heightHint - doubleMargin;
			//validate the widget
			if (this.horizontal) {
				widget.validate(widgetWidthHint, 0);
			} else {
				widget.validate(0, widgetHeightHint);
			}
			int wpw = widget.getPreferredWidth();
			int wph = widget.getPreferredHeight();
			//check reach bounds
			if (this.horizontal) {
				if (!computeWidth && currentX + wpw + doubleMargin > widthHint) {
					//create a new line
					currentX = cPadding;
					currentY += currentMaxHeight;
					currentMaxHeight = 0;
				}
			} else {
				if (!computeHeight && currentY + wph + doubleMargin > heightHint) {
					// create a new column
					currentY = cPadding;
					currentX += currentMaxWidth;
					currentMaxWidth = 0;
				}
			}
			//set widget bounds
			widget.setBounds(currentX + margin, currentY + margin, wpw, wph);
			if (this.horizontal) {
				//shift cx
				currentX += wpw + doubleMargin;
				//check width
				if (computeWidth && currentX + cPadding > maxWidth) {
					maxWidth = currentX + cPadding;
				}
				//check height
				if (wph + doubleMargin > currentMaxHeight) {
					currentMaxHeight = wph + doubleMargin;
				}
			} else {
				// shift cy
				currentY += wph + doubleMargin;
				//check width
				if (wpw + doubleMargin > currentMaxWidth) {
					currentMaxWidth = wpw + doubleMargin;
				}
				//check height
				if (computeHeight && currentY + cPadding > maxHeight) {
					maxHeight = currentY + cPadding;
				}
			}
		}
		if (this.horizontal) {
			setPreferredSize(computeWidth ? maxWidth : widthHint, currentY + currentMaxHeight + cPadding);
		} else {
			setPreferredSize(currentX + currentMaxWidth + cPadding, computeHeight ? maxHeight : heightHint);
		}
	}


}
