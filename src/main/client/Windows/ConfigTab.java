package main.client.Windows;

import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import main.client.HabitatConfig;
import main.client.Data.ConfigGenerator;
import main.client.Data.Configuration;
import main.client.Data.LandingGrid;
import main.client.Data.Module;
import main.client.Data.ModuleTypes.MODULE_TYPE;

public class ConfigTab extends GwtWindow {

	private HabitatConfig root;
	private ScrollPanel configPanel; //Panel of configurations available
	private VerticalPanel controls;
	RadioButton mincon1;
	Button configSave = new Button("Save Configuration");
	Button configLoad = new Button("Load Configuration");
	private ConfigGenerator configList;
	public static RadioButton mincon2;
	private ListBox availBox; //Listbox of possible options
	protected Storage configStore;
	protected LandingGrid cList;
	protected String configListKey;
	public static Grid configGrid;
	LinkedList<MODULE_TYPE> onMap = new LinkedList<MODULE_TYPE>();
	private String moduleListKey = "configList";
	/**
	 * Default constructor
	 */
	public ConfigTab(final HabitatConfig root) {
		
		super();
		this.root = root;
		
	}
	
	public void drawModule(MODULE_TYPE type, int x, int y) {
		Image image = getImage(type);
		if ( image != null ) {
			
			image.setSize("10px", "10px");
		    configGrid.setWidget(50-y, x-1, image);
		    onMap.add(type);
		}
	}
	
	public Image getImage(MODULE_TYPE type) {
		
		Image imag = null;
		if ( type != null )
		{	
			switch ( type )
			{
				case Airlock: imag = new Image("images/Airlock.jpg"); break;
				case Plain: imag = new Image("images/Plain.jpg"); break;
				case Dormitory:	imag = new Image("images/Dormitory.jpg"); break;
				case Sanitation: imag = new Image("images/Sanitation.jpg"); break;
				case FoodAndWater: imag = new Image("images/Food.jpg"); break;
				case GymAndRelaxation: imag = new Image("images/Gym.jpg"); break;
				case Canteen: imag = new Image("images/Canteen.jpg"); break;
				case Power: imag = new Image("images/Power.jpg"); break;
				case Control: imag = new Image("images/Control.jpg"); break;
				case Medical: imag = new Image("images/Medical.jpg"); break;
				default: imag = null; break;
			}
		}
		return imag;
	}
	
	public void renderConfig(Configuration config) {
		onMap.clear();
		configGrid.clear();
		for ( int y=0; y<config.getDepth(); y++ ) {
			
			for ( int x=0; x<config.getWidth(); x++ ) {
				
				MODULE_TYPE type = config.getFutureModule(x, y);
				drawModule(type, x, y);
				onMap.add(type);
			}
		}
	}
	
	public void saveConfig(){
		configStore = Storage.getLocalStorageIfSupported();
		if(configStore!=null){
			configStore.setItem(configListKey, configString());
		}
	}
	private String configString() {
		return "Store";
		/**
		String returnString = "{ ";
		for(int i = 0; i < onMap.size(); i++){
			
			if(onMap.get(i).equals(MODULE_TYPE.Airlock)){
				returnString += "Air, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Canteen)){
				returnString += "Can, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Control)){
				returnString += "Con, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Dormitory)){
				returnString +="Dorm, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.FoodAndWater)){
				returnString +="Food, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.GymAndRelaxation)){
				returnString +="Gym, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Medical)){
				returnString +="Med, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Plain)){
				returnString +="Plain, ";
			}
			else if(onMap.get(i).equals(MODULE_TYPE.Power)){
				returnString +="Power, ";
			}
			
		}
		returnString += "}";
		return returnString;
		*/
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
		
		configGrid.setCellPadding(0);
		configGrid.setBorderWidth(0);
		configGrid.setCellSpacing(0);
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 100; j++) {
				if(i>=40 && i<=50 && j>=40 && j<=50){
					configGrid.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
				}
			}
		}

		
		configGrid.addStyleName("landingStyle");
		//configGrid.setSize("700px", "1000px");
		// Proabably want to convert this to generation on load rather than on button click...

		final Button generate = new Button("Generate Configurations");
		generate.addClickHandler(new ClickHandler() {
			
			public void onClick(final ClickEvent event) {
				
				ConfigGenerator generator = root.configGenerator;
				LandingGrid landingGrid = root.landingGrid;
				
				if ( generator.GenerateTwoMinimumConfigs(landingGrid) ) {
					
					LinkedList<Configuration> configs = generator.getMinimumConfigs();
					
					if( mincon1.getValue() && configs.size() > 0 )
						renderConfig(configs.get(0));
					else if ( mincon2.getValue() && configs.size() > 1 )
						renderConfig(configs.get(1));
					else
						configGrid.clear();
				}
				else
					Window.alert("Config Generation Failed");
			}
		});

		mincon1 = new RadioButton("Min","Minimum Configuration 1");
		mincon2 = new RadioButton("Min","Minimum Configuration 2");
		controls.add(mincon1);
		controls.add(mincon2);
		controls.add(generate);
		controls.add(configSave);
		add(controls);
		add(configGrid);
		return true;
	}

	
}
