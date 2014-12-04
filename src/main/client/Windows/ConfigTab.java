package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import main.client.HabitatConfig;
import main.client.Data.ConfigGenerator;
import main.client.Data.Configuration;
import main.client.Data.LandingGrid;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	private ScrollPanel configPanel; //Panel of configurations available
	
	/**
	 * Default constructor
	 */
	public ConfigTab(final HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
		
		configPanel = new ScrollPanel();
		configPanel.setSize("275px", "500px");
		configPanel.setTitle("Configurations");
		
		// Proabably want to convert this to generation on load rather than on button click...
		
		final Button generate = new Button("Generate Configurations");
		generate.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				
				ConfigGenerator generator = root.configGenerator;
				LandingGrid landingGrid = root.landingGrid;
				
				if ( generator.GenerateTwoMinimumConfigs(landingGrid) ) {
					
					LinkedList<Configuration> configs = generator.getMinimumConfigs();
					ListIterator<Configuration> i = configs.listIterator();
					while ( i.hasNext() ) {
						
						Configuration config = i.next();
						Window.alert(config.getModulesString());
					}
				}
				else
					Window.alert("Config Generation Failed");
			}
		});
		configPanel.add(generate);
		add(configPanel);
		
		return true;
	}
	
}
