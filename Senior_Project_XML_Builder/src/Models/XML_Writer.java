/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

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
}
