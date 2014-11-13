package main.client.Data;

/**
 * A class to help describe the current damage/usability status of a module
 */
public class DamageStatus {

	/**
	 * An enumeration of the module damage/usability status
	 */
	public enum STATUS {
		Usable, UsableAfterRepair, DamagedBeyondRepair 
	};
	
}
