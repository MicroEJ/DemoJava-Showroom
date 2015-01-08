/**
 * Java
 *
 * Copyright 2013-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import com.is2t.mwt.widgets.tiny.Label;

/**
 * A multi-lines label displays a multi-lines text.
 */
public class MultiLinesLabel extends Label {

	private int linesCount;

	/**
	 * Creates a multi lines label.
	 * 
	 * @param linesCount
	 *            the number of lines displayed
	 */
	public MultiLinesLabel(int linesCount) {
		this.linesCount = linesCount;
	}

	/**
	 * @return the lines count
	 */
	public int getLinesCount() {
		return linesCount;
	}

}
