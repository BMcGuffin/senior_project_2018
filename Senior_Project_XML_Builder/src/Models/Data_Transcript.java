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
class Data_Transcript implements Buildable
{

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
    public Data_Transcript()
    {
        actors = new ArrayList<>();
        lines = new ArrayList<>();
    }
    
    /**
     * Add a single line of dialog to this transcript.
     * 
     * @param actor
     * @param dial 
     */
    public void addLine(String actor, String dial)
    {
        lines.add(new Line(actor, dial));
    }
    
    /**
     * Access a particular line from this transcript.
     * 
     * @param index the index of the line in question.
     * @return a line from the transcript.
     */
    public Line getLine(int index)
    {
        return lines.get(index);
    }
    
    /**
     * Delete a line of dialog from this transcript.
     * 
     * @param index the index of the line to be removed.
     * @return true if the line was successfully removed.
     */
    public boolean removeLine(int index)
    {
        return lines.remove(index) != null;
    }
    
    /**
     * Move a line up one position in the transcript.
     * 
     * @param index the index of the line to be moved.
     */
    public void moveLineUp(int index)
    {
        if(index > 0 && index < lines.size())
        {
            Line temp = lines.get(index - 1);
            lines.set(index - 1, lines.get(index));
            lines.set(index, temp);
        }
    }
    
    /**
     * Move a line down one position in the transcript.
     * 
     * @param index the index of the line to be moved.
     */
    public void moveLineDown (int index)
    {
        if(index >= 0 && index < lines.size() - 1)
        {
            Line temp = lines.get(index + 1);
            lines.set(index + 1, lines.get(index));
            lines.set(index, temp);
        }
    }

}
