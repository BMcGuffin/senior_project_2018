/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Represents the DTD used by this script. Has a reference to the script it's
 * part of. Contains a tree of tags.
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 */
public class DTDBuilder
{
	/**
	 * Reference to this object. All classes that utilize DTDBuilder need a
	 * consistent reference to the same instance.
	 */
	private static DTDBuilder singleton;
	private ArrayList<String> reserved;
	/**
	 * Key is the name of an element. Value is a list of sub-elements of that
	 * element.
	 */
	private TreeMap<String, ArrayList<String>> subelements;
	/**
	 * Key is the name of an element. Value is a list of attributes of that element.
	 */
	private TreeMap<String, ArrayList<String>> attributes;

	private DTDBuilder()
	{
		reserved = new ArrayList<>();
		subelements = new TreeMap<>();
		attributes = new TreeMap<>();
		addReservedTags();
	}

	/**
	 * Reserved tags that are defined by the system and are unavailable for use by
	 * users.
	 */
	private void addReservedTags()
	{
		for (XML_Writer.tags tag : XML_Writer.tags.values())
		{
			reserved.add(tag.name());
			for (XML_Writer.attributes attr : tag.ATTRS)
			{
				addNewAttribute(tag.name(), attr.name());
			}
		}
	}

	/**
	 * Access this DTDBuilder. Generates a singleton reference to the DTDBuilder
	 * object such that all classes which utilize this class have a reference to the
	 * same instance of the class.
	 *
	 * @param scr
	 *            the current script
	 * @return the DTDBuilder for this script
	 */
	public static DTDBuilder getDTDBuilder()
	{
		if (singleton == null)
		{
			singleton = new DTDBuilder();
		}
		return singleton;
	}

	/**
	 * Generate the DTD file for this script.
	 */
	public void generateDTD(String scriptTitle) throws IOException
	{
		//FIXME this method should save the DTD in the same folder as the script
		String filename = getDTDName(scriptTitle);
		File file = new File(filename);
		file.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(formatExternalDTD());
		bw.flush();
		bw.close();
	}

	public String getDTDName(String title)
	{
		return "" + title + ".dtd";
	}

	/**
	 * Check each prospective tag in this event. If the event type of this event is
	 * not unique and valid, or more than one data field has the same label, reject
	 * it; otherwise, format new tags for all data fields and add them to the DTD.
	 *
	 * @param evt
	 *            the event to be checked
	 * @return true if the event can be safely added to the DTD.
	 */
	public boolean digestEvent(Event evt)
	{
		// Check to make sure event name is not reserved keyword.
		if (reserved.contains(evt.eventType))
		{
			System.out.println("ERROR: Event name is reserved keyword");
			return false;
		}
		// Check to make sure event name is not already in list of known elements.
		if (subelements.containsKey(evt.eventType))
		{
			System.out.println("ERROR: Event name is in list of known elements");

			return false;
		}
		// Check to make sure the event name doesn't contain an invalid character.
		if (evt.eventType.contains("."))
		{
			System.out.println("ERROR: Event name contains invalid characters");

			return false;
		}
		// Check data field names for uniqueness.
		ArrayList<String> fieldNames = new ArrayList<>();
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			if (fieldNames.contains(evt.getElement(i).elementName()))
			{
				return false;
			}
			fieldNames.add(evt.getElement(i).elementName());
		}
		// If we get here, we can safely add all elements to the DTD.
		addEvent(evt);
		return true;
	}

	public void addEvent(Event evt)
	{
		ArrayList<Buildable> fields = new ArrayList<>();
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			fields.add(evt.getElement(i));
		}
		addNewSubElement(XML_Writer.tags.INSTANT.name(), evt.eventType);
		for (Buildable field : fields)
		{
			addNewSubElement(evt.eventType, field.elementName());
			addNewAttribute(field.elementName(), XML_Writer.attributes.Data_Type.name());
			if (field instanceof Data_Media)
			{
				addNewAttribute(field.elementName(), XML_Writer.attributes.Src_File.name());
				addNewAttribute(field.elementName(), XML_Writer.attributes.Playback.name());
				addNewAttribute(field.elementName(), XML_Writer.attributes.Start.name());
			}
			if (field instanceof Data_Text)
			{
				subelements.put(field.elementName(), new ArrayList<String>());
			}
			if (field instanceof Data_Transcript)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.TS_DIALOG_LINE.name());
			}
			if (field instanceof Data_Menu)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.MENU_OPTION.name());
			}
		}
	}

	/**
	 * Update the DTD elements relating to this event. Remove tags that have been
	 * deprecated, and add newly created tags.
	 *
	 * @param evt
	 *            the event in question
	 * @return true if the new tags retain uniqueness
	 */
	public boolean updateEvent(Event evt)
	{
		// Check data field names for uniqueness.
		ArrayList<String> fieldNames = new ArrayList<>();
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			if (fieldNames.contains(evt.getElement(i).elementName()))
			{
				return false;
			}
			fieldNames.add(evt.getElement(i).elementName());
		}
		// Remove data field names relevant to this event.
		subelements.remove(evt.eventType);
		// If we get here, we can safely add all elements to the DTD.
		addEvent(evt);
		return true;
	}

	/**
	 * Remove all tags related to this event.
	 *
	 * @param evt
	 *            the event in question.
	 * @return true if the event was successfully removed.
	 */
	public void removeEvent(Event evt)
	{
		subelements.get(XML_Writer.tags.INSTANT.name()).remove(evt.eventType);
		subelements.remove(evt.eventType);
	}

	private void addNewSubElement(String superElement, String element)
	{
		if (subelements.get(superElement) == null)
		{
			subelements.put(superElement, new ArrayList<String>());
		}
		subelements.get(superElement).add(element);
	}

	private void addNewAttribute(String element, String attribute)
	{
		if (attributes.get(element) == null)
		{
			attributes.put(element, new ArrayList<String>());
		}
		attributes.get(element).add(attribute);
	}

	private String formatExternalDTD()
	{
		String output = "";
		ArrayDeque<String> elementQueue = new ArrayDeque<String>();
		ArrayList<String> alreadyAdded = new ArrayList<>();
		ArrayList<String> tempList = new ArrayList<>();
		tempList.add(XML_Writer.tags.PLOTLINE.name() + "*");
		output += elementWithSubs(XML_Writer.tags.SCRIPT.name(), tempList);
		tempList.clear();
		tempList.add(XML_Writer.tags.INSTANT.name() + "*");
		output += elementWithSubs(XML_Writer.tags.PLOTLINE.name(), tempList);
		elementQueue.add(XML_Writer.tags.INSTANT.name());
		alreadyAdded.add(XML_Writer.tags.INSTANT.name());
		while (!elementQueue.isEmpty())
		{
			String nextElement = elementQueue.poll();
			ArrayList<String> sublist = subelements.get(nextElement);
			if (sublist != null)
			{
				for (String subElement : sublist)
				{
					if (!alreadyAdded.contains(subElement))
					{
						elementQueue.add(subElement);
						alreadyAdded.add(subElement);
					}
				}
			}
			output += elementWithSubs(nextElement, sublist);
		}
		return output;
	}

	private String elementWithSubs(String element, ArrayList<String> subs)
	{
		String output = elementOpen(element);
		if (subs != null && subs.size() > 0)
		{
			output += "(";
			for (int i = 0; i < subs.size(); i++)
			{
				output += subs.get(i);
				if (i < subs.size() - 1)
				{
					output += "|";
				}
			}
			output += ")*";
		}
		else if (subs != null && subs.isEmpty())// Subs is empty if subElements does contain an entry for this element,
												// but no subelements were added
		{
			output += "(#PCDATA)";
		}
		else if (subs == null)// Subs is null if subElements does NOT contain an entry for this element
		{
			output += "EMPTY";
		}
		output += ">\n";
		output += attributes(element);
		return output;
	}

	private String elementOpen(String tag)
	{
		return "<!ELEMENT " + tag + " ";
	}

	private String attributes(String element)
	{
		String output = "";
		if (attributes.get(element) != null)
		{
			for (String attr : attributes.get(element))
			{
				output += singleAttribute(element, attr);
			}
		}
		return output;
	}

	private String singleAttribute(String element, String attName)
	{
		return "<!ATTLIST " + element + " " + attName + " CDATA #REQUIRED>\n";
	}

	public void reset()
	{
		reserved = new ArrayList<>();
		subelements = new TreeMap<>();
		addReservedTags();
	}
}
