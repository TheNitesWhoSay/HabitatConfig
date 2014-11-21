package main.client.Data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TerrainSquareTest {

	public TerrainSquareTest() {
		
	}
	
	@Test
	public void testTerrainSquare() {
		
		TerrainSquare square = new TerrainSquare();
		assertTrue(square.isBuildable());
		assertTrue(square.isTraversable());
		square.setBuildable(false);
		assertFalse(square.isBuildable());
		assertTrue(square.isTraversable());
		square.setTraversable(false);
		assertFalse(square.isBuildable());
		assertFalse(square.isTraversable());
		square.setBuildable(true);
		assertTrue(square.isBuildable());
		assertFalse(square.isTraversable());
	}

}
