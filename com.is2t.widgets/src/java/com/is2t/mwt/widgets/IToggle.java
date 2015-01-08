/*
 * Java
 *
 * Copyright 2011-2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

import ej.microui.Listener;

public interface IToggle extends ILabel, IObservable {

	/**
	 * Indicates whether the toggle is selected or not.
	 * 
	 * @return <code>true</code> if the toggle is selected, <code>false</code> otherwise.
	 */
	boolean isSelected();
	
	void setSelected(boolean selected);
	
	void toggleSelection();
	
	void setGroupListener(Listener listener);
	
	Listener getGroupListener();
}
