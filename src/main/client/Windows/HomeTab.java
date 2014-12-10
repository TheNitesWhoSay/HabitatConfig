package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.HTML;

public class HomeTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	@SuppressWarnings("unused")
	private JSONValue city; // Zipcode of user based on geolocation data
	/**
	 * Default constructor
	 */
	public HomeTab(final HabitatConfig root) {
		
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
