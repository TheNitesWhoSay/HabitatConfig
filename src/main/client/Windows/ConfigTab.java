package main.client.Windows;

import com.google.gwt.user.client.ui.ScrollPanel;

import main.client.HabitatConfig;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	private ScrollPanel configPanel; //Panel of configurations available
	
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
		configPanel = new ScrollPanel();
		configPanel.setSize("275px", "500px");
		configPanel.setTitle("Configurations");
		add(configPanel);
		return true;
	}
	
}
