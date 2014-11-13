package main.client.Windows;

import com.google.gwt.user.client.ui.HTML;

public class ModulesTab extends GwtWindow  {

	/**
	 * Default constructor
	 */
	public ModulesTab() {
		
		super();
	}
	
	/**
	 * Creates the contents of the modules tab
	 */
	protected boolean create() {
	
		add(new HTML("Modules"));
		return true;
	}
}
