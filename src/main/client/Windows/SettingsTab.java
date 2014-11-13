package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.user.client.ui.HTML;

public class SettingsTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public SettingsTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the settings tab
	 */
	public boolean create() {
	
		add(new HTML("Settings"));
		return true;
	}
}
