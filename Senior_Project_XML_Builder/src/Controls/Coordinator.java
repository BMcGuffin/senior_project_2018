/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

import Models.*;
import java.io.File;
import java.util.List;

/**
 * The Controller portion of the MCV model. Accepts inputs from the view and subsequently manipulates the model.
 *
 * @author Bryan McGuffin
 * @version Mar 10, 2018
 */
public class Coordinator implements I_Controller
{

    private Script sc;
    private EventBuilder builder;

    public Coordinator(Script script)
    {
        sc = script;
        builder = EventBuilder.getBuilder(sc);
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
                        String filename = args.get(0);
                        if(!filename.endsWith(".xml"))
                        {
                            filename += ".xml";
                        }
                        sc.saveFile = new File(filename);
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
                if (len == 3)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        int time = Integer.parseInt(args.get(1));
                        Event evt = builder.generateEvent(args.get(2));
                        if (sc.getPlotLine(index).getInstance(time) == null)
                        {
                            sc.getPlotLine(index).addInstance(time);
                        }
                        sc.getPlotLine(index).getInstance(time).addEvent(evt);
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case RELOCATE_EVENT:
                if (len == 4)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        int from = Integer.parseInt(args.get(1));
                        int pos = Integer.parseInt(args.get(2));
                        int to = Integer.parseInt(args.get(3));
                        Instance inst = sc.getPlotLine(index).getInstance(from);
                        Event evt = inst.events.get(pos);
                        inst.removeEvent(evt);
                        if (sc.getPlotLine(index).getInstance(to) == null)
                        {
                            sc.getPlotLine(index).addInstance(to);
                        }
                        sc.getPlotLine(index).getInstance(to).addEvent(evt);
                        if (inst.events.isEmpty())
                        {
                            sc.getPlotLine(index).removeInstance(from);
                        }
                    }
                    catch (Exception e)
                    {
                    }
                }
                break;
            case REMOVE_EVENT:
                if (len == 3)
                {
                    try
                    {
                        index = Integer.parseInt(args.get(0));
                        int time = Integer.parseInt(args.get(1));
                        int evt = Integer.parseInt(args.get(2));
                        Instance inst = sc.getPlotLine(index).getInstance(time);
                        inst.removeEvent(inst.events.get(evt));
                    }
                    catch (Exception e)
                    {
                    }
                }
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
