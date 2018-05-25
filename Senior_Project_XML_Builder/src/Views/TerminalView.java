/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Views;

import Controls.*;
import java.util.Observable;
import java.util.Scanner;
import com.sun.media.sound.SF2Modulator;
import com.sun.xml.internal.ws.util.NoCloseOutputStream;
import Models.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A terminal interface for the program. Reads from, and writes to, the
 * terminal.
 *
 * @author Bryan McGuffin
 * @version Mar 5, 2018
 */
public class TerminalView implements I_View
{
	String demarcation = "================================================================================";
	Scanner in;
	I_Controller ctrl;
	EventBuilder bldr;
	boolean quitThis;
	menus currentMenu;
	int currentPlotIndex;
	int currentInstIndex;
	int currentEventIndex;
	int currentDataFieldIndex;
	Script scr;

	public TerminalView(I_Controller c)
	{
		in = new Scanner(System.in);
		quitThis = false;
		currentMenu = menus.MAINMENU;
		ctrl = c;
		currentPlotIndex = 0;
		bldr = EventBuilder.getBuilder();
	}

	@Override
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			this.getNextInput();
		}
	}

	/**
	 * Print the current menu text and get the next input from the user.
	 */
	public void getNextInput()
	{
		while (true)
		{
			updateTerminal();
			System.out.println();
			System.out.println(demarcation);
			System.out.println(currentMenu.MENU_TEXT);
			System.out.println(demarcation);
			String input = in.nextLine();
			for (String option : currentMenu.OPTIONS)
			{
				if (input.equalsIgnoreCase(option))
				{
					processCommand(option);
				}
			}
		}
	}

	/**
	 * Change the menu based on the user's input. Also send commands to the
	 * controller based on user input.
	 * 
	 * @param option
	 *            the input given by the user.
	 */
	public void processCommand(String option)
	{
		Command cmd = Command.NOTHING;
		List<String> args = new ArrayList<>();
		Instance inst = null;
		Event evt = null;
		switch (currentMenu)
		{
			case MAINMENU:
				switch (option.toLowerCase())
				{
					case "x":// exit the program
						cmd = Command.QUIT;
						break;
					case "a":// add a new plotline to the script
						cmd = Command.ADD_PLOTLINE;
						System.out.println("Plotline name:");
						args.add(in.nextLine());
						args.add("" + getIntFromInput("Start time:"));
						break;
					case "r":// remove a plotline from the script
						cmd = Command.REMOVE_PLOTLINE;
						args.add("" + getIntFromInput("Index to remove:"));
						break;
					case "s":// print out the current script to a file
						if (scr.saveFile == null)
						{
							cmd = Command.SAVE_AS;
							System.out.println("Enter the name of the save file:");
							args.add(in.nextLine());
						}
						else
						{
							cmd = Command.SAVE;
						}
						break;
					case "p":// go to script settings menu
						cmd = Command.NOTHING;
						currentMenu = menus.SCRIPTPREFERENCES;
						break;
					case "e":// go to edit plotline menu
						cmd = Command.NOTHING;
						currentPlotIndex = getIntFromInput("Enter the index of the plotline you'd like to edit.");
						currentMenu = menus.EDITPLOTLINE;
						break;
					default:
						break;
				}
				break;
			case SCRIPTPREFERENCES:
				switch (option.toLowerCase())
				{
					case "r":// rename script
						cmd = Command.CHANGE_SCRIPT_TITLE;
						System.out.println("Enter new script title:");
						args.add(in.nextLine());
						break;
					case "d":// change script description
						cmd = Command.CHANGE_SCRIPT_DESCRIPTION;
						System.out.println("Enter new description:");
						args.add(in.nextLine());
						break;
					case "s":// save as
						cmd = Command.SAVE_AS;
						System.out.println("Enter the name of the save file:");
						args.add(in.nextLine());
						break;
					case "l":// load script
						cmd = Command.LOAD_FILE;
						System.out.println("Enter the path to the file:");
						args.add(in.nextLine());
						break;
					case "n":// new script
						cmd = Command.NEW_FILE;
						System.out.println("Do you want to save the current file? y/n");
						if (in.nextLine().equalsIgnoreCase("n"))
						{
							args.add("0");
						}
						else
						{
							args.add("1");
						}
						break;
					case "b":// back to main menu
						currentMenu = menus.MAINMENU;
						break;
					default:
						break;
				}
				break;
			case EDITPLOTLINE:
				switch (option.toLowerCase())
				{
					case "n":// Rename plotline
						cmd = Command.CHANGE_PLOTLINE_TITLE;
						System.out.println("Enter new plotline title:");
						args.add(in.nextLine());
						break;
					case "d":// Change plotline description
						cmd = Command.CHANGE_PLOTLINE_DESCRIPTION;
						System.out.println("Enter new plotline description:");
						args.add(in.nextLine());
						break;
					case "t":// Change start time
						cmd = Command.CHANGE_PLOTLINE_START;
						args.add("" + getIntFromInput("Enter new start time in seconds:"));
						break;
					case "l":// Get list of events in this plotline
						cmd = Command.NOTHING;
						for (Integer i : scr.getPlotLine(currentPlotIndex).getPopulatedTimes())
						{
							inst = scr.getPlotLine(currentPlotIndex).getInstance(i);
							for (Event e : inst.events)
							{
								String str = String.format("(%d) %s", i, e.toString());
								System.out.println(str);
							}
						}
						break;
					case "a":// Add new event
						cmd = Command.ADD_EVENT;
						if (scr.getPlotLine(currentPlotIndex).instanceCount() == 0)
						{
							System.out.println("First event in this plotline will be added to time T = 0.");
							args.add("" + 0);
						}
						else
						{
							args.add("" + getIntFromInput("Enter time (in seconds) to add new event:"));
						}
						System.out.println("Available event types:");
						ArrayList<String> types = bldr.getEventDeck();
						for (int i = 0; i < types.size(); i++)
						{
							System.out.println(i + ": " + types.get(i));
						}
						System.out.println("Choose an event type:");
						args.add(in.nextLine());
						break;
					case "r":// Remove event
						cmd = Command.REMOVE_EVENT;
						int tempInstIndex = getIntFromInput("Enter time (in seconds) of event to be removed:");
						if (scr.getPlotLine(currentPlotIndex).getInstance(tempInstIndex) == null)
						{
							System.out.println("No events at that time.");
							cmd = Command.NOTHING;
							break;
						}
						currentInstIndex = tempInstIndex;
						inst = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex);
						System.out.println("Found " + inst.events.size() + " events at this time.");
						for (int i = 0; i < inst.events.size(); i++)
						{
							String str = String.format("[%d] %s", i, inst.events.get(i));
							System.out.println(str);
						}
						args.add("" + getIntFromInput("Enter index of event to remove:"));
						break;
					case "e":// Go to edit event menu
						cmd = Command.NOTHING;
						tempInstIndex = getIntFromInput("Enter time (in seconds) of event to be edited:");
						if (scr.getPlotLine(currentPlotIndex).getInstance(tempInstIndex) == null)
						{
							System.out.println("No events at that time.");
							break;
						}
						currentInstIndex = tempInstIndex;
						inst = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex);
						System.out.println("Found " + inst.events.size() + " events at this time.");
						for (int i = 0; i < inst.events.size(); i++)
						{
							String str = String.format("[%d] %s", i, inst.events.get(i).eventType);
							System.out.println(str);
						}
						int evtIndex = getIntFromInput("Enter index of event to edit:");
						if (evtIndex < inst.events.size())
						{
							currentEventIndex = evtIndex;
							currentMenu = menus.EDITEVENT;
						}
						else
						{
							System.out.println("No event at that index.");
						}
						break;
					case "m":// Move event
						cmd = Command.RELOCATE_EVENT;
						tempInstIndex = getIntFromInput("Enter time (in seconds) of event to be moved:");
						if (scr.getPlotLine(currentPlotIndex).getInstance(tempInstIndex) == null)
						{
							System.out.println("No events at that time.");
							cmd = Command.NOTHING;
							break;
						}
						currentInstIndex = tempInstIndex;
						inst = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex);
						System.out.println("Found " + inst.events.size() + " events at this time.");
						for (int i = 0; i < inst.events.size(); i++)
						{
							String str = String.format("[%d] %s", i, inst.events.get(i));
							System.out.println(str);
						}
						args.add("" + getIntFromInput("Enter index of event to move:"));
						args.add("" + getIntFromInput("Enter new time (in seconds) for event:"));
						break;
					case "b":// Back to main menu
						currentMenu = menus.MAINMENU;
						break;
					default:
						break;
				}
				break;
			case EDITEVENT:
				cmd = Command.NOTHING;
				evt = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex).getEvent(currentEventIndex);
				switch (option.toLowerCase())
				{
					case "e": // Choose which data element in this event to edit.
						int tempData = getIntFromInput("Enter index of data field to be edited:");
						if (tempData >= evt.fieldCount())
						{
							System.out.println("No field at that index.");
							break;
						}
						currentDataFieldIndex = tempData;
						if (evt.getElement(currentDataFieldIndex) instanceof Data_Menu)
						{
							currentMenu = menus.EDITDATA_MENU;
						}
						else if (evt.getElement(currentDataFieldIndex) instanceof Data_Media)
						{
							currentMenu = menus.EDITDATA_MEDIA;
						}
						else if (evt.getElement(currentDataFieldIndex) instanceof Data_Text)
						{
							currentMenu = menus.EDITDATA_TEXTFIELD;
						}
						else if (evt.getElement(currentDataFieldIndex) instanceof Data_Transcript)
						{
							currentMenu = menus.EDITDATA_TRANSCRIPT;
						}
						break;
					case "b": // Back to plotline editing menu
						currentMenu = menus.EDITPLOTLINE;
						break;
					default:
						break;
				}
				break;
			case EDITDATA_TEXTFIELD:
				switch (option.toLowerCase())
				{
					case "r": // Replace the current text
						cmd = Command.DATA_TEXTFIELD_REPLACE_TEXT;
						System.out.println("Enter the new text for this text field:");
						args.add(in.nextLine());
						break;
					case "b": // Back to event editing menu
						currentMenu = menus.EDITEVENT;
						break;
					default:
						break;
				}
				break;
			case EDITDATA_MEDIA:
				switch (option.toLowerCase())
				{
					case "c": // Change the current media file
						cmd = Command.DATA_MEDIA_CHANGE_FILE;
						System.out.println("Enter the path to the new media file:");
						args.add(in.nextLine());
						break;
					case "s": // Change start time
						cmd = Command.DATA_MEDIA_START_TIME;
						args.add("" + getIntFromInput("Enter the number of seconds into the file to begin playback:"));
						break;
					case "l": // Change playback length
						cmd = Command.DATA_MEDIA_PLAYBACK_LENGTH;
						args.add("" + getIntFromInput("Enter the length, in seconds, of playback:"));
						break;
					case "b": // Back to event editing menu
						currentMenu = menus.EDITEVENT;
						break;
					default:
						break;
				}
				break;
			case EDITDATA_MENU:
				evt = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex).getEvent(currentEventIndex);
				Data_Menu data_menu = (Data_Menu) evt.getElement(currentDataFieldIndex);
				switch (option.toLowerCase())
				{
					case "c": // Choose new option from the list
						cmd = Command.DATA_MENU_OPTION_SELECT;
						String[] options = data_menu.getElements();
						System.out.println("Available options:");
						for (int i = 0; i < options.length; i++)
						{
							System.out.println(i + ": " + options[i]);
						}
						args.add("" + getIntFromInput("Enter index of selected option:"));
						break;
					case "b": // Back to event editing menu
						currentMenu = menus.EDITEVENT;
						break;
					default:
						break;
				}
				break;
			case EDITDATA_TRANSCRIPT:
				evt = scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex).getEvent(currentEventIndex);
				Data_Transcript data_transcript = (Data_Transcript) evt.getElement(currentDataFieldIndex);
				switch (option.toLowerCase())
				{
					case "a": // Add new line of dialogue
						cmd = Command.DATA_TRANSCRIPT_NEW_LINE;
						System.out.println("Enter the identifier for the speaker of this line:");
						args.add(in.nextLine());
						System.out.println("Enter the text for this line:");
						args.add(in.nextLine());
						break;
					case "r": // Remove existing line of dialogue
						cmd = Command.DATA_TRANSCRIPT_REMOVE_LINE;
						args.add("" + getIntFromInput("Enter the index of the line to remove:"));
						break;
					case "e": // Edit existing line of dialogue
						cmd = Command.DATA_TRANSCRIPT_EDIT_LINE;
						args.add("" + getIntFromInput("Enter the index of the line to edit:"));
						System.out.println("Enter the new identifier for the speaker of this line:");
						args.add(in.nextLine());
						System.out.println("Enter the new text for this line:");
						args.add(in.nextLine());
						break;
					case "<": // Move a line earlier in the conversation
						cmd = Command.DATA_TRANSCRIPT_LINE_BACK;
						args.add("" + getIntFromInput("Enter the index of the line to move back:"));
						break;
					case ">": // Move a line later in the conversation
						cmd = Command.DATA_TRANSCRIPT_LINE_FORWARD;
						args.add("" + getIntFromInput("Enter the index of the line to move forward:"));
						break;
					case "b": // Back to event editing menu
						currentMenu = menus.EDITEVENT;
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		ctrl.setPlotNum(currentPlotIndex);
		ctrl.setInstNum(currentInstIndex);
		ctrl.setEvtNum(currentEventIndex);
		ctrl.setDataNum(currentDataFieldIndex);
		ctrl.readCommand(cmd, args);
	}

	/**
	 * When data changes, update our reference.
	 *
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof Script)
		{
			scr = (Script) o;
		}
	}

	/**
	 * Print relevant information from the current menu to the terminal.
	 */
	public void updateTerminal()
	{
		System.out.println();
		String output = "";
		if (scr == null)
		{
			System.out.println("<No script data>");
			return;
		}
		Plotline plt = null;
		Instance inst = null;
		Event evt = null;
		switch (currentMenu)
		{
			case MAINMENU:
				output = String.format("Current Script: %s\n", scr.scriptTitle);
				output += String.format("Script contains %d plotlines.\n", scr.countPlotlines());
				int length = 0;
				for (int i = 0; i < scr.countPlotlines(); ++i)
				{
					int plotlength = scr.getPlotLine(i).length() + scr.getPlotLine(i).startTime;
					if (plotlength > length)
					{
						length = plotlength;
					}
					output += String.format("%d: %s [%d] (%d seconds)\n", i, scr.getPlotLine(i).title,
							scr.getPlotLine(i).startTime, scr.getPlotLine(i).length());
				}
				output += String.format("Total run time: %d seconds", length);
				System.out.println(output);
				break;
			case SCRIPTPREFERENCES:
				String path = "<None>";
				if (scr.saveFile != null)
				{
					path = scr.saveFile.getPath();
				}
				output = String.format("Save location: %s\n", path);
				output += String.format("Script Name: %s\n", scr.scriptTitle);
				output += String.format("Script Description:\n\t%s", scr.description);
				System.out.println(output);
				break;
			case EDITPLOTLINE:
				plt = scr.getPlotLine(currentPlotIndex);
				output = String.format("Plotline Name: %s\n", plt.title);
				output += String.format("Plotline contains %d events across %d moments.\n", plt.totalEventCount(),
						plt.instanceCount());
				output += String.format("Start time: %d seconds into script\n", plt.startTime);
				output += String.format("Length: %d seconds", plt.length());
				output += String.format("(Ends %d seconds into script)", plt.length() + plt.startTime);
				System.out.println(output);
				break;
			case EDITEVENT:
				plt = scr.getPlotLine(currentPlotIndex);
				inst = plt.getInstance(currentInstIndex);
				evt = inst.getEvent(currentEventIndex);
				output = String.format("Plotline Name: %s\n", plt.title);
				output += String.format("Event Type: %s\n", evt.eventType);
				output += String.format("Event start time: %d seconds into plotline\n", currentInstIndex);
				output += String.format("(%d seconds into script)\n", currentInstIndex + plt.startTime);
				output += String.format("%d data fields in this event:\n", evt.fieldCount());
				for (int i = 0; i < evt.fieldCount(); i++)
				{
					output += String.format("%d: %s\n", i, evt.getFieldLabel(i));
				}
				System.out.println(output);
				break;
			case EDITDATA_TEXTFIELD:
				Data_Text dt = (Data_Text) scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
						.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
				output = String.format("Current Data Field: %s <Text Field>\n", dt.elementName());
				output += String.format("Current Text:\n%s\n", dt.getContent());
				System.out.println(output);
				break;
			case EDITDATA_MEDIA:
				Data_Media dm = (Data_Media) scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
						.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
				output = String.format("Current Data Field: %s <Media Element>\n", dm.elementName());
				output += String.format("Playback starts %d seconds into file and lasts for %d seconds.\n",
						dm.getStartTime(), dm.getPlayLength());
				output += String.format("Media source file: %s\n", dm.getFileName());
				System.out.println(output);
				break;
			case EDITDATA_MENU:
				Data_Menu de = (Data_Menu) scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
						.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
				output = String.format("Current Data Field: %s <Menu>\n", de.elementName());
				output += String.format("Current selection: %s\n", de.getSelected());
				System.out.println(output);
				break;
			case EDITDATA_TRANSCRIPT:
				Data_Transcript tr = (Data_Transcript) scr.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
						.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
				output = String.format("Current Data Field: %s <Transcript>\n", tr.elementName());
				output += String.format("Line\tActor\t\tLine\n");
				for (int i = 0; i < tr.length(); i++)
				{
					output += String.format("[%d]\t%s\t\t%s\n", i, tr.getLine(i).actor, tr.getLine(i).dialog);
				}
				System.out.println(output);
				break;
			default:
				System.out.println("Well, how did we get here?");
				break;
		}
	}

	/**
	 * Enumeration of the different menus in the app. A menu has a string of options
	 * to present on the terminal, and a matching array of acceptable inputs.
	 */
	public static enum menus
	{
		/**
		 * The starting menu. From here, the user can add a new plotline, remove or edit
		 * an existing plotline, save the script, change the script settings, or exit
		 * the program.
		 * 
		 * Options: "a", "r", "e", "s", "p", "x"
		 */
		MAINMENU(
				"[A] Add Plotline | [R] Remove Plotline | [E] Edit Plotline | \n"
						+ "[P] Script Preferences | [S] Save Script | [X] Exit",
				new String[] { "a", "r", "e", "s", "p", "x" }),
		/**
		 * The script preferences menu. From here, the user can rename the script,
		 * change the script description, change the save file, load a different script
		 * or start a new one, or go back to the main menu.
		 * 
		 * Options: "r", "d", "s", "l", "n", "b"
		 */
		SCRIPTPREFERENCES(
				"[R] Rename Script | [D] Change Script Description | [S] Save As | \n"
						+ "[L] Load File | [N] New File | [B] Go Back",
				new String[] { "r", "d", "s", "l", "n", "b" }),
		/**
		 * The plotline editing menu. From here, the user can rename the plotline,
		 * change its description, change its start time, check the events currently in
		 * the plotline, add a new event, remove or edit an existing event, move an
		 * event to a different time, or go back to the main menu.
		 * 
		 * Options: "n", "d", "t", "l", "a", "r", "e", "m", "b"
		 */
		EDITPLOTLINE(
				"[N] Rename Plotline | [D] Change Description | [T] Change Start Time | \n"
						+ "[L] Get Event List | [A] Add Event | [R] Remove Event | \n"
						+ "[E] Edit Event | [M] Move Event | [B] Go Back",
				new String[] { "n", "d", "t", "l", "a", "r", "e", "m", "b" }),
		/**
		 * The event editing menu. From here, the user can edit one of the event's data
		 * fields, or go back to the plotline editing menu.
		 * 
		 * Options: "e", "b"
		 */
		EDITEVENT("[E] Edit Data Field | [B] Go Back", new String[] { "e", "b" }),
		/**
		 * The text field editing menu. From here, the user can replace the current
		 * text, or go back to the event editing menu.
		 * 
		 * Options: "r", "b"
		 */
		EDITDATA_TEXTFIELD("[R] Replace Text | [B] Go Back", new String[] { "r", "b" }),
		/**
		 * The media editing menu. From here, the user can change the current media
		 * file, change its start time or its playback length, or go back to the event
		 * editing menu.
		 * 
		 * Options: "c", "s", "l", "b"
		 */
		EDITDATA_MEDIA(
				"[C] Change Media File | [S] Change Start Time | \n" + "[L] Change Playback Length | [B] Go Back",
				new String[] { "c", "s", "l", "b" }),
		/**
		 * The list editing menu. From here, the user can choose a new option on the
		 * list, or go back to the event editing menu.
		 * 
		 * Options: "c", "b"
		 */
		EDITDATA_MENU("[C] Change Selection | [B] Go Back", new String[] { "c", "b" }),
		/**
		 * The transcript editing menu. From here, the user can add a new line of
		 * dialogue, edit or remove an existing line of dialogue, move a line up or down
		 * in order, or go back to the event editing menu.
		 * 
		 * Options: "a", "r", "e", "<", ">", "b"
		 */
		EDITDATA_TRANSCRIPT(
				"[A] Add Line | [R] Remove Line | [E] Edit Line | \n"
						+ "[<] Move Line Earlier | [>] Move Line Later | [B] Go Back",
				new String[] { "a", "r", "e", "<", ">", "b" });
		public String MENU_TEXT;
		public String[] OPTIONS;

		private menus(String text, String[] options)
		{
			this.MENU_TEXT = text;
			this.OPTIONS = options;
		}
	}

	private int getIntFromInput(String prompt)
	{
		int number = 0;
		boolean found = false;
		while (!found)
		{
			System.out.println(prompt);
			String input = in.nextLine();
			try
			{
				number = Integer.parseInt(input);
				found = true;
			}
			catch (NumberFormatException e)
			{
				found = false;
				System.out.println("Invalid input.");
			}
		}
		return number;
	}
}
