package main.client.Data;

import java.util.LinkedList;

import main.client.Data.ModuleStatuses.MODULE_STATUS;

/**
 * Holds information about terrain and modules within the landing zone
 */
public class LandingGrid {

	private int width;
	private int depth;
	private TerrainSquare terrain[/*X*/][/*Y*/];
	private Module modules[/*X*/][/*Y*/];
	
	/**
	 * Constructs a basic landing grid,
	 * fills it with buildingable, traversable terrain and null modules
	 */
	public LandingGrid() {
		
		width = 0; // Replace with actual landing zone width
		depth = 0; // Replace with actual landing zone depth
		
		terrain = new TerrainSquare[width][depth]; // Ensure dimensions equate to the actual landing zone size
		modules = new Module[width][depth]; // Ensure dimensions equate to the actual landing zone size
	}
	
	public boolean setModuleInfo(int x, int y, int code, int rotationsTillUpright, MODULE_STATUS status) {
		
		if ( x >= 0 && x < width && y >= 0 && y < depth )
		{
			modules[x][y].setCode(code);
			modules[x][y].setRotationsTillUpright(rotationsTillUpright);
			modules[x][y].setDamageStatus(status);
			return true;
		}
		else
			return false;
	}
	
	public LinkedList<Module> getModuleList() {
		
		LinkedList<Module> moduleList = new LinkedList<Module>();
		for ( int x=0; x<width; x++ )
		{
			for ( int y=0; y<depth; y++ )
			{
				if ( modules[x][y] != null )
					moduleList.add(modules[x][y]);
			}
		}
		return moduleList;
	}
}
