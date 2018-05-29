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
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * A complete script, which contains one or more plotlines. It has a running
 * time and may have some header data. It contains a reference to the DTD which
 * is used by this script. The script can be converted to XML via the
 * XML_Writable interface.
 *
 * @author Bryan McGuffin
 * @version Feb 1, 2018
 * @see Plotline
 */
public class Script extends Observable implements XML_Writable
{

	/**
	 * The plotlines which make up this script.
	 */
	public List<Plotline> plotlines;
	public String scriptTitle;
	public String description;
	public File saveFile;
	private EventBuilder eb;
	public DTDBuilder dtd;

	/**
	 * Constructor. Generates a new Script object.
	 */
	public Script()
	{
		plotlines = new ArrayList<>();
		scriptTitle = "untitled1";
		description = "";
		saveFile = null;
		dtd = DTDBuilder.getDTDBuilder();
		eb = EventBuilder.getBuilder();
	}

	/**
	 * Generate a new plotline which starts at the given time, and add it to the
	 * list.
	 *
	 * @param start
	 *            The number of seconds into the script that the plotline should
	 *            begin.
	 * @return the index of the new plotline.
	 */
	public int addPlotline(String title, int start)
	{
		Plotline p = new Plotline(title, start, this);

		plotlines.add(p);
		setChanged();
		notifyObservers();
		return plotlines.size() - 1;
	}

	/**
	 * Get the plotline at the given index.
	 *
	 * @param index
	 *            The index of the plotline.
	 * @return the plotline in question.
	 */
	public Plotline getPlotLine(int index)
	{
		if (index < plotlines.size() && index >= 0)
		{
			return plotlines.get(index);
		}
		return null;
	}

	/**
	 * Remove a plotline from the script.
	 *
	 * @param index
	 *            the position of the plotline to be removed.
	 * @return true if that plotline was removed.
	 */
	public boolean removePlotline(int index)
	{
		if (index < plotlines.size() && index >= 0)
		{
			plotlines.remove(index);
			setChanged();
			notifyObservers();
			return true;
		}
		setChanged();
		notifyObservers();
		return false;
	}

	/**
	 * Get the number of plotlines in this script.
	 *
	 * @return the number of registered plotlines.
	 */
	public int countPlotlines()
	{
		return plotlines.size();
	}

	/**
	 * Send out the initial update so that all views have a connection.
	 */
	public void spark()
	{
		setChanged();
		notifyObservers();
	}

	@Override
	public String toXML()
	{
		String output = "";
		// Preamble
		output += "<?xml version=\"1.0\" standalone=\"no\"?>\n";
		output += "<!DOCTYPE SCRIPT SYSTEM \"" + dtd.getDTDName(this.scriptTitle) + "\">\n";


		ArrayList<XML_Writer.attributes> attrs = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		String elementName = XML_Writer.tags.SCRIPT.name();
		attrs.add(XML_Writer.attributes.Title);
		values.add(scriptTitle);
		output += XML_Writer.openTag(XML_Writer.tagWithAttributes(elementName, attrs, values));
		for (Plotline plt : plotlines)
		{
			output += plt.toXML();
		}
		output += XML_Writer.closeTag(elementName);
		return output;
	}

	/**
	 * Write the current script out to a file.
	 */
	public void saveToFile()
	{
		try
		{
			dtd.generateDTD(this.scriptTitle);
		}
		catch (Exception ex)
		{
			System.out.println("Unable to generate DTD file " + dtd.getDTDName(this.scriptTitle));
			ex.printStackTrace();
		}
		BufferedWriter bw;
		try
		{
			bw = new BufferedWriter(new FileWriter(saveFile));
			bw.write(this.toXML());
			bw.flush();
			bw.close();
		}
		catch (IOException ex)
		{
			System.out.println("Unable to generate XML file " + saveFile.getName());
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Start a new file.
	 */
	public void newFile()
	{
		plotlines = new ArrayList<>();
		scriptTitle = "untitled1";
		description = "";
		saveFile = null;
		dtd.reset();
		eb.reset();
		setChanged();
		notifyObservers();
	}

	public void loadFromFile(File newFile)
	{
		try
		{
			newFile();
			saveFile = newFile;
			System.out.println("Parsing file...");
			XML_ParseHandler ph = new XML_ParseHandler();
			SAXParserFactory.newInstance().newSAXParser().parse(newFile, ph);
			System.out.println("Parsed file.");
			Script newScript = ph.generateScript();
			this.description = newScript.description;
			this.saveFile = newScript.saveFile;
			this.scriptTitle = newScript.scriptTitle;
			this.plotlines = newScript.plotlines;
		}
		catch (Exception ex)
		{
			System.out.println("Unable to read XML file " + newFile.getName());
		}
		setChanged();
		notifyObservers();
	}

	public String toString()
	{
		return "Script \"" + this.scriptTitle + "\"";
	}

	public int getLength()
	{
		int largest = 0;
		for (Plotline plt : plotlines)
		{
			if ((plt.startTime + plt.length()) > largest)
			{
				largest = (plt.startTime + plt.length());
			}
		}
		return largest;
	}

}
