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
     * No-op. Take no action. Used as a default value just in case some invalid
     * data is entered.
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
     * Print the script to a file using the toXML method.
     */
    PRINT,
    LOAD_FILE,
    NEW_FILE,
    SAVE_AS,
    CHANGE_SCRIPT_TITLE,
    CHANGE_PLOTLINE_TITLE,
    ADD_EVENT,
    RELOCATE_EVENT,
    REMOVE_EVENT,
    CHANGE_SCRIPT_DESCRIPTION,
    CHANGE_PLOTLINE_DESCRIPTION,
    CHANGE_PLOTLINE_START,
    /**
     * Exit the program.
     */
    QUIT;

}
