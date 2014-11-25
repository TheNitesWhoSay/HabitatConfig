package main.client.Data;

import java.util.LinkedList;

public class ConfigGenerator {
	
	/*	General Assumptions:
		
		It is assumed that quality is more important than build cost.
		It is assumed that the two equilviant habitats built at different locations are of equal quality.
		It is assumed that a lower build cost is desireable when it does not affect quality. */
	
	/**
	 * Constructs a ConfigGenerator
	 */
	public ConfigGenerator() {
		
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
	Step 4: For every combination of two configurations... ensure that they are not
			equivilant but rotated, if they are, toss out the one with the higher build
			cost until none are equivilant&rotated or only two configurations remain.
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
		
		Configuration firstMinConfig = new Configuration();
		firstMinConfig.copyTerrain(landingGrid);
		
		LinkedList<Module> modules = landingGrid.getModuleList();
		firstMinConfig.findMinimumClusterAverage(landingGrid);
		int clusterXc = firstMinConfig.getMinimumClusterXc(),
			clusterYc = firstMinConfig.getMinimumClusterYc();
		
		Configuration[] config = new Configuration[6];
		for ( int i=0; i<6; i++ ) {
			config[i].setLayoutType(i+1);
			config[i].copyTerrain(landingGrid);
			config[i].copyDamagedModules(landingGrid);
		}
		
		return false;
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
		
		return false;
	}
}
