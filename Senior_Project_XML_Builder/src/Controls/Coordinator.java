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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        int index;
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

//            case PRINT:
//                sc.toXML();
//                break;
            case QUIT:
                System.exit(0);
            case LOAD_FILE:
                if (len == 1)
                {
                    try
                    {
                        File newFile = new File(args.get(0));
                        sc.loadFromFile(newFile);
                        sc.saveFile = newFile;
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case NEW_FILE:
                if (len == 1)
                {
                    try
                    {
                        int decision = Integer.parseInt(args.get(0));
                        if (decision == 1)
                        {
                            sc.saveToFile();
                        }
                        sc.newFile();
                    }
                    catch (Exception e)
                    {
                    }
                }
                
                break;
            case CHANGE_SCRIPT_TITLE:
                if (len == 1)
                {
                    sc.scriptTitle = args.get(0);
                }
                break;
            case CHANGE_PLOTLINE_TITLE:
                if (len == 2)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        sc.getPlotLine(index).title = args.get(1);
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case SAVE_AS:
                if (len == 1)
                {
                    try
                    {
                        sc.saveFile = new File(args.get(0));
                        sc.saveFile.createNewFile();
                        sc.saveToFile();
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case SAVE:
                sc.saveToFile();
                break;
            case ADD_EVENT:
                //TODO Add case for ADD_EVENT
                break;
            case RELOCATE_EVENT:
                //TODO Add case for RELOCATE_EVENT
                break;
            case REMOVE_EVENT:
                //TODO Add case for REMOVE_EVENT
                break;
            case CHANGE_SCRIPT_DESCRIPTION:
                if (len == 1)
                {
                    sc.description = args.get(0);
                }
                break;
            case CHANGE_PLOTLINE_DESCRIPTION:
                if (len == 2)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        sc.getPlotLine(index).description = args.get(1);
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case CHANGE_PLOTLINE_START:
                if (len == 2)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        int newStart = Integer.parseInt(args.get(1));
                        sc.getPlotLine(index).startTime = newStart;
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            default:
                break;
        }

    }

}
