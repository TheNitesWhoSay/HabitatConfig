package main.client.Data;

/**
 * A stored habitat configuration
 * 
 * A habitat configuration is a LandingGrid
 * with some extra functionality
 */
public class Configuration extends LandingGrid {

	/**
	 * A number representing one of the following minimum configuration layouts,
	 * this is zero if the configuration is not minimum or hasn't been generated.
	 * 
	 * Diagram key:
	 * #: Plain
	 * @: Non-Plain
	 * $: Where an x and y value for a layout would point to (anchor)
	 * 
	 *  
	 * ------- All @ spaces required -------
	 *  
	 * Space 1   Space 2   Space 3   Space 4
	 * $@@       $@        $@@       $ @
	 * @##@      @#@       @##@       @#@
	 * @#@   or  @##@  or   @#@  or  @##@
	 *  @         @@         @        @@
	 *  
	 *  
	 * ------- 7/8 @ spaces Required -------
	 * 
	 * Space 5    Space 6  
	 *            $@
	 * $@@@       @#@      
	 * @###@  or  @#@
	 *  @@@       @#@       
	 *             @
	 *  
	 * Is one more preferrable than the other? TBD.
	 * In the meantime, 1=2=3=4>5>6 will be used
	 */
	@SuppressWarnings("unused")
	private int layoutIndex;
	
	/**
	 * The layout x anchor point
	 */
	@SuppressWarnings("unused")
	private int layoutX;
	
	/**
	 * The layout y anchor point
	 */
	@SuppressWarnings("unused")
	private int layoutY;
	
	/**
	 * Constructs a configuration
	 */
	public Configuration() {
		
		layoutIndex = 0;
		layoutX = -1;
		layoutY = -1;
	}
	
	/**
	 * Generates a minimum configuration from the given landing grid
	 * @param landingGrid the landing grid containing information about modules & locations
	 * @return whether the landing grid generation was successful
	 */
	public boolean generateMinConfig(final LandingGrid landingGrid) {
		
		if ( landingGrid == null || !landingGrid.hasMinimumConfiguration() )
			return false;
		
		this.copyTerrain(landingGrid);
		
		@SuppressWarnings("unused")
		ModuleCount count = landingGrid.getUsableModuleCount();
		
		return true;
	}

}
