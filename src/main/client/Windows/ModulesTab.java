package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

public class ModulesTab extends GwtWindow  {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public ModulesTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the modules tab
	 */
	protected boolean create() {
	
		add(new HTML("Modules"));
		Window.alert("Grant made change");
		return true;
	}
}
