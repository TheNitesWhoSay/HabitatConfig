package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;

public class SettingsTab extends GwtWindow {

	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public SettingsTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the settings tab
	 */
	public boolean create() {
	
		final Button toggleLoginRequired = new Button("");
		if ( root.configOptions.loginRequired() )
			toggleLoginRequired.setText("Remove Login Requirement");
		else
			toggleLoginRequired.setText("Add Login Requirement");
		
		toggleLoginRequired.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				root.configOptions.setLoginRequired("mars", "12345", !root.configOptions.loginRequired());
				if ( root.configOptions.loginRequired() )
					toggleLoginRequired.setText("Remove Login Requirement");
				else
					toggleLoginRequired.setText("Add Login Requirement");
			}
		});
		add(new HTML("Settings"));
		add(toggleLoginRequired);
		return true;
	}
}
