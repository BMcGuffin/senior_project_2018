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

		ArrayList<String> arguments = new ArrayList(Arrays.asList(args));
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

			// /* Disable the following Nimbus code for a default netbeans look. */
			// /* Set the Nimbus look and feel */
			// //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code
			// (optional) ">
			// /* If Nimbus (introduced in Java SE 6) is not available, stay with the
			// default look and feel.
			// * For details see
			// http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
			// */
			// try
			// {
			// for (javax.swing.UIManager.LookAndFeelInfo info :
			// javax.swing.UIManager.getInstalledLookAndFeels())
			// {
			// if ("Nimbus".equals(info.getName()))
			// {
			// javax.swing.UIManager.setLookAndFeel(info.getClassName());
			// break;
			// }
			// }
			// }
			// catch (ClassNotFoundException ex)
			// {
			// java.util.logging.Logger.getLogger(ScriptOverview.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
			// }
			// catch (InstantiationException ex)
			// {
			// java.util.logging.Logger.getLogger(ScriptOverview.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
			// }
			// catch (IllegalAccessException ex)
			// {
			// java.util.logging.Logger.getLogger(ScriptOverview.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
			// }
			// catch (javax.swing.UnsupportedLookAndFeelException ex)
			// {
			// java.util.logging.Logger.getLogger(ScriptOverview.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
			// }
			// //</editor-fold>
			//
			ScriptOverview mainWindow = new ScriptOverview(ctrl);
			script.addObserver(mainWindow);
			java.awt.EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					mainWindow.setVisible(true);
				}
			});
		}
	}
}
