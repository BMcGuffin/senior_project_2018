/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */

package Views;

import java.util.Observer;

/**
 *
 * @author Bryan McGuffin
 */
public interface I_View extends Observer
{
    /**
     * Turn on this view.
     * 
     * @param visible whether or not to display this view
     */
    void setVisible(boolean visible);
}
