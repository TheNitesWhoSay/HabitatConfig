package main.client.Windows;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * This is a simple abstraction for a window (or subwindow)
 * in gwt to share common commands
 */
public class GwtWindow extends FlowPanel { // IS A FlowPanel (a blank panel)

	private boolean created;
	
	public GwtWindow() {
		
		super();
		created = false;
	}
	
	public void show(Panel parent) {
		
		if ( !created )
			created = create();
			
		parent.add(this);
	}
	
	public void hide() {
		
		if ( created )
			removeFromParent();
	}
	
	protected boolean create() {
		
		return false; // must be overriden
	}
}
