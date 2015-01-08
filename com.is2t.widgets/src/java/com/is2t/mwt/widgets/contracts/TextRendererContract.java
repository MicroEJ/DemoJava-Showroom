/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.contracts;

import com.is2t.mwt.widgets.IText;

/**
 * The contract that must be offered by any renderer that wishes to render {@link IText} widgets.
 */
public interface TextRendererContract {

	/**
	 * Get the index into the text held by the widget implied by a pointer position of the specified
	 * x and y. The returned value should be in the range <code>-1</code> to <code>length-1</code>
	 * of {@link IText#getText()}
	 * 
	 * @param textField
	 *            the {@link IText} widget
	 * @param x
	 *            the x position, relative to the widget bounds, of the pointer
	 * @param y
	 *            the y position, relative to the widget bounds, of the pointer
	 * @return the selection index
	 */
	public int getCaret(IText textField, int x, int y);

}
