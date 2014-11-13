package main.client.Windows;

import com.google.gwt.user.client.ui.HTML;

public class CommunicationsTab extends GwtWindow {

	/**
	 * Default constructor
	 */
	public CommunicationsTab() {
		
		super();
	}
	
	/**
	 * Creates the contents of the communications tab
	 */
	public boolean create() {
	
		add(new HTML("Communications"));
		return true;
	}
}
