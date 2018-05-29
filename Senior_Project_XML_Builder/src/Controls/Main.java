/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

import Models.*;
import Views.*;
import Views.SwingView.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Bryan McGuffin
 * @version Feb 23, 2018
 */
public class Main
{
	static boolean terminal;

	/**
	 *
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
		terminal = arguments.contains("-term");
		Script script = new Script();
		I_Controller ctrl = new Coordinator(script);
		if (terminal)
		{
			TerminalView term = new TerminalView(ctrl);
			script.addObserver(term);
			script.spark();
			term.setVisible(true);
		}
		else
		{
			SwingView window = new SwingView(ctrl);
			script.addObserver(window);
			TerminalView term = new TerminalView(ctrl);
			script.addObserver(term);
			script.spark();
			
			java.awt.EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					window.setVisible(true);
				}
			});
			term.setVisible(true);
		}
	}
}
