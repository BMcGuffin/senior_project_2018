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
 * The transcript of a conversation between one or more actors. A series of
 * lines in chronological order.
 *
 * @author Bryan McGuffin
 * @version Feb 20, 2018
 */
public class Data_Transcript implements Buildable
{
	/**
	 * The name of this text field.
	 */
	public String elementName;
	/**
	 * A list of the actors in this conversation.
	 */
	private List<String> actors;
	/**
	 * A list of the lines spoken in this conversation.
	 */
	private List<Line> lines;

	/**
	 * Constructor.
	 */
	public Data_Transcript(String eName)
	{
		elementName = eName;
		actors = new ArrayList<>();
		lines = new ArrayList<>();
	}

	/**
	 * Add a single line of dialog to this transcript.
	 *
	 * @param actor
	 *            the entity speaking this line.
	 * @param dialog
	 *            the line spoken.
	 */
	public void addLine(String actor, String dialog)
	{
		if (!actors.contains(actor))
		{
			actors.add(actor);
		}
		lines.add(new Line(actor, dialog));
	}

	/**
	 * Get the number of lines of dialog in this conversation.
	 *
	 * @return the length of the transcript.
	 */
	public int length()
	{
		return lines.size();
	}

	/**
	 * Access a particular line from this transcript.
	 *
	 * @param index
	 *            the index of the line in question.
	 * @return a line from the transcript.
	 */
	public Line getLine(int index)
	{
		if (index < lines.size() && index >= 0)
		{
			return lines.get(index);
		}
		return null;
	}

	/**
	 * Delete a line of dialog from this transcript.
	 *
	 * @param index
	 *            the index of the line to be removed.
	 * @return true if the line was successfully removed.
	 */
	public boolean removeLine(int index)
	{
		if (index < lines.size() && index >= 0)
		{
			return lines.remove(index) != null;
		}
		return false;
	}

	/**
	 * Move a line up one position in the transcript.
	 *
	 * @param index
	 *            the index of the line to be moved.
	 */
	public boolean moveLineUp(int index)
	{
		if (index > 0 && index < lines.size())
		{
			Line temp = lines.get(index - 1);
			lines.set(index - 1, lines.get(index));
			lines.set(index, temp);
			return true;
		}
		return false;
	}

	/**
	 * Move a line down one position in the transcript.
	 *
	 * @param index
	 *            the index of the line to be moved.
	 */
	public boolean moveLineDown(int index)
	{
		if (index >= 0 && index < lines.size() - 1)
		{
			Line temp = lines.get(index + 1);
			lines.set(index + 1, lines.get(index));
			lines.set(index, temp);
			return true;
		}
		return false;
	}

	/**
	 * Get all the actors in this conversation.
	 *
	 * @return an array of actors.
	 */
	public String[] getActors()
	{
		String[] result = new String[actors.size()];
		result = actors.toArray(result);
		return result;
	}

	@Override
	public Buildable duplicate()
	{
		Data_Transcript dup = new Data_Transcript(this.elementName);
		for (Line l : this.lines)
		{
			dup.addLine(l.actor, l.dialog);
		}
		return dup;
	}

	@Override
	public String elementName()
	{
		return elementName;
	}

	/**
	 * {@inheritDoc} This class uses the following tags: DIALOG_LINE (holds one line
	 * of dialog), LINE_ACTOR (holds the identifier for this line), LINE_SPEECH
	 * (holds the dialog spoken on this line).
	 */
	@Override
	public String toXML()
	{
		String output = "";
		ArrayList<XML_Writer.attributes> attrs = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		attrs.add(XML_Writer.attributes.Data_Type);
		values.add("Transcript");
		output += XML_Writer.openTag(XML_Writer.tagWithAttributes(elementName, attrs, values));
		for (Line l : lines)
		{
			XML_Writer.tags tag = XML_Writer.tags.TS_DIALOG_LINE;
			ArrayList<String> vals = new ArrayList<>();
			vals.add(l.actor);
			vals.add(l.dialog);
			output += XML_Writer.emptyTag(XML_Writer.tagWithAttributes(tag.name(), tag.ATTRS, vals));
		}
		output += XML_Writer.closeTag(elementName);
		return output;
	}

	@Override
	public void reset()
	{
		actors = new ArrayList<>();
		lines = new ArrayList<>();
	}
}
