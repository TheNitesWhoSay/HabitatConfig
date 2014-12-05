package main.client.Data;

import static org.junit.Assert.*;

import java.util.LinkedList;

import main.client.Data.ModuleStatuses.MODULE_STATUS;

import org.junit.Test;

public class ConfigurationGeneratorTest {

	@Test
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
	}

}
