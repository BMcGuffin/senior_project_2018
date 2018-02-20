/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.util.TreeMap;

/**
 * A plotline is an time-ordered sequence of Instances. It has a start time and
 * 0 or more instances.
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 * @see Instance
 * @see Event
 */
public class Plotline implements XML_Writable
{

    /**
     * Mapping of each instance by the number of seconds into the plotline that
     * it starts. Instances are chronologically distinct. Key: Number of seconds
     * into the plotline. Value: An instance.
     */
    public TreeMap<Integer, Instance> instances;

    /**
     * Number of seconds into the script that this plotline starts. Not relevant
     * to length of plotline.
     */
    public int startTime;
    
    public Script script;

    public Plotline(int start, Script parentScript)
    {
        instances = new TreeMap<Integer, Instance>();
        startTime = start;
        script = parentScript;
    }

    public int instanceCount()
    {
        return instances.size();
    }

    public int length()
    {
        return instances.lastKey();
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
