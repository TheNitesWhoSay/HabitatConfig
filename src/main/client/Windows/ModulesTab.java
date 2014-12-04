package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.HabitatConfig;
import main.client.Data.LandingGrid;
import main.client.Data.Module;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
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
	private int deleteHandler = 0;
	private HorizontalPanel logpanel = new HorizontalPanel();
	private Button save = new Button("Save to Local Storage");
	private Button load = new Button("Load Modules");
	private Button clear = new Button("Clear Storage");
	private Button clean = new Button("Clear Modules");
	private Module emptyMod = new Module();
	private ListBox tests = new ListBox();
	private FlexTable storetable;
	ScrollPanel sp;
	TreeItem modTree = new TreeItem();
	private boolean alerted;
	public static Grid g = new Grid(50, 100);
	private ScrollPanel p;
	TextBox id = new TextBox();
	TextBox xcord = new TextBox();
    TextBox ycord = new TextBox();
	final ListBox statbox = new ListBox();
	final ListBox orienbox = new ListBox();
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
	private int gridTop;
	private int gridLeft;
	private Storage moduleStore;
	private LandingGrid moduleList;
	private String moduleListKey = "ModuleList";
   // Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG,
     //   "http(s)/url/to/your/sound/file.mp3");
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
		/** Label header for logging modules */
		HorizontalPanel modLabel = new HorizontalPanel();
		moduleList = root.landingGrid;
		// Label start = new Label("");
		Label modID = new Label("ID");
		final Label xcor = new Label("X-Cor");
		final Label ycor = new Label("Y-Cor");
		final Label status = new Label("Status");
		final Label orientation = new Label("Orientation");
		tests.addItem("Test 1");
		tests.addItem("Test 2");
		tests.addItem("Test 3");
		tests.addItem("Test 4");
		tests.addItem("Test 5");
		tests.addItem("Test 6");
		tests.addItem("Test 7");
		tests.addItem("Test 8");
		tests.addItem("Test 9");
		tests.addItem("Test 10");
		tests.setSelectedIndex(9);
		tests.addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {
				int selection = tests.getSelectedIndex()+1;
				loadTests(selection);
				
			}
		});
		leftpanel.add(save);
		leftpanel.add(load);
		leftpanel.add(clear);
		leftpanel.add(clean);
		leftpanel.add(tests);
		/**Potential save handler for landing grid moduleList. (Maybe) Thinking eventBus for loading modules */
		save.addClickHandler(new ClickHandler() {
			@SuppressWarnings("unused")
			// private Panel rp;
			public void onClick(final ClickEvent e) {
				storeList();
			}
		});
		load.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent e){
				loadList();
			}
		});
		clear.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent e){
				emptyStorage();
			}
		});
		clean.addClickHandler(new ClickHandler(){
			public void onClick(final ClickEvent e){
				storetable.clear(true);
				g.clear();
				root.landingGrid.getModuleList().clear();
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
		comppanel.add(leftpanel, DockPanel.WEST); //Adding logging components on the west side
		comppanel.add(p, DockPanel.EAST);  //Add grid components on the east side
		add(comppanel);
		return true;
	}
	/**
	 * Sets up URL for test feeds
	 * @param search
	 */
	private void loadTests(int search){
		String proxy = "http://d.umn.edu/~phil0837/Proxy.php?url=";
        String url =proxy+"http://www.d.umn.edu/~abrooks/SomeTests.php?q="+search;
        url = URL.encode(url);
        getResponse(url);
	}
	/**
	 * Connects with server and gathers data
	 * @param url2
	 */
	private void getResponse(String url2) {
		System.out.println(url2);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url2);
		try {
		 Request request = builder.sendRequest(null, new RequestCallback() {
			 
		 public void onError(Request request, Throwable exception) {
		 Window.alert("onError: Couldn't retrieve JSON");
		 }

		 public void onResponseReceived(Request request, Response response) {

		 if (200 == response.getStatusCode()) {
			 String rt = response.getText();
			 runTest(rt);
             
             }
      else {
             Window.alert("Couldn't retrieve JSON (" + response.getStatusText()
                             + ")");
     }
		 }
		 });
		 } catch (RequestException e) {
                Window.alert("RequestException: Couldn't retrieve JSON");
                System.out.println(e.getMessage());
		 }
		 
  }
	protected void runTest(String rtest) {
        String sAll = rtest;
        JSONArray jA = 
        (JSONArray)JSONParser.parseLenient(sAll);
        JSONNumber jN1,jN2,jN3,jN4;
        JSONString jS1;
        double id;
        double x;
        double y;
        String cond;
        double orient;
        for (int i = 0; i < jA.size(); i++) {
                JSONObject jO = (JSONObject)jA.get(i);
                jN1 = (JSONNumber) jO.get("code");
                id = jN1.doubleValue();
                jS1 = (JSONString) jO.get("status");
                cond = jS1.stringValue();
                jN4 = (JSONNumber) jO.get("turns");
                orient = jN4.doubleValue();
                jN2 = (JSONNumber) jO.get("X");
                x = jN2.doubleValue();
                jN3 = (JSONNumber) jO.get("Y");
                y = jN3.doubleValue();
                int intx = (int) x;
                int inty = (int) y;
                int intid = (int) id;
                MODULE_STATUS s;
                if(cond.equals("undamaged")){
                	s = MODULE_STATUS.Usable;
                }
                else if(cond.equals("usable after repair")){
                	s = MODULE_STATUS.UsableAfterRepair;
                }
                else if(cond.equals("damaged beyond repair")){
                	s = MODULE_STATUS.DamagedBeyondRepair;
                }
                else {
                	s = MODULE_STATUS.Unknown;
                }
                String orientation = String.valueOf(orient);
                if(orientation.equals("0")){
                root.landingGrid.setModuleInfo(intx,inty ,intid, 0, s);
                }
                else if(orientation.equals("1")){
                	root.landingGrid.setModuleInfo(intx, inty, intid, 1, s);
                }
                else {
                	root.landingGrid.setModuleInfo(intx, inty, intid, 2, s);
                }
        }
        refreshDisplayedModules(root.landingGrid.getModuleList());
		
	}

	/**
	 * Clears any module elements stored in local storage
	 */
	private void emptyStorage() {
		 moduleStore = Storage.getLocalStorageIfSupported();
         if (moduleStore != null){
                 moduleStore.removeItem(moduleListKey);
         }
	}
	/**
	 * Loads module data from local storage and updates stocktable and map
	 */
	private void loadList() {
		moduleStore = Storage.getLocalStorageIfSupported();
        if (moduleStore != null) {
                String modtext = moduleStore.getItem(moduleListKey);
                if (modtext != null){
                        root.landingGrid.pullStorage(modtext);
                        //refreshLandingMap(root.landingGrid.getModuleList());
                        refreshDisplayedModules(root.landingGrid.getModuleList());
                }
        }
		
	}
	/**
	 * Saves modules into local storage
	 */
	private void storeList() {
		moduleStore = Storage.getLocalStorageIfSupported();
        if (moduleStore != null) {
                if(moduleList.getModuleList().isEmpty()){
                }
                else if(root.landingGrid.getModuleList().get(0)!=emptyMod){
                	moduleStore.setItem(moduleListKey, root.landingGrid.generateStorage());
                Window.alert("Module(s) Saved Successfully");
                }
        }
		
	}

	/**
	 * Creates the landing zone for logging modules(Non-Configuration grid)
	 */
	private void createLanding() {
		canvas = Canvas.createIfSupported();
		if (canvas != null) {
			canvas.setWidth("" + 100);
			canvas.setHeight("" + 50);
			canvas.setCoordinateSpaceHeight(50);
			canvas.setCoordinateSpaceWidth(100);
			
			/** Sets the unbuildable area design */
			for (int i = 0; i < 50; i++) {
				for (int j = 0; j < 100; j++) {
					if(i>=40 && i<=50 && j>=40 && j<=50){
						g.getCellFormatter().addStyleName(49-i, j-1, "Unbuildable");
					}
				}
			}
			g.setCellPadding(2);
			g.setCellSpacing(2);
			//g.setSize("900px", "200px");
			this.p = new ScrollPanel();
			this.p.setSize("900px", "600px");
			this.p.add(this.g);
			g.addStyleName("landingStyle");
			gridLeft = p.getAbsoluteLeft();//Gets top left corner of grid
			gridTop = p.getAbsoluteTop();//"                             "(0,0)
		}
		
	}

	/**
	 * Creates the flex table that handles logging modules
	 * 
	 * @param storetable2
	 *            the storetable to make
	 */
	private void createTable(FlexTable storetable2) {
		//Initialize module logging entry components.


		statbox.addItem("Usable");
		statbox.addItem("Usable after repair");
		statbox.addItem("Beyond repair");
		statbox.addItem("Unknown");
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
		
		/** Handler for adding events to moduleList. */
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
				try { code = Integer.parseInt(   id.getText()); } catch ( NumberFormatException nfe ) { }//Make sure valid input for text box information 
				try {   xc = Integer.parseInt(xcord.getText()); } catch ( NumberFormatException nfe ) { }//Make sure valid input for text box information
				try {   yc = Integer.parseInt(ycord.getText()); } catch ( NumberFormatException nfe ) { }//Make sure valid input for text box information
				
				if ( validateCode(code) && validateXc(xc) && validateYc(yc) && validateLocation(xc, yc) )
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
					if ( validateCode(code) && validateXc(xc) && validateYc(yc) && validateLocation(xc, yc) ){
					if(curr.getCode() == code){
						storetable.removeRow(moduleCount);
						deleteHandler = storetable.getRowCount();
						root.landingGrid.removeModule(curr.getXPos(), curr.getYPos());
						
						/** Using -50 and -1 in order to change the natural layout of the grid */
						g.setWidget(50-curr.getYPos(), curr.getXPos()-1, null);
						moduleCount--;
						refreshLandingMap(root.landingGrid.getModuleList());
				 	//refreshDisplayedModules(root.landingGrid.getModuleList());
					}
					moduleCount++;
					}
				}
					if(root.landingGrid.setModuleInfo(xc, yc, code, rotations, ms)){
						deleteHandler = storetable.getRowCount();
						refreshDisplayedModules(root.landingGrid.getModuleList());
				//refreshLandingMap(root.landingGrid.getModuleList());
					}
				}
			}
		});
	}
	/**
	 * Validates that the module attempted to be added
	 * is not within an unbuildable area.
	 * @param xc2 the unbuildable x-coordinate area the robot reported.
	 * @param yc2 the unbuildable y-coordinate are the robot reported.
	 * @return whether or not module was attempting to be built in unbuildable area.
	 */
	protected boolean validateLocation(int xc2, int yc2) {
		if((xc2>=40 && xc2<=50)&&(yc2>=40 && yc2<=50)){
			Window.alert("Unbuildable Area");
			return false;
		}
		else{
		return true;
		}
	}
	/**
	 * Renders modules and their information on the map
	 * as they are loaded into the moduleList.
	 * @param ycc 
	 * @param xcc 
	 */
	protected void refreshLandingMap(LinkedList<Module> m) {
		ListIterator<Module> i = m.listIterator();
		int moduleCount = 0;
		while (i.hasNext()) {
		final Module curr = i.next();
		ima = new Image();
		ima = getImage(curr.getCode());
		if(ima == null){
			return;
		}
		else{
		ima.setSize("10px", "10px");
		ima.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent e){
				id.setText(""+curr.getCode());
				xcord.setText(""+curr.getXPos());
				ycord.setText(""+curr.getYPos());
				if(curr.getStatus()==MODULE_STATUS.Usable){
					statbox.setSelectedIndex(0);
				}
				else if(curr.getStatus()==MODULE_STATUS.UsableAfterRepair){
					statbox.setSelectedIndex(1);
				}
				else if(curr.getStatus()==MODULE_STATUS.DamagedBeyondRepair){
					statbox.setSelectedIndex(2);
				}
				else{
					statbox.setSelectedIndex(3);
				}
				orienbox.setSelectedIndex(curr.getRotationsTillUpright());
			}
		});
		g.setWidget(50-curr.getYPos(), curr.getXPos()-1, ima);
		}
		}
	}
	
	private Image getImage(int code2) {
	  Image im = null;
		if(code2 > 0 && code2 < 41){
		    im = new Image("images/Plain.jpg");
			}
			else if(code2 >=61 && code2 <= 80){
				im = new Image("images/Dormitory.jpg");
			}
			else if(code2 >=91 && code2 <= 100){
				im = new Image("images/Sanitation.jpg");
			}
			else if(code2 >=61 && code2 <= 80){
				im = new Image("images/Dormitory.jpg");
			}
			else if(code2 >=111 && code2 <= 120){
				im = new Image("images/Food.jpg");
			}
			else if(code2 >=141 && code2 <= 144){
				im = new Image("images/Canteen.jpg");
			}
			else if(code2 >=151 && code2 <= 154){
				im = new Image("images/Power.jpg");
			}
			else if(code2 >=161 && code2 <= 164){
				im = new Image("images/Control.jpg");
			}
			else if(code >=171 && code <= 174){
				im = new Image("images/Airlock.jpg");
			}
		if(im != null){
		im.setSize("5", "5");
		}
		return im;
	}

	/**
	 * Refreshes the display(s) of stored modules
	 */
	public void refreshDisplayedModules(final LinkedList<Module> modules) {
		if(modules.isEmpty()){
			return;
		}
		ListIterator<Module> i = modules.listIterator();
		int modulecount = 0;
		while (i.hasNext()) {

			final Module curr = i.next();
			storetable.setText(modulecount, 0, "" + curr.getCode());
			storetable.setText(modulecount, 1, "" + curr.getXPos());
			storetable.setText(modulecount, 2, "" + curr.getYPos());
			storetable.setText(modulecount, 3, "" + curr.getStatus());
			storetable.setText(modulecount, 4, "" + curr.getRotationsTillUpright());
			storetable.setText(modulecount, 5, "" + ModuleTypes.getType(curr.getCode()));
			Button removebutton = new Button("X");
			storetable.setWidget(modulecount, 6, removebutton);
			storetable.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent e){
					getOnMap(curr.getCode(), curr.getXPos(), curr.getYPos(), curr.getStatus(), curr.getRotationsTillUpright());
				}
			});
			final int modcount = modulecount;
			yc = curr.getYPos();
			xc = curr.getXPos();
			code = curr.getCode();
			//refreshDisplayedModules(root.landingGrid.getModuleList());
			refreshLandingMap(root.landingGrid.getModuleList());
			/** Handler to remove items from the landing grid list, as well as the UI */
			removebutton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent e){
					int modscount = modcount;
						storetable.removeRow(modscount);
					root.landingGrid.removeModule(curr.getXPos(),curr.getYPos());
					g.setWidget(50-curr.getYPos(), curr.getXPos()-1, null);
					refreshLandingMap(root.landingGrid.getModuleList());
					refreshDisplayedModules(root.landingGrid.getModuleList());
					return;
				}
			});
			modulecount++;
		}

		if (hasMinConfig(root.landingGrid.getModuleList())) {

			boolean b = Window.confirm("Check out configuration available?");
			if (b) {
				root.mainWindow.selectTab(2);
			}
		}
	}
	/**
	 * Locates where a particular module is located on the map.
	 * @param i
	 * @param j
	 * @param k
	 * @param module_STATUS
	 * @param l
	 */
	protected void getOnMap(int i, int j, int k, MODULE_STATUS module_STATUS, int l) {
		
	}
	/**
	 * Confirms whether or not a possible min configuration is available
	 * 
	 * @param moduleList of logged items on the landing grid
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
		else
			return true;
	}

}