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
public class Data_Menu implements Buildable
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
	 * The number of options in this menu.
	 *
	 * @return the size of the menu
	 */
	public int menuSize()
	{
		return options.size();
	}

	/**
	 * Add a new String option to the end of the menu.
	 *
	 * @param newOp
	 *            the new option to add to the menu.
	 * @return true if the new element was successfully added to the menu.
	 */
	public boolean addOption(String newOp)
	{
		return options.add(newOp);
	}

	/**
	 * Remove an option from the menu.
	 *
	 * @param index
	 *            the position of the option to be removed.
	 * @return true if the index was valid and the option was removed.
	 */
	public boolean removeOption(int index)
	{
		if (index < options.size() && index >= 0)
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
		if (options.size() > 0)
		{
			return options.get(currentlySelected);
		}
		return null;
	}

	/**
	 * Set the currently selected option to a different valid state.
	 *
	 * @param newIndex
	 *            the index of an item in the menu.
	 * @return true if the new index is not out of bounds, and the current index was
	 *         successfully changed.
	 */
	public boolean setSelected(int newIndex)
	{
		if (newIndex < options.size() && newIndex >= 0)
		{
			currentlySelected = newIndex;
			return true;
		}
		return false;
	}

	@Override
	public Buildable duplicate()
	{
		Data_Menu dup = new Data_Menu(this.elementName);
		for (String op : options)
		{
			dup.addOption(op);
		}
		dup.setSelected(this.currentlySelected);
		return dup;
	}

	@Override
	public String elementName()
	{
		return elementName;
	}

	public String[] getElements()
	{
		String[] elements = new String[options.size()];
		for (int i = 0; i < elements.length; i++)
		{
			elements[i] = options.get(i);
		}
		return elements;
	}

	/**
	 * {@inheritDoc} This class uses the following tags: OPTION (holds one entry
	 * from the list), SELECTED_OPTION (holds index of the selected option).
	 */
	@Override
	public String toXML()
	{
		String output = "";
		for (String opt : options)
		{
			output += XML_Writer.SimpleTag(XML_Writer.tags.MENU_OPTION.name(), opt);
		}
		output += XML_Writer.SimpleTag(XML_Writer.tags.MENU_SELECTED.name(), "" + currentlySelected);
		return output;
	}

	@Override
	public void reset()
	{
		options = new ArrayList<>();
		currentlySelected = 0;
	}
}
