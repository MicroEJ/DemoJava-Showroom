/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.contracts;

import ej.mwt.Widget;

/**
 * <p>
 * The contract that must be offered by any renderer that wishes to render widgets that
 * expect to be able to translate a pointer position into an "increase" ("next")
 * or a "decrease" ("previous") command.
 * </p>
 * <p>
 * Renderers implementing this contract can make no assumptions about the specific class
 * the widget that is making the request.
 * </p>
 */
public interface UpDownRendererContract {

	public static final int NO_OP = 0;
	public static final int DECREASE_COMMAND = 1;
	public static final int INCREASE_COMMAND = 2;
	
	/**
	 * Get the command implied by a pointer position of the specified x and y.
	 * The returned value should be one of the commands defined in {@link UpDownRendererContract}
	 * @param widget the widget making the enquiry
	 * @param x the x position, relative to the widget bounds, of the pointer
	 * @param y the y position, relative to the widget bounds, of the pointer
	 * @return the command, or {@link UpDownRendererContract#NO_OP} if nothing to do
	 */
	public int getCommand(Widget widget, int x, int y);

}
