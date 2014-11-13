package main.client.Data;

import main.client.Data.ModuleStatuses.MODULE_STATUS;
import main.client.Data.ModuleTypes.MODULE_TYPE;

/** Holds information about a specific module */
public class Module {
	
	private int moduleCode;
	private int rotationsTillUpright;
	private MODULE_STATUS status;
	
	public MODULE_TYPE getType() {
		return ModuleTypes.getType(moduleCode);
	}
	
	public int getCode() {
		return moduleCode;
	}
	
	public int getRotationsTillUpright() {
		return rotationsTillUpright;
	}
	
	public MODULE_STATUS getStatus() {
		return status;
	}
	
	public void setCode(int moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	public void setRotationsTillUpright(int rotationsTillUpright) {
		this.rotationsTillUpright = rotationsTillUpright;
	}
	
	public void setDamageStatus(MODULE_STATUS status) {
		this.status = status;
	}
}
