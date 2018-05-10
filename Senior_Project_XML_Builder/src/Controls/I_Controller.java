/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

import java.util.List;

/**
 *
 * @author Bryan McGuffin
 */
public interface I_Controller
{

	/**
	 * Read in a command and any relevant data.
	 *
	 * @param cmd
	 *            the command object.
	 * @param args
	 *            the data values going along with this command.
	 */
	public void readCommand(Command cmd, List<String> args);
}
