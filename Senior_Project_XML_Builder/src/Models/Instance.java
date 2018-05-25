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
	 * The number of seconds into the plotline that this instance occurs.
	 */
	public int time;

	/**
	 * Constructor. Generates a new instance.
	 *
	 * @param parent
	 *            the plotline to which this instance belongs.
	 * 
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
	 * Add a new, preformatted event to this instance.
	 *
	 * @return the new event.
	 */
	public Event addEvent(Event e)
	{
		if (e == null)
		{
			return addEvent();
		}
		events.add(e);
		e.setParentInstance(this);
		return e;
	}

	/**
	 * Get the event at the specified index.
	 * 
	 * @param index
	 *            the index of the event requested
	 * @return a reference to an Event object
	 */
	public Event getEvent(int index)
	{
		return events.get(index);
	}

	/**
	 * Remove an event from this instance.
	 *
	 * @param evt
	 *            the event to be removed.
	 * @return true if the event was removed.
	 */
	public boolean removeEvent(Event evt)
	{
		return events.remove(evt);
	}

	@Override
	public String toString()
	{
		return "Instance occurring in:\n" + plotline.toString();
	}

	@Override
	public String toXML()
	{
		String output = "";
		ArrayList<XML_Writer.attributes> attrs = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		String elementName = XML_Writer.tags.INSTANT.name();
		attrs.add(XML_Writer.attributes.Time);
		values.add("" + time);
		output += XML_Writer.openTag(XML_Writer.tagWithAttributes(elementName, attrs, values));
		for (Event evt : events)
		{
			output += evt.toXML();
		}
		output += XML_Writer.closeTag(elementName);
		return output;
	}
}
