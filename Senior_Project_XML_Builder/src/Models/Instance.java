/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * An Instance is a cluster of one or more Events. It has a group of media-based
 * events, and a group of non-media-based events. It has a reference to the
 * plotline it's currently inside.
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 */
public class Instance implements XML_Writable
{

    /**
     * All the events which occur at this moment in time.
     */
    public List<Event> events;

    /**
     * The plotline to which this event belongs.
     */
    public Plotline plotline;

    /**
     * The time at which this instance occurs.
     */
    public Integer time;

    /**
     * Constructor. Generates a new instance.
     *
     * @param parent the plotline to which this instance belongs.
     */
    public Instance(Plotline parent, int t)
    {
        events = new ArrayList<>();
        plotline = parent;
        time = t;
    }

    /**
     * Add a new blank event to this instance.
     *
     * @return the new event.
     */
    public Event addEvent()
    {
        Event e = new Event(this);
        events.add(e);
        return e;
    }

    /**
     * Remove an event from this instance.
     *
     * @param evt the event to be removed.
     * @return true if the event was removed.
     */
    public boolean removeEvent(Event evt)
    {
        return events.remove(evt);
    }

    @Override
    public String toString()
    {
        return "Instance occurring at t=" + time + " seconds, in:\n" + plotline.toString();
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
