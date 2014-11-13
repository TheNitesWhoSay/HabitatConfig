package main.client.Data;

/** Holds information about terrain and
	modules within the landing zone */
public class LandingGrid {

	private int width;
	private int depth;
	@SuppressWarnings("unused")
	private TerrainSquare terrain[/*X*/][/*Y*/];
	@SuppressWarnings("unused")
	private Module modules[/*X*/][/*Y*/];
	
	public LandingGrid() {
		
		width = 0; // Replace with actual landing zone width
		depth = 0; // Replace with actual landing zone depth
		
		terrain = new TerrainSquare[width][depth]; // Ensure dimensions equate to the actual landing zone size
		modules = new Module[width][depth]; // Ensure dimensions equate to the actual landing zone size
	}
}
