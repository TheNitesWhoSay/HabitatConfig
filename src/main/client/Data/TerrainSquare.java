package main.client.Data;

/** A single square of terrain within the Landing Grid */
public class TerrainSquare {
	
	private boolean traversable; // Whether the rover can pass through this square
	private boolean buildable; // Whether modules can be permanently setup on this space
	
	/**
	 * Constructs a terrain square that by default can be traversed/built on
	 */
	public TerrainSquare() {
		
		traversable = true;
		buildable = true;
	}
	
	/**
	 * Gets whether the given terrain square can be traversed
	 * @return whether the given terrain square can be traversed
	 */
	public boolean isTraversable() {
		
		return traversable;
	}
	
	/**
	 * Gets whether the given terrain square can be built on
	 * @return whether the given terrain square can be built on
	 */
	public boolean isBuildable() {
		
		return buildable;
	}
	
	/**
	 * Sets whether the given terrain square can be traversed
	 * @param traversable indicates if this terrain square can be traversed
	 */
	public void setTraversable(final boolean traversable) {
		
		this.traversable = traversable;
	}
	
	/**
	 * Sets whether the given terrain square can be built on
	 * @param buildable indicates if this terrain square can be built on
	 */
	public void setBuildable(final boolean buildable) {
		
		this.buildable = buildable;
	}
}
