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
    public List<Event> mediaEvents;
    public List<Event> nonMediaEvents;
    
    public Plotline plotline;
    
    public Instance(Plotline parent)
    {
        mediaEvents = new ArrayList<Event>();
        nonMediaEvents = new ArrayList<Event>();
        plotline = parent;
    }
    
    public int mediaCount()
    {
        return mediaEvents.size();
    }
    
    public int nonMediaCount()
    {
        return nonMediaEvents.size();
    }
    
    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
