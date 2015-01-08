/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.events.internal;

public class WeakReference<T> extends java.lang.ref.WeakReference<T> implements Reference<T> {

	public WeakReference(T referent) {
		super(referent);
	}

	@Override
	public String toString() {
		try {
			return get().toString();
		} catch (NullPointerException e) {
			return super.toString();
		}
	}

}
