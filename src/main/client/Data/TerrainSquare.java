package main.client.Data;

/** A single square of terrain within the Landing Grid */
public class TerrainSquare {
	
	private boolean traversable; // Whether the rover can pass through this square
	private boolean buildable; // Whether modules can be permanently setup on this space
	
	public TerrainSquare() {
		traversable = true;
		buildable = true;
	}
	
	public boolean isTraversable() {
		return traversable;
	}
	
	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}
	
	public boolean isBuildable() {
		return buildable;
	}
	
	public void setBuildable(boolean buildable) {
		this.buildable = buildable;
	}
}
