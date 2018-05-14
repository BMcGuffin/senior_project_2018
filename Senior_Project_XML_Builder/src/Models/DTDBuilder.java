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
	private static DTDBuilder singleton;

	private ArrayList<String> reserved;

	private TreeMap<String, ArrayList<String>> subelements;

	private DTDBuilder()
	{
		reserved = new ArrayList<>();
		subelements = new TreeMap<>();
		addReservedTags();
	}

	private void addReservedTags()
	{
		for (XML_Writer.tags tag : XML_Writer.tags.values())
		{
			reserved.add(tag.name());
		}
	}

	/**
	 * Access this DTDBuilder.
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
		if (reserved.contains(formatTag(evt.eventType)))
		{
			return false;
		}

		// Check to make sure event name is not already in list of known elements.
		if (subelements.containsKey(formatTag(evt.eventType)))
		{
			return false;
		}
		// Check to make sure the event name doesn't contain an invalid character.
		if (evt.eventType.contains(".") || evt.eventType.contains("_"))
		{
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
		ArrayList<Buildable> fields = new ArrayList<>();
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			fields.add(evt.getElement(i));
		}
		addNewSubElement(XML_Writer.tags.INSTANT.name(), formatTag(evt.eventType));
		for (Buildable field : fields)
		{
			addNewSubElement(evt.eventType, field.elementName());
			if (field instanceof Data_Media)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_SRC_FILE.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_PLAYBACK.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_START.name());
			}
			if (field instanceof Data_Text)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.TEXT_FIELD.name());
			}
			if (field instanceof Data_Transcript)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.TS_DIALOG_LINE.name());
				addNewSubElement(XML_Writer.tags.TS_DIALOG_LINE.name(), XML_Writer.tags.TS_LINE_ACTOR.name());
				addNewSubElement(XML_Writer.tags.TS_DIALOG_LINE.name(), XML_Writer.tags.TS_LINE_SPEECH.name());
			}
			if (field instanceof Data_Menu)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.MENU_OPTION.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MENU_SELECTED.name());
			}
		}

		return true;
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
			if (fieldNames.contains(formatTag(evt.getElement(i).elementName())))
			{
				return false;
			}
			fieldNames.add(evt.getElement(i).elementName());
		}

		// Remove data field names relevant to this event.
		subelements.remove(evt.eventType);

		// If we get here, we can safely add all elements to the DTD.
		ArrayList<Buildable> fields = new ArrayList<>();
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			fields.add(evt.getElement(i));
		}
		addNewSubElement(XML_Writer.tags.INSTANT.name(), formatTag(evt.eventType));
		for (Buildable field : fields)
		{
			addNewSubElement(evt.eventType, field.elementName());
			if (field instanceof Data_Media)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_SRC_FILE.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_PLAYBACK.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MEDIA_START.name());
			}
			if (field instanceof Data_Text)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.TEXT_FIELD.name());
			}
			if (field instanceof Data_Transcript)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.TS_DIALOG_LINE.name());
				addNewSubElement(XML_Writer.tags.TS_DIALOG_LINE.name(), XML_Writer.tags.TS_LINE_ACTOR.name());
				addNewSubElement(XML_Writer.tags.TS_DIALOG_LINE.name(), XML_Writer.tags.TS_LINE_SPEECH.name());
			}
			if (field instanceof Data_Menu)
			{
				addNewSubElement(field.elementName(), XML_Writer.tags.MENU_OPTION.name());
				addNewSubElement(field.elementName(), XML_Writer.tags.MENU_SELECTED.name());
			}
		}

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
		if (subelements.get(formatTag(superElement)) == null)
		{
			subelements.put(formatTag(superElement), new ArrayList<String>());
		}
		subelements.get(formatTag(superElement)).add(formatTag(element));
	}

	public static String formatTag(String tag)
	{
		String upper = tag.toUpperCase();
		String underscores = upper.replace(" ", "_");
		return underscores;
	}

	private String formatExternalDTD()
	{
		String output = "";
		
		ArrayDeque<String> elementQueue = new ArrayDeque<String>();
		ArrayList<String> alreadyAdded = new ArrayList<>();

		output += elementWithSubs(XML_Writer.tags.SCRIPT.name(), XML_Writer.tags.PLOTLINE.name() + "*");

		output += elementWithSubs(XML_Writer.tags.PLOTLINE.name(), XML_Writer.tags.INSTANT.name() + "*");
		
		elementQueue.add(XML_Writer.tags.INSTANT.name());
		alreadyAdded.add(XML_Writer.tags.INSTANT.name());
		
		while(!elementQueue.isEmpty())
		{
			String nextElement = elementQueue.poll();
			ArrayList<String> sublist = subelements.get(nextElement);
			
			if (sublist != null)
			{
				for (String subElement : sublist)
				{
					if(!alreadyAdded.contains(subElement))
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
		else
		{
			output += "(#PCDATA)";
		}
		output += ">\n";
		return output;
	}

	private String elementWithSubs(String element, String...subs)
	{
		String output = elementOpen(element);
		if (subs.length > 0)
		{
			output += "(";
			for (int i = 0; i < subs.length; i++)
			{
				output += subs[i];
				if (i < subs.length - 1)
				{
					output += "|";
				}

			}
			output += ")*";
		}
		else
		{
			output += "EMPTY";
		}
		output += ">\n";
		return output;
	}

	private String elementOpen(String tag)
	{
		return "<!ELEMENT " + tag + " ";
	}

	private String attListOpen(String element, String attName)
	{
		return "<!ATTLIST " + element + " " + attName + " ";
	}

	public void reset()
	{
		reserved = new ArrayList<>();
		subelements = new TreeMap<>();
		addReservedTags();
	}

}
