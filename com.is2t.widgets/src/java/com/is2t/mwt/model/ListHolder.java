/**
 * Java
 * Copyright 2009-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.model;

import ej.microui.Model;
import ej.mwt.MWT;

/**
 * Holds a list of objects with a selection. Designed to be used as a model for widgets. Notifies
 * its listeners when the selection changes.
 */
public class ListHolder extends Model {

	/**
	 * Creates a new list holder.<br>
	 */
	public ListHolder() {
		removeAll();
	}

	/**
	 * Adds an item in the list.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addItem(String item) {
		String[] tItems = this.items;
		int ntItems = tItems.length;
		System.arraycopy(tItems, 0, tItems = new String[ntItems + 1], 0, ntItems);
		tItems[ntItems] = item;
		this.items = tItems;
	}

	/**
	 * Adds items in the list.
	 *
	 * @param items
	 *            the items to add
	 */
	public void addItems(String[] items) {
		String[] tItems = this.items;
		int ntItems = tItems.length;
		int nItems = items.length;
		System.arraycopy(tItems, 0, tItems = new String[ntItems + nItems], 0, ntItems);
		System.arraycopy(items, 0, tItems, ntItems, nItems);
		this.items = tItems;
	}

	public void removeAll() {
		items = new String[0];
		currentSelection = MWT.EMPTY;
	}

	/**
	 * Gets all the items of the list.
	 *
	 * @return the items
	 */
	public String[] getItems() {
		String[] items = this.items;
		int itemsLength = items.length;
		String[] result = new String[itemsLength];
		System.arraycopy(items, 0, result, 0, itemsLength);
		return result;
	}

	/**
	 * Gets the number of items of the list.
	 *
	 * @return the number of items
	 */
	public int getNumberOfItems() {
		return items.length;
	}

	/**
	 * Selects the item at the specified index. If the given index is not valid, nothing is done.
	 *
	 * @param index
	 *            the selection index to set
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the index is invalid.
	 */
	public void setSelection(int index) throws ArrayIndexOutOfBoundsException {
		checkBounds(index);
		if (currentSelection != index) {
			currentSelection = (short) index;
			changed();
		}
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item or <code>null</code> if none
	 */
	public String getSelectedItem() {
		try {
			return items[currentSelection];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Gets the selected item index.
	 *
	 * @return the selected item index or {@link MWT#EMPTY} if none
	 */
	public int getSelection() {
		return currentSelection;
	}

	/**
	 * Checks whether the given index is a valid index of the list:
	 * <ul>
	 * <li>greater or equals than <code>0</code></li>
	 * <li>lower than {@link #getNumberOfItems()}</li>
	 * </ul>
	 *
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the index is invalid.
	 */
	public void checkBounds(int index) throws ArrayIndexOutOfBoundsException {
		if (!isInBounds(index)) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	/**
	 * Gets whether the given index is a valid index of the list:
	 * <ul>
	 * <li>greater or equals than <code>0</code></li>
	 * <li>lower than {@link #getNumberOfItems()}</li>
	 * </ul>
	 *
	 * @return <code>true</code> if the index is valid, <code>false</code> otherwise.
	 */
	public boolean isInBounds(int index) {
		return index >= 0 && index < items.length;
	}

	/****************************************************************
	 * NOT IN API
	 ****************************************************************/

	protected String[] items;
	protected short currentSelection;

}
