package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class contains the tabbed interface,
 * and holds the individual tab windows
 */
public class MainWindow extends GwtWindow {

	private HabitatConfig root;
	
	private HomeTab homeTab;
	private ModulesTab modulesTab;
	private ConfigTab configTab;
	private CommunicationsTab communicationsTab;
	private SettingsTab settingsTab;
	private VerticalPanel weatherPanel = new VerticalPanel();
	private HorizontalPanel hpLogout;
	private TabPanel tabs;
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public MainWindow(final HabitatConfig root) {
		String url = "http://api.wunderground.com/api/32e8543fe9f5ddaf/geolookup/conditions/forecast/q/autoip.json";//pulls information based on user IP address
	    url = URL.encode(url);
		getResponse(url);
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
         JSONObject jB = (JSONObject)JSONParser.parseLenient(jEnter.toString());
         JSONValue temp = jB.get("temp_f");
         JSONValue visibility = jB.get("visibility_mi");
         JSONValue wind = jB.get("wind_string");
         Label weatherHeader = new Label("Mars Weather Report");
         Label temperature = new Label(""+temp);
         weatherPanel.add(weatherHeader);
         weatherPanel.add(temperature);
		 }
		});
	}

	/**
	 * Selects the given tab number
	 * @param tabNum the given tab number
	 */
	public void selectTab(final int tabNum) {
		tabs.selectTab(tabNum);
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
			}
		});
		hpLogout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hpLogout.setWidth("100%");
		hpLogout.add(logout);
		hpLogout.setVisible(false);
		add(hpLogout);
		if ( root.configOptions.loginRequired() )
			showLogout();
			
		tabs = new TabPanel();
		weatherPanel.setBorderWidth(2);
		weatherPanel.setWidth("50%");
		weatherPanel.getElement().getStyle().setWidth(150, Unit.PX);
		tabs.setWidth("100%");
		homeTab.show(tabs, "Home");
		modulesTab.show(tabs, "Modules");
		configTab.show(tabs, "Configurations");
		communicationsTab.show(tabs, "Communications");
		settingsTab.show(tabs, "Settings");
		selectTab(0);
		add(weatherPanel);
		add(tabs);
		return true;
	}
}