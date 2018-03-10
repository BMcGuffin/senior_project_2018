/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

import Models.Script;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan McGuffin
 * @version Mar 10, 2018
 */
public class Coordinator implements I_Controller
{

    Script sc;

    public Coordinator(Script script)
    {
        sc = script;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        Command cmd = Command.valueOf(e.getActionCommand());

        switch (cmd)
        {
            case NOTHING:
                break;
            case PING:
                System.out.println("Pong!");
                break;
            case QUIT:
                System.exit(0);
        }

    }

    
}
