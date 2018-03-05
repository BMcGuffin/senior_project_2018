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
 * 0 or more instances. Instances are keyed relative to the start of the
 * plotline itself; thus, the first instance is always located at position 0.
 * Adding an instance to the start of the plotline, or relocating/removing the
 * first instance, causes the plotline to shift the instances to reassert this
 * condition.
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
    private TreeMap<Integer, Instance> instances;

    /**
     * Number of seconds into the script that this plotline starts. Not relevant
     * to length of plotline.
     */
    public int startTime;

    /**
     * Name of this plotline.
     */
    public String title;

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
    public Plotline(String name, int start, Script parentScript)
    {
        instances = new TreeMap<>();
        startTime = start;
        script = parentScript;
        title = name;
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
        Instance inst = new Instance(this, time);
        instances.put(time, inst);
        assertOrder();
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
            assertOrder();
            return true;
        }
        assertOrder();
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
        if (instances.containsKey(from) && !instances.containsKey(to))
        {
            Instance ins = instances.remove(from);
            ins.time = to;
            instances.put(to, ins);
            assertOrder();
            return true;
        }
        assertOrder();
        return false;
    }

    /**
     * Gets the instance associated at a given time.
     *
     * @param time The amount of seconds into the plotline that this instance
     * occurs.
     * @return the instance in question, or null if no instance exists at that
     * time.
     */
    public Instance getInstance(int time)
    {
        return instances.get(time);

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
        if (instances.isEmpty())
        {
            return 0;
        }
        return instances.lastKey() + 1;
    }

    private void assertOrder()
    {
        if (instances.isEmpty())
        {
            return;
        }
        int start = instances.firstKey();
        //If earliest event is after t=0, pull all events backwards
        if (start > 0)
        {
            TreeMap<Integer, Instance> instances2 = new TreeMap<>();

            for (int i : instances.keySet())
            {
                Instance ins = instances.get(i);
                ins.time -= start;
                instances2.put(i - start, ins);
            }
            instances = instances2;
        }

        //If earliest event is before t=0, pull all events forwards
        else if (start < 0)
        {
            TreeMap<Integer, Instance> instances2 = new TreeMap<>();

            for (int i : instances.descendingKeySet())
            {
                Instance ins = instances.get(i);
                ins.time -= start;
                instances2.put(i - start, ins);
            }
            instances = instances2;
        }
    }

    public String toString()
    {
        return "Plotline \"" + title + "\", starting at time t=" + startTime
                + " seconds, in:\n" + script.toString();
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
