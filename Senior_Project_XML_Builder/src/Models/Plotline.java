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
     * into the plotline. Value: An instance. NOTE: All times are relative only
     * to the start of this plotline; the value here is different from the
     * absolute script time.
     */
    public TreeMap<Integer, Instance> instances;

    /**
     * Number of seconds into the script that this plotline starts. Not relevant
     * to length of plotline.
     */
    public int startTime;

    /**
     * The script that this plotline belongs to.
     */
    public Script script;

    /**
     * Constructor. Generates a new plotline at the given time.
     *
     * @param start The start time, in seconds, of this plotline.
     * @param parentScript The script to which this plotline belongs.
     */
    public Plotline(int start, Script parentScript)
    {
        instances = new TreeMap<>();
        startTime = start;
        script = parentScript;
    }

    /**
     * Add an instance to this plotline. Doesn't overwrite existing instance at
     * the given time.
     *
     * @param time the time, in seconds, of the new instance, relative to the
     * beginning of the plotline
     * @return the instance that exists at the given time, or a new instance if
     * one didn't already exist there
     */
    public Instance addInstance(int time)
    {
        if (instances.containsKey(time))
        {
            return instances.get(time);
        }
        Instance inst = new Instance(this);
        instances.put(time, inst);
        return inst;
    }

    /**
     * Removes the instance at the given time.
     *
     * @param time the time, in seconds, of the instance to be removed, relative
     * to the beginning of the plotline
     * @return true if an instance was removed at the given time
     */
    public boolean removeInstance(int time)
    {
        if (instances.containsKey(time))
        {
            instances.remove(time);
            return true;
        }
        return false;
    }

    /**
     * Moves an entire instance from one time to another. Will not overwrite an
     * existing instance at the destination time; in such a case, the relocation
     * will fail.
     *
     * @param from the current time of the instance to be moved.
     * @param to the destination time.
     * @return true if the move was successful.
     */
    public boolean relocateInstance(int from, int to)
    {
        if(instances.containsKey(from) && !instances.containsKey(to))
        {
            instances.put(to, instances.remove(from));
            return true;
        }
        return false;
    }

    /**
     * Count the number of populated instances in this plotline.
     *
     * @return the number of instances.
     */
    public int instanceCount()
    {
        return instances.size();
    }

    /**
     * Get the length, in seconds, from the start to the end of the plotline.
     *
     * @return the time of the last instance.
     */
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
