package main.client.Windows;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.HabitatConfig;
import main.client.Data.Module;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ConfigTab extends GwtWindow {

	private HabitatConfig root;
	public Grid g;
	ScrollPanel p;
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
		g = new Grid(100, 50);
		canvas = Canvas.createIfSupported();
		if(canvas != null){
		canvas.setWidth(""+root.landingGrid.getWidth());
		canvas.setHeight(""+root.landingGrid.getDepth());
		canvas.setCoordinateSpaceHeight(root.landingGrid.getDepth());
		canvas.setCoordinateSpaceWidth(root.landingGrid.getWidth());
		Context2d context = canvas.getContext2d(); // a rendering context
		for(int i = 0; i<50; i++){
			for(int j = 0; j<100; j++){
				g.setCellPadding(50);
			}
		}
		g.setBorderWidth(5);
		LinkedList<Module> modules = root.landingGrid.getModuleList();
		ListIterator<Module> i = modules.listIterator();
		int moduleCount = 0;
		
		while ( i.hasNext() ) {
			
			Module curr = i.next();
			g.setText(curr.getXPos(), curr.getYPos(), "Module");
		
		}
			p = new ScrollPanel();
			p.setSize("1200px", "600px");
			p.add(g);
			add(p);
			return true;
		}
		else {
			
			return false;
		}
	}
	
	public void setGrid(int rowNum, int colNum, Image type){
		
		g.setWidget(rowNum, colNum, type);
	}
}
