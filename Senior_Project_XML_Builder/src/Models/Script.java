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
import java.util.TreeMap;

/**
 * A complete script, which contains one or more plotlines. It has a running
 * time and may have some header data. It contains a reference to the DTD which
 * is used by this script. The script can be converted to XML via the
 * XML_Writable interface.
 *
 * @author Bryan McGuffin
 * @version Feb 1, 2018
 * @see Plotline
 */
public class Script implements XML_Writable
{

    /**
     * The plotlines which make up this script.
     */
    public List<Plotline> plotlines;

    /**
     * Constructor. Generates a new Script object.
     */
    public Script()
    {
        plotlines = new ArrayList<>();
    }

    /**
     * Generate a new plotline which starts at the given time, and add it to the
     * list.
     *
     * @param start The number of seconds into the script that the plotline
     * should begin.
     * @return the new plotline.
     */
    public Plotline addPlotline(int start)
    {
        Plotline p = new Plotline(start, this);
        plotlines.add(p);
        return p;
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
