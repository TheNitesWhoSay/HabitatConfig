package main.client.Data;

import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

/**
 * Holds information about a specific module
 */
public class Module {
	
	private int xc;
	private int yc;
	private int moduleCode;
	private int rotationsTillUpright;
	private MODULE_STATUS status;
	
	public Module() {
		
		xc = -1;
		yc = -1;
		moduleCode = 0;
		rotationsTillUpright = -1;
		status = MODULE_STATUS.Unknown;
	}
	
	/**
	 * Gets this modules current x coordinate
	 * @return the current x coordinate
	 */
	public int getXPos() {
		
		return xc;
	}
	
	/**
	 * Gets this modules current y coordinate
	 * @return the current y coordinate
	 */
	public int getYPos() {
		
		return yc;
	}
	
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
	 * Sets this modules current x coordinate for bookeeping's sake
	 * This should NOT be used except by the LandingGrid class
	 * Use methods in the landing grid class to set module coordinates
	 * Fails and no change occurs if xPos is negative
	 * @param xPos the current x coordinate
	 */
	public void setBookeepingXPos(final int xPos) {
		
		if ( xPos >= 0 )
			xc = xPos;
	}
	
	/**
	 * Sets this modules current y coordainte for bookeeping's sake
	 * This should NOT be used except by the LandingGrid class
	 * Use methods in the landing grid class to set module coordinates
	 * Fails and no change occurs if yPos is negative
	 * @param yPos the current y coordinate
	 */
	public void setBookeepingYPos(final int yPos) {
		
		if ( yPos >= 0 )
			yc = yPos;
	}
	
	/**
	 * Sets the code number of the module
	 * Fails and no change occurs unless 1 <= moduleCode <= 190
	 * @param moduleCode the new code number of the module
	 */
	public void setCode(final int moduleCode) {
		
		if ( moduleCode >= 1 && moduleCode <= 190)
			this.moduleCode = moduleCode;
	}
	
	/**
	 * Sets the rotations required to get the module upright
	 * Fails and no change occurs unless 0 <= rotationsTillUpright <= 2
	 * @param rotationsTillUpright the new number of rotations required to get the module upright
	 */
	public void setRotationsTillUpright(final int rotationsTillUpright) {
		
		if ( rotationsTillUpright >= 0 && rotationsTillUpright <= 2 )
			this.rotationsTillUpright = rotationsTillUpright;
	}
	
	/**
	 * Sets the damage/usability status
	 * @param status the new damage/usability status of the module
	 */
	public void setDamageStatus(final MODULE_STATUS status) {
		
		this.status = status;
	}
	public String storageString() {
		return "{"+moduleCode+":"+xc+":"+yc+":"+status+":"+rotationsTillUpright+"}";
	}
	
}
