/**
 * Java
 *
 * Copyright 2012 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.mwt.widgets.renderers.theme;

import com.is2t.mwt.widgets.keyboard.VKeyboardRenderer;
import com.is2t.mwt.widgets.renderers.ButtonRenderer;
import com.is2t.mwt.widgets.renderers.CalendarRenderer;
import com.is2t.mwt.widgets.renderers.CheckBoxRenderer;
import com.is2t.mwt.widgets.renderers.ComboRenderer;
import com.is2t.mwt.widgets.renderers.DialogRenderer;
import com.is2t.mwt.widgets.renderers.ImageAnimationRenderer;
import com.is2t.mwt.widgets.renderers.LabelRenderer;
import com.is2t.mwt.widgets.renderers.PanelRenderer;
import com.is2t.mwt.widgets.renderers.ProgressBarRenderer;
import com.is2t.mwt.widgets.renderers.RadioButtonRenderer;
import com.is2t.mwt.widgets.renderers.ScaleRenderer;
import com.is2t.mwt.widgets.renderers.TextFieldRenderer;
import com.is2t.mwt.widgets.renderers.util.Drawer;

import ej.mwt.rendering.Renderer;
import ej.mwt.rendering.Theme;

public abstract class WidgetsTheme extends Theme {

	protected void populate() {

		Drawer drawer = newDrawer();
		add(newButtonRenderer(drawer));
		add(newCalendarRenderer(drawer));
		add(newCheckBoxRenderer(drawer));
		add(newComboRenderer(drawer));
		add(newClockRenderer(drawer));
		add(newLabelRenderer(drawer));
		add(newPanelRenderer());
		add(newProgressBarRenderer(drawer));
		add(newRadioButtonRenderer(drawer));
		add(newScaleRenderer(drawer));
		add(newTextFieldRenderer(drawer));
		add(newVirtualKeyboardRenderer(drawer));
		add(newDialogRenderer(drawer));
		add(newImageAnimationRenderer(drawer));
	}

	protected Renderer newImageAnimationRenderer(Drawer drawer) {
		return new ImageAnimationRenderer(drawer);
	}

	protected Renderer newDialogRenderer(Drawer drawer) {
		return new DialogRenderer(drawer);
	}

	protected Renderer newVirtualKeyboardRenderer(Drawer drawer) {
		return new VKeyboardRenderer(drawer);
	}

	protected Renderer newScaleRenderer(Drawer drawer) {
		return new ScaleRenderer(drawer);
	}

	protected Renderer newTextFieldRenderer(Drawer drawer) {
		return new TextFieldRenderer(drawer);
	}

	protected Renderer newProgressBarRenderer(Drawer drawer) {
		return new ProgressBarRenderer(drawer);
	}

	protected Renderer newRadioButtonRenderer(Drawer drawer) {
		return new RadioButtonRenderer(drawer);
	}

	protected Renderer newPanelRenderer() {
		return new PanelRenderer();
	}

	protected Renderer newLabelRenderer(Drawer drawer) {
		return new LabelRenderer(drawer);
	}

	protected abstract Renderer newClockRenderer(Drawer drawer);

	protected Renderer newComboRenderer(Drawer drawer) {
		return new ComboRenderer(drawer);
	}

	protected Renderer newCheckBoxRenderer(Drawer drawer) {
		return new CheckBoxRenderer(drawer);
	}

	protected Renderer newCalendarRenderer(Drawer drawer) {
		return new CalendarRenderer(drawer);
	}

	protected Renderer newButtonRenderer(Drawer drawer) {
		return new ButtonRenderer(drawer);
	}

	protected abstract Drawer newDrawer();

}
