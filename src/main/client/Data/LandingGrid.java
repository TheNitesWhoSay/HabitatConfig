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
		
		width = 100;
		depth = 50;
		
		terrain = new TerrainSquare[width][depth]; // Ensure dimensions equate to the actual landing zone size
		modules = new Module[width][depth]; // Ensure dimensions equate to the actual landing zone size
		
		// Fill the landing grid with default values
		for ( int x=0; x<width; x++ )
		{
			for ( int y=0; y<depth; y++ )
			{
				modules[x][y] = null;
				terrain[x][y] = new TerrainSquare();
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
				 terrain[newX][newY].isTraversable() )
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
	 * Gets a list of all modules currently logged in the landing grid
	 * @return a LinkedList of modules in the landing grid
	 */
	public LinkedList<Module> getModuleList() {
		
		LinkedList<Module> moduleList = new LinkedList<Module>();
		for ( int x=0; x<width; x++ )
		{
			for ( int y=0; y<depth; y++ )
			{
				if (modules[x][y] != null ){
					moduleList.add(modules[x][y]);
			}
		}
		}
		return moduleList;
	}

	public void removeModule(final int xPos, final int yPos) {
		
		modules[xPos][yPos] = null;
	}
		
}
