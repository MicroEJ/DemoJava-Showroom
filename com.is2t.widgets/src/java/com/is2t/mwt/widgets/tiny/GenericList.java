/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import com.is2t.mwt.model.ListHolder;
import com.is2t.mwt.widgets.IStringList;

import ej.microui.Listener;
import ej.mwt.Widget;

/**
 * A list is a widget that allow to select an item in a list of items.
 */
public abstract class GenericList extends Widget implements IStringList, Listener {

	/**
	 * Creates a new list.<br>
	 * A list is enabled and transparent by default.<br>
	 */
	public GenericList() {
		model = new ListHolder();
		model.addListener(this);
	}

	/**
	 * Adds an item in the list.
	 * @param item the item to add
	 */
	public void addItem(String item) {
		model.addItem(item);
	}

	/**
	 * Adds items in the list.
	 * @param items the items to add
	 */
	public void addItems(String[] items) {
		model.addItems(items);
	}

	/**
	 * Gets all the items of the list.
	 * @return the items
	 */
	public String[] getItems() {
		return model.getItems();
	}

	/**
	 * Gets the number of items of the list.
	 * @return the number of items
	 */
	public int getNumberOfItems() {
		return model.getNumberOfItems();
	}

	/**
	 * Selects the item at the specified index.
	 * If the given index is not valid, nothing is done.
	 * @param index the selection index to set
	 * @return <code>true</code> if the index is valid, <code>false</code> otherwise.
	 */
	public void setSelection(int index) throws ArrayIndexOutOfBoundsException {
		model.setSelection(index);
		repaint();
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	public String getSelectedItem() {
		return model.getSelectedItem();
	}

	/**
	 * Gets the selected item index.
	 *
	 * @return the selected item index
	 */
	public int getSelection() {
		return model.getSelection();
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected ListHolder model;

	public void performAction() {
		repaint();
	}

	public void performAction(int value) {
	}

	public void performAction(int value, Object object) {
	}

}
