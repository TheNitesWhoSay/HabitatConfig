package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import org.json.JSONStringer;

import main.client.HabitatConfig;
import main.client.Data.Module;
import main.client.Data.ModuleTypes;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.server.testing.Parent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

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
private boolean alerted = false;
	protected Widget rp;
	protected String mod;
	
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
		Button save = new Button("Save to local storage");
		add(save);
		
	
		save.addClickHandler(new ClickHandler(){
			private Panel rp;

			public void onClick(ClickEvent e){
				LinkedList<Module> modules = root.landingGrid.getModuleList();
				ListIterator<Module> i = modules.listIterator();
				int moduleCount = 0;
				/**
				while ( i.hasNext() ) {
					
				Module curr = i.next();
				mod = "["+"{code: "+curr.getCode()+", "+"Status: "+curr.getStatus()+" turns: "+curr.getRotationsTillUpright()+" X: "+curr.getXPos()+" Y:"+curr.getYPos()+"}";
			    
				}
				Storage moduleStore = Storage.getLocalStorageIfSupported();
				if(moduleStore != null){
					moduleStore.setItem("mod1", mod);
				}
				String mod1 = moduleStore.getItem("mod1");
				JSONArray jA = 
						
						 (JSONArray)JSONParser.parseLenient(mod1);
						 JSONNumber jN;
						 JSONString jS;
						 double d;
						 String s;
						 for (int i1 = 0; i1 < jA.size(); i1++) {
							 JSONObject jO = (JSONObject)jA.get(i1);
							 jN = (JSONNumber) jO.get("code");
							 d = jN.doubleValue();
							 rp = null;
							rp.add(new Label(Double.toString(d))); //TO VIEW
							 jS = (JSONString) jO.get("status");
							 s = jS.stringValue();
							 rp.add(new Label(s)); //TO VIEW
							 jN = (JSONNumber) jO.get("turns");
							 d = jN.doubleValue();
							 rp.add(new Label(Double.toString(d))); //TO VIEW
							 jN = (JSONNumber) jO.get("X");
							 d = jN.doubleValue();
							 rp.add(new Label(Double.toString(d))); //TO VIEW
							 jN = (JSONNumber) jO.get("Y");
							 d = jN.doubleValue();
							 rp.add(new Label(Double.toString(d))); //TO VIEW
							 rp.add(new HTML("<hr />"));
							 }
				add(rp);
			}
			*/
			}});
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
					Button removebutton = new Button("X");
					LinkedList<Module> modules = root.landingGrid.getModuleList();
					ListIterator<Module> i = modules.listIterator();
					int moduleCount = 0;

					while ( i.hasNext() ) {
						
					Module curr = i.next();
					if(curr.getCode() == code){
						storetable.removeRow(moduleCount);
						root.landingGrid.removeModule(curr.getCode(), curr.getXPos(), curr.getYPos());
						root.landingGrid.getModuleList();
						refreshDisplayedModules();
					}
					moduleCount++;
					}
					// Add the module to the programs collection of stored modules
					if ( root.landingGrid.setModuleInfo(xc, yc, code, rotations, ms) )
						refreshDisplayedModules(); // Refreshes storetable
						Image im = new Image();
						
						if(code > 0 && code < 41){
					    im = new Image("images/Plain.jpg");
					    im.setSize("50px", "50px");
						root.mainWindow.setGrid(xc, yc, im);
						}
						else if(code >=61 && code <= 80){
							im = new Image("images/Dormitory.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=91 && code <= 100){
							im = new Image("images/Sanitation.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=61 && code <= 80){
							im = new Image("images/Dormitory.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=111 && code <= 120){
							im = new Image("images/Food.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=61 && code <= 80){
							im = new Image("images/Dormitory.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=141 && code <= 144){
							im = new Image("images/Canteen.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=61 && code <= 80){
							im = new Image("images/Dormitory.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=151 && code <= 154){
							im = new Image("images/Power.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=161 && code <= 164){
							im = new Image("images/Control.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
						else if(code >=171 && code <= 174){
							im = new Image("images/Airlock.jpg");
							im.setSize("50px", "50px");
							root.mainWindow.setGrid(xc, yc, im);
							}
				}
			}
		});
		return true;
	}
	

	/**
	 * Refreshes the display(s) of stored modules
	 */
	public void refreshDisplayedModules() {
		Button removebutton = new Button("X");
		LinkedList<Module> modules = root.landingGrid.getModuleList();
		ListIterator<Module> i = modules.listIterator();
		int moduleCount = 0;
		while ( i.hasNext() ) {
			
			final Module curr = i.next();
			storetable.setText(moduleCount, 0, ""+curr.getCode());
			storetable.setText(moduleCount, 1, ""+curr.getXPos());
			storetable.setText(moduleCount, 2, ""+curr.getYPos());
			storetable.setText(moduleCount, 3, ""+curr.getStatus());
			storetable.setText(moduleCount, 4, ""+curr.getRotationsTillUpright());
			storetable.setText(moduleCount, 5, ""+ModuleTypes.getType(curr.getCode()));
			storetable.setWidget(moduleCount, 6, removebutton = new Button("X"));
			final int modcount = moduleCount;
			removebutton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int modscount = modcount;
					storetable.removeRow(modscount);
					root.landingGrid.removeModule(curr.getCode(), curr.getXPos(), curr.getYPos());
					root.landingGrid.getModuleList();
					refreshDisplayedModules();
					root.mainWindow.setGrid(curr.getXPos(), curr.getYPos(), null);
					return;
				}
			});
			moduleCount++;
		}
		if(hasMinConfig(root.landingGrid.getModuleList())){
			boolean b = Window.confirm("Check out configuration available?");
			if(b == true){
				root.mainWindow.selectTab(2);
			}
		}
	}
	/**
	 * Confirms whether or not a possible min configuration is available
	 * @param moduleList
	 * @return
	 */
	private boolean hasMinConfig(LinkedList<Module> moduleList) {
		if(alerted == true){
			return false;
		}
		else{
		ListIterator<Module> i = moduleList.listIterator();
		boolean hasAir = false;
		boolean hasPower = false;
		boolean hasControl = false;
		boolean hasDorm = false;
		boolean hasFood = false;
		boolean hasCanteen = false;
		boolean hasSanitation = false;
		boolean hasPlains = false;
		int plainCount = 0;
		int moduleCount = 0;
		while ( i.hasNext() ) {
		Module curr = i.next();
		
		if(curr.getCode()>0 && curr.getCode()<41){
			plainCount++;
			if(plainCount >= 3){
				hasPlains = true;
			}
		}
		if(curr.getCode()>=61 && curr.getCode()<=80){
			hasDorm = true;
		}
		if(curr.getCode()>=91 && curr.getCode() <= 100){
			hasSanitation = true;
		}
		if(curr.getCode()>=111 && curr.getCode() >= 120){
			hasFood = true;
		}
		if(curr.getCode()>=141 && curr.getCode()<=144){
			hasCanteen = true;
		}
		if(curr.getCode()>=151 && curr.getCode()<=154){
			hasPower = true;
		}
		if(curr.getCode()>=161 && curr.getCode()<=164){
			hasControl = true;
		}
		if(curr.getCode()>=171 && curr.getCode()<= 174){
			hasAir = true;
		}
		}
		
		if(hasPlains == true && hasDorm == true && hasSanitation == true && hasFood == true && hasCanteen == true && hasPower == true && hasControl == true && hasAir == true){
			alerted = true;
			return true;
		}
		else{
			return false;
		}
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