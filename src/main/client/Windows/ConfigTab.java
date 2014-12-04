package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.ButtonGroup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import main.client.HabitatConfig;
import main.client.Data.ConfigGenerator;
import main.client.Data.Configuration;
import main.client.Data.LandingGrid;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	private ScrollPanel configPanel; //Panel of configurations available
	private VerticalPanel controls;
	RadioButton mincon1;
	public static RadioButton mincon2;
	private ListBox availBox; //Listbox of possible options
	public static Grid configGrid;
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
		controls = new VerticalPanel();
		configPanel = new ScrollPanel();
		//configPanel.setSize("275px", "500px");
		configPanel.setTitle("Configurations");
		availBox = new ListBox();
		availBox.addItem("Min Config");
		availBox.addItem("Min Config2");
		configGrid = new Grid(50, 100);
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 100; j++) {
				if(i>=40 && i<=50 && j>=40 && j<=50){
					configGrid.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
				}
			}
		}
		configGrid.setCellPadding(2);
		configGrid.setCellSpacing(2);
		configGrid.addStyleName("landingStyle");
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
						//Window.alert(config.getModulesString());
						if(mincon1.isChecked()){
							config.getModulesString();
							return;
						}
						else if(mincon2.isChecked()){
							configGrid.clear();
							config = i.next();
							config.getModulesString();
						}
					}
				}
				else
					Window.alert("Config Generation Failed");
			}
		});
		mincon1 = new RadioButton("Min","Minimun Configuration 1");
		mincon2 = new RadioButton("Min","Minimun configuration 2");
		controls.add(mincon1);
		controls.add(mincon2);
		controls.add(generate);
		add(controls);
		//configPanel.add(availBox);
		//add(configPanel);
		add(configGrid);
		return true;
	}
	
}
