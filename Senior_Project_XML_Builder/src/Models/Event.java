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

    /**
     * The instance to which this event belongs.
     */
    private Instance instance;

    /**
     * Contains the data fields for this event.
     */
    private List<Buildable> fields;

    /**
     * Contains the labels for each field in this event.
     */
    private List<String> labels;

    /**
     * Constructor. Generate a new Event with no data.
     *
     * @param parent the Instance that this event is a part of.
     */
    public Event(Instance parent)
    {
        instance = parent;
        fields = new ArrayList<>();
        labels = new ArrayList<>();
    }

    /**
     * Add a new data field to the end of the list.
     *
     * @param b The buildable data field entity
     * @param label the title for this data field
     */
    public void addElement(Buildable b, String label)
    {
        fields.add(b);
        labels.add(label);
    }

    /**
     * Remove an element from the list.
     *
     * @param index the index of the field to remove
     * @return true if the event was removed successfully
     */
    public boolean removeElement(int index)
    {
        Buildable check = fields.remove(index);
        if (check != null)
        {
            return labels.remove(index) != null;
        }
        return false;
    }

    /**
     * Swap the index of this element and the element above it.
     *
     * @param index the index of the element to be moved.
     * @return true if the element was successfully moved.
     */
    public boolean moveElementUp(int index)
    {
        if (index <= 0)
        {
            return false;
        }
        Buildable temp = fields.get(index - 1);
        fields.set(index - 1, fields.get(index));
        fields.set(index, temp);
        return true;
    }

    /**
     * Swap the index of this element and the one below it.
     *
     * @param index the index of the element to be moved.
     * @return true if the element was successfully moved.
     */
    public boolean moveElementDown(int index)
    {
        if (index >= fields.size() - 1)
        {
            return false;
        }
        Buildable temp = fields.get(index + 1);
        fields.set(index + 1, fields.get(index));
        fields.set(index, temp);
        return true;
    }

    /**
     * Determines whether event can be classified as a media event. A media
     * event contains at least one media field.
     *
     * @return true if this event contains at least one media field
     */
    public boolean isMediaEvent()
    {
        for (Buildable b : fields)
        {
            if (b instanceof Media)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
