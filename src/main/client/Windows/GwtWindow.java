package main.client.Windows;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * This is a simple abstraction for a window (or subwindow) in gwt
 * 
 * Allows for easy creation, showing, and hiding of windows
 */
public class GwtWindow extends FlowPanel { // IS A FlowPanel (a blank panel)

	private boolean created; // Whether the window has been created
	
	/**
	 * Constructor, calls FlowPanel's constructor
	 * and sets default variable values
	 */
	public GwtWindow() {
		
		super(); // Call FlowPanel's constructor
		created = false;
	}
	
	/**
	 * Shows the window
	 * 
	 * Creates the window before showing
	 * if it has not already been created
	 * 
	 * @param the panel this window is shown in
	 */
	public void show(final Panel parent) {
		
		if ( !created )
			created = create();
		
		if ( created )
			parent.add(this);
	}
	
	/**
	 * Shows the tab
	 * 
	 * Creates the tab before showing
	 * if it has not already been created
	 * 
	 * @param parent the TabPanel this is inserted into
	 * @param tabTitle the title of this tab
	 */
	public void show(final TabPanel parent, final String tabTitle) {
		
		if ( !created )
			created = create();
		
		if ( created )
			parent.add(this, tabTitle);
	}
	
	/**
	 * Hides the window
	 */
	public void hide() {
		
		removeFromParent();
	}
	
	/**
	 * Checks whether this window has been created
	 * @return whether the window has been created
	 */
	public boolean isCreated() {
	
		return created;
	}
	
	/**
	 * The create method, write this method in inheriting classes
	 * @return true if the method has been overriden and window creation was sucessful.
	 */
	protected boolean create() {
		
		return false; // must be overriden
	}
}
