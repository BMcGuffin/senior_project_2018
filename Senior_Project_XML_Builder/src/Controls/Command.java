/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

/**
 * All valid commands for the controller. Basically, if it can be done in the
 * program, it's represented here.
 *
 * @author Bryan McGuffin
 */
public enum Command
{

	/**
	 * No-op. Take no action. Used as a default value just in case some invalid data
	 * is entered.
	 */
	NOTHING,
	/**
	 * Add a new plotline to the script. Args should contain a title and a start
	 * time for the new plotline.
	 */
	ADD_PLOTLINE,
	/**
	 * Remove a plotline from the script. Args should contain the index of the
	 * plotline to be removed.
	 */
	REMOVE_PLOTLINE,
	/**
	 * Deprecated. Print the script to a file using the toXML method. Args should be
	 * empty.
	 */
	// PRINT,
	/**
	 * Load a new script from a file. Args should contain the path to the new file.
	 */
	LOAD_FILE,
	/**
	 * Start a new file. Args should contain a 1 if the user wants to save the
	 * current file, 0 if they don't.
	 */
	NEW_FILE,
	/**
	 * Change the name of the save file. Args should contain the name of the new
	 * file.
	 */
	SAVE_AS,
	/**
	 * Save the current file. Args should contain nothing.
	 */
	SAVE,
	/**
	 * Change the title of the script. Args should contain the new title.
	 */
	CHANGE_SCRIPT_TITLE,
	/**
	 * Change the title of a plotline. Args should contain the index of the plotline
	 * to be renamed, and the new title.
	 */
	CHANGE_PLOTLINE_TITLE,
	/**
	 * Add a new event to this plotline. Args should include the index of the
	 * plotline, the time to add the event at, and the type of event.
	 */
	ADD_EVENT,
	/**
	 * Move an event from one part of the plotline to another. Args should include
	 * the index of the plotline to be changed, the current time of the event, the
	 * index of the event at that time, and the new time for the event.
	 */
	RELOCATE_EVENT,
	/**
	 * Remove an event from the plotline. Args should include the index of the
	 * plotline to be changed, the time of the event, and the index of the event at
	 * that time.
	 */
	REMOVE_EVENT,
	/**
	 * Change the description for the script. Args should contain the new
	 * description.
	 */
	CHANGE_SCRIPT_DESCRIPTION,
	/**
	 * Change the description of a plotline. Args should contain the index of the
	 * plotline to be changed, and the new description.
	 */
	CHANGE_PLOTLINE_DESCRIPTION,
	/**
	 * Change the start time of a plotline. Args should contain the index of the
	 * plotline to be changed, and the number of seconds into the script that the
	 * plotline should start.
	 */
	CHANGE_PLOTLINE_START,
	/**
	 * Exit the program. Args should contain nothing.
	 */
	QUIT;

}
