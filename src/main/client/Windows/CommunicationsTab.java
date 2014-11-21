package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.user.client.ui.HTML;

public class CommunicationsTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public CommunicationsTab(final HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the communications tab
	 */
	public boolean create() {
	
		add(new HTML("Communications"));
		return true;
	}
}
