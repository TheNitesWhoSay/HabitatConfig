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
		
		landingGrid.setModuleInfo(5, 6, 10, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(6, 6, 11, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(7, 6, 12, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(8, 6, 171, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(9, 6, 161, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(10, 6, 151, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(11, 6, 111, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(12, 6, 61, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(13, 6, 141, 1, MODULE_STATUS.Usable);
		landingGrid.setModuleInfo(14, 6, 91, 1, MODULE_STATUS.Usable);
		
		assertTrue(landingGrid.hasMinimumConfiguration());
		assertTrue(generator.GenerateTwoMinimumConfigs(landingGrid));
		LinkedList<Configuration> configs = generator.getMinimumConfigs();
		assertTrue(configs.size() == 2);
		System.out.println(configs.get(0).getModulesString());
		System.out.println(configs.get(1).getModulesString());
	}

}
