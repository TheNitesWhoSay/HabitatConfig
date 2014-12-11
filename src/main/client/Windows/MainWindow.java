package main.client.Windows;

import main.client.HabitatConfig;
import main.client.SoundOutput;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class contains the tabbed interface,
 * and holds the individual tab windows
 */
public class MainWindow extends GwtWindow {

	private HabitatConfig root;
	private HorizontalPanel ad;
	@SuppressWarnings("unused")
	private HomeTab homeTab;
	private ModulesTab modulesTab;
	public ConfigTab configTab;
	@SuppressWarnings("unused")
	private CommunicationsTab communicationsTab;
	private SettingsTab settingsTab;
	private VerticalPanel weatherPanel = new VerticalPanel();
	private HorizontalPanel hpLogout;
	private String url;
	Button adbutton = new Button();
	@SuppressWarnings("unused")
	private Image adimage = new Image();
	private Grid weatherGrid = new Grid(3,2);
	private TabPanel tabs;
	private SoundOutput sound = new SoundOutput();
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public MainWindow(final HabitatConfig root) {
		url = "http://api.wunderground.com/api/32e8543fe9f5ddaf/geolookup/conditions/astronomy/q/autoip.json";//pulls information based on user IP address
	    url = URL.encode(url);
		getResponse(url);
		adbutton.setStyleName("Wunderground");
		adbutton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent e){
				getResponse(url);
			}
		});

		this.root = root;
		homeTab = new HomeTab(root);
		modulesTab = new ModulesTab(root);
		configTab = new ConfigTab(root);
		communicationsTab = new CommunicationsTab(root);
		settingsTab = new SettingsTab(root);
	}
	/**
	 * Method that connects with API and gathers data
	 * @param url
	 */
	private void getResponse(String url) {
		final JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("callback");
		jsonp.requestObject(url, new AsyncCallback<JavaScriptObject>() {
		@Override
		 public void onFailure(final Throwable caught) {
		 Window.alert("JSONP onFailure");
		 }
		 @Override
		 public void onSuccess(JavaScriptObject s) {
		 JSONObject obj = new JSONObject(s);
		 String result = obj.toString();
		 JSONObject jA = (JSONObject)JSONParser.parseLenient(result);
         JSONValue jEnter = jA.get("current_observation");
         JSONValue jEnter2 = jA.get("moon_phase");
         JSONObject jB = (JSONObject)JSONParser.parseLenient(jEnter.toString());
         JSONObject jC = (JSONObject)JSONParser.parseLenient(jEnter2.toString());
         JSONValue temp = jB.get("temp_f");
         JSONValue tempc = jB.get("temp_c");
         JSONValue mph = jB.get("wind_mph");
         JSONValue imageLink = jB.get("icon");
         JSONValue kph = jB.get("wind_kph");
         JSONValue update = jB.get("observation_time");
         JSONValue visibility = jC.get("sunset");
         int beforeH = visibility.toString().indexOf(':');
         int afterH = visibility.toString().indexOf('"', visibility.toString().indexOf(','));
         beforeH = beforeH+2;
         afterH = afterH-3;
         String hour = visibility.toString().substring(beforeH, afterH);
         int beforeM = visibility.toString().indexOf(':', afterH);
         int afterM = visibility.toString().indexOf('"', beforeM);
         beforeM = beforeM+2;
         afterM = afterM+3;
         String minute = visibility.toString().substring(beforeM, afterM);
         int mil = 0;
         try { mil = Integer.parseInt(hour); } catch ( NumberFormatException nfe ) { }
        	mil = mil-12;
         @SuppressWarnings("unused")
		JSONValue wind = jB.get("wind_string");
         Label weatherHeader = new Label("Mars Weather Report:");
         weatherHeader.getElement().getStyle().setFontWeight(FontWeight.BOLD);
         Label temperature = new Label("Temperature: "+temp+"f / "+""+tempc+" c");
         temperature.getElement().getStyle().setFontWeight(FontWeight.BOLD);
         Label windflow = new Label("Wind: "+mph+" mph/"+kph+" kph");
         windflow.getElement().getStyle().setFontWeight(FontWeight.BOLD);
         Label visible = new Label("	Sunset Time: "+mil+":"+minute+" PM");
         visible.getElement().getStyle().setFontWeight(FontWeight.BOLD);
         Image status = new Image();
         String editUrl = imageLink.toString();
         editUrl = editUrl.substring(1, editUrl.length()-1);
        	 status.setUrl("http://icons.wxug.com/i/c/a/"+editUrl+".gif");
         Label updatetime = new Label(""+update);
         updatetime.getElement().getStyle().setFontWeight(FontWeight.BOLD);
         weatherGrid.setWidget(0, 0, weatherHeader);
         weatherGrid.setWidget(1,0,temperature);
         weatherGrid.setWidget(2,0,windflow); 
         weatherGrid.setWidget(0,1,updatetime);
         weatherGrid.setWidget(1, 1, status);
         weatherGrid.setWidget(2, 1, visible);
         weatherGrid.setStyleName("WeatherStyle");
         
        
 		
		 }
		});
	}

	/**
	 * Selects the given tab number
	 * @param tabNum the given tab number
	 */
	public void selectTab(final int tabNum) {
		
		tabs.selectTab(tabNum);
		configTab.refreshTab();
	}
	
	/**
	 * Shows the logout button
	 */
	public void showLogout() {
		
		hpLogout.setVisible(true);
	}
	
	/**
	 * Hides the logout button
	 */
	public void hideLogout() {
		
		hpLogout.setVisible(false);
	}
	
	/**
	 * This method will create the main window
	 * @return true if window creation was successful
	 */
	protected boolean create() {
		
		// Create the main window...
		
		hpLogout = new HorizontalPanel();
		final Button logout = new Button("Logout");
		logout.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				hide();
				if ( root.loginWindow.isCreated() )
					root.loginWindow.ClearCredentials(true);
				
				root.loginWindow.show(RootPanel.get());
				sound.playLogoffSuccess();
				
			}
		});
		hpLogout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hpLogout.setWidth("100%");
		hpLogout.add(logout);
		hpLogout.setVisible(false);
		
		if ( root.configOptions.loginRequired() )
			showLogout();
			
		tabs = new TabPanel();
		
		weatherPanel.setBorderWidth(2);
		weatherPanel.setWidth("50%");
		weatherPanel.getElement().getStyle().setWidth(150, Unit.PX);
		tabs.setWidth("100%");
		
		ad = new HorizontalPanel();
        //adimage = new Image("images/wunderground.jpg");
        adbutton.setSize("370px", "70px");
 		ad.add(adbutton);
 		ad.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
 		ad.setWidth("100%");
		
		//homeTab.show(tabs, "Home"); // Uncomment to restore this tab
		modulesTab.show(tabs, "Modules");
		configTab.show(tabs, "Configurations");
		//communicationsTab.show(tabs, "Communications"); // Uncomment to restore this tab
		
		settingsTab.show(tabs, "Settings");
		
		selectTab(0);
		
		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.setWidth("100%");
		headerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		headerPanel.add(weatherGrid);
		headerPanel.add(ad);
		headerPanel.add(hpLogout);
		
		add(headerPanel);
		add(tabs);
		
		tabs.addSelectionHandler(new SelectionHandler<Integer>() {

			public void onSelection(SelectionEvent<Integer> event) {
				
				if ( event.getSelectedItem() == tabs.getWidgetIndex(configTab) )
					configTab.refreshTab();
			}
		});
		
		// 10 day alert
		
		Timer calibrationTime = new Timer() {
			@Override
			public void run() {
				Window.alert("Ten days have elapsed since the milometer device on "
								+ "the lift rover was calibrated.");
			}
		};
		//15 seconds
		calibrationTime.schedule(15000);
		
		return true;
	}
}