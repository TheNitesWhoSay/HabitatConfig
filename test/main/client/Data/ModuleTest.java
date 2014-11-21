package main.client.Data;

import static org.junit.Assert.*;
import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import org.junit.Test;

public class ModuleTest {

	public ModuleTest() {
		
	}
	
	@Test
	public void testModuleConstructorAndAccessors() {
		
		Module module = new Module();
		assertTrue(module.getXPos() == -1);
		assertTrue(module.getYPos() == -1);
		assertTrue(module.getType() == MODULE_TYPE.Unknown);
		assertTrue(module.getRotationsTillUpright() == -1);
		assertTrue(module.getStatus() == MODULE_STATUS.Unknown);
	}
	
	@Test
	public void testModuleMutators() {
		
		Module module = new Module();
		module.setBookeepingXPos(5);
		module.setBookeepingYPos(6);
		module.setCode(131);
		module.setRotationsTillUpright(2);
		module.setDamageStatus(MODULE_STATUS.DamagedBeyondRepair);
		
		assertTrue(module.getXPos() == 5);
		assertTrue(module.getYPos() == 6);
		assertTrue(module.getType() == MODULE_TYPE.GymAndRelaxation);
		assertTrue(module.getRotationsTillUpright() == 2);
		assertTrue(module.getStatus() == MODULE_STATUS.DamagedBeyondRepair);
	}

	@Test
	public void testModuleLimits() {
		
		Module module = new Module();
		
		module.setBookeepingXPos(-5);
		module.setBookeepingYPos(-6);
		module.setCode(-1);
		module.setCode(0);
		module.setCode(191);
		module.setRotationsTillUpright(-1);
		module.setRotationsTillUpright(3);
		
		assertTrue(module.getXPos() == -1);
		assertTrue(module.getYPos() == -1);
		assertTrue(module.getType() == MODULE_TYPE.Unknown);
		assertTrue(module.getRotationsTillUpright() == -1);
	}
	
}
