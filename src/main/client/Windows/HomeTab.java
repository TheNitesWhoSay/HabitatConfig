package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HomeTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	@SuppressWarnings("unused")
	private JSONValue city; // Zipcode of user based on geolocation data
	/**
	 * Default constructor
	 */
	public HomeTab(final HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the home tab
	 */
	protected boolean create() {
	
		add(new HTML("Home"));
	    String url = "http://api.wunderground.com/api/32e8543fe9f5ddaf/geolookup/conditions/forecast/q/autoip.json";//pulls information based on user IP address
	    url = URL.encode(url);
		getResponse(url);
		return true;
	}
	public void getResponse(String url2){
		final JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setCallbackParam("callback");
		jsonp.requestObject(url2, new AsyncCallback<JavaScriptObject>() {
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
		 VerticalPanel vp = new VerticalPanel();
		 vp.add(new Label(""+temp)); //TO VIEW
		 add(vp);
		 }
		});
	}

	protected void update(String rt) {
		
		
	}

}
