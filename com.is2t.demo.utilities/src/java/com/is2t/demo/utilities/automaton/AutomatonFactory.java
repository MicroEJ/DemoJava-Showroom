/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.automaton;

/**
 * Factory that creates automatons.
 */
public interface AutomatonFactory<R extends Automaton> {

	/**
	 * Creates a automaton.
	 * 
	 * @return a newly created automaton.
	 */
	R createAutomaton();

	/**
	 * Stops the automaton.
	 * 
	 * @param automaton
	 *            the automaton to stop.
	 */
	void stopAutomaton(R automaton);

}
