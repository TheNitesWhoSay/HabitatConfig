package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.HTML;

public class ConfigTab extends GwtWindow {

	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public ConfigTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
	
		add(new HTML("Config"));
		Canvas canvas;
		canvas = Canvas.createIfSupported();
		if(canvas != null){
		canvas.setWidth(""+root.landingGrid.getWidth());
		canvas.setHeight(""+root.landingGrid.getDepth());
		canvas.setCoordinateSpaceHeight(root.landingGrid.getDepth());
		canvas.setCoordinateSpaceWidth(root.landingGrid.getWidth());
		Context2d context = canvas.getContext2d(); // a rendering context
		add(canvas);
		return true;
		}
		else{
		return false;
		}
	}
}
