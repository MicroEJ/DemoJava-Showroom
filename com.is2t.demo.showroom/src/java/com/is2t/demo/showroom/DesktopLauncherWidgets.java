/*
 * Java
 *
 * Copyright 2014 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.showroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.is2t.demo.launcher.Activity;
import com.is2t.demo.launcher.ActivityMetaData;
import com.is2t.demo.launcher.Background;
import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.showroom.common.Pictos;
import com.is2t.demo.showroom.weather.Day;
import com.is2t.demo.showroom.weather.WeatherPictos;
import com.is2t.demo.showroom.widgets.ActivityLauncher;
import com.is2t.demo.showroom.widgets.DateTime;
import com.is2t.demo.showroom.widgets.DesktopLauncherTheme;
import com.is2t.demo.showroom.widgets.LeftIconLabel;
import com.is2t.demo.showroom.widgets.Weather;
import com.is2t.demo.utilities.transition.SimpleTransitionDisplayable;
import com.is2t.demo.utilities.transition.TransitionDisplayable;
import com.is2t.demo.utilities.transition.TransitionDisplayableNo;
import com.is2t.layers.LayersManager;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.composites.GridComposite;
import com.is2t.mwt.util.ListenerAdapter;
import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.Picto.PictoSize;
import com.is2t.transition.MonitoringLayersManager;

import ej.bon.Timer;
import ej.microui.io.Display;
import ej.microui.io.DisplayFont;
import ej.microui.io.Displayable;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;

public class DesktopLauncherWidgets implements Launcher {

	private static final boolean ANIMATIONS_DISABLED = System
			.getProperty("com.is2t.demo.NoAnimation") != null;
	private static final int GRID_COLUMN_COUNT = 3;
	private static final int PICTO_FONT_ID = 91;

	private final Display display;
	private Desktop desktop;
	private LayersManager layersManager;

	private final List<Activity> activities;
	private final Map<Activity, ActivityMetaData> activityToMetaData;
	private DesktopLauncherTheme theme;
	private DateTime dateTime;
	private Weather weather;
	private boolean onBoot;
	private TransitionDisplayable transitionDisplayable;

	private Activity currentActivity;
	private List<ActivityLauncher> activityLaunchers;

	public DesktopLauncherWidgets(Display display, Timer timer) {
		this.display = display;
		this.activities = new ArrayList<Activity>();
		this.activityToMetaData = new HashMap<Activity, ActivityMetaData>();
		onBoot = true;

		if (ANIMATIONS_DISABLED) {
			transitionDisplayable = new TransitionDisplayableNo();
		} else {
			transitionDisplayable = new SimpleTransitionDisplayable(display,
					timer);
		}
	}

	@Override
	public void start() {
		this.theme = new DesktopLauncherTheme(this.display);
		MWT.RenderingContext.add(this.theme);
		this.desktop = new Desktop(this.display) {
			protected void showNotify() {
				super.showNotify();

				if (weather != null) {
					weather.showNotify();
				}

				if (dateTime != null) {
					dateTime.showNotify();
				}

				for (ActivityLauncher activityLauncher : activityLaunchers) {
					activityLauncher.showNotify();
				}
			}

			protected void hideNotify() {
				super.hideNotify();

				if (weather != null) {
					weather.hideNotify();
				}

				if (dateTime != null) {
					dateTime.hideNotify();
				}

				for (ActivityLauncher activityLauncher : activityLaunchers) {
					activityLauncher.hideNotify();
				}
			}
		};
		Panel panel = new Panel();
		GridComposite gridComposite = new GridComposite(GRID_COLUMN_COUNT);
		DisplayFont pictoFont = DisplayFont.getFont(PICTO_FONT_ID, 40,
				DisplayFont.STYLE_PLAIN);
		this.activityLaunchers = new ArrayList<>();
		for (Activity activity : this.activities) {
			ActivityMetaData metaData = this.activityToMetaData.get(activity);
			ActivityLauncher activityLauncher = new ActivityLauncher(this,
					activity, metaData, pictoFont);
			this.activityLaunchers.add(activityLauncher);
			gridComposite.add(activityLauncher);
		}

		BorderComposite mainComposite = new BorderComposite();
		mainComposite.add(gridComposite);

		GridComposite northComposite = new GridComposite(3);
		LeftIconLabel is2tLabel = new LeftIconLabel("", new Picto(Pictos.IS2T,
				PictoSize.XSmall));
		northComposite.add(is2tLabel);

		dateTime = new DateTime();
		northComposite.add(dateTime);

		mainComposite.addAt(northComposite, MWT.NORTH);

		weather = createWeather();
		weather.setClockAppearListener(new ListenerAdapter() {

			@Override
			public void performAction() {
				dateTime.switchTo();
			}
		});
		mainComposite.addAt(weather, MWT.EAST);

		panel.setWidget(mainComposite);
		panel.show(this.desktop, true);

		if (this.currentActivity != null) {
			transitionDisplayable.setNewDisplayable(desktop);
			transitionDisplayable.setForward(true);
			transitionDisplayable.start();
			this.currentActivity.stop();
		} else {
			show();
		}

		MonitoringLayersManager monitoringLayersManager = new MonitoringLayersManager(
				display);
		MonitoringLayersManager.setLocation(display.getWidth()
				- MonitoringLayersManager.getWidth(), 0);
		MonitoringLayersManager.setMonitoringLook(theme.getLook());
		layersManager = monitoringLayersManager;
		layersManager.show();
		
		if (onBoot) {
			onBoot = false;
		}
	}

	private Weather createWeather() {
		Day monday = new Day("Monday", WeatherPictos.SUNNY, 19);
		Day tuesday = new Day("Tuesday", WeatherPictos.SUNNY, 17);
		Day wednesday = new Day("Wednesday", WeatherPictos.CLOUDY, 13);
		Day thursday = new Day("Thursday", WeatherPictos.RAINY, 10);
		Day friday = new Day("Friday", WeatherPictos.RAINY, 8);
		Day saturday = new Day("Saturday", WeatherPictos.SNOWY, 2);
		Day sunday = new Day("Sunday", WeatherPictos.CLOUDY, 10);
		return new Weather(WeatherPictos.FONT, new Day[] { monday, tuesday,
				wednesday, thursday, friday, saturday, sunday }, onBoot);
	}

	public void show() {
		this.desktop.show();
	}

	@Override
	public void pause() {
		stop();
	}

	@Override
	public void resume() {
		start();
	}

	@Override
	public void stop() {
		MWT.RenderingContext.remove(this.theme);
		layersManager.hide();
	}

	@Override
	public Displayable getDisplayable() {
		return this.desktop;
	}

	@Override
	public void launch(Activity activity) {
		pause();
		activity.start();
		this.currentActivity = activity;
		transitionDisplayable.setNewDisplayable(activity.getDisplayable());
		transitionDisplayable.setForward(false);
		transitionDisplayable.start();
	}

	@Override
	public void add(Activity activity, ActivityMetaData metaData) {
		this.activities.add(activity);
		this.activityToMetaData.put(activity, metaData);
	}

	@Override
	public void remove(Activity activity) {
		this.activities.remove(activity);
		this.activityToMetaData.remove(activity);
	}

	@Override
	public Display getDisplay() {
		return this.display;
	}

	@Override
	public void add(Background background) {
	}

	@Override
	public void remove(Background background) {
	}

	@Override
	public void setLauncher(Launcher launcher) {
		// Nothing to do.
	}

}
