package main.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigOptionsTest {

	private String strmars = "mars";
	
	public ConfigOptionsTest() {
		
		
	}
	
	@Test
	public void testBasicLogin() {
		
		ConfigOptions config = new ConfigOptions();
		config.load();
		
		assertTrue( config.validateLogin("mars", "12345") );
		assertTrue( config.validateLogin("Mars", "12345") );
		assertTrue( config.validateLogin("mArS", "12345") );
		assertFalse( config.validateLogin("asdf", "12345") );
		assertFalse( config.validateLogin("mars", "52345") );
	}
	
	@Test
	public void testPasswordChange() {
		
		ConfigOptions config = new ConfigOptions();
		config.load();
		
		assertTrue( config.setPassword("mars", "12345", "asDf") );
		
		assertTrue( config.validateLogin("mars", "asDf") );
		assertFalse( config.validateLogin("mars", "12345") );
	}
	
	@Test
	public void testLoginCaseSensativity() {
		
		ConfigOptions config = new ConfigOptions();
		config.load();
		
		assertTrue( config.validateLogin("mARs", "12345") );
		
		assertTrue( config.setLoginNameCaseSensative("mars", "12345", true) );
		
		assertFalse( config.validateLogin("mArs", "12345") );
		assertTrue( config.validateLogin("mars", "12345") );
		
		assertTrue( config.setPassword("mars", "12345", "asDf") );
		
		assertTrue( config.validateLogin("mars", "asDf") );
		assertFalse( config.validateLogin("mars", "asdf") );
		
		assertTrue( config.setLoginPassCaseSensative("mars", "asDf", false) );
		
		assertTrue( config.validateLogin("mars", "asDf") );
		assertTrue( config.validateLogin("mars", "asdf") );
	}

	@Test
	public void testLoginRequired() {
	
		ConfigOptions config = new ConfigOptions();
		config.load();
		
		assertTrue( config.loginRequired() );
	}
	
	@Test
	public void testAlterLoginRequired() {
	
		ConfigOptions config = new ConfigOptions();
		config.load();
		
		assertTrue( config.setLoginRequired("mars", "12345", false) );
		assertFalse( config.loginRequired() );
		assertTrue( config.setLoginRequired("Mars", "12345", true) );
		assertTrue( config.loginRequired() );
	}
	
}
