package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class contains the tabbed interface,
 * and holds the individual tab windows
 */
public class MainWindow extends GwtWindow {

	private HabitatConfig root;
	
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public MainWindow(HabitatConfig root) {
		
		this.root = root;
	}
	
	/**
	 * This method will create the main window
	 * @return true if window creation was successful
	 */
	protected boolean create() {
		
		// Create the main window...
		
		final Button logout = new Button("Logout");
		logout.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				hide();
				root.loginWindow.show(RootPanel.get());
			}
		});
			
		add(logout);
		return true;
	}
}
