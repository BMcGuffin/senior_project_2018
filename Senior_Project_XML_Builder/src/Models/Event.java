/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

/**
 * The event is the atomic unit of the script model. It contains some data which
 * begins at some moment in time. It may or may not be a media event. It has a
 * reference to the instance it's a part of. It can be written to XML using the
 * XML_Writable interface.
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 */
public class Event implements XML_Writable
{

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
