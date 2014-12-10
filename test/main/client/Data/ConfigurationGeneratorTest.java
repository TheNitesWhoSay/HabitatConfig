package main.client.Data;

import static org.junit.Assert.*;

import java.util.LinkedList;

import main.client.Data.ModuleStatuses.MODULE_STATUS;

import org.junit.Test;

public class ConfigurationGeneratorTest {

	/*@Test
	public void test() {
		
		int width = 101;
		int depth = 50;
		ConfigGenerator generator = new ConfigGenerator(width, depth);
		
		LandingGrid landingGrid = new LandingGrid();
		
		landingGrid.setModuleInfo(41, 39, 1, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(42, 39, 2, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(43, 39, 3, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(44, 39, 61, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(45, 39, 91, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(46, 39, 111, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(47, 39, 141, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(48, 39, 151, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(49, 39, 161, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(50, 39, 171, 0, MODULE_STATUS.Usable);
		
		assertTrue(landingGrid.hasMinimumConfiguration());
		assertTrue(generator.GenerateTwoMinimumConfigs(landingGrid));
		LinkedList<Configuration> configs = generator.getMinimumConfigs();
		assertTrue(configs.size() == 2);
		System.out.println(configs.get(0).getModulesString());
		System.out.println(configs.get(1).getModulesString());
	}*/
	
	@Test
	public void maxConfigTest() {
		
		System.out.println("Start test");
		int width = 101;
		int depth = 50;
		ConfigGenerator generator = new ConfigGenerator(width, depth);
		
		LandingGrid landingGrid = new LandingGrid();
		
		// Plains
		landingGrid.setModuleInfo(41, 39, 1, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(42, 39, 2, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(43, 39, 3, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(44, 39, 4, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(45, 39, 5, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(46, 39, 6, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(47, 39, 7, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(48, 39, 8, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(49, 39, 9, 2, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(50, 39, 10, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(51, 39, 11, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(52, 39, 12, 2, MODULE_STATUS.Usable);
		
		// Dorms
		landingGrid.setModuleInfo(41, 40, 61, 0, MODULE_STATUS.Usable);
		
		// Sanitations
		landingGrid.setModuleInfo(41, 41, 91, 1, MODULE_STATUS.Usable);
		
		// Food And Water
		landingGrid.setModuleInfo(41, 42, 111, 2, MODULE_STATUS.Usable);
		
		// Gym & Relaxation
		landingGrid.setModuleInfo(41, 43, 131, 0, MODULE_STATUS.Usable);
		
		// Canteen
		landingGrid.setModuleInfo(41, 44, 141, 0, MODULE_STATUS.Usable);
		
		// Power
		landingGrid.setModuleInfo(41, 45, 151, 1, MODULE_STATUS.Usable);
		
		// Control
		landingGrid.setModuleInfo(41, 46, 161, 2, MODULE_STATUS.Usable);
		
		// Airlock
		landingGrid.setModuleInfo(41, 47, 171, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(42, 47, 172, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(43, 47, 173, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(44, 47, 174, 0, MODULE_STATUS.Usable);
		
		// Medical
		landingGrid.setModuleInfo(41, 48, 181, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(42, 48, 182, 0, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(43, 48, 183, 0, MODULE_STATUS.Usable);
		
		
		assertTrue(landingGrid.hasMinimumConfiguration());
		assertTrue(generator.GenerateTwoMaximumConfigs(landingGrid));
		System.out.println("Past assertions");
		LinkedList<Configuration> configs = generator.getMaximumConfigs();
		//assertTrue(configs.size() == 1);
		System.out.println(configs.get(0).getModulesString());
		
	}

}
