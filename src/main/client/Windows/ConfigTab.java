package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.HabitatConfig;
import main.client.Data.LandingGrid;
import main.client.Data.Module;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ConfigTab extends GwtWindow {

	private HabitatConfig root;
	/**
	 * Default constructor
	 */
	public ConfigTab(final HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the config tab
	 */
	protected boolean create() {
	
		return true;
	}
	
}
