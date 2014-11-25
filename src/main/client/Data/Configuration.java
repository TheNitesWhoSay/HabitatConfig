package main.client.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

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
	
	private int clusterAvgX;
	private int clusterAvgY;
	
	/**
	 * Constructs a configuration
	 */
	public Configuration() {
		
		layoutIndex = 0;
		layoutX = -1;
		layoutY = -1;
		clusterAvgX = 0;
		clusterAvgY = 0;
	}
	
	/**
	 * Finds a set of minimum modules with the smallest distance
	 * (that can be generated in short time) between them.
	 * @param landingGrid
	 * @return
	 */
	public boolean findMinimumClusterAverage(LandingGrid landingGrid) {
		
		clusterAvgX = landingGrid.getWidth()/2;
		clusterAvgY = landingGrid.getDepth()/2;
		return true;
	}
	
	/**
	 * Sets the type of minimum configuration this is supposed to become.
	 * @param layoutTypeIndex The type of minimum configuration this is supposed to become.
	 * @return Whether the layoutTypeIndex was one of the possible layouts.
	 */
	public boolean setLayoutType(int layoutTypeIndex) {
		
		if ( layoutTypeIndex >= 0 && layoutTypeIndex <= 6 ) {
			layoutIndex = layoutTypeIndex;
			return true;
		}
		else
			return false;
	}
	
	public boolean selectBuildZone() {
		
		return false;
	}
	
	public int getMinimumClusterXc() {
		
		return clusterAvgX;
	}
	
	public int getMinimumClusterYc() {
		
		return clusterAvgY;
	}

}
