package main.client.Data;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.Data.ModuleStatuses.MODULE_STATUS;

/**
 * Holds information about terrain and modules within the landing zone
 */
public class LandingGrid {

	private int width;
	private int depth;
	private TerrainSquare[/*X*/][/*Y*/] terrain;
	private Module[/*X*/][/*Y*/] modules;
	
	/**
	 * Constructs a basic landing grid,
	 * fills it with buildingable, traversable terrain and null modules
	 */
	public LandingGrid() {
		
		width = 101;//+1ed these values because the grid cannot contain (0,0).
		depth = 51;
		
		terrain = new TerrainSquare[width][depth]; // Ensure dimensions equate to the actual landing zone size
		modules = new Module[width][depth]; // Ensure dimensions equate to the actual landing zone size
		
		// Fill the landing grid with default values
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				modules[x][y] = null;
				terrain[x][y] = new TerrainSquare();
				if(x>=40 && x<=50 && y>=40 && y<=50){
					terrain[x][y].setBuildable(false);
				}
			}
		}
	}
	
	/**
	 * Copies the terrain of another LandingGrid.
	 * @param other The other LandingGrid.
	 */
	public void copyTerrain(final LandingGrid other) {
		
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				terrain[x][y].setTraversable(other.terrain[x][y].isTraversable());
				terrain[x][y].setBuildable(other.terrain[x][y].isBuildable());
			}
		}
	}
	
	/**
	 * Copies all damaged of another LandingGrid.
	 * @param other The other LandingGrid.
	 */
	public void copyDamagedModules(final LandingGrid other) {
		
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				if ( other.modules[x][y].getStatus() == MODULE_STATUS.DamagedBeyondRepair ||
					 other.modules[x][y].getStatus() == MODULE_STATUS.UsableAfterRepair )
				{
					modules[x][y] = other.modules[x][y];
				}
			}
		}
	}
	
	/**
	 * Gets the current landing grid width
	 * @return the current landing grid width
	 */
	public int getWidth() {
		
		return width;
	}
	
	/**
	 * Gets the current landing grid depth
	 * @return the current landing grid depth
	 */
	public int getDepth() {
		
		return depth;
	}
	
	public boolean isBuildable(final int x, final int y) {
		
		return terrain[x][y].isBuildable() &&
			   ( modules[x][y] == null ||
			     modules[x][y].getStatus() == MODULE_STATUS.Usable );
	}
	
	/**
	 * Returns whether this landing grid can make a minimum configuration
	 * @return
	 */
	public boolean hasMinimumConfiguration() {
		
		ModuleCount count = getUsableModuleCount();
		return count.getNumAirlock() >= 1 &&
			   count.getNumControl() >= 1 &&
			   count.getNumPower() >= 1 &&
			   count.getNumFoodAndWater() >= 1 &&
			   count.getNumDormitory() >= 1 &&
			   count.getNumCanteen() >= 1 &&
			   count.getNumSanitation() >= 1 &&
			   count.getNumPlain() >= 3;
	}
	
	/**
	 * Returns the number of each type of usable module within this landing grid
	 * @return The number of each type of usable module within the landing grid.
	 */
	public ModuleCount getUsableModuleCount() {
		
		return getModuleCount(MODULE_STATUS.Usable);
	}
	
	/**
	 * Gets the number of each type of module within this landing grid.
	 * @param maximumDamageStatus The maximum amount of damage any counted module should have.
	 * @return The number of each type of module within the landing grid, with less than
	 * 	or equal too the maximumDamage.
	 */
	public ModuleCount getModuleCount(final MODULE_STATUS maximumDamageStatus) {
		
		ModuleCount moduleCount = new ModuleCount();
		moduleCount.getModuleCount(this, maximumDamageStatus);
		return moduleCount;
	}
	
	/**
	 * Creates or replaces module info at the specified coordinates
	 * @param x the x coordinate of the module
	 * @param y the y coordinate of the module
	 * @param code the code number of the module
	 * @param rotationsTillUpright the rotations required till the module is upright
	 * @param status the status of the module
	 * @return whether the module was successfully updated
	 */
	public boolean setModuleInfo(final int x, final int y, final int code, final int rotationsTillUpright, final MODULE_STATUS status) {
		
		if ( x >= 0 && x < width && y >= 0 && y < depth )
		{	
			LinkedList<Module> mods = getModuleList();
			ListIterator<Module> i = mods.listIterator();
			while ( i.hasNext() ) {
				
				Module curr = i.next();
				if(curr.getCode()==code){
					
					removeModule(curr.getXPos(), curr.getYPos());
				}
			}
			modules[x][y] = new Module();
			modules[x][y].setBookeepingXPos(x);
			modules[x][y].setBookeepingYPos(y);
			modules[x][y].setCode(code);
			modules[x][y].setRotationsTillUpright(rotationsTillUpright);
			modules[x][y].setDamageStatus(status);
			getModuleList().add(modules[x][y]);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Moves a module to a new position
	 * @param currX the current xPos of a module
	 * @param currY the current yPos of a module
	 * @param newX the destination xPos of a module
	 * @param newY the destination yPos of a module
	 * @return whether the move was successful
	 */
	public boolean moveModule(final int currX, final int currY, final int newX, final int newY) {
		
		if ( newX >= 0 && newX < width && newY >= 0 && newY < depth &&
			 currX >= 0 && currX < width && currY >= 0 && currY < depth )
		{
			if ( modules[currX][currY] != null &&
				 terrain[newX][newY].isTraversable() && terrain[newX][newY].isTraversable() )
			{
				modules[newX][newY] = modules[currX][currY];
				modules[newX][newY].setBookeepingXPos(newX);
				modules[newX][newY].setBookeepingYPos(newY);
				modules[currX][currY] = null;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Attempts to get a module at the given coordinates.
	 * @param x The given X coordinate.
	 * @param y The given Y coordinate.
	 * @return The module if found, null otherwise.
	 */
	public Module getModule(final int x, final int y) {
		
		if ( x >= 0 && y >= 0 && x<width && y<depth )
			return modules[x][y];
		else
			return null;
	}
	
	/**
	 * Gets the first module with the given code.
	 * @param code the given code.
	 * @return The module if found, null otherwise.
	 */
	public Module getModule(final int code) {
		
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				if ( modules[x][y] != null &&
					 modules[y][y].getCode() == code ) {
					return modules[x][y];
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets a list of all modules currently logged in the landing grid
	 * @return a LinkedList of modules in the landing grid
	 */
	public LinkedList<Module> getModuleList() {
		
		LinkedList<Module> moduleList = new LinkedList<Module>();
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				if ( modules[x][y] != null )
					moduleList.add(modules[x][y]);
			}
		}
		return moduleList;
	}
	
	public LinkedList<Module> getUsableModuleList() {
		
		LinkedList<Module> moduleList = new LinkedList<Module>();
		for ( int y=0; y<depth; y++ )
		{
			for ( int x=0; x<width; x++ )
			{
				if ( modules[x][y] != null && modules[x][y].getStatus() == MODULE_STATUS.Usable )
					moduleList.add(modules[x][y]);
			}
		}
		return moduleList;
	}
	/**
	 * Deletes module from moduleList
	 * @param xPos
	 * @param yPos
	 */
	public void removeModule(final int xPos, final int yPos) {
		
		modules[xPos][yPos] = null;
		getModuleList().remove(modules[xPos][yPos]);
	}
	/**
	 * Converts all modules in the moduleList into string format
	 * @return module information in string format
	 */
	public String generateStorage() {
		 String modlist = "{";
         for(int i = 0; i < getModuleList().size(); i++){
                 modlist += getModuleList().get(i).storageString();
         }
         modlist += "}";
         return modlist;
	}
	/**
	 * Grabs module information from local storage
	 * and converts the string into usable data
	 * @param inModText
	 */
	public void pullStorage(final String inModText) {
		String modtext = inModText.substring(1, inModText.length()-1);
        while(modtext.length() > 0){
                int endBracket = modtext.indexOf('}');
                String oneMod = modtext.substring(1,endBracket);
                modtext = modtext.substring(endBracket+1);
                int endid = oneMod.indexOf(':');
                int endx = oneMod.indexOf(':', endid+1);
                int endy = oneMod.indexOf(':', endx+1);
                int endcond = oneMod.indexOf(':',endy+1);
                int code = Integer.parseInt(oneMod.substring(0,endid));
                int xc = Integer.parseInt(oneMod.substring(endid+1,endx));
                int yc = Integer.parseInt(oneMod.substring(endx+1,endy));
                String cond = oneMod.substring(endy+1, endcond);
                String orient = oneMod.substring(endcond+1,oneMod.length());
//              System.out.println(id+":"+x+":"+y+":"+cond+":"+orient);
                if(orient.equals("0")){
                this.setModuleInfo(xc, yc, code, 0, MODULE_STATUS.valueOf(cond));
                }
                else if(orient.equals("1")){
                	this.setModuleInfo(xc, yc, code, 1, MODULE_STATUS.valueOf(cond));
                }
                else if(orient.equals("2")){
                	this.setModuleInfo(xc, yc, code, 2, MODULE_STATUS.valueOf(cond));
                }
        }
                
	}

	/*private boolean validateString(final int code, final int xc, final int yc) {
		
		return code != 0 && xc != -1 && yc != -1;
	}*/
		
}
