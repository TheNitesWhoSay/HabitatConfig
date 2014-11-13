package main.client.Windows;

import com.google.gwt.user.client.ui.HTML;

public class HomeTab extends GwtWindow {

	/**
	 * Default constructor
	 */
	public HomeTab() {
		
		super();
	}
	
	/**
	 * Creates the contents of the home tab
	 */
	protected boolean create() {
	
		add(new HTML("Home"));
		return true;
	}
}
