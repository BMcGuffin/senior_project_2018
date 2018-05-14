/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates events from templates. Accesses the folder of event templates,
 * known here as the "deck."
 *
 * @author Bryan McGuffin
 * @version Mar 11, 2018
 */
public class EventBuilder
{

	private DTDBuilder dtd;

	private File templatesFolder;

	private static EventBuilder singleton;

	/**
	 * Contains a prototype for each event type in the template deck.
	 */
	private Map<String, Event> prototypes;

	private List<Event> inScript;

	/**
	 * Constructor. Builds prototypes for each event. This will be a singleton
	 * class.
	 */
	private EventBuilder()
	{
		dtd = DTDBuilder.getDTDBuilder();
		prototypes = new TreeMap<>();
		templatesFolder = new File("src/Templates");
		inScript = new ArrayList<>();
		File[] files = templatesFolder.listFiles();
		for (File f : files)
		{
			try
			{
				buildPrototypesFromFile(f);
			}
			catch (Exception ex)
			{
				System.out.println("Had a problem importing event templates.");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Get the singleton instance of this builder.
	 *
	 * @return the event builder
	 */
	public static EventBuilder getBuilder()
	{
		if (singleton == null)
		{
			singleton = new EventBuilder();
		}
		return singleton;
	}

	/**
	 * Generate a new event of the given type.
	 *
	 * @param eventType
	 *            the type of the event.
	 * @return a deep copy of the prototype for the given event.
	 */
	public Event generateEvent(String eventType)
	{
		Event prototype = prototypes.get(eventType);

		// Return null if we don't have a prototype matching that event type
		if (prototype == null)
		{
			return null;
		}

		// If this type of event is being added to the script for the first time, add it
		// to the dtd
		if (!inScript.contains(prototype))
		{
			inScript.add(prototype);
			dtd.digestEvent(prototype);
		}

		Event newEvent = new Event(null);

		newEvent.eventType = prototype.eventType;

		for (int i = 0; i < prototype.fieldCount(); i++)
		{
			newEvent.addElement(prototype.getElement(i).duplicate(), prototype.getLabel(i));
		}

		return newEvent;

	}

	/**
	 * Get a list of all valid event types in the deck.
	 *
	 * @return a list of all names of event types.
	 */
	public ArrayList<String> getEventDeck()
	{
		ArrayList<String> values = new ArrayList<>(prototypes.keySet());
		return values;
	}

	/**
	 * Given a new event, create an entry for it in the event deck and add a
	 * prototype to the list of prototypes.
	 *
	 * @param evt
	 *            the event to be added to the prototype list.
	 * @return false if the event name is already taken.
	 */
	public boolean recordEventToDeck(Event evt)
	{
		// Check to make sure a file with this name doesn't exist.
		String ecfName = formatECFName(evt);
		File[] files = templatesFolder.listFiles();
		for (File f : files)
		{
			if (f.getName().equals(ecfName))
			{
				return false;
			}
		}

		// Construct a file in the templates folder.
		File newEvent = new File(templatesFolder.getPath() + File.pathSeparator + ecfName);
		try
		{
			newEvent.createNewFile();
		}
		catch (IOException ex)
		{
			return false;
		}

		// Write to the new file.
		BufferedWriter bw;
		try
		{
			bw = new BufferedWriter(new FileWriter(newEvent));
			bw.write(this.writeEventToConfigFile(evt));
			bw.flush();
			bw.close();
		}
		catch (IOException ex)
		{
			System.out.println("Unable to generate ecf file " + ecfName);
			return false;
		}
		return true;
	}

	private String formatECFName(Event evt)
	{
		String lower = evt.eventType.toLowerCase();
		String underscores = lower.replace(" ", "_");
		String ecf = underscores + ".ecf";
		return ecf;
	}

	/**
	 * Create a prototype from a given event type file in the deck.
	 *
	 * @param typeName
	 *            the type of the event.
	 * @return a prototype event.
	 */
	private Event buildPrototypesFromFile(File conf) throws FileNotFoundException, IOException
	{
		FileReader fr = null;
		fr = new FileReader(conf);
		BufferedReader in = new BufferedReader(fr);
		Event evt = new Event(null);
		int numberOfFields;
		// First line is event type
		evt.eventType = in.readLine();
		// Second line is number of fields
		numberOfFields = Integer.parseInt(in.readLine());
		// Execute on each of the remaining fields
		for (int i = 0; i < numberOfFields; i++)
		{
			// First line is label for field
			String label = in.readLine();
			// Second line is type of data
			String dataType = in.readLine();
			Buildable b = null;
			switch (dataType)
			{
				case "type_media":
					b = new Data_Media(label);
					// First line is path to media file
					String filename = in.readLine();
					// Second line is time to start playback
					int startTime = Integer.parseInt(in.readLine());
					// Third line is length of playback
					int playLength = Integer.parseInt(in.readLine());
					File f = new File(filename);
					((Data_Media) b).setMediaFile(f);
					((Data_Media) b).setPlayLength(startTime);
					((Data_Media) b).setStartTime(playLength);
					break;
				case "type_menu":
					b = new Data_Menu(label);
					// First line is number of options in menu
					int numberOfOptions = Integer.parseInt(in.readLine());
					// Next lines are all the menu options
					for (int j = 0; j < numberOfOptions; j++)
					{
						((Data_Menu) b).addOption(in.readLine());
					}
					// Last line is currently selected index
					((Data_Menu) b).setSelected(Integer.parseInt(in.readLine()));
					break;
				case "type_text":
					b = new Data_Text(label);
					// First line is 1 or 0. If 1, text follows. If 0, textbox is blank
					int hasText = Integer.parseInt(in.readLine());
					if (hasText == 1)
					{
						((Data_Text) b).setContent(in.readLine());
					}
					break;
				case "type_transcript":
					b = new Data_Transcript(label);
					// First line is number of lines of dialog
					int numberOfLines = Integer.parseInt(in.readLine());
					// Next lines are all lines of dialog
					// Actor on one line, dialog on the next
					for (int j = 0; j < numberOfLines; j++)
					{
						String actor = in.readLine();
						String dialog = in.readLine();
						((Data_Transcript) b).addLine(actor, dialog);
					}
					break;
			}
			evt.addElement(b, label);
		}
		in.close();

		prototypes.put(DTDBuilder.formatTag(evt.eventType), evt);
		return evt;
	}

	public void reset()
	{
		inScript = new ArrayList<>();
	}

	private String writeEventToConfigFile(Event evt)
	{
		String output = "";
		// First line is event type
		output += evt.eventType + "\n";
		// Second line is number of fields
		output += evt.fieldCount() + "\n";
		// Execute on each of the remaining fields
		for (int i = 0; i < evt.fieldCount(); i++)
		{
			Buildable b = evt.getElement(i);
			// First line is label for field
			output += evt.getLabel(i) + "\n";
			// Second line is type of data
			if (b instanceof Data_Media)
			{
				output += "type_media" + "\n";
				// First line is path to media file
				output += ((Data_Media) b).getFileName() + "\n";
				// Second line is time to start playback
				output += ((Data_Media) b).getStartTime() + "\n";
				// Third line is length of playback
				output += ((Data_Media) b).getPlayLength() + "\n";
			}
			else if (b instanceof Data_Menu)
			{
				output += "type_menu" + "\n";
				// First line is number of options in menu
				output += ((Data_Menu) b).menuSize() + "\n";
				// Next lines are all the menu options
				String currentSelection = ((Data_Menu) b).getSelected();
				int selectedIndex = 0;
				for (int j = 0; j < ((Data_Menu) b).menuSize(); j++)
				{
					((Data_Menu) b).setSelected(j);
					String str = ((Data_Menu) b).getSelected();
					if (str.equals(currentSelection))
					{
						selectedIndex = j;
					}
					output += str + "\n";

				}
				// Last line is currently selected index
				output += selectedIndex + "\n";
			}
			else if (b instanceof Data_Text)
			{
				output += "type_text" + "\n";
				String text = ((Data_Text) b).getContent();
				// First line is 1 or 0. If 1, text follows. If 0, textbox is blank
				if (text.isEmpty())
				{
					output += 0 + "\n";
				}
				else
				{
					output += 1 + "\n";
					output += text + "\n";
				}

			}
			else if (b instanceof Data_Transcript)
			{
				output += "type_transcript" + "\n";
				// First line is number of lines of dialog
				output += ((Data_Transcript) b).length() + "\n";
				// Next lines are all lines of dialog
				// Actor on one line, dialog on the next
				for (int j = 0; j < ((Data_Transcript) b).length(); j++)
				{
					Line line = ((Data_Transcript) b).getLine(j);
					output += line.actor + "\n";
					output += line.dialog + "\n";
				}
			}
		}

		return output;
	}
}
