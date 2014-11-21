package main.client;

import java.util.LinkedList;

import main.client.Data.LandingGrid;
import main.client.Windows.ConfigTab;
import main.client.Windows.LoginWindow;
import main.client.Windows.MainWindow;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The root (or main, or program) class for the Habitat Configuration Software
 * 
 * Subclasses may use a reference to this class to access various parts
 * of the program when responding to user events.
 * 
 * @author Marsellie
 */
public class HabitatConfig implements EntryPoint {
	
	public ConfigOptions configOptions;
	public MainWindow mainWindow;
	public LoginWindow loginWindow;
	public ConfigTab configTab;
	public LandingGrid landingGrid;
	public LinkedList<LandingGrid> configurations;
	
	/**
	 * Constructs the HabitatConfig program
	 */
	public HabitatConfig() {
		
	}
	
	/**
	 * This is the entry point method.
	 * 
	 * Starts the Habitat Configuration Software
	 */
	public void onModuleLoad() {
				
		// Call all constructors
		mainWindow = new MainWindow(this);
		loginWindow = new LoginWindow(this);
		configOptions = new ConfigOptions();
		landingGrid = new LandingGrid();
		
		// Load the program's configuration options
		configOptions.load();
		
		// Start the program
		if ( configOptions.loginRequired() )
			loginWindow.show(RootPanel.get());
		else
			mainWindow.show(RootPanel.get());
		
		// From here on out the program is message driven
	}
	
}
