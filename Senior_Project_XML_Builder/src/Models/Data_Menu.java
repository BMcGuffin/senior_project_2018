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
 * Represents a dropdown menu. Contains a series of unique, mutually exclusive
 * choices, exactly one of which may be selected. Has a list of selection
 * options, and a reference to the currently selected option. The list of
 * options may be arbitrarily long.
 *
 * @author Bryan McGuffin
 * @version Feb 20, 2018
 */
class Data_Menu implements Buildable
{

    /**
     * The name of this particular data field.
     */
    public String elementName;

    /**
     * The list of options. Ordering may not be arbitrarily changed by user.
     */
    private List<String> options;

    /**
     * The index for the currently selected option.
     */
    private int currentlySelected;

    /**
     * Constructor.
     */
    public Data_Menu(String eName)
    {
        elementName = eName;
        options = new ArrayList<>();
        currentlySelected = 0;
    }

    /**
     * Add a new String option to the end of the menu.
     *
     * @param newOp the new option to add to the menu.
     * @return true if the new element was successfully added to the menu.
     */
    public boolean addOption(String newOp)
    {
        return options.add(newOp);
    }

    /**
     * Remove an option from the menu.
     *
     * @param index the position of the option to be removed.
     * @return true if the index was valid and the option was removed.
     */
    public boolean removeOption(int index)
    {
        if (index < options.size())
        {
            options.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Get the currently selected list item.
     *
     * @return the list item at the last selected position.
     */
    public String getSelected()
    {
        return options.get(currentlySelected);
    }

    /**
     * Set the currently selected option to a different valid state.
     *
     * @param newIndex the index of an item in the menu.
     * @return true if the new index is not out of bounds, and the current index
     * was successfully changed.
     */
    public boolean setSelected(int newIndex)
    {
        if (newIndex < options.size())
        {
            currentlySelected = newIndex;
            return true;
        }
        return false;
    }
}
