/*
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.tiny;

import java.util.ArrayList;
import java.util.List;

import com.is2t.mwt.util.ListenerAdapter;
import com.is2t.mwt.widgets.IToggle;

public class ButtonGroup {

	private List<IToggle> toggles;

	public ButtonGroup() {
		toggles = new ArrayList<IToggle>();
	}

	public void add(final IToggle toggle) {
		toggles.add(toggle);
		toggle.setGroupListener(new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				// Search the toggle in the group that currently holds the selection.
				for (IToggle toggle : toggles) {
					if (toggle.isSelected() && toggle != object) {
						toggle.setSelected(false);
					}
				}
			}
		});
	}

	public void remove(IToggle toggle) {
		toggles.remove(toggle);
		toggle.setGroupListener(null);
	}
}