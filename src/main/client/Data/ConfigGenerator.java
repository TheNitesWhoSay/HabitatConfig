package main.client.Data;

import java.util.ArrayList;
import java.util.LinkedList;

import main.client.Data.ModuleTypes.MODULE_TYPE;

/**
 * A class for generating multiple, unique configurations.
 * Relys heavily on methods in the Configuration class,
 * this class is required to work with multiple configurations
 * at once.
 */
public class ConfigGenerator {
	
	private NearestSquare nearestSquares;
	private LinkedList<Configuration> minimumConfigs;
	private LinkedList<Configuration> maximumConfigs;
	
	/*	General Assumptions:
		
		It is assumed that quality is more important than build cost.
		It is assumed that the two equilviant habitats built at different locations are of equal quality.
		It is assumed that a lower build cost is desireable when it does not affect quality. */
	
	/**
	 * Constructs a ConfigGenerator
	 */
	public ConfigGenerator(final int width, final int height) {
		
		nearestSquares = new NearestSquare(width, height);
		minimumConfigs = new LinkedList<Configuration>();
		maximumConfigs = new LinkedList<Configuration>();
	}
	
	public LinkedList<Configuration> getMinimumConfigs() {
		
		return minimumConfigs;
	}
	
	public LinkedList<Configuration> getMaximumConfigs() {
		
		return maximumConfigs;
	}
	
	
	/*	Generating Minimum Configurations:
	
	The small nature of the minimum configuration problem allows for all possible layouts to be
	constructed (if there is space somewhere for them) and then for the highest quality
	configurations to be selected.
	
	A minimum configuration consists of 1 airlock, 1 control, 1 power, 1 food & Water storage,
	1 Dormitory, 1 Canteen, 1 Sanitation, and 3 Plain.  This is a total of 10 modules.

	Until a final verdict can be rendered on which types of layouts
	are better than others it will be assumed, all else equal, that 1=2=3=4>5>6
	(see layout index in the Configuration class).

	Step 1: Find a group of minimum modules as clustered as possible.
	Step 2: Pair the best buildable position to all possible layouts, prioritize those with
			no restrictions.
	Step 3: Make a "configuration" by determing the module positions that result in the highest
	 		module quality for each pair
	 		
	 		... There are 216 possible ways to wrap around a corner-shaped minimum config and
	 			1824 possible ways to wrap around a straight-shaped config
	 			
	 			Select the 2 most ideal wraps for a corner-shaped minimum config and the 2 most
	 			ideal wraps for a straight-shaped minimum config.
	 			
	 			Current wrap selections (in order of currently perceived quality/convenience)
	 			
	 			Corner wrap 0   > Airlock, Control, Sanitation, Dormitory, Storage, Canteen, Power        <
	 			Straight wrap 0 > Airlock, _____, Power, Sanitation, Dormitory, Storage, Canteen, Control <
	 			Corner wrap 1   > Airlock, Power, Sanitation, Dormitory, Storage, Canteen, Control        <
				Straight wrap 1 > Airlock, Control, Sanitation, Dormitory, Storage, Canteen, Power, _____ <
	 		
	Step 5: Until only two configurations remain... toss out the configuration of
			lowest quality, if two configurations are of equal quality toss out the
			one with the highest build cost. */
	
	/**
	 * Attempts to generate two possible minimum configurations.
	 * @param landingGrid the landing grid containing information about modules and locations
	 * @return Whether the minimum configuration generation was successful.
	 */
	public boolean GenerateTwoMinimumConfigs(final LandingGrid landingGrid) {
		
		if ( landingGrid == null || !landingGrid.hasMinimumConfiguration() )
			return false;
		
		LinkedList<Module> usableModules = landingGrid.getUsableModuleList();
		
		ArrayList<Configuration> minConfigs = new ArrayList<Configuration>(6);
		for ( int i=0; i<6; i++ ) {
			Configuration config = new Configuration(nearestSquares);
			config.copyTerrain(landingGrid);
			config.copyDamagedModules(landingGrid);
			if ( config.setLayoutType(i+1) &&
				 config.findMinimumClusterAverage(usableModules) &&
				 config.findUsableAnchor() )
			{	
				minConfigs.add(config);
			}
		}
		
		if ( minConfigs.size() < 1 ) // No configurations will be possible
			return false;
		
		int numCornerWrapsUsed = 0;
		int numStraightWrapsUsed = 0;
		for ( int i=minConfigs.size()-1; i>=0; i-- ) {
		
			Configuration config = minConfigs.get(i);
			boolean goodConfig = false;
			int numTypeWrapsUsed = config.isCornerLayout() ? numCornerWrapsUsed:numStraightWrapsUsed;
			
			if ( config.isValidMinimumLayout() )
			{
				if ( config.placePlains() &&
					 placeWrap(numTypeWrapsUsed, config) ) 
				{	
					goodConfig = true;
					if ( config.isCornerLayout() )
						numCornerWrapsUsed ++;
					else
						numStraightWrapsUsed ++;
				}
			}
			
			if ( !goodConfig ) 
				minConfigs.remove(i);
		}
		
		if ( minConfigs.size() > 2 ) {
			
			if ( numCornerWrapsUsed == 2 && numStraightWrapsUsed == 2 && minConfigs.size() == 4 ) {
				minConfigs.remove(3); // Remove the 2nd straight
				minConfigs.remove(1); // Remove the 1st corner
			}
			else if ( numCornerWrapsUsed == 2 && numStraightWrapsUsed == 1 && minConfigs.size() == 3 ) {
				minConfigs.remove(1); // Remove the 2nd corner
			}
			else if ( numCornerWrapsUsed == 1 && numStraightWrapsUsed == 2 && minConfigs.size() == 3 ) {
				minConfigs.remove(2); // Remove the 2nd straight
			}
		}
		
		minimumConfigs.clear();
		if ( minConfigs.size() == 1 ) {
			minimumConfigs.add(minConfigs.get(0));
			return true; // ish...
		}
		else if ( minConfigs.size() >= 2 ) {
			minimumConfigs.add(minConfigs.get(0));
			minimumConfigs.add(minConfigs.get(1));
			return true;
		}
		else
			return false;
	}
	
	private boolean placeWrap(final int wrapNum, final Configuration config) {
		
		if ( config.isCornerLayout() ) {
			
			if ( wrapNum == 0 )
				return placeCornerWrapZero(config);
			else if ( wrapNum == 1 )
				return placeCornerWrapOne(config);
		}
		else if ( config.isStraightLayout() ) {
			
			if ( wrapNum == 0 )
				return placeStraightWrapZero(config);
			else if ( wrapNum == 1 )
				return placeStraightWrapOne(config);
		}
		return false;
	}
	
	private boolean placeCornerWrapZero(final Configuration config) {
		
		return config.placeWrap(
				MODULE_TYPE.Airlock,
				MODULE_TYPE.Control,
				MODULE_TYPE.Sanitation,
				MODULE_TYPE.Dormitory,
				MODULE_TYPE.FoodAndWater,
				MODULE_TYPE.Canteen,
				MODULE_TYPE.Power
			);
	}
	
	private boolean placeCornerWrapOne(final Configuration config) {
		
		return config.placeWrap(
			MODULE_TYPE.Airlock,
			MODULE_TYPE.Power,
			MODULE_TYPE.Sanitation,
			MODULE_TYPE.Dormitory,
			MODULE_TYPE.FoodAndWater,
			MODULE_TYPE.Canteen,
			MODULE_TYPE.Control
		);
	}
	
	private boolean placeStraightWrapZero(final Configuration config) {
		
		LinkedList<MODULE_TYPE> moduleTypes = new LinkedList<MODULE_TYPE>();
		moduleTypes.add(MODULE_TYPE.Airlock);
		moduleTypes.add(MODULE_TYPE.Unknown);
		moduleTypes.add(MODULE_TYPE.Power);
		moduleTypes.add(MODULE_TYPE.Sanitation);
		moduleTypes.add(MODULE_TYPE.Dormitory);
		moduleTypes.add(MODULE_TYPE.FoodAndWater);
		moduleTypes.add(MODULE_TYPE.Canteen);
		moduleTypes.add(MODULE_TYPE.Control);
		return config.placeWrap(moduleTypes);
	}
	
	private boolean placeStraightWrapOne(final Configuration config) {
		
		LinkedList<MODULE_TYPE> moduleTypes = new LinkedList<MODULE_TYPE>();
		moduleTypes.add(MODULE_TYPE.Airlock);
		moduleTypes.add(MODULE_TYPE.Control);
		moduleTypes.add(MODULE_TYPE.Sanitation);
		moduleTypes.add(MODULE_TYPE.Dormitory);
		moduleTypes.add(MODULE_TYPE.FoodAndWater);
		moduleTypes.add(MODULE_TYPE.Canteen);
		moduleTypes.add(MODULE_TYPE.Power);
		moduleTypes.add(MODULE_TYPE.Unknown);
		return config.placeWrap(moduleTypes);
	}
	
	/*	Generating Maximum Configurations:
 	
 	To generate optimal maximum configurations one would have to generate all possible configurations,
 	then compare their quality selecting the highest. This is assumed impossible within the 1 minute time limit,
 	because of this, all generated maximum configurations will in all likelyhood be of lower quality than is optimal,
 	thus as best quality as can be achieved in the time limit will be saught after.
 	
 	At the most basic level a configuration requires connected plain modules, thus a way of ordering these should be
 	determined first, giving as much consideration as possible to how their layout will impact quality.
 	
 	Step 1: Determine a skeleton (of plain modules) for the habitat, ensure quality rule 4 (viewing one part from another)
 			is satisfiable, never leave a buildable space such that no window could have a view.
 	Step 2: Determine the average xc and yc for the skeleton
 	Step 3: Add airlocks... If the habitat has more width than depth, choose a location as far as possible from the
 			average xc, left if equal (and then as close as possible to the average yc), then add one on the opposite end
 			(again as close as possible to the average yc), then choose a location as far as possible from the average yc,
 			top if equal, (and then as close as possible to the average xc), then add one on the opposite end (again as
 			close as possible to the average xc) if more depth than width is present, perform the 3rd/4th placement 1st/2nd,
 			and vise versa. Damaged airlocks may have to be left off, leave off the last(s) in the order.
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
 	Step 10: Repeat step 9
 	
 	Note: If at any point in 7-10 a medical module is not available (yet the others based on it still are), choose the location the
 		  med module would go at, and proceed from there.
 		  
 	Step 11: Place all canteen modules as far from sanitation modules as possible, never allowing them to touch diagonally.
 	Step 12: Place all food and water storage's as far from the sanitation modules as possible, never allowing them to touch diagonally,
 			 keep a record of the last 4 placed.
 	Step 13: Validate that all canteens have a food and water storage 3 spaces away, if not move the last food&waters placed into an
 			 acceptable position. If none are acceptable, randomely search for an acceptable location for the food&water and canteen
 	Step 14: Place 2 dorms as close as possible to each existing sanitation, ensure airlock is not next to (including diagonally)
 			 a dorm. Record the position of each existing sanitation.
 	Step 15: Rotating to each of the existing sanitations, place pairs 1 sanitation and 2 dorms as close as possible to the existing
 			 sanitation's position, avoid being next to (including diagonally) canteens and food&water with the sanition's */
	
	/**
	 * Attempts to generate two maximum configurations.
	 * @param landingGrid the landing grid containing information about modules and locations
	 * @return Whether the config generation was successful.
	 */
	public boolean GenerateTwoMaximumConfigs(final LandingGrid landingGrid) {
		
		if ( landingGrid == null || !landingGrid.hasMinimumConfiguration() )
			return false;
		
		ArrayList<Configuration> maxConfigs = new ArrayList<Configuration>(2);
		for ( int i=0; i<2; i++ ) {
			Configuration config = new Configuration(nearestSquares);
			maxConfigs.add(config);
		}
		
		/**
		 * Test Code Start
		 */
		
		/*System.out.println("Test start");
		Configuration configr = new Configuration(nearestSquares);
		ModuleCount countr = landingGrid.getUsableModuleCount();
		if ( !configr.generateSkeleton(countr, 0) )
			System.out.println("Bad skeleton generation");
		if ( !configr.getSkeletonAverageCoordinates() )
			System.out.println("Bad skeleton average coordinates");
		if ( !configr.placeAirlocks(countr) )
			System.out.println("Bad airlocks");
		if ( !configr.placeSpecialModules(countr) )
			System.out.println("Bad special modules");
		if ( !configr.placeEateries(countr) )
			System.out.println("Bad eateries");
		if ( !configr.pairDormsToSanitations(countr) )
			System.out.println("bad sanitations");
		if ( !configr.placeDormSanitationPairs(countr) )
			System.out.println("Bad pairing");
		
		System.out.println(configr.getModulesString());*/
		
		/**
		 * Test Code End
		 */
		
		for ( int i=maxConfigs.size()-1; i>=0; i-- ) {
			
			Configuration config = maxConfigs.get(i);
			ModuleCount count = landingGrid.getUsableModuleCount();
			
			if ( !( config.findMinimumClusterAverage(landingGrid.getUsableModuleList()) &&
					config.generateSkeleton(count, i) &&
					config.getSkeletonAverageCoordinates() &&
					config.placeAirlocks(count) &&
					config.placeSpecialModules(count) &&
					config.placeEateries(count) &&
					config.pairDormsToSanitations(count) &&
					config.placeDormSanitationPairs(count)
				  )
			   )
			{
				maxConfigs.remove(i);
			}
		}
	
		maximumConfigs.clear();
		if ( maxConfigs.size() == 1 ) {
			maximumConfigs.add(maxConfigs.get(0));
			return true; // ish...
		}
		else if ( maxConfigs.size() >= 2 ) {
			maximumConfigs.add(maxConfigs.get(0));
			maximumConfigs.add(maxConfigs.get(1));
			return true;
		}
		else
			return false;
	}
	
}
