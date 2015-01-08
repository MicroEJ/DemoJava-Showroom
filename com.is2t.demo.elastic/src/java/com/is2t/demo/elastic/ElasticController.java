/*
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.elastic;

import ej.microui.Event;
import ej.microui.Listener;
import ej.microui.Model;
import ej.microui.io.Buttons;
import ej.microui.io.Pointer;

public class ElasticController extends Model implements Listener {

	private ElasticModel elasticModel;

	public ElasticController(ElasticModel elasticModel) {
		this.elasticModel = elasticModel;

	}

	public void performAction(int value) {
		int type = Event.getType(value);
		if (type == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(value);

			if (elasticModel.getHomeButton().handleEvent(value)) {
				return;
			}

			// depending on the event, add or remove a link
			if (Buttons.isPressed(value)) {
				elasticModel.addLink(pointer);
			} else if (Buttons.isReleased(value)) {
				elasticModel.removeLink(pointer);
			}
		}
	}

	public void performAction() {
	}

	public void performAction(int value, Object object) {
	}

}
