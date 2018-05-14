/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */

package Models;

/**
 *
 * @author Bryan McGuffin
 */
public interface Buildable extends XML_Writable
{
	/**
	 * Duplicate this object.
	 * 
	 * @return a deep copy of the buildable object
	 */
	Buildable duplicate();

	/**
	 * Get the name of this buildable element.
	 * @return the title of the object.
	 */
	String elementName();
	
	/**
	 * Reset the data field to a blank state.
	 */
	void reset();
}
