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
	
	private int nearestUsableX;
	private int nearestUsableY;
	
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
		
		if ( layoutFits(clusterAvgX, clusterAvgY) )
		{
			layoutX = clusterAvgX;
			layoutY = clusterAvgY;
			return true;
		}
		
		/*
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
	
	/*
	 * ################################################################
	 */
	
	/**
	 * Generates a layout for the plain modules.
	 * @param count The amount of modules available.
	 * @param skeletonType The index of the type of skeleton to generate (ensures uniqueness for different numbers).
	 * @return Whether the skeleton could be generated.
	 */
	public boolean generateSkeleton(final ModuleCount count, final int skeletonType) {
		
		if ( skeletonType == 0 )
			return generateSkeletonZero(count);
		else if ( skeletonType == 1 )
			return generateSkeletonOne(count);
		else
			return false;
	}
	
	/**
	 * Generates a layout of plain modules in a antennae layout.
	 * @param count The amount of modules available.
	 * @return Whether generation was successful.
	 */
	private boolean generateSkeletonZero(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Generates a layout of plain modules in a swastika layout.
	 * @param count The amount of modules available.
	 * @return Whether generation was successful.
	 */
	private boolean generateSkeletonOne(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Gets the average coordinates of a given skeleton.
	 * @return Whether the averaeg coordinates were successfully generated.
	 */
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
	
	/**
	 * Places airlocks onto the skeleton.
	 * @param count The amount of modules available.
	 * @return Whether airlocks were placed successfully.
	 */
	public boolean placeAirlocks(final ModuleCount count) {
		/* Step 3: Add airlocks... If the habitat has more width than depth, choose a location as far as possible from the
 			average xc, left if equal (and then as close as possible to the average yc), then add one on the opposite end
 			(again as close as possible to the average yc), then choose a location as far as possible from the average yc,
 			top if equal, (and then as close as possible to the average xc), then add one on the opposite end (again as
 			close as possible to the average xc) if more depth than width is present, perform the 3rd/4th placement 1st/2nd,
 			and vise versa. Damaged airlocks may have to be left off, leave off the last(s) in the order.
		 */
		
		int topYc = getTopmostRow();
		int bottomYc = getBottomostRow();
		int leftXc = getLeftmostColumn();
		int rightXc = getRightmostColumn();
		
		int configWidth = rightXc-leftXc;
		int configHeight = bottomYc-topYc;
		
		boolean placeWideOverTall = configWidth > configHeight;
		boolean placeTopOrLeft = true;
		if ( placeWideOverTall )
			placeTopOrLeft = Math.abs(topYc-skeletonAvgY) > Math.abs(bottomYc-skeletonAvgY);
		else
			placeTopOrLeft = Math.abs(leftXc-skeletonAvgX) > Math.abs(rightXc-skeletonAvgX);
		
		int numPlaced = 0;
		for ( int i=0; i<4; i++ ) {
			
			if ( placeWideOverTall ) {
				if ( placeTopOrLeft ) {
					// Leftmost
					int xc = leftXc;
					int yc = closestPlainYc(skeletonAvgY, xc);
					if ( findNearestUsableSpace(xc, yc) ) {
						
						if ( count.useAirlock() )
							futureModules[nearestUsableX][nearestUsableY] = MODULE_TYPE.Airlock;
						
						airlockXcs[numPlaced] = nearestUsableX;
						airlockYcs[numPlaced] = nearestUsableY;
						numPlaced ++;
					}
				}
				else {
					// Rightmost
					int xc = rightXc;
					int yc = closestPlainYc(skeletonAvgY, xc);
					if ( findNearestUsableSpace(xc, yc) ) {
						
						if ( count.useAirlock() )
							futureModules[nearestUsableX][nearestUsableY] = MODULE_TYPE.Airlock;
						
						airlockXcs[numPlaced] = nearestUsableX;
						airlockYcs[numPlaced] = nearestUsableY;
						numPlaced ++;
					}
				}
				
				if ( numPlaced%2 == 0 ) // Multiple of two has been placed
					placeWideOverTall = false;
			}
			else {
				if ( placeTopOrLeft ) {
					// Topmost
					int yc = topYc;
					int xc = closestPlainXc(skeletonAvgX, yc);
					if ( findNearestUsableSpace(xc, yc) ) {
						
						if ( count.useAirlock() )
							futureModules[nearestUsableX][nearestUsableY] = MODULE_TYPE.Airlock;
						
						airlockXcs[numPlaced] = nearestUsableX;
						airlockYcs[numPlaced] = nearestUsableY;
						numPlaced ++;
					}
				}
				else {
					// Bottommost
					int yc = bottomYc;
					int xc = closestPlainXc(skeletonAvgY, yc);
					if ( findNearestUsableSpace(xc, yc) ) {
						
						if ( count.useAirlock() )
							futureModules[nearestUsableX][nearestUsableY] = MODULE_TYPE.Airlock;
						
						airlockXcs[numPlaced] = nearestUsableX;
						airlockYcs[numPlaced] = nearestUsableY;
						numPlaced ++;
					}
				}
				
				if ( numPlaced%2 == 0 ) // Multiple of two has been placed
					placeWideOverTall = false;
			}
		}
		
		return false;
	}
	
	/**
	 * Places most special modules (Medical, Control, Power, Gym & Relaxation).
	 * @param count The amount of modules available.
	 * @return Whether the special modules were placed successfully.
	 */
	public boolean placeSpecialModules(final ModuleCount count) {
		/*
		Step 4: Place one medical module diagonally next to the airlocks, prioritize the order the airlocks were placed in
	 			if all airlocks dissallow this placement move a plain module from the first airlock somewhere such that
	 			rule 4 is still satisfiable
	 	Step 5: Place one power and one control module as close to the medical module as possible
	 	Step 6: Place one gym&relaxation as close to the medical module as possible, while having a horizontal space next to it
	 	Step 7: Place one sanitation module in the horizontal space closest to the medical module, that is horizontally next to the
	 			gym&relaxation module
	 	Step 8: Place the next medical module as "opposite" as possible to the first, repeat 5-7, but attempt a clockwise
	 			orientation of the power, control, and gym&relaxation modules from the manner they were next to the first medical
	 	Step 9: Place the next medical module (if available) at the maximum distance from the first two, repeat 5-7 with the clockwise attempt
	 	Step 10: Repeat step 9 */
		
		if ( count.getNumMedical() > 0 ) {
			
			for ( int i=0; i<4; i++ ) { // Check all airlocks
				
				int maxXc = getWidth()-1;
				int maxYc = getDepth()-1;
				int x = airlockXcs[i];
				int y = airlockYcs[i];
				if ( x > 0 && x < maxXc && y > 0 && y < maxYc ) {
					
					if ( x > 1 && y > 1 ) { // Can use top left
						if ( futureModules[x-1][y-1] == null ) {
							
						}
					}
					else if ( x > 1 ) { // Can use bottom left
						if ( futureModules[x-1][y+1] == null ) {
							
						}
					}
					else if ( y > 1 ) { // Can use top right
						if ( futureModules[x+1][y-1] == null ) {
							
						}
					}
					
					// Can use bottom right by outer check
					if ( futureModules[x+1][y+1] == null ) {
						
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Places all the canteen modules.
	 * @param count The amount of modules available.
	 * @return Whether canteens were placed successfully.
	 */
	public boolean placeCanteens(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Places all food and water modules
	 * @param count The amount of modules available.
	 * @return Whether food and water modules were placed successfully.
	 */
	public boolean placeFoodAndWater(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Places a sanitation with each existing gym if possible.
	 * @param count The amount of modules available.
	 * @return Whether the santiations could be placed.
	 */
	public boolean pairSanitationToGyms(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Places dorms with the already placed sanitations.
	 * @param count The amount of modules available.
	 * @return Whether the dorms could be placed.
	 */
	public boolean pairDormsToSanitations(final ModuleCount count) {
		
		return false;
	}
	
	/**
	 * Places the rest of the dorms and sanitation pairs.
	 * @param count The amount of modules available.
	 * @return Whether the dorms and sanitation pairs could be placed.
	 */
	public boolean placeDormSanitationPairs(final ModuleCount count) {
		
		return false;
	}
	
	private int getConfigurationWidth() {
		
		int min = 0;
		for ( int x=0; x<getWidth(); x++ ) {
			
			for ( int y=0; y<getDepth(); x++ ) {
				
				if ( futureModules[x][y] != null ) {
					min = x;
					break;
				}
			}
		}
		
		for ( int x=min+1; x<getWidth(); x++ ) {
			
			for ( int y=0; y<getDepth(); y++ ) {
				
				if ( futureModules[x][y] != null )
					return x-min;
			}
		}
		
		return 0;
	}
	
	private int getConfigurationHeight() {
		
		int min = 0;
		
		for ( int y=0; y<getDepth(); y++ ) {
			
			for ( int x=0; x<getWidth(); x++ ) {
				
				if ( futureModules[x][y] != null )
					min = y;
			}
		}
		
		for ( int y=min+1; y<getDepth(); y++ ) {
			
			for ( int x=0; x<getWidth(); x++ ) {
				
				if ( futureModules[x][y] != null )
					return y-min;
			}
		}
		
		return 0;
	}
	
	private int getTopmostRow() {
		
		for ( int y=0; y<getDepth(); y++ ) {
			
			for ( int x=0; x<getWidth(); x++ ) {
				
				if ( futureModules[x][y] != null )
					return y;
			}
		}
		return 0;
	}
	
	private int getBottomostRow() {
		
		for ( int y=getDepth()-1; y>=0; y-- ) {
			
			for ( int x=0; x<getWidth(); x++ ) {
				
				if ( futureModules[x][y] != null )
					return y;
			}
		}
		return 0;
	}
	
	private int getLeftmostColumn() {
		
		for ( int x=0; x<getWidth(); x++ ) {
			
			for ( int y=0; y<getDepth(); y++ ) {
				
				if ( futureModules[x][y] != null )
					return x;
			}
		}
		return 0;
	}
	
	private int getRightmostColumn() {
		
		for ( int x=getWidth()-1; x>=0; x-- ) {
			
			for ( int y=0; y<getDepth(); y++ ) {
				
				if ( futureModules[x][y] != null )
					return x;
			}
		}
		return 0;
	}
	
	private int closestPlainXc(int nearXc, int withinRow) {
		
		int closestX = getWidth();
		for ( int x=(nearXc-1); x>=0; x-- ) {
			
			if ( futureModules[x][withinRow] != null &&
				 futureModules[x][withinRow] == MODULE_TYPE.Plain &&
				 (nearXc-x) < closestX )
			{
				closestX = x;
				break;
			}
		}
		
		for ( int x=nearXc+1; x<getWidth(); x++ ) {
			
			if ( futureModules[x][withinRow] != null &&
				 futureModules[x][withinRow] == MODULE_TYPE.Plain &&
				 (x-nearXc) < closestX )
			{
				return x;
			}
		}
		
		if ( closestX != getWidth() )
			return closestX;
		else
			return 0;
	}
	
	private int closestPlainYc(int nearYc, int withinColumn) {
		
		int closestY = getDepth();
		
		for ( int y=(nearYc-1); y>=0; y-- ) {
			
			if ( futureModules[withinColumn][y] != null &&
				 futureModules[withinColumn][y] == MODULE_TYPE.Plain &&
				 (nearYc-y) < closestY )
			{
				closestY = y;
				break;
			}
		}
		
		for ( int y=nearYc+1; y<getWidth(); y++ ) {
			
			if ( futureModules[withinColumn][y] != null &&
				 futureModules[withinColumn][y] == MODULE_TYPE.Plain &&
				 (y-nearYc) < closestY )
			{
				return y;
			}
			else if ( (y-nearYc) > (closestY-nearYc) )
				return closestY;
		}
		
		if ( closestY != getDepth() )
			return closestY;
		else
			return 0;
	}
	
	/**
	 * Crawls to the nearest usable space (for non-plain modules).
	 * @param x The current xc, must be attatched to a plain.
	 * @param y The current yc, must be attatched to a plain.
	 * @return Whether a usable space could be found.
	 */
	private boolean findNearestUsableSpace(int xc, int yc) {
		
		/*
		 * Consider optimizing this by following plain modules
		 * rather than checking every nearest square in a brute
		 * force manner.
		 */
		int numSquares = nearestSquares.getNumSquares();
		int width = getWidth();
		int depth = getDepth();
		for ( int i=0; i<numSquares; i++ ) {
			
			int xCheck = xc+nearestSquares.getX(i);
			int yCheck = yc+nearestSquares.getY(i);
			
			if ( xCheck > 0 && xCheck < width && yCheck > 0 && yCheck < depth ) {
				
				if ( futureModules[xCheck][yCheck] == null &&
					 canPlaceNonPlain(xCheck, yCheck) )
				{
					nearestUsableX = xCheck;
					nearestUsableY = yCheck;
					return true;
				}
				/*else if ( futureModules[xCheck][yCheck] == MODULE_TYPE.Plain ) {
					// Branch from this new plain ?
					
				}*/
			}
		}
		return false;
	}
	
	/**
	 * Checks if a space is usable for a non-plain module
	 * @param xc
	 * @param yc
	 * @return
	 */
	private boolean canPlaceNonPlain(int xc, int yc) {
		
		int maxX = getWidth()-1;
		int maxY = getDepth()-1;
		if ( xc > 0 && yc > 0 ) {
			
			if ( xc < maxX && yc < maxY ) {
				
				return isBuildable(xc, yc) &&
					   ( futureModules[xc][yc-1] != null &&
					     futureModules[xc][yc-1] == MODULE_TYPE.Plain ) ||
					   ( futureModules[xc-1][yc] != null &&
					     futureModules[xc-1][yc] == MODULE_TYPE.Plain ) ||
					   ( futureModules[xc+1][yc] != null &&
					     futureModules[xc+1][yc] == MODULE_TYPE.Plain ) ||
					   ( futureModules[xc][yc+1] != null &&
					     futureModules[xc][yc+1] == MODULE_TYPE.Plain );
			}
			else if ( xc <= maxX && yc <= maxY ) { // On the right or bottom edge
				
				if ( xc == maxX && yc < maxY ) { // On the right edge, not bottom
					
					return isBuildable(xc, yc) &&
						   ( futureModules[xc][yc-1] != null &&
						     futureModules[xc][yc-1] == MODULE_TYPE.Plain ) ||
						   ( futureModules[xc-1][yc] != null &&
						     futureModules[xc-1][yc] == MODULE_TYPE.Plain ) ||
						   ( futureModules[xc][yc+1] != null &&
						     futureModules[xc][yc+1] == MODULE_TYPE.Plain );
				}
				else if ( xc < maxX && yc == maxY ) { // On the bottom edge, not right
					
					return isBuildable(xc, yc) &&
						   ( futureModules[xc][yc-1] != null &&
						     futureModules[xc][yc-1] == MODULE_TYPE.Plain ) ||
						   ( futureModules[xc-1][yc] != null &&
						     futureModules[xc-1][yc] == MODULE_TYPE.Plain ) ||
						   ( futureModules[xc+1][yc] != null &&
						     futureModules[xc+1][yc] == MODULE_TYPE.Plain );
				}
				else { // In bottom right corner
					
					return isBuildable(xc, yc) &&
						   ( futureModules[xc][yc-1] != null &&
						     futureModules[xc][yc-1] == MODULE_TYPE.Plain ) ||
						   ( futureModules[xc-1][yc] != null &&
						     futureModules[xc-1][yc] == MODULE_TYPE.Plain );
				}
			}
		}
		
		return false;
	}
	
}
