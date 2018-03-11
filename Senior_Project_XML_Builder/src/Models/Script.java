/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
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
public class Script extends Observable implements XML_Writable
{

    /**
     * The plotlines which make up this script.
     */
    private List<Plotline> plotlines;

    public String scriptTitle;
    
    public String description;
    
    public File saveFile;

    /**
     * Constructor. Generates a new Script object.
     */
    public Script()
    {
        plotlines = new ArrayList<>();
        scriptTitle = "untitled1";
    }

    /**
     * Generate a new plotline which starts at the given time, and add it to the
     * list.
     *
     * @param start The number of seconds into the script that the plotline
     * should begin.
     * @return the index of the new plotline.
     */
    public int addPlotline(String title, int start)
    {
        Plotline p = new Plotline(title, start, this);

        plotlines.add(p);
        setChanged();
        notifyObservers();
        return plotlines.size() - 1;
    }

    /**
     * Get the plotline at the given index.
     *
     * @param index The index of the plotline.
     * @return the plotline in question.
     */
    public Plotline getPlotLine(int index)
    {
        if (index < plotlines.size() && index >= 0)
        {
            return plotlines.get(index);
        }
        return null;
    }

    /**
     * Remove a plotline from the script.
     *
     * @param index the position of the plotline to be removed.
     * @return true if that plotline was removed.
     */
    public boolean removePlotline(int index)
    {
        if (index < plotlines.size() && index >= 0)
        {
            plotlines.remove(index);
            setChanged();
            notifyObservers();
            return true;
        }
        setChanged();
        notifyObservers();
        return false;
    }

    /**
     * Get the number of plotlines in this script.
     *
     * @return the number of registered plotlines.
     */
    public int countPlotlines()
    {
        return plotlines.size();
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Send out the initial update so that all views have a connection.
     */
    public void spark()
    {
        setChanged();
        notifyObservers();
    }

}
