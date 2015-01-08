/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

public interface IItemList extends IWidget {

	void addItem(IItem item);

	void addItems(IItem[] items);

	IItem[] getItems();

	int getSize();

	void select(int index) throws IllegalArgumentException;

	IItem getSelection();

}
