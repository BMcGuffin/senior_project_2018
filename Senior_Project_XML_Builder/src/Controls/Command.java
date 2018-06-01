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
	 * Exit the program. Args should contain nothing.
	 */
	QUIT,
	/************************************************************************************/
	/* Script Preferences */
	/************************************************************************************/
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
	 * Change the title of the script. Args should contain the new title.
	 */
	CHANGE_SCRIPT_TITLE,
	/**
	 * Change the description for the script. Args should contain the new
	 * description.
	 */
	CHANGE_SCRIPT_DESCRIPTION,
	/************************************************************************************/
	/* Edit Script */
	/************************************************************************************/
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
	 * Save the current file. Args should contain nothing.
	 */
	SAVE,
	/************************************************************************************/
	/* Edit Plotline */
	/************************************************************************************/
	/**
	 * Change the title of the current plotline. Args should contain the plotline
	 * index, and the new title.
	 */
	CHANGE_PLOTLINE_TITLE,
	/**
	 * Add a new event to this plotline. Args should include the plotline index, and
	 * the time to add the event at, and the type of event.
	 */
	ADD_EVENT,
	/**
	 * Move an event from one part of the plotline to another. Args should include
	 * the plotline index, and the current time, and the index of the event, and the
	 * new time for the event.
	 */
	RELOCATE_EVENT,
	/**
	 * Remove an event from the plotline. Args should include the plotline index,
	 * and the current time, and the index of the event.
	 */
	REMOVE_EVENT,
	/**
	 * Change the description of a plotline. Args should contain the plotline index,
	 * and the new description.
	 */
	CHANGE_PLOTLINE_DESCRIPTION,
	/**
	 * Change the start time of a plotline. Args should contain the plotline index,
	 * and the number of seconds into the script that the plotline should start.
	 */
	CHANGE_PLOTLINE_START,
	/************************************************************************************/
	/* Edit Event */
	/************************************************************************************/
	/**
	 * Replace the text in a text field in an event. Args should contain the
	 * plotline index, and the time of the event, and the index of the event, and
	 * the index of the data field, and the new text.
	 */
	DATA_TEXTFIELD_REPLACE_TEXT,
	/**
	 * Change the file attached to this media field. Args should contain the
	 * plotline index, and the time of the event, and the index of the event, and
	 * the index of the data field, and the location of the new file.
	 */
	DATA_MEDIA_CHANGE_FILE,
	/**
	 * Change the time, in seconds, into the media object to begin playback. Args
	 * should contain the plotline index, and the time of the event, and the index
	 * of the event, and the index of the data field, and the number of seconds into
	 * the file to begin playback.
	 */
	DATA_MEDIA_START_TIME,
	/**
	 * Change the length, in seconds, of playback time for this media field. Args
	 * should contain the plotline index, and the time of the event, and the index
	 * of the event, and the index of the data field, and the number of seconds for
	 * playback to occur.
	 */
	DATA_MEDIA_PLAYBACK_LENGTH,
	/**
	 * Select a new option from the menu. Args should contain the plotline index,
	 * and the time of the event, and the index of the event, and the index of the
	 * data field, and the index of the new selection.
	 */
	DATA_MENU_OPTION_SELECT,
	/**
	 * Append a new line of dialogue to the end of this transcript field. Args
	 * should contain the plotline index, and the time of the event, and the index
	 * of the event, and the index of the data field, and then a string identifying
	 * the actor, and then the dialogue.
	 */
	DATA_TRANSCRIPT_NEW_LINE,
	/**
	 * Remove a line of dialogue from this transcript field. Args should contain the
	 * plotline index, and the time of the event, and the index of the event, and
	 * the index of the data field, and the index of the line of dialogue to be
	 * removed.
	 */
	DATA_TRANSCRIPT_REMOVE_LINE,
	/**
	 * Edit a line of dialogue in this transcript field. Args should contain the
	 * plotline index, and the time of the event, and the index of the event, and
	 * the index of the data field, and the index of the line to edit, and the new
	 * name for the actor, and the new text for the dialogue.
	 */
	DATA_TRANSCRIPT_EDIT_LINE,
	/**
	 * Move a line of dialogue one index backwards in the transcript. Args should
	 * contain the plotline index, and the time of the event, and the index of the
	 * event, and the index of the data field, and the index of the line of dialogue
	 * to move.
	 */
	DATA_TRANSCRIPT_LINE_BACK,
	/**
	 * Move a line of dialogue one index forwards in the transcript. Args should
	 * contain the index of the plotline index, and the time of the event, and the
	 * index of the event, and the index of the data field, and the line of dialogue
	 * to move.
	 */
	DATA_TRANSCRIPT_LINE_FORWARD;
}
