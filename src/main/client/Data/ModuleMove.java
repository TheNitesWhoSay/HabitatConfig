package main.client.Data;

/**
 * A planned move of a module within a LandingGrid

 */
public class ModuleMove {

	private int originXc;
	private int originYc;
	private int destXc;
	private int destYc;
	
	/**
	 * Constructs a ModuleMove object with the given origin and destination coordinates.
	 */
	public ModuleMove(final int originXc, final int originYc, final int destXc, final int destYc) {
		
		this.originXc = originXc;
		this.originYc = originYc;
		this.destXc = destXc;
		this.destYc = destYc;
	}
	
	/**
	 * Returns the origin Xc.
	 * @return The origin Xc.
	 */
	public int getOriginXc() {
		return originXc;
	}
	
	/**
	 * Returns the origin Yc.
	 * @return The origin Yc.
	 */
	public int getOriginYc() {
		return originYc;
	}
	
	/**
	 * Returns the destination Xc.
	 * @return The destination Xc.
	 */
	public int getDestXc() {
		return destXc;
	}
	
	/**
	 * Returns the destination Yc.
	 * @return The destination Yc.
	 */
	public int getDestYc() {
		return destYc;
	}
	
}
