/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.util.ArrayList;

/**
 * A tool for making XML formatting easier. Contains methods for formatting XML
 * data. Utilize this class in combination with the XML_Writable interface.
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 * @see XML_Writable
 */
public abstract class XML_Writer
{
	public static String openTag(String s)
	{
		return "<" + s + ">\n";
	}

	public static String closeTag(String s)
	{
		return "</" + s + ">\n";
	}

	public static String emptyTag(String s)
	{
		return "<" + s + "/>\n";
	}

	public static String SimpleTag(String s, String body)
	{
		return openTag(s) + body + closeTag(s);
	}
	
	public static String tagWithAttributes(String tagName, ArrayList<attributes> attrs, ArrayList<String> values)
	{
		String output = "" + tagName;
		
		for(int i = 0; i < attrs.size() - 1; i++)
		{
			output += " " + attrs.get(i).name() + "=\""+values.get(i)+"\"";
		}
		output += " " + attrs.get(attrs.size() - 1) + "=\""+values.get(attrs.size() - 1)+"\"";
		
		return output;
	}

	public static enum tags
	{
		SCRIPT(attributes.Title),
		PLOTLINE(attributes.Title, attributes.Time),
		INSTANT(attributes.Time),
		TS_DIALOG_LINE(attributes.Actor, attributes.Text),
		MENU_OPTION(attributes.Title, attributes.Selected);
		private tags(attributes...attrs)
		{
			this.ATTRS = new ArrayList<>();
			for (attributes attr : attrs)
			{
				this.ATTRS.add(attr);
			}
		}

		public ArrayList<attributes> ATTRS;

		public static tags fromString(String str)
		{
			for (tags t : tags.values())
			{
				if (t.name().equalsIgnoreCase(str))
				{
					return t;
				}
			}
			return null;
		}
	}

	public static enum attributes
	{
		//Attributes of reserved tags with known names
		Title,
		Time,
		Actor,
		Text,
		Selected,
		
		//Attributes of events; names may vary
		Src_File,
		Playback,
		Start,
		Data_Type;
	}
}
