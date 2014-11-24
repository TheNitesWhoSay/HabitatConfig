package main.client.Data;

import static org.junit.Assert.*;
import main.client.Data.ModuleTypes.MODULE_TYPE;

import org.junit.Test;

public class ModuleTypesTest {

	// Remove pointless magic number checkstyle warnings
	private static final int FORTY = 40;
	private static final int FORTY_ONE = 41;
	private static final int SIXTY = 60;
	private static final int SIXTY_ONE = 61;
	private static final int EIGHTY = 80;
	private static final int EIGHTY_ONE = 81;
	private static final int NINETY = 90;
	private static final int NINETY_ONE = 91;
	private static final int HUNDRED = 100;
	private static final int HUNDRED_ONE = 101;
	private static final int HUNDRED_TEN = 110;
	private static final int HUNDRED_ELEVEN = 111;
	private static final int HUNDRED_TWENTY = 120;
	private static final int HUN_TWENTY_ONE = 121;
	private static final int HUNDRED_THIRTY = 130;
	private static final int HUN_THIRTY_ONE = 131;
	private static final int HUN_THIRTY_FOUR = 134;
	private static final int HUN_THIRTY_FIVE = 135;
	private static final int HUNDRED_FORTY = 140;
	private static final int HUN_FORTY_ONE = 141;
	private static final int HUN_FORTY_FOUR = 144;
	private static final int HUN_FORTY_FIVE = 145;
	private static final int HUNDRED_FIFTY = 150;
	private static final int HUN_FIFTY_ONE = 151;
	private static final int HUN_FIFTY_FOUR = 154;
	private static final int HUN_FIFTY_FIVE = 155;
	private static final int HUNDRED_SIXTY = 160;
	private static final int HUN_SIXTY_ONE = 161;
	private static final int HUN_SIXTY_FOUR = 164;
	private static final int HUN_SIXTY_FIVE = 165;
	private static final int HUNDRED_SEVENTY = 170;
	private static final int HUN_SEVENTY_ONE = 171;
	private static final int HUN_SEVENTY_FOUR = 174;
	private static final int HUN_SEVENTY_FIVE = 175;
	private static final int HUNDRED_EIGHTY = 180;
	private static final int HUN_EIGHTY_ONE = 181;
	private static final int HUN_EIGHTY_FOUR = 184;
	private static final int HUN_EIGHTY_FIVE = 185;
	private static final int HUNDRED_NINETY = 190;
	
	private static final int dirty_data_one = -2147483648;
	private static final int dirty_data_two = 191;

	public ModuleTypesTest() {
		
	}
	
	@Test
	public void testUnknownModuleType() {
		
		assertTrue( ModuleTypes.getType(0) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(-1) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(dirty_data_one) == MODULE_TYPE.Unknown );
		assertTrue( ModuleTypes.getType(dirty_data_two) == MODULE_TYPE.Unknown );
	}
	
	@Test
	public void testPlainModuleType() {
		// i++ cannot use post increment operator via checkstyle rules
		for ( int i=1; i <= FORTY; i = i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Plain);
	}
	
	@Test
	public void testReservedModuleType() {
		
		for ( int i=FORTY_ONE; i <= SIXTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=EIGHTY_ONE; i <= NINETY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUNDRED_ONE; i <= HUNDRED_TEN; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_TWENTY_ONE; i <= HUNDRED_THIRTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_THIRTY_FIVE; i <= HUNDRED_FORTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_FORTY_FIVE; i <= HUNDRED_FIFTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_FIFTY_FIVE; i <= HUNDRED_SIXTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_SIXTY_FIVE; i <= HUNDRED_SEVENTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_SEVENTY_FIVE; i <= HUNDRED_EIGHTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
		for ( int i=HUN_EIGHTY_FIVE; i <= HUNDRED_NINETY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Reserved);
		
	}
	
	@Test
	public void testDormitoryModuleType() {
		
		for ( int i=SIXTY_ONE; i <= EIGHTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Dormitory);
		
	}
	
	@Test
	public void testSanitationModuleType () {
		
		for ( int i=NINETY_ONE; i <= HUNDRED; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Sanitation);
		
	}
	
	@Test
	public void testFoodAndWaterModuleType() {
		
		for ( int i=HUNDRED_ELEVEN; i <= HUNDRED_TWENTY; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.FoodAndWater);
		
	}
	
	@Test
	public void testGymAndRelaxationModuleType() {
		
		for ( int i=HUN_THIRTY_ONE; i <= HUN_THIRTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.GymAndRelaxation);
		
	}
	
	@Test
	public void testCanteenModuleType() {
		
		for ( int i=HUN_FORTY_ONE; i <= HUN_FORTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Canteen);
		
	}
	
	@Test
	public void testPowerModuleType() {
		
		for ( int i=HUN_FIFTY_ONE; i <= HUN_FIFTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Power);
		
	}
	
	@Test
	public void testControlModuleType() {
		
		for ( int i=HUN_SIXTY_ONE; i <= HUN_SIXTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Control);
	}
		
	@Test
	public void testAirlockModuleType() {
		for ( int i=HUN_SEVENTY_ONE; i <= HUN_SEVENTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Airlock);
	}
		
	@Test 
	public void testMedicalModuleType() {
		
		for ( int i=HUN_EIGHTY_ONE; i <= HUN_EIGHTY_FOUR; i=i+1 )
			assertTrue( ModuleTypes.getType(i) == MODULE_TYPE.Medical);
		
	}

}
