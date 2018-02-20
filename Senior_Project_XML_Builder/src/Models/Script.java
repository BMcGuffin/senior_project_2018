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
    public List<Plotline> plotlines;
    
    public Script()
    {
        plotlines = new ArrayList<Plotline>();
    }
    
    public boolean addPlotline(int start)
    {
        return plotlines.add(new Plotline(start, this));
    }
    
    public int plotlineCount()
    {
        return plotlines.size();
    }
    
    
    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
