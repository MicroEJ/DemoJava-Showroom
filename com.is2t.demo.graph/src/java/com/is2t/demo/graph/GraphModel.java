/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.graph;

import ej.microui.Model;

public class GraphModel extends Model {

	public static final int NO_VALUE = -1;

	private final int max;
	private final int threshold;
	private int[] datas;

	public GraphModel(int datasCount, int threshold, int max) {
		this.threshold = threshold;
		this.max = max;
		datas = new int[datasCount];
		addData(NO_VALUE);
	}

	public int getThreshold() {
		return threshold;
	}

	public int getMax() {
		return max;
	}

	public void addData(int value) {
		System.arraycopy(datas, 1, datas, 0, datas.length - 1);
		datas[datas.length - 1] = value;
		changed(value);
	}

	public int[] getDatas() {
		return datas;
	}

	public int getDatasCount() {
		return datas.length;
	}

	public int getLastData() {
		return datas[datas.length - 1];
	}

}
