package main.client.Windows;

import main.client.HabitatConfig;
import main.client.Data.Module;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class ModulesTab extends GwtWindow  {
private int storedmodcount = 0;
	@SuppressWarnings("unused")
	private HabitatConfig root;
	
	/**
	 * Default constructor
	 */
	public ModulesTab(HabitatConfig root) {
		
		super();
		this.root = root;
	}
	
	/**
	 * Creates the contents of the modules tab
	 */
	protected boolean create() {
		
		add(new HTML("Modules"));
		HorizontalPanel modLabel = new HorizontalPanel();
		
		//Label start = new Label("");
		Label modID = new Label("ID");
		Label xcor = new Label("X-Cor");
		Label ycor = new Label("Y-Cor");
		Label status = new Label("Status");
		Label orientation = new Label("Orientation");
		Label type = new Label("Type");
		Button addb = new Button("Add");
	
		modLabel.add(modID);
		modLabel.add(xcor);
		modLabel.add(ycor);
		modLabel.add(status);
		modLabel.add(orientation);
		modLabel.add(type);
		modLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		modLabel.setSpacing(30);
		add(modLabel);
		
		HorizontalPanel logpanel = new HorizontalPanel();
		final TextBox id = new TextBox();
		final TextBox xcord = new TextBox();
		final TextBox ycord = new TextBox();
		final ListBox statbox = new ListBox();
		final ListBox orienbox = new ListBox();
		final ListBox typebox = new ListBox();
		final FlexTable storetable = new FlexTable();
		
		statbox.addItem("Usable");
		statbox.addItem("Usable after repair");
		statbox.addItem("Beyond repair");
		logpanel.add(id);
		logpanel.add(xcord);
		logpanel.add(ycord);
		logpanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		logpanel.setSpacing(22);
		id.setPixelSize(20, 10);
		xcord.setPixelSize(20, 10);
		ycord.setPixelSize(20, 10);
		statbox.setPixelSize(70, 25);
		logpanel.add(statbox);
		
		orienbox.addItem("0-Upright");
		orienbox.addItem("1-Side");
		orienbox.addItem("2-Upside down");
		orienbox.setPixelSize(80, 25);
		
		typebox.addItem("Plain");
		typebox.addItem("Dormitory");
		typebox.addItem("Sanitation");
		typebox.addItem("Food & Water");
		typebox.addItem("Gym & Relaxation");
		typebox.addItem("Canteen");
		typebox.addItem("Power");
		typebox.addItem("Control");
		typebox.addItem("Airlock");
		typebox.addItem("Medical");
		typebox.setPixelSize(70, 25);
		
		logpanel.add(orienbox);
		logpanel.add(typebox);
		logpanel.add(addb);
		
		add(logpanel);
		add(storetable);
		
		addb.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				Module storem = new Module();
				MODULE_STATUS ms = null;
				int rotations = 0;
				MODULE_TYPE mt = null;
				
				if(statbox.getTabIndex()==0){
					ms = MODULE_STATUS.Usable;
				}
				else if(statbox.getTabIndex()==1){
					ms = MODULE_STATUS.UsableAfterRepair;
				}
				else{
					ms = MODULE_STATUS.DamagedBeyondRepair;
				}
				if(orienbox.getTabIndex()==0){
					rotations = 0;
				}
				else if(orienbox.getTabIndex()==1){
					rotations = 1;
				}
				else{
					rotations = 2;
				}
				if(typebox.getTabIndex()==0){
				     mt = MODULE_TYPE.Plain;
				}
				else if(typebox.getTabIndex()==1){
					mt = MODULE_TYPE.Dormitory;
				}
				else if(typebox.getTabIndex()==2){
					mt = MODULE_TYPE.Sanitation;
				}
				else if(typebox.getTabIndex()==3){
					mt = MODULE_TYPE.FoodAndWater;
				}
				else if(typebox.getTabIndex()==4){
					mt = MODULE_TYPE.GymAndRelaxation;
				}
				else if(typebox.getTabIndex()==5){
					mt = MODULE_TYPE.Canteen;
				}
				else if(typebox.getTabIndex()==6){
					mt = MODULE_TYPE.Power;
				}
				else if(typebox.getTabIndex()==7){
					mt = MODULE_TYPE.Control;
				}
				else if(typebox.getTabIndex()==8){
					mt = MODULE_TYPE.Airlock;
				}
				else{
					mt = MODULE_TYPE.Medical;
				}
				storem.setCode(Integer.parseInt(id.getText()));
				storem.setBookeepingXPos(Integer.parseInt(xcord.getText()));
				storem.setBookeepingYPos(Integer.parseInt(ycord.getText()));
				storem.setDamageStatus(ms);
				storem.setRotationsTillUpright(rotations);
				storem.setType(mt);
				
				storetable.setText(storedmodcount, 0, ""+storem.getCode());
				storetable.setText(storedmodcount, 1, ""+storem.getXPos());
				storetable.setText(storedmodcount, 2, ""+storem.getYPos());
				storetable.setText(storedmodcount, 3, ""+storem.getStatus());
				storetable.setText(storedmodcount, 4, ""+storem.getRotationsTillUpright());
				storetable.setText(storedmodcount, 5, ""+storem.getType());
				
				storedmodcount++;
			}
		});
		return true;
	}
	
}
