/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities;

import ej.microui.Listener;

/**
 * Overrides this class to avoid creating empty methods when implementing {@link Listener}.
 */
public class ListenerAdapter implements Listener {

	@Override
	public void performAction() {
	}

	@Override
	public void performAction(int value) {
	}

	@Override
	public void performAction(int value, Object object) {
	}

}
