package main.client.Windows;

import com.google.gwt.user.client.ui.HTML;

public class SettingsTab extends GwtWindow {

	/**
	 * Default constructor
	 */
	public SettingsTab() {
		
		super();
	}
	
	/**
	 * Creates the contents of the settings tab
	 */
	public boolean create() {
	
		add(new HTML("Settings"));
		return true;
	}
}
