/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.transition;

import ej.components.BundleActivator;
import ej.components.Registry;
import ej.components.RegistryFactory;
import ej.flow.FlowManager;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.TransitionManager;

public class Activator implements BundleActivator {

	private MWTFlowManager mwtFlowManager;

	@Override
	public void initialize(String parameters) {
		mwtFlowManager = new MWTFlowManagerImpl();
		RegistryFactory.getRegistry().register(MWTFlowManager.class, mwtFlowManager);
	}

	@Override
	public void link(String parameters) {
		Registry registry = RegistryFactory.getRegistry();
		FlowManager flowManager = registry.getService(FlowManager.class);
		mwtFlowManager.setFlowManager(flowManager);
		TransitionManager transitionManager = registry.getService(TransitionManager.class);
		mwtFlowManager.setTransitionManager(transitionManager);
	}

	@Override
	public void start(String parameters) {
	}

	@Override
	public void stop(String parameters) {
	}

}
