package main.client.Data;

/** Enumerates module types and provides conversion to types from codes */
public class ModuleTypes {

	/**
	 * Enumerates the various module types
	 */
	public enum MODULE_TYPE {
		Unknown, Plain, Dormitory, Sanitation, FoodAndWater,
		GymAndRelaxation, Canteen, Power, Control, Airlock, Medical
	};
	
	/**
	 * Gets the module type based on the given code
	 * @param code the code of the module
	 * @return the module type for the given code
	 */
	public static MODULE_TYPE getType(int code) {
		
		if ( code >= 1 && code <= 40 )
			return MODULE_TYPE.Plain;
		else if ( code >= 61 && code <= 80 )
			return MODULE_TYPE.Dormitory;
		else if ( code >= 91 && code <= 100 )
			return MODULE_TYPE.Sanitation;
		else if ( code >= 111 && code <= 120 )
			return MODULE_TYPE.FoodAndWater;
		else if ( code >= 131 && code <= 134 )
			return MODULE_TYPE.GymAndRelaxation;
		else if ( code >= 141 && code <= 144 )
			return MODULE_TYPE.Canteen;
		else if ( code >= 151 && code <= 154 )
			return MODULE_TYPE.Power;
		else if ( code >= 161 && code <= 164 )
			return MODULE_TYPE.Control;
		else if ( code >= 171 && code <= 174 )
			return MODULE_TYPE.Airlock;
		else if ( code >= 181 && code <= 184 )
			return MODULE_TYPE.Medical;
		else
			return MODULE_TYPE.Unknown;
	}
	
}
