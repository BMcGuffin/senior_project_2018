/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

/**
 * A single line of dialogue. Spoken by one actor.
 *
 * @author Bryan McGuffin
 * @version Feb 26, 2018
 */
class Line
{

    /**
     * The person or entity speaking the line.
     */
    public String actor;

    /**
     * The dialog that is spoken during this line.
     */
    public String dialog;

    /**
     * Constructor.
     *
     * @param a the actor for this line
     * @param d the dialog for this line
     */
    public Line(String a, String d)
    {
        actor = a;
        dialog = d;
    }
}
