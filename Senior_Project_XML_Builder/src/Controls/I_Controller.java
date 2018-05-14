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
	void readCommand(Command cmd, List<String> args);
	
	/**
	 * Change the reference to the current plotline.
	 * @param newNum the index of the new plotline
	 */
	void setPlotNum(int newNum);
	
	/**
	 * Change the reference to the current instance.
	 * @param newNum the time of the new instance
	 */
	void setInstNum(int newNum);
	
	/**
	 * Change the reference to the current event.
	 * @param newNum the index of the new event
	 */
	void setEvtNum(int newNum);
	
	/**
	 * Change the reference to the current data field.
	 * @param newNum the index of the new data field
	 */
	void setDataNum(int newNum);
}
