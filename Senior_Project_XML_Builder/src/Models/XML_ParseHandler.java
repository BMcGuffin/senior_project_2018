/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Bryan McGuffin
 * @version Apr 17, 2018
 */
public class XML_ParseHandler extends DefaultHandler
{
	public XML_ParseHandler()
	{
		System.out.println("Constructor for handler");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
	{
		System.out.println("Start: " + qName);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		System.out.println("End: " + qName);
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException
	{
		System.out.println("Characters: " + ch);
	}

	public Script generateScript()
	{
		Script newScript = new Script();
		newScript.addPlotline("Hello world", 0);
		return newScript;
	}
}
