/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.events.internal;

public class SimpleReference<T> implements Reference<T> {

	private T referent;

	public SimpleReference(T referent) {
		this.referent = referent;
	}

	@Override
	public T get() {
		return referent;
	}

	@Override
	public String toString() {
		return referent.toString();
	}

}
