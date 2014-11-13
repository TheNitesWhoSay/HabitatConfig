package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the login window for the habitat configuration software
 */
public class LoginWindow extends GwtWindow {
	
	private HabitatConfig root; // A reference to the root class
	
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public LoginWindow(HabitatConfig root) {
		
		this.root = root;
	}
	
	/**
	 * This method will create the login window
	 * @return true if window creation was successful
	 */
	protected boolean create() {
		
		// Create login window...
		
	    final Button login = new Button("Login");
		login.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				root.mainWindow.show(RootPanel.get());
			}
		});
		    
		add(login);
		return true;
	}
}
