package main.client.Windows;

import main.client.HabitatConfig;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

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
	
	private HorizontalPanel hpLogout;
	private TabPanel tabs;
	
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public MainWindow(final HabitatConfig root) {
		
		this.root = root;
		homeTab = new HomeTab(root);
		modulesTab = new ModulesTab(root);
		configTab = new ConfigTab(root);
		communicationsTab = new CommunicationsTab(root);
		settingsTab = new SettingsTab(root);
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
		tabs.setWidth("100%");
		homeTab.show(tabs, "Home");
		modulesTab.show(tabs, "Modules");
		configTab.show(tabs, "Configurations");
		communicationsTab.show(tabs, "Communications");
		settingsTab.show(tabs, "Settings");
		selectTab(0);
		add(tabs);
		
		return true;
	}
}
