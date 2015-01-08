/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

public interface ActivityListener {

	/**
	 * Called when the supervisor detects an inactivity.
	 */
	void inactive();

	/**
	 * Called when the supervisor detects an activity.
	 */
	void active();

}
