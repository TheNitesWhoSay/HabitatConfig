package main.client.Data;

import java.util.LinkedList;
import java.util.ListIterator;

import main.client.Data.ModuleStatuses.MODULE_STATUS;

/**
 * Contains statistical information about the modules in a LandingGrid
 */
public class ModuleCount {

	private int numUnknown;
	private int numReserved;
	private int numPlain;
	private int numDormitory;
	private int numSanitation;
	private int numFoodAndWater;
	private int numGymAndRelaxation;
	private int numCanteen;
	private int numPower;
	private int numControl;
	private int numAirlock;
	private int numMedical;
	
	/**
	 * Constructs a ModuleCount object.s
	 */
	public ModuleCount() {
		
		zeroModuleCount();
	}
	
	/**
	 * Returns how many modules of unknown type there are.
	 * @return The number of modules of unknown type.
	 */
	public int getNumUnknown() {
		return numUnknown;
	}
	
	/**
	 * Returns how many modules of reserved type there are.
	 * @return The number of modules of reserved type.
	 */
	public int getNumReserved() {
		return numReserved;
	}
	
	/**
	 * Returns the number of plain modules.
	 * @return The number of plain modules.
	 */
	public int getNumPlain() {
		return numPlain;
	}
	
	/**
	 * Returns the number of dormitory modules.
	 * @return The number of dormitory modules.
	 */
	public int getNumDormitory() {
		return numDormitory;
	}
	
	/**
	 * Returns the number of sanitation modules.
	 * @return The number of sanitation modules.
	 */
	public int getNumSanitation() {
		return numSanitation;
	}
	
	/**
	 * Returns the number of Food & Water modules.
	 * @return The number of Food & Water modules.
	 */
	public int getNumFoodAndWater() {
		return numFoodAndWater;
	}
	
	/**
	 * Returns the number of Gym & Relaxation modules.
	 * @return The number of Gym & Relaxation modules.
	 */
	public int getNumGymAndRelaxation() {
		return numGymAndRelaxation;
	}
	
	/**
	 * Returns the number of Canteen modules.
	 * @return The number of Canteen modules.
	 */
	public int getNumCanteen() {
		return numCanteen;
	}
	
	/**
	 * Returns the number of Power modules.
	 * @return The number of Power modules.
	 */
	public int getNumPower() {
		return numPower;
	}
	
	/**
	 * Returns the number of Control modules.
	 * @return The number of Control modules.
	 */
	public int getNumControl() {
		return numControl;
	}
	
	/**
	 * Returns the number of Airlock modules.
	 * @return The number of Airlock modules.
	 */
	public int getNumAirlock() {
		return numAirlock;
	}
	
	/**
	 * Returns the number of Medical modules.
	 * @return The number of medical modules.
	 */
	public int getNumMedical() {
		return numMedical;
	}
	
	/**
	 * Decrements the number of plain modules if it's greater than 0
	 * @return whether the number of plain modules was greater than 0
	 */
	public boolean usePlain() {
		if ( numPlain > 0 ) {
			numPlain --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of dormitory modules if it's greater than 0
	 * @return whether the number of dormitory modules was greater than 0
	 */
	public boolean useDormitory() {
		if ( numDormitory > 0 ) {
			numDormitory --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of sanitation modules if it's greater than 0
	 * @return whether the number of sanitation modules was greater than 0
	 */
	public boolean useSanitation() {
		if ( numSanitation > 0 ) {
			numSanitation --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of food & water modules if it's greater than 0
	 * @return whether the number of food & water modules was greater than 0
	 */
	public boolean useFoodAndWater() {
		if ( numFoodAndWater > 0 ) {
			numFoodAndWater --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of gym & relaxation modules if it's greater than 0
	 * @return whether the number of gym & relaxation modules was greater than 0
	 */
	public boolean useGymAndRelaxation() {
		if ( numGymAndRelaxation > 0 ) {
			numGymAndRelaxation --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of canteen modules if it's greater than 0
	 * @return whether the number of canteen modules was greater than 0
	 */
	public boolean useCanteen() {
		if ( numCanteen > 0 ) {
			numCanteen --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of power modules if it's greater than 0
	 * @return whether the number of power modules was greater than 0
	 */
	public boolean usePower() {
		if ( numPower > 0 ) {
			numPower --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of control modules if it's greater than 0
	 * @return whether the number of control modules was greater than 0
	 */
	public boolean useControl() {
		if ( numControl > 0 ) {
			numControl --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of airlock modules if it's greater than 0
	 * @return whether the number of airlock modules was greater than 0
	 */
	public boolean useAirlock() {
		if ( numAirlock > 0 ) {
			numAirlock --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Decrements the number of medical modules if it's greater than 0
	 * @return whether the number of medical modules was greater than 0
	 */
	public boolean useMedical() {
		if ( numMedical > 0 ) {
			numMedical --;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Gets the number of each type of module within the landing zone.
	 * @param landingGrid Contains information about modules in the landing zone.
	 * @return Whether the counting was successful.
	 */
	public boolean getModuleCount(final LandingGrid landingGrid, final MODULE_STATUS maximumDamageStatus) {
		
		if ( landingGrid == null || maximumDamageStatus == MODULE_STATUS.Unknown )
			return false;
		
		zeroModuleCount();
		
		LinkedList<Module> moduleList = landingGrid.getModuleList();
		ListIterator<Module> i = moduleList.listIterator();
		
		while ( i.hasNext() ) {
			
			Module module = i.next();
			MODULE_STATUS damageStatus = module.getStatus();
			if ( damageStatus == maximumDamageStatus ||
				 damageStatus == MODULE_STATUS.Usable ||
				 (damageStatus == MODULE_STATUS.UsableAfterRepair && maximumDamageStatus == MODULE_STATUS.Usable)
			   ) // All other cases should be covered...
			{
				switch ( module.getType() ) {
				
					case Unknown:			numUnknown ++;			break;
					case Reserved:			numReserved ++;			break;
					case Plain:				numPlain ++;			break;
					case Dormitory:			numDormitory ++;		break;
					case Sanitation:		numSanitation ++;		break;
					case FoodAndWater:		numFoodAndWater ++;		break;
					case GymAndRelaxation:	numGymAndRelaxation ++; break;
					case Canteen:			numCanteen ++;			break;
					case Power:				numPower ++;			break;
					case Control:			numControl ++;			break;
					case Airlock:			numAirlock ++;			break;
					case Medical:			numMedical ++;			break;
					default:
						throw new RuntimeException("Unexpected Module Type");
				}
			}
		}
		return true;
	}
	
	/**
	 * Zeros the count of all module types.
	 */
	private void zeroModuleCount() {
		
		numUnknown = 0;
		numReserved = 0;
		numPlain = 0;
		numDormitory = 0;
		numSanitation = 0;
		numFoodAndWater = 0;
		numGymAndRelaxation = 0;
		numCanteen = 0;
		numPower = 0;
		numControl = 0;
		numAirlock = 0;
		numMedical = 0;
	}
	
}
