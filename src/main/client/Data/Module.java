package main.client.Data;

import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

/**
 * Holds information about a specific module
 */
public class Module {
	
	private int moduleCode;
	private int rotationsTillUpright;
	private MODULE_STATUS status;
	
	/**
	 * Get the module type based on the code
	 * @return the module type (ex: MODULE_TYPE.Plain)
	 */
	public MODULE_TYPE getType() {
		return ModuleTypes.getType(moduleCode);
	}
	
	/**
	 * Get the code number of the module
	 * @return the module's code number
	 */
	public int getCode() {
		return moduleCode;
	}
	
	/**
	 * Gets the rotations required to get the module upright
	 * @return the number of rotations required to get a module uprigth
	 */
	public int getRotationsTillUpright() {
		return rotationsTillUpright;
	}
	
	/**
	 * Gets the damage/usability status of a module
	 * @return the damage/usability status of a module
	 */
	public MODULE_STATUS getStatus() {
		return status;
	}
	
	/**
	 * Sets the code number of the module
	 * @param moduleCode the new code number of the module
	 */
	public void setCode(int moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	/**
	 * Sets the rotations required to get the module upright
	 * @param rotationsTillUpright the new number of rotations required to get the module upright
	 */
	public void setRotationsTillUpright(int rotationsTillUpright) {
		this.rotationsTillUpright = rotationsTillUpright;
	}
	
	/**
	 * Sets the damage/usability status
	 * @param status the new damage/usability status of the module
	 */
	public void setDamageStatus(MODULE_STATUS status) {
		this.status = status;
	}
}
