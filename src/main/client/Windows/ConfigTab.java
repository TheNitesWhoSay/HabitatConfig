package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import main.client.HabitatConfig;
import main.client.Data.Configuration;
import main.client.Data.LandingGrid;
import main.client.Data.Module;
import main.client.Data.ModuleTypes.MODULE_TYPE;

public class ConfigTab extends GwtWindow {

	private HabitatConfig root;
	private ScrollPanel configPanel; //Panel of configurations available
	private VerticalPanel controls;
	private Label rating;
	public static RadioButton mincon1;
	public static RadioButton mincon2;
	public static RadioButton maxcon1;
	public static RadioButton maxcon2;
	static TabPanel change = new TabPanel();
	Button configSave;
	static Grid beforeG = new Grid(50,100);
	//private ListBox availBox; //Listbox of possible options
	protected Storage configStore;
	protected LandingGrid cList;
	protected String configListKey;
	public static Grid configGrid;
	Configuration minConfigOne;
	Configuration minConfigTwo;
	Configuration maxConfigOne;
	Configuration maxConfigTwo;
	
	public void refreshTab() {
		
		if ( root.configGenerator.GenerateTwoMinimumConfigs(root.landingGrid) ) {
			
			LinkedList<Configuration> configs = root.configGenerator.getMinimumConfigs();
			if ( configs.size() > 0 )
				minConfigOne = configs.get(0);
			if ( configs.size() > 0 )
				minConfigTwo = configs.get(1);
		}
		if ( root.configGenerator.GenerateTwoMaximumConfigs(root.landingGrid) ) {

			LinkedList<Configuration> configs = root.configGenerator.getMaximumConfigs();
			if ( configs.size() > 0 )
				maxConfigOne = configs.get(0);
			if ( configs.size() > 0 )
				maxConfigTwo = configs.get(1);
		}
		
		mincon1.setValue(Boolean.FALSE);
		mincon2.setValue(Boolean.FALSE);
		maxcon1.setValue(Boolean.FALSE);
		maxcon2.setValue(Boolean.FALSE);
		rating.setText("Rating: ");
		configGrid.clear();
	}
	
	/**
	 * Default constructor
	 */
	public ConfigTab(final HabitatConfig root) {
		
		super();
		this.root = root;
		minConfigOne = null;
		minConfigTwo = null;
		maxConfigOne = null;
		maxConfigTwo = null;
	}
	
	public void drawModule(MODULE_TYPE type, int x, int y) {
		
		Image image = getImage(type);
		if ( image != null ) {
			
			image.setSize("10px", "10px");
		    configGrid.setWidget(50-y, x-1, image);
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
		
		configGrid.clear();
		for ( int y=0; y<config.getDepth(); y++ ) {
			
			for ( int x=0; x<config.getWidth(); x++ ) {
				
				MODULE_TYPE type = config.getFutureModule(x, y);
				drawModule(type, x, y);
			}
		}
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 100; j++) {
				if(i>=40 && i<=50 && j>=40 && j<=50){
					beforeG.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
				}
			}
		}
		beforeG.setStyleName("landingStyle");
		beforeG.setCellPadding(2);
		beforeG.setCellSpacing(2);
		
		change.add(beforeG, "Before");
		controls = new VerticalPanel();
		configPanel = new ScrollPanel();
		configSave = new Button("Save Configurations");
		//configPanel.setSize("275px", "500px");
		configPanel.setTitle("Configurations");
		/*availBox = new ListBox();
		availBox.addItem("Min Config");
		availBox.addItem("Min Config2");*/
		configGrid = new Grid(50, 100);
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 100; j++) {
				if(i>=40 && i<=50 && j>=40 && j<=50){
					configGrid.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
				}
			}
		}
		rating = new Label();
		rating.setText("Rating: ");
		configGrid.setCellPadding(2);
		configGrid.setCellSpacing(2);
		configGrid.addStyleName("landingStyle");
		// Proabably want to convert this to generation on load rather than on button click...
		
		mincon1 = new RadioButton("Min","Minimum Configuration 1");
		mincon1.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				renderConfig(minConfigOne);
				rating.setText("Rating: " + minConfigOne.getQualityRating() + "%");
			}
		});
		mincon2 = new RadioButton("Min","Minimum Configuration 2");
		mincon2.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				renderConfig(minConfigTwo);
				rating.setText("Rating: " + minConfigTwo.getQualityRating() + "%");
			}
		});
		maxcon1 = new RadioButton("Min","Maximum Configuration 1");
		maxcon1.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				renderConfig(maxConfigOne);
				rating.setText("Rating: " + maxConfigOne.getQualityRating() + "%");
			}
		});
		maxcon2 = new RadioButton("Min","Maximum Configuration 2");
		maxcon2.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				renderConfig(maxConfigTwo);
				rating.setText("Rating: " + maxConfigTwo.getQualityRating() + "%");
			}
		});
		controls.add(mincon1);
		controls.add(mincon2);
		controls.add(maxcon1);
		controls.add(maxcon2);
		controls.add(configSave);
		controls.add(rating);
		add(controls);
		add(change);
		change.add(configGrid, "After");
		
		//add(configGrid);
		return true;
	}
	
}
