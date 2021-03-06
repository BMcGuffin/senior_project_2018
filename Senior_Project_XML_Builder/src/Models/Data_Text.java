/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.util.ArrayList;
import com.sun.xml.internal.ws.spi.db.XMLBridge;

/**
 * Represents a text field.
 *
 * @author Bryan McGuffin
 * @version Feb 20, 2018
 */
public class Data_Text implements Buildable
{
	/**
	 * The name of this text field.
	 */
	public String elementName;
	/**
	 * The content of this text field.
	 */
	private String text;

	/**
	 * Constructor.
	 */
	public Data_Text(String eName)
	{
		text = "";
		elementName = eName;
	}

	/**
	 * Setter for the text field.
	 *
	 * @param str
	 *            the text to be used.
	 */
	public void setContent(String str)
	{
		text = str;
	}

	/**
	 * Getter for the text field.
	 *
	 * @return the current text.
	 */
	public String getContent()
	{
		return text;
	}

	@Override
	public Buildable duplicate()
	{
		Data_Text dup = new Data_Text(this.elementName);
		dup.setContent(this.text);
		return dup;
	}

	@Override
	public String elementName()
	{
		return elementName;
	}

	/**
	 * {@inheritDoc} This class uses the following tags: TEXT (Holds the text from
	 * this text field).
	 */
	@Override
	public String toXML()
	{
		String output = "";
		ArrayList<XML_Writer.attributes> attrs = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		
		attrs.add(XML_Writer.attributes.Data_Type);
		
		values.add("Text");
		output += XML_Writer.openTag(XML_Writer.tagWithAttributes(elementName, attrs, values));
		output += text;
		output += XML_Writer.closeTag(elementName);
		return output;
	}

	@Override
	public void reset()
	{
		text = "";
	}
}
