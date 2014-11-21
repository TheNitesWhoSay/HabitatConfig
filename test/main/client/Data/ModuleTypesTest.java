package main.client.Data;

import static org.junit.Assert.*;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import org.junit.Test;

public class ModuleTypesTest {

	public ModuleTypesTest() {
		
	}
	
	@Test
	public void testModuleTypes() {
		
		assertTrue( ModuleTypes.getType(0) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(-1) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(-2147483648) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(191) == MODULE_TYPE.Unknown );
		
		for ( int i=1; i <= 40; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Plain);
		
		for ( int i=41; i <= 60; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=61; i <= 80; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Dormitory);
		
		for ( int i=81; i <= 90; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=91; i <= 100; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Sanitation);
		
		for ( int i=101; i <= 110; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=111; i <= 120; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.FoodAndWater);
		
		for ( int i=121; i <= 130; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=131; i <= 134; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.GymAndRelaxation);
		
		for ( int i=135; i <= 140; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=141; i <= 144; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Canteen);
		
		for ( int i=145; i <= 150; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=151; i <= 154; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Power);
		
		for ( int i=155; i <= 160; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=161; i <= 164; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Control);
		
		for ( int i=165; i <= 170; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=171; i <= 174; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Airlock);
		
		for ( int i=175; i <= 180; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=181; i <= 184; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Medical);
		
		for ( int i=185; i <= 190; i++ )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
	}

}
