package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.user.client.ui.HTML;

public class HomeTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public HomeTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the home tab
	 */
	protected boolean create() {
	
		add(new HTML("Home"));
		return true;
	}
}
