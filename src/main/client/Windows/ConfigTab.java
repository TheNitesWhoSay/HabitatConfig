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
	private Grid g;
	private ScrollPanel p;
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
	
		LandingGrid landingGrid = this.root.landingGrid;
		int width = landingGrid.getWidth();
		int depth = landingGrid.getDepth();
		
		Canvas canvas;
		this.g = new Grid(width, depth);
		canvas = Canvas.createIfSupported();
		if(canvas != null){
		canvas.setWidth(""+width);
		canvas.setHeight(""+depth);
		canvas.setCoordinateSpaceHeight(depth);
		canvas.setCoordinateSpaceWidth(width);
		
		for ( int i = 0; i<depth; i++ ) {
			for( int j = 0; j<width; j++ ) {
				this.g.setCellPadding(50);
			}
		}
		this.g.setBorderWidth(5);
		LinkedList<Module> modules = landingGrid.getModuleList();
		ListIterator<Module> i = modules.listIterator();
		
		while ( i.hasNext() ) {
			
			Module curr = i.next();
			this.g.setText(curr.getXPos(), curr.getYPos(), "Module");
		
		}
			this.p = new ScrollPanel();
			this.p.setSize("1200px", "600px");
			this.p.add(this.g);
			add(this.p);
			return true;
		}
		else {
			
			return false;
		}
	}
	
	public void setGrid(final int rowNum, final int colNum, final Image type){
		
		this.g.setWidget(rowNum, colNum, type);
	}
}
