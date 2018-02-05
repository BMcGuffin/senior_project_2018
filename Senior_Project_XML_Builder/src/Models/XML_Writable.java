/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

/**
 * A class which implements XML_Writable can have its data converted to XML.
 *
 * @author Bryan McGuffin
 * @see XML_Writer
 */
public interface XML_Writable
{

    /**
     * Convert the data in this object to XML.
     */
    void toXML();
}
