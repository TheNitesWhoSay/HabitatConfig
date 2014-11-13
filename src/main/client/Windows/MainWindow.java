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
	
	private TabPanel tabs;
	
	/**
	 * Sets default variable values
	 * @param root a reference to the root class
	 */
	public MainWindow(HabitatConfig root) {
		
		this.root = root;
		homeTab = new HomeTab();
		modulesTab = new ModulesTab();
		configTab = new ConfigTab();
		communicationsTab = new CommunicationsTab();
		settingsTab = new SettingsTab();
	}
	
	public void selectTab(int tabNum)
	{
		tabs.selectTab(tabNum);
	}
	
	/**
	 * This method will create the main window
	 * @return true if window creation was successful
	 */
	protected boolean create() {
		
		// Create the main window...
		
		HorizontalPanel hpLogout = new HorizontalPanel();
		final Button logout = new Button("Logout");
		logout.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				hide();
				root.loginWindow.show(RootPanel.get());
			}
		});
		hpLogout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		hpLogout.setWidth("100%");
		hpLogout.add(logout);
		add(hpLogout);
		
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
