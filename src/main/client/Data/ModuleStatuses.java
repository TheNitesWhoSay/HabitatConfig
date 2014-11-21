package main.client.Data;

public class ModuleStatuses {

	/**
	 * Constructs a ModuleStatuses object
	 */
	public ModuleStatuses() {
		
	}
	
	/**
	 * Enumerates the various module statuses
	 */
	public enum MODULE_STATUS {
		Unknown, Usable, UsableAfterRepair, DamagedBeyondRepair
	};
	
}
