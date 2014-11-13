package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.user.client.ui.HTML;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public ConfigTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
	
		add(new HTML("Config"));
		return true;
	}
}
