/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.automaton;

/**
 * Defines an automaton that will simulate user interaction.
 */
public interface Automaton extends Runnable {

	/**
	 * Gets the period to schedule the automaton at.
	 * 
	 * @return the period.
	 */
	long getPeriod();

}
