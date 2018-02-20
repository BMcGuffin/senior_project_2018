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

    public Instance instance;

    private int curr_index;
    private int curr_text;
    private int curr_menu;
    private int curr_media;
    private int curr_transcript;
    /**
     * The meta-list of all text fields in this event.
     */
    public List<Text> textFields;
    /**
     * The meta-list of all dropdown menus in this event.
     */
    public List<Menu> menus;
    /**
     * The meta-list of all media in this event.
     */
    public List<Media> media;
    /**
     * The meta-list of all transcripts in this event.
     */
    public List<Transcript> transcripts;

    /**
     * Contains the ordering by which to construct the data in this event. Read
     * as K = index.get(i); Where i is the element in buildableIndex and K is
     * the list to pull the next element from.
     */
    public List<buildableType> buildableIndex;

    /**
     * Contains the labels for each field in this event.
     */
    public List<String> labels;

    public Event(Instance parent)
    {
        instance = parent;
        textFields = new ArrayList<Text>();
        menus = new ArrayList<Menu>();
        media = new ArrayList<Media>();
        transcripts = new ArrayList<Transcript>();
        buildableIndex = new ArrayList<buildableType>();
        reset();
    }
    
    public Buildable next()
    {
        if(curr_index < buildableIndex.size())
            return getItem(curr_index++);
        else
            return null;
    }

    public void reset()
    {
        curr_index = 0;
        curr_text = 0;
        curr_menu = 0;
        curr_media = 0;
        curr_transcript = 0;
    }
    
    private Buildable getItem(int index)
    {
        buildableType choice = buildableIndex.get(index);
        switch (choice)
        {
            case B_TEXT:
                return textFields.get(curr_text++);
            case B_MENU:
                return menus.get(curr_menu++);
            case B_MEDIA:
                return media.get(curr_media++);
            case B_TRANSCRIPT:
                return transcripts.get(curr_transcript++);
            default:
                return null;
        }
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private enum buildableType
    {
        B_TEXT,
        B_MENU,
        B_MEDIA,
        B_TRANSCRIPT;
    }

}
