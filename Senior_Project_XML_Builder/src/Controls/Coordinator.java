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
import java.util.List;
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
    public void readCommand(Command cmd, List<String> args)
    {
        int len = args.size();
        switch (cmd)
        {
            case NOTHING:
                break;

            case ADD_PLOTLINE:
                String title;
                int startTime;

                if (len == 2)
                {
                    title = args.get(0);
                    try
                    {
                        startTime = Integer.parseInt(args.get(1));
                        sc.addPlotline(title, startTime);
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;

            case REMOVE_PLOTLINE:
                int index;
                if (len == 1)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        sc.removePlotline(index);
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;

            case PRINT:
                sc.toXML();
                break;

            case QUIT:
                System.exit(0);
            case LOAD_FILE:
                break;
            case NEW_FILE:
                break;
            case CHANGE_SCRIPT_TITLE:
                break;
            case CHANGE_PLOTLINE_TITLE:
                break;
            case SAVE_AS:
                break;
            case ADD_EVENT:
                break;
            case RELOCATE_EVENT:
                break;
            case REMOVE_EVENT:
                break;
            case CHANGE_SCRIPT_DESCRIPTION:
                break;
            case CHANGE_PLOTLINE_DESCRIPTION:
                break;
            default:
                break;
        }

    }

}
