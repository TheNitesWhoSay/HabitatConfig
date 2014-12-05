package main.client.Data;

import java.util.LinkedList;
import java.util.ListIterator;

import com.google.gwt.user.client.Window;

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
	 * ------- All @ Spaces required -------
	 *  
	 * Space 1   Space 2   Space 3   Space 4
	 * $@@       $@        $@@       $ @
	 * @##@      @#@       @##@       @#@
	 * @#@   or  @##@  or   @#@  or  @##@
	 *  @         @@         @        @@
	 *  
	 * ------- 7/8 @ Spaces Required -------
	 * 
	 * Space 5    Space 6  
	 *            $@
	 * $@@@       @#@      
	 * @###@      @#@
	 *  @@@       @#@       
	 *             @
	 *  
	 * ------ Space 5/6 Slot Numbering -----
	 *  
	 *            $0
	 * $123       7#1      
	 * 0###4      6#2
	 *  765       5#3       
	 *             4
	 *  
	 * Is one more preferrable than the other? TBD.
	 * In the meantime, 1=2=3=4>5>6 will be used
	 */
	private int layoutIndex;
	
	private int layoutX; // The layout x anchor point
	private int layoutY; // The layout y anchor point
	private int clusterAvgX;
	private int clusterAvgY;
	private boolean completedConfig;
	private NearestSquare nearestSquares;
	private MODULE_TYPE[/*x*/][/*y*/] futureModules;
	@SuppressWarnings("unused")
	private int skeletonAvgX;
	@SuppressWarnings("unused")
	private int skeletonAvgY;
	
	@SuppressWarnings("unused")
	private int[/*x*/] airlockXcs;
	@SuppressWarnings("unused")
	private int[/*y*/] airlockYcs;
	
	/**
	 * Constructs a configuration
	 */
	public Configuration(final NearestSquare nearestSquares) {
		
		airlockXcs = new int[4];
		airlockYcs = new int[4];
		futureModules = new MODULE_TYPE[getWidth()][getDepth()];
		for ( int y=0; y<getDepth(); y++ )
		{
			for ( int x=0; x<getWidth(); x++ )
				futureModules[x][y] = null;
		}
		this.nearestSquares = nearestSquares;
		layoutIndex = 0;
		layoutX = -1;
		layoutY = -1;
		clusterAvgX = 0;
		clusterAvgY = 0;
		completedConfig = false;
		skeletonAvgX = getWidth()/2;
		skeletonAvgY = getDepth()/2;
	}
	
	public String getModulesString() {
		
		String modulesString = "";
		for ( int y=0; y<getDepth(); y++ )
		{
			for ( int x=0; x<getWidth(); x++ )
			{
				if ( futureModules[x][y] == null ) {
					modulesString += " ";
				}
				else {
					switch ( futureModules[x][y] )
					{
						case Airlock:
							modulesString += "A"; break;
						case Plain:
							modulesString += "P"; break;
						case Dormitory:
							modulesString += "D"; break;
						case Sanitation:
							modulesString += "S"; break;
						case FoodAndWater:
							modulesString += "F"; break;
						case GymAndRelaxation:
							modulesString += "G"; break;
						case Canteen:
							modulesString += "C"; break;
						case Power:
							modulesString += "W"; break;
						case Control:
							modulesString += "L"; break;
						case Medical:
							modulesString += "M"; break;
						default:
							modulesString += " "; break;
					}
				}
			}
			modulesString += "\r\n";
		}
		return modulesString;
	}
	
	public MODULE_TYPE getFutureModule(int x, int y) {
		
		if ( x >= 0 && x <= getWidth() && y >= 0 && y <= getDepth() &&
			 futureModules[x][y] != null &&
			 futureModules[x][y] != MODULE_TYPE.Reserved &&
			 futureModules[x][y] != MODULE_TYPE.Unknown )
		{
			return futureModules[x][y];
		}
		else
			return null;
	}
	
	public LinkedList<MODULE_TYPE> getFutureModules() {
		
		LinkedList<MODULE_TYPE> futureModuleTypes = new LinkedList<MODULE_TYPE>();
		for ( int y=0; y<getDepth(); y++ )
		{
			for ( int x=0; x<getWidth(); x++ )
			{
				if ( futureModules[x][y] != MODULE_TYPE.Reserved &&
					 futureModules[x][y] != MODULE_TYPE.Unknown )
				{
					futureModuleTypes.add(futureModules[x][y]);
				}
			}
		}
		return futureModuleTypes;
	}
	
	/**
	 * Returns the configuration layout type index.
	 * @return The configuration layout type index.
	 */
	public int getConfigurationLayoutIndex() {
		
		return layoutIndex;
	}
	
	public boolean isCompletedConfig() {
		
		return completedConfig;
	}
	
	public boolean isCornerLayout() {
		
		return layoutIndex >= 1 && layoutIndex <= 4;
	}
	
	public boolean isStraightLayout() {
		
		return layoutIndex >= 5 && layoutIndex <= 6;
	}
	
	public boolean isValidMinimumLayout() {
		
		return layoutIndex >= 1 && layoutIndex <= 6;
	}
	
	/**
	 * Finds a set of minimum modules with the smallest distance
	 * (that can be generated in short time) between them.
	 * @param landingGrid
	 * @return
	 */
	public boolean findMinimumClusterAverage(final LinkedList<Module> usableModules) {
		
		if ( usableModules == null )
			return false;
		else
		{
			int totalXcs = 0;
			int totalYcs = 0;
			int numModules = usableModules.size();
			if ( numModules > 0 ) {
				
				for ( int i=0; i<numModules; i++ ) {
					
					totalXcs += usableModules.get(i).getXPos();
					totalYcs += usableModules.get(i).getYPos();
				}
				
				clusterAvgX = totalXcs/numModules;
				clusterAvgY = totalYcs/numModules;
			}
			else {
				
				clusterAvgX = getWidth()/2;
				clusterAvgY = getDepth()/2;
			}
			return true;
		}
	}
	
	/**
	 * Finds a usable anchor point for this minimum configuration.
	 * @return Whether an anchor point for this layout could be found.
	 */
	public boolean findUsableAnchor() {
		
		/* Directions...
		
			Mirroring scheme
			
		      1 1 1
		 	  1 1 1 0 0 0
			  1 1 1 0 0 0
		 	2 2 2 $ 0 0 0
		 	2 2 2 3 3 3
		 	2 2 2 3 3 3
		 	      3 3 3
		 	      
		 	To translate from mirror part 0...
		 	
		 	1: x =  y, y = -x
		 	2: x = -x, y = -y
		 	3: x = -y, y =  x
		 	      
			Need to circle around the anchor point that is nearest
			to the cluster average until a usable space is found.
		*/
		
		int maxSquareIndex = nearestSquares.getNumSquares();
		for ( int i=0; i<maxSquareIndex; i++ ) {
			
			int x = clusterAvgX + nearestSquares.getX(i);
			int y = clusterAvgY + nearestSquares.getY(i);;
			
			if ( layoutFits(x, y) )
			{
				layoutX = x;
				layoutY = y;
				return true;
			}
			else if ( layoutFits(y, -x) ) // 1
			{
				layoutX = y;
				layoutY = -x;
				return true;
			}
			else if ( layoutFits(-x, -y) ) // 2
			{
				layoutX = -x;
				layoutY = -y;
				return true;
			}
			else if ( layoutFits(-y, x) ) // 3
			{
				layoutX = -y;
				layoutY = x;
				return true;
			}
		}
		
		Window.alert("Couldn't find usable anchor");
		return false;
	}
	
	/**
	 * Attempts to place all the plain modules.
	 * @return Whether the layout type index dictates where they should go.
	 */
	public boolean placePlains() {
		
		switch ( layoutIndex ) {
		case 1:
			futureModules[layoutX+1][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+1][layoutY+2] = MODULE_TYPE.Plain;
			return true;
		case 2:
			futureModules[layoutX+1][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+1][layoutY+2] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+2] = MODULE_TYPE.Plain;
			return true;
		case 3:
			futureModules[layoutX+1][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+2] = MODULE_TYPE.Plain;
			return true;
		case 4:
			futureModules[layoutX+2][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+1][layoutY+2] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+2] = MODULE_TYPE.Plain;
			return true;
		case 5:
			futureModules[layoutX+1][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+2][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+3][layoutY+1] = MODULE_TYPE.Plain;
			return true;
		case 6:
			futureModules[layoutX+1][layoutY+1] = MODULE_TYPE.Plain;
			futureModules[layoutX+1][layoutY+2] = MODULE_TYPE.Plain;
			futureModules[layoutX+1][layoutY+3] = MODULE_TYPE.Plain;
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Attemps to wrap modules around the plains in the specified order.
	 * @param m1 The first module (at present this should always be an airlock).
	 * @param m2 The second module.
	 * @param m3 The third module.
	 * @param m4 The fourth module.
	 * @param m5 The fifth module.
	 * @param m6 The sixth module.
	 * @param m7 The seventh module.
	 * @return Whether module placement planning was successful.
	 */
	public boolean placeWrap( final MODULE_TYPE m1, final MODULE_TYPE m2, final MODULE_TYPE m3, final MODULE_TYPE m4, 
							  final MODULE_TYPE m5, final MODULE_TYPE m6, final MODULE_TYPE m7 )
	{
		switch ( layoutIndex ) {
		case 1:
			futureModules[layoutX+1][layoutY+3] = m1;
			futureModules[layoutX+2][layoutY+2] = m2;
			futureModules[layoutX+3][layoutY+1] = m3;
			futureModules[layoutX+2][layoutY+0] = m4;
			futureModules[layoutX+1][layoutY+0] = m5;
			futureModules[layoutX+0][layoutY+1] = m6;
			futureModules[layoutX+0][layoutY+2] = m7;
			break;
		case 2:
			futureModules[layoutX+1][layoutY+0] = m1;
			futureModules[layoutX+2][layoutY+1] = m2;
			futureModules[layoutX+3][layoutY+2] = m3;
			futureModules[layoutX+2][layoutY+3] = m4;
			futureModules[layoutX+1][layoutY+3] = m5;
			futureModules[layoutX+0][layoutY+2] = m6;
			futureModules[layoutX+0][layoutY+1] = m7;
			break;
		case 3:
			futureModules[layoutX+2][layoutY+3] = m1;
			futureModules[layoutX+1][layoutY+2] = m2;
			futureModules[layoutX+0][layoutY+1] = m3;
			futureModules[layoutX+1][layoutY+0] = m4;
			futureModules[layoutX+2][layoutY+0] = m5;
			futureModules[layoutX+3][layoutY+1] = m6;
			futureModules[layoutX+3][layoutY+2] = m7;
			break;
		case 4:
			futureModules[layoutX+2][layoutY+0] = m1;
			futureModules[layoutX+1][layoutY+1] = m2;
			futureModules[layoutX+0][layoutY+2] = m3;
			futureModules[layoutX+1][layoutY+3] = m4;
			futureModules[layoutX+2][layoutY+3] = m5;
			futureModules[layoutX+3][layoutY+2] = m6;
			futureModules[layoutX+3][layoutY+1] = m7;
			break;
		default:
			return false;
		}
		completedConfig = true;
		return true;
	}
	
	/**
	 * Attemps to wrap modules around the plains in the specified order.
	 * Use MODULE_TYPE.Unknown for the blank space.
	 * @param moduleTypes An ordered list of module types to be wrapped around the plains.
	 * @return Whether module placement planning was successful.
	 */
	public boolean placeWrap(final LinkedList<MODULE_TYPE> moduleTypes)
	{
		if ( moduleTypes.size() < 8 )
			return false;
		
		ListIterator<MODULE_TYPE> it = moduleTypes.listIterator();
		MODULE_TYPE currType = it.next();
		boolean skippedSlot = false;
		for ( int slot=0; slot<8; slot++ ) {
			
			currType = moduleTypes.get(slot);
			if ( !setSlot(slot, currType) )
			{
				if ( !skippedSlot )
					skippedSlot = true;
				else
					return false;
			}
		}
		
		completedConfig = true;
		return true;
	}
	
	/**
	 * Sets the type of minimum configuration this is supposed to become.
	 * @param layoutTypeIndex The type of minimum configuration this is supposed to become.
	 * @return Whether the layoutTypeIndex was one of the possible layouts.
	 */
	public boolean setLayoutType(final int layoutTypeIndex) {
		
		if ( layoutTypeIndex >= 0 && layoutTypeIndex <= 6 ) {
			layoutIndex = layoutTypeIndex;
			return true;
		}
		else
			return false;
	}
	
	public int getMinimumClusterXc() {
		
		return clusterAvgX;
	}
	
	public int getMinimumClusterYc() {
		
		return clusterAvgY;
	}
	
	/**
	 * Checks if a layout fits at a given anchor point.
	 * @param x The xc of the anchor point to be tested.
	 * @param y The yc of the anchor point to be tested.
	 * @return Whether the layout fits in this spot.
	 */
	private boolean layoutFits(final int x, final int y) {
		
		switch ( layoutIndex ) {
		case 1:
			return
										 isBuildable(x+1, y  ) && isBuildable(x+2, y  ) &&
				isBuildable(x  , y+1) && isBuildable(x+1, y+1) && isBuildable(x+2, y+1) && isBuildable(x+3, y+1) &&
				isBuildable(x  , y+2) && isBuildable(x+1, y+2) && isBuildable(x+2, y+2) &&
										 isBuildable(x+1, y+3);
		case 2:
			return
										 isBuildable(x+1, y  ) &&
				isBuildable(x  , y+1) && isBuildable(x+1, y+1) && isBuildable(x+2, y+1) &&
				isBuildable(x  , y+2) && isBuildable(x+1, y+2) && isBuildable(x+2, y+2) && isBuildable(x+3, y+2) &&
										 isBuildable(x+1, y+3) && isBuildable(x+2, y+3);
		case 3:
			return
										 isBuildable(x+1, y  ) && isBuildable(x+2, y  ) &&
				isBuildable(x  , y+1) && isBuildable(x+1, y+1) && isBuildable(x+2, y+1) && isBuildable(x+3, y+1) &&
										 isBuildable(x+1, y+2) && isBuildable(x+2, y+2) && isBuildable(x+3, y+2) &&
										 						  isBuildable(x+2, y+3);
		case 4:
			return
																  isBuildable(x+2, y  ) &&
										 isBuildable(x+1, y+1) && isBuildable(x+2, y+1) && isBuildable(x+3, y+1) &&
				isBuildable(x  , y+2) && isBuildable(x+1, y+2) && isBuildable(x+2, y+2) && isBuildable(x+3, y+2) &&
										 isBuildable(x+1, y+3) && isBuildable(x+2, y+3);
		case 5:
		{
			final boolean goodPlains =
										 
										 isBuildable(x+1, y+1) && isBuildable(x+2, y+1) && isBuildable(x+3, y+1);
				 						 
			
			int numSpaces = 0;
			if ( isBuildable(x+1, y  ) )
				numSpaces ++;
			if ( isBuildable(x+2, y  ) )
				numSpaces ++;
			if ( isBuildable(x+3, y  ) )
				numSpaces ++;
			if ( isBuildable(x  , y+1) )
				numSpaces ++;
			if ( isBuildable(x+4, y+1) )
				numSpaces ++;
			if ( isBuildable(x+1, y+2) )
				numSpaces ++;
			if ( isBuildable(x+2, y+2) )
				numSpaces ++;
			if ( isBuildable(x+3, y+2) )
				numSpaces ++;
			
			return goodPlains && numSpaces >= 7;
		}	
		case 6:
		{
			final boolean goodPlains =
										 
										 isBuildable(x+1, y+1) &&
										 isBuildable(x+1, y+2) && 
										 isBuildable(x+1, y+3);
			
			int numSpaces = 0;
			if ( isBuildable(x+1, y  ) )
				numSpaces ++;
			if ( isBuildable(x  , y+1) )
				numSpaces ++;
			if ( isBuildable(x+2, y+1) )
				numSpaces ++;
			if ( isBuildable(x  , y+2) )
				numSpaces ++;
			if ( isBuildable(x+2, y+2) )
				numSpaces ++;
			if ( isBuildable(x  , y+3) )
				numSpaces ++;
			if ( isBuildable(x+2, y+3) )
				numSpaces ++;
			if ( isBuildable(x+1, y+4) )
				numSpaces ++;
			
			return goodPlains && numSpaces >= 7;
		}
		default:
			return false;
		}
	}
	
	/**
	 * Sets a slot in layout 5 or 6.
	 * @param slotNum The number of the slot to be set.
	 * @param type A module type that will go in this slot.
	 * @return Whether the slot was set successfully.
	 */
	private boolean setSlot(final int slotNum, final MODULE_TYPE type) {
		
		int x = layoutX;
		int y = layoutY;
		MODULE_TYPE newType = null;
		if ( type != MODULE_TYPE.Unknown )
			newType = type;
		
		if ( layoutIndex == 5 ) {
			
			switch ( slotNum ) {
			case 0:
				x += 0;
				y += 1;
				break;
			case 1:
				x += 1;
				y += 0;
				break;
			case 2:
				x += 2;
				y += 0;
				break;
			case 3:
				x += 3;
				y += 0;
				break;
			case 4:
				x += 4;
				y += 1;
				break;
			case 5:
				x += 3;
				y += 2;
				break;
			case 6:
				x += 2;
				y += 2;
				break;
			case 7:
				x += 1;
				y += 2;
				break;
			default:
				return false;
			}
		}
		else if ( layoutIndex == 6 ) {
			switch ( slotNum ) {
			case 0:
				x += 1;
				y += 0;
				break;
			case 1:
				x += 2;
				y += 1;
				break;
			case 2:
				x += 2;
				y += 2;
				break;
			case 3:
				x += 2;
				y += 3;
				break;
			case 4:
				x += 1;
				y += 4;
				break;
			case 5:
				x += 0;
				y += 3;
				break;
			case 6:
				x += 0;
				y += 2;
				break;
			case 7:
				x += 0;
				y += 1;
				break;
			default:
				return false;
			}
		}
		else // Layout index doesn't support this method
			return false;
		
		if ( isBuildable(x, y) ) {
			futureModules[x][y] = newType;
			return true;
		}
		return newType == null;
	}
	
	/**
	 * 
	 */
	/*
	 * 
	 */
	
	public boolean generateSkeleton(final ModuleCount count, final int skeletonType) {
		
		if ( skeletonType == 0 )
			return generateSkeletonZero(count);
		else if ( skeletonType == 1 )
			return generateSkeletonOne(count);
		else
			return false;
	}
	
	private boolean generateSkeletonZero(final ModuleCount count) {
		
		return false;
	}
	
	private boolean generateSkeletonOne(final ModuleCount count) {
		
		return false;
	}
	
	public boolean getSkeletonAverageCoordinates() {
		
		int totalXcs = 0;
		int totalYcs = 0;
		int numPlains = 0;
		for ( int y=0; y<getDepth(); y++ )
		{
			for ( int x=0; x<getWidth(); x++ )
			{
				if ( futureModules[x][y] == MODULE_TYPE.Plain ) {
					totalXcs += x;
					totalYcs += y;
					numPlains ++;
				}
			}
		}
		
		if ( numPlains > 0 ) {
			
			skeletonAvgX = totalXcs / numPlains;
			skeletonAvgY = totalYcs / numPlains;
			return true;
		}
		else
			return false;
	}
	
	public boolean placeAirlocks(final ModuleCount count) {
		
		return false;
	}
	
	public boolean placeSpecialModules(final ModuleCount count) {
		
		return false;
	}
	
	public boolean placeCanteens(final ModuleCount count) {
		
		return false;
	}
	
	public boolean placeFoodAndWater(final ModuleCount count) {
		
		return false;
	}
	
	public boolean pairSanitationToGyms(final ModuleCount count) {
		
		return false;
	}
	
	public boolean pairDormsToSanitations(final ModuleCount count) {
		
		return false;
	}
	
	public boolean placeDormSanitationPairs(final ModuleCount count) {
		
		return false;
	}

}
