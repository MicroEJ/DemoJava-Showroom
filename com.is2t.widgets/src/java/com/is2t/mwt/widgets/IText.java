/**
 * Java
 *
 * Copyright 2011-2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets;

public interface IText extends ILabel, IObservable {

	void back();

	void insert(String text);

	void insert(char c);

	int getCaret();

	void setCaret(int caret);

	int getSelectionStart();

	int getSelectionEnd();

	void setSelection(int start, int end);

	String getSelection();

}
