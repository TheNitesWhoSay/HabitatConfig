package main.client.Windows;

import main.client.HabitatConfig;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public ConfigTab(final HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
	
		return true;
	}
	
}
