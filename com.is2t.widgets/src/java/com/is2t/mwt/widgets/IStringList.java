/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

public interface IStringList extends IWidget, IObservable {

	void addItem(String item);

	void addItems(String[] items);

	String[] getItems();

	int getNumberOfItems();

	void setSelection(int index) throws IllegalArgumentException;

	String getSelectedItem();

	int getSelection();

}
