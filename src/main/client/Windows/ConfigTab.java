package main.client.Windows;

import com.google.gwt.user.client.ui.HTML;

public class ConfigTab extends GwtWindow {

	/**
	 * Default constructor
	 */
	public ConfigTab() {
		
		super();
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
	
		add(new HTML("Config"));
		return true;
	}
}
