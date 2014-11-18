package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.HabitatConfig;
import main.client.Data.Module;
import main.client.Data.ModuleTypes;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author Grant
 *
 */
public class ModulesTab extends GwtWindow  {
	/**
	 * 
	 */
private HabitatConfig root;
	/**
	 * 
	 */
private FlexTable storetable;
	
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
		
		HorizontalPanel modLabel = new HorizontalPanel();
		
		//Label start = new Label("");
		Label modID = new Label("ID");
		Label xcor = new Label("X-Cor");
		Label ycor = new Label("Y-Cor");
		Label status = new Label("Status");
		Label orientation = new Label("Orientation");
		Button addb = new Button("Add");
	
		modLabel.add(modID);
		modLabel.add(xcor);
		modLabel.add(ycor);
		modLabel.add(status);
		modLabel.add(orientation);
		modLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		modLabel.setSpacing(30);
		add(modLabel);
		
		HorizontalPanel logpanel = new HorizontalPanel();
		final TextBox id = new TextBox();
		final TextBox xcord = new TextBox();
		final TextBox ycord = new TextBox();
		final ListBox statbox = new ListBox();
		final ListBox orienbox = new ListBox();
		storetable = new FlexTable();
		
		statbox.addItem("Usable");
		statbox.addItem("Usable after repair");
		statbox.addItem("Beyond repair");
		logpanel.add(id);
		logpanel.add(xcord);
		logpanel.add(ycord);
		logpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		logpanel.setSpacing(22);
		id.setPixelSize(20, 10);
		xcord.setPixelSize(20, 10);
		ycord.setPixelSize(20, 10);
		statbox.setPixelSize(70, 25);
		logpanel.add(statbox);
		
		orienbox.addItem("0-Upright");
		orienbox.addItem("1-Side");
		orienbox.addItem("2-Upside down");
		orienbox.setPixelSize(80, 25);
		
		logpanel.add(orienbox);
		logpanel.add(addb);
		
		add(logpanel);
		add(storetable);
		
		addb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				MODULE_STATUS ms = MODULE_STATUS.Unknown;
				int rotations = -1;
				
				if ( statbox.getSelectedIndex() == 0 ) {
					ms = MODULE_STATUS.Usable;
				}
				else if ( statbox.getSelectedIndex() == 1 ) {
					ms = MODULE_STATUS.UsableAfterRepair;
				}
				else {
					ms = MODULE_STATUS.DamagedBeyondRepair;
				}
				
				if ( orienbox.getSelectedIndex() == 0 ) {
					rotations = 0;
				}
				else if ( orienbox.getSelectedIndex() == 1 ) {
					rotations = 1;
				}
				else {
					rotations = 2;
				}
				
				int code = 0, xc = -1, yc = -1; // Init to invalid values
				try { code = Integer.parseInt(   id.getText()); } catch ( NumberFormatException nfe ) { }
				try {   xc = Integer.parseInt(xcord.getText()); } catch ( NumberFormatException nfe ) { }
				try {   yc = Integer.parseInt(ycord.getText()); } catch ( NumberFormatException nfe ) { }
				
				if ( validateCode(code) && validateXc(xc) && validateYc(yc) )
				{
					// Add the module to the programs collection of stored modules
					if ( root.landingGrid.setModuleInfo(xc, yc, code, rotations, ms) )
						refreshDisplayedModules(); // Refreshes storetable
				}
			}
		});
		return true;
	}
	
	/**
	 * Refreshes the display(s) of stored modules
	 */
	public void refreshDisplayedModules() {
		
		LinkedList<Module> modules = root.landingGrid.getModuleList();
		ListIterator<Module> i = modules.listIterator();
		int moduleCount = 0;
		while ( i.hasNext() ) {
			
			Module curr = i.next();
			storetable.setText(moduleCount, 0, ""+curr.getCode());
			storetable.setText(moduleCount, 1, ""+curr.getXPos());
			storetable.setText(moduleCount, 2, ""+curr.getYPos());
			storetable.setText(moduleCount, 3, ""+curr.getStatus());
			storetable.setText(moduleCount, 4, ""+curr.getRotationsTillUpright());
			storetable.setText(moduleCount, 5, ""+ModuleTypes.getType(curr.getCode()));
			moduleCount++;
		}
	}
	
	/**
	 * Checks whether a code number is valid
	 * @param code the given code number
	 * @return whether the code number matches up with a real module type
	 */
	private boolean validateCode(int code) {
		
		MODULE_TYPE mt = ModuleTypes.getType(code);
		if ( mt == MODULE_TYPE.Unknown || mt == MODULE_TYPE.Reserved ) {
			Window.alert("Invalid module code.");
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Checks whether a xc is valid
	 * @param xc the given xc
	 * @return whether the xc is within the landing grid
	 */
	private boolean validateXc(int xc) {
		
		if ( xc < 0 || xc >= root.landingGrid.getWidth() ) {
			Window.alert("Invalid xc: " + xc);
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Checks whether a yc is valid
	 * @param yc the given yc
	 * @return whether the yc is within the landing grid
	 */
	private boolean validateYc(int yc) {
		
		if ( yc < 0 || yc >= root.landingGrid.getDepth() ) {
			Window.alert("Invalid yc.");
			return false;
		}
		else
			return true;
	}
	
}

