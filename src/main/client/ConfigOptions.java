package main.client;

/**
 * Handles all persistent configuration options
 */
public class ConfigOptions {

	private boolean loginRequired;
	
	/** Sets all default configuration settings */
	public ConfigOptions() {
		
		loginRequired = true;
	}
	
	/**
	 * Loads the programs configuration options from storage
	 * @return whether loading was 100% successful
	 */
	public boolean load() {
		
		// Load configuration options from storage
		return false; // Unimplemented
	}
	
	/**
	 * Returns whether or not the program should require a username/password
	 * @return whether the program requires a username/password
	 */
	public boolean loginRequired() {
		
		return loginRequired;
	}
}
