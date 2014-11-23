package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.HabitatConfig;
import main.client.Data.Module;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Marsellie
 *
 */
public class ModulesTab extends GwtWindow {
	private DockPanel comppanel = new DockPanel();
	private HabitatConfig root;
	private HorizontalPanel logpanel = new HorizontalPanel();
	private Button save = new Button("Save to Local Storage");
	private FlexTable storetable;
	private boolean alerted;
	private Grid g;
	private ScrollPanel p;
	int code = 0; 
	int xc = -1;
	int yc = -1;
	MODULE_STATUS m;
	int rot;
	@SuppressWarnings("unused")
	private Widget rp;
	private Button addb = new Button("Add");
	private Canvas canvas;
	private VerticalPanel leftpanel = new VerticalPanel();
	@SuppressWarnings("unused")
	private String mod;
	ClickHandler addHandler;
	private Image ima;

	/**
	 * Default constructor
	 */
	public ModulesTab(final HabitatConfig root) {

		super();
		this.root = root;
		alerted = false;
	}

	/**
	 * Creates the contents of the modules tab
	 */
	protected boolean create() {

		HorizontalPanel modLabel = new HorizontalPanel();

		// Label start = new Label("");
		Label modID = new Label("ID");
		final Label xcor = new Label("X-Cor");
		final Label ycor = new Label("Y-Cor");
		final Label status = new Label("Status");
		final Label orientation = new Label("Orientation");
		leftpanel.add(save);

		save.addClickHandler(new ClickHandler() {
			@SuppressWarnings("unused")
			// private Panel rp;
			public void onClick(final ClickEvent e) {
				LinkedList<Module> modules = root.landingGrid.getModuleList();
				@SuppressWarnings("unused")
				ListIterator<Module> i = modules.listIterator();
				/**
				 * while ( i.hasNext() ) {
				 * 
				 * Module curr = i.next(); mod =
				 * "["+"{code: "+curr.getCode()+", "
				 * +"Status: "+curr.getStatus()+
				 * " turns: "+curr.getRotationsTillUpright
				 * ()+" X: "+curr.getXPos()+" Y:"+curr.getYPos()+"}";
				 * 
				 * } Storage moduleStore = Storage.getLocalStorageIfSupported();
				 * if(moduleStore != null){ moduleStore.setItem("mod1", mod); }
				 * String mod1 = moduleStore.getItem("mod1"); JSONArray jA =
				 * 
				 * (JSONArray)JSONParser.parseLenient(mod1); JSONNumber jN;
				 * JSONString jS; double d; String s; for (int i1 = 0; i1 <
				 * jA.size(); i1++) { JSONObject jO = (JSONObject)jA.get(i1); jN
				 * = (JSONNumber) jO.get("code"); d = jN.doubleValue(); rp =
				 * null; rp.add(new Label(Double.toString(d))); //TO VIEW jS =
				 * (JSONString) jO.get("status"); s = jS.stringValue();
				 * rp.add(new Label(s)); //TO VIEW jN = (JSONNumber)
				 * jO.get("turns"); d = jN.doubleValue(); rp.add(new
				 * Label(Double.toString(d))); //TO VIEW jN = (JSONNumber)
				 * jO.get("X"); d = jN.doubleValue(); rp.add(new
				 * Label(Double.toString(d))); //TO VIEW jN = (JSONNumber)
				 * jO.get("Y"); d = jN.doubleValue(); rp.add(new
				 * Label(Double.toString(d))); //TO VIEW rp.add(new HTML("
				 * <hr />
				 * ")); } add(rp); }
				 */
			}
		});
		modLabel.add(modID);
		modLabel.add(xcor);
		modLabel.add(ycor);
		modLabel.add(status);
		modLabel.add(orientation);
		modLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		modLabel.setSpacing(30);
		storetable = new FlexTable();
		createTable(storetable);
		leftpanel.add(modLabel);
		leftpanel.add(logpanel);
		leftpanel.add(storetable);
		createLanding();
		comppanel.add(leftpanel, DockPanel.WEST);
		comppanel.add(p, DockPanel.EAST);
		add(comppanel);
		return true;
	}

	/**
	 * Creates the landing zone for logging modules(Non-Configuration grid)
	 */
	private void createLanding() {
		this.g = new Grid(50, 100);
		canvas = Canvas.createIfSupported();
		if (canvas != null) {
			canvas.setWidth("" + 100);
			canvas.setHeight("" + 50);
			canvas.setCoordinateSpaceHeight(50);
			canvas.setCoordinateSpaceWidth(100);

			for (int i = 0; i < 50; i++) {
				for (int j = 0; j < 100; j++) {
					if(i>=40 && i<=50 && j>=40 && j<=50){
						g.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
					}
				}
			}
			g.setCellPadding(5);
			//this.g.setBorderWidth(5);
			
			this.p = new ScrollPanel();
			this.p.setSize("900px", "600px");
			this.p.add(this.g);
			g.addStyleName("landingStyle");
			
			// add(this.p);
		}
		
		// TO DO: Add handler for loading modules onto the map
	}

	/**
	 * Creates the flex table that handles logging modules
	 * 
	 * @param storetable2
	 *            the storetable to make
	 */
	private void createTable(FlexTable storetable2) {
		// TODO Auto-generated method stub

		final TextBox id = new TextBox();
		final TextBox xcord = new TextBox();
		final TextBox ycord = new TextBox();
		final ListBox statbox = new ListBox();
		final ListBox orienbox = new ListBox();

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
		// add(logpanel);
		// add(storetable);

		// TO DO: Add handler for add button and supporting methods
		addb.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent e){
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
				
				code = 0; // Init to invalid values
				xc = -1;
				yc = -1;
				try { code = Integer.parseInt(   id.getText()); } catch ( NumberFormatException nfe ) { }
				try {   xc = Integer.parseInt(xcord.getText()); } catch ( NumberFormatException nfe ) { }
				try {   yc = Integer.parseInt(ycord.getText()); } catch ( NumberFormatException nfe ) { }
				
				if ( validateCode(code) && validateXc(xc) && validateYc(yc) )
				{
					@SuppressWarnings("unused")
					Button removebutton = new Button("X");
					LinkedList<Module> modules = root.landingGrid.getModuleList();
					ListIterator<Module> i = modules.listIterator();
					int moduleCount = 0;
					final int codec = code;
					final int xcc = xc;
					final int ycc = yc;
					
					@SuppressWarnings("unused")
					final MODULE_TYPE mt = ModuleTypes.getType(code);
					while ( i.hasNext() ) {
						
					Module curr = i.next();
					if(curr.getCode() == code){
						storetable.removeRow(moduleCount);

						root.landingGrid.removeModule(curr.getXPos(), curr.getYPos());
						root.landingGrid.getModuleList();
						//root.landingGrid.moveModule(curr.getXPos(), curr.getYPos(), xc, yc);
						g.setWidget(50-curr.getYPos(), curr.getXPos()-1, null);
						g.setWidget(50-yc, xc-1, getImage(code));
				 	refreshDisplayedModules();
					}
					moduleCount++;
					}
					if(root.landingGrid.setModuleInfo(xcc, ycc, codec, rotations, ms)){
				refreshDisplayedModules();
				refreshLandingMap();
					}
				}
			}
		});
	}

	protected void refreshLandingMap() {
		ima = new Image();
		ima = getImage(code);
		if(ima == null){
			return;
		}
		else{
		ima.setSize("10px", "10px");
		g.setWidget(50-yc, xc-1, ima);
		}
	}

	private Image getImage(int code2) {
	  Image im = null;
		if(code > 0 && code < 41){
		    im = new Image("images/Plain.jpg");
			}
			else if(code >=61 && code <= 80){
				im = new Image("images/Dormitory.jpg");
			}
			else if(code >=91 && code <= 100){
				im = new Image("images/Sanitation.jpg");
			}
			else if(code >=61 && code <= 80){
				im = new Image("images/Dormitory.jpg");
			}
			else if(code >=111 && code <= 120){
				im = new Image("images/Food.jpg");
			}
			else if(code >=141 && code <= 144){
				im = new Image("images/Canteen.jpg");
			}
			else if(code >=151 && code <= 154){
				im = new Image("images/Power.jpg");
			}
			else if(code >=161 && code <= 164){
				im = new Image("images/Control.jpg");
			}
			else if(code >=171 && code <= 174){
				im = new Image("images/Airlock.jpg");
			}
		return im;
	}

	/**
	 * Refreshes the display(s) of stored modules
	 */
	public void refreshDisplayedModules() {
		Button removebutton = new Button("X");
		LinkedList<Module> modules = root.landingGrid.getModuleList();
		ListIterator<Module> i = modules.listIterator();
		int moduleCount = 0;
		while (i.hasNext()) {

			final Module curr = i.next();
			storetable.setText(moduleCount, 0, "" + curr.getCode());
			storetable.setText(moduleCount, 1, "" + curr.getXPos());
			storetable.setText(moduleCount, 2, "" + curr.getYPos());
			storetable.setText(moduleCount, 3, "" + curr.getStatus());
			storetable.setText(moduleCount, 4,
					"" + curr.getRotationsTillUpright());
			storetable.setText(moduleCount, 5,
					"" + ModuleTypes.getType(curr.getCode()));
			removebutton = new Button("X");
			storetable.setWidget(moduleCount, 6, removebutton);
			final int modcount = moduleCount;
			removebutton.addClickHandler(new ClickHandler() {
				public void onClick(final ClickEvent event) {
					int modscount = modcount;
					storetable.removeRow(modscount);
					//g.setWidget(curr.getYPos()-50,curr.getXPos()-1, null);
					root.landingGrid.removeModule(curr.getXPos(),curr.getYPos());
					g.setWidget(50-curr.getYPos(), curr.getXPos()-1, null);
					root.landingGrid.getModuleList();
					refreshDisplayedModules();

					return;
				}
			});
			moduleCount++;
		}

		if (hasMinConfig(root.landingGrid.getModuleList())) {

			boolean b = Window.confirm("Check out configuration available?");
			if (b) {
				root.mainWindow.selectTab(2);
			}
		}
	}

	/**
	 * Confirms whether or not a possible min configuration is available
	 * 
	 * @param moduleList
	 * @return
	 */
	private boolean hasMinConfig(final LinkedList<Module> moduleList) {
		if (alerted) {
			return false;
		} else {
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
			while (i.hasNext()) {
				Module curr = i.next();

				if (curr.getCode() > 0 && curr.getCode() < 41) {
					plainCount++;
					if (plainCount >= 3) {
						hasPlains = true;
					}
				}
				if (curr.getCode() >= 61 && curr.getCode() <= 80) {
					hasDorm = true;
				}
				if (curr.getCode() >= 91 && curr.getCode() <= 100) {
					hasSanitation = true;
				}
				if (curr.getCode() >= 111 && curr.getCode() >= 120) {
					hasFood = true;
				}
				if (curr.getCode() >= 141 && curr.getCode() <= 144) {
					hasCanteen = true;
				}
				if (curr.getCode() >= 151 && curr.getCode() <= 154) {
					hasPower = true;
				}
				if (curr.getCode() >= 161 && curr.getCode() <= 164) {
					hasControl = true;
				}
				if (curr.getCode() >= 171 && curr.getCode() <= 174) {
					hasAir = true;
				}
			}

			if (hasPlains && hasDorm && hasSanitation && hasFood && hasCanteen
					&& hasPower && hasControl && hasAir) {
				alerted = true;
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Checks whether a code number is valid
	 * 
	 * @param code
	 *            the given code number
	 * @return whether the code number matches up with a real module type
	 */
	private boolean validateCode(final int code) {
		MODULE_TYPE mt = ModuleTypes.getType(code);
		if (mt == MODULE_TYPE.Unknown || mt == MODULE_TYPE.Reserved) {
			Window.alert("Invalid module code.");
			return false;
		} else
			return true;
	}

	/**
	 * Checks whether a xc is valid
	 * 
	 * @param xc
	 *            the given xc
	 * @return whether the xc is within the landing grid
	 */
	private boolean validateXc(final int xc) {

		if (xc <= 0 || xc >= root.landingGrid.getWidth()) {
			Window.alert("Invalid xc: " + xc);
			return false;
		} 
		else if(xc>=40 && xc<=50){
			Window.alert("Unbuildable Area");
			return false;
		}
			else
			return true;
	}

	/**
	 * Checks whether a yc is valid
	 * 
	 * @param yc
	 *            the given yc
	 * @return whether the yc is within the landing grid
	 */
	private boolean validateYc(final int yc) {

		if (yc <= 0 || yc >= root.landingGrid.getDepth()) {
			Window.alert("Invalid yc.");
			return false;
		}
		else if(yc>=40 && yc<=50){
			Window.alert("Unbuildable Area");
			return false;
		}
		else
			return true;
	}

}
