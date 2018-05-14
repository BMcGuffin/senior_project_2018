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
	 * The type of event that this is.
	 */
	public String eventType;
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
	 * @param parent
	 *            the Instance that this event is a part of.
	 */
	public Event(Instance parent)
	{
		instance = parent;
		fields = new ArrayList<>();
		labels = new ArrayList<>();
		eventType = "Blank";
	}

	/**
	 * Add a new data field to the end of the list.
	 *
	 * @param b
	 *            The buildable data field entity
	 * @param label
	 *            the title for this data field
	 * @return the buildable data, for convenience
	 */
	public Buildable addElement(Buildable b, String label)
	{
		fields.add(b);
		labels.add(label);
		return b;
	}

	/**
	 * Set a new instance as the parent of this one.
	 *
	 * @param parent
	 *            the new parent instance.
	 */
	public void setParentInstance(Instance parent)
	{
		instance = parent;
	}

	/**
	 * Accessor for the buildable data.
	 *
	 * @param index
	 *            the index of the element to access
	 * @return the data requested
	 */
	public Buildable getElement(int index)
	{
		return fields.get(index);
	}

	/**
	 * Accessor for the field labels.
	 *
	 * @param index
	 *            the index of the label to access
	 * @return the label requested
	 */
	public String getLabel(int index)
	{
		return labels.get(index);
	}

	/**
	 * Get the number of fields in this event.
	 *
	 * @return the size of this event
	 */
	public int fieldCount()
	{
		return fields.size();
	}

	/**
	 * Remove an element from the list.
	 *
	 * @param index
	 *            the index of the field to remove
	 * @return true if the event was removed successfully
	 */
	public boolean removeElement(int index)
	{
		if (index >= fieldCount())
		{
			return false;
		}
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
	 * @param index
	 *            the index of the element to be moved.
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
		String tempStr = labels.get(index - 1);
		labels.set(index - 1, labels.get(index));
		labels.set(index, tempStr);
		return true;
	}

	/**
	 * Swap the index of this element and the one below it.
	 *
	 * @param index
	 *            the index of the element to be moved.
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
		String tempStr = labels.get(index + 1);
		labels.set(index + 1, labels.get(index));
		labels.set(index, tempStr);
		return true;
	}

	/**
	 * Determines whether event can be classified as a media event. A media event
	 * contains at least one media field.
	 *
	 * @return true if this event contains at least one media field
	 */
	public boolean isMediaEvent()
	{
		for (Buildable b : fields)
		{
			if (b instanceof Data_Media)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "Event of type \"" + eventType + "\", containing " + fieldCount() + " fields in:\n"
				+ instance.toString();
	}

	@Override
	public String toXML()
	{
		String output = "";
		output += XML_Writer.openTag(DTDBuilder.formatTag(eventType));
		for (Buildable b : fields)
		{
			output += XML_Writer.SimpleTag(DTDBuilder.formatTag(b.elementName()), b.toXML());
		}
		output += XML_Writer.closeTag(DTDBuilder.formatTag(eventType));
		return output;
	}

	public String getFieldLabel(int index)
	{
		return labels.get(index);
	}
}
