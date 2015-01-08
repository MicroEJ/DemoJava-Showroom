/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.utilities.activity;

import com.is2t.debug.CPULoad;
import com.is2t.debug.Framerate;

/**
 * Implementation that uses the BSP debug library.<br>
 * Native support in the BSP is needed to use this implementation.
 */
public class BSPDebugMonitoring implements Monitoring{

	private static final int FPS_SCHED_TIME = 1000;
	
	/**
	 * Set to true when the framerate is initialized with {@link Framerate#init(int)}.
	 * Must be done once.
	 */
	private static boolean FPSInitialized = false;
	
	public BSPDebugMonitoring(){
		initFPS();
	}
	
	synchronized private static void initFPS(){
		if(!FPSInitialized){
			Framerate.init(FPS_SCHED_TIME);
			FPSInitialized = true;
		}
	}

	@Override
	public int getCPULoad() {
		return CPULoad.get();
	}

	@Override
	public int getFPS() {
		return Framerate.get();
	}

}
