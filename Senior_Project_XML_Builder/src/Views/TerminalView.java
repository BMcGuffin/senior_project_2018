/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Views;

import Controls.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import Models.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A terminal interface for the program. Reads from, and writes to, the
 * terminal.
 *
 * @author Bryan McGuffin
 * @version Mar 5, 2018
 */
public class TerminalView implements I_View
{

    String demarcation = "================================================================================";

    Scanner in;

    I_Controller ctrl;

    boolean quitThis;

    menus currentMenu;

    int currentPlotIndex;

    int currentEventIndex;

    Script scr;

    public TerminalView(I_Controller c)
    {
        in = new Scanner(System.in);
        quitThis = false;
        currentMenu = menus.MAINMENU;
        ctrl = c;
        currentPlotIndex = 0;
    }

    @Override
    public void setVisible(boolean visible)
    {
        if (visible)
        {
            this.getNextInput();
        }
    }

    public void getNextInput()
    {
        while (true)
        {
            updateTerminal();
            System.out.println();
            System.out.println(demarcation);
            System.out.println(currentMenu.MENU_TEXT);
            System.out.println(demarcation);

            String input = in.nextLine();
            for (String option : currentMenu.OPTIONS)
            {
                if (input.equalsIgnoreCase(option))
                {
                    processCommand(option);
                }
            }

        }
    }

    public void processCommand(String option)
    {
        Command cmd = Command.NOTHING;
        List<String> args = new ArrayList<>();

        switch (currentMenu)
        {
            case MAINMENU:
                switch (option.toLowerCase())
                {
                    case "x"://exit the program
                        cmd = Command.QUIT;
                        break;
                    case "a"://add a new plotline to the script
                        cmd = Command.ADD_PLOTLINE;
                        System.out.println("Plotline name:");
                        args.add(in.nextLine());
                        System.out.println("Start time:");
                        args.add("" + in.nextLine());
                        break;
                    case "r"://remove a plotline from the script
                        cmd = Command.REMOVE_PLOTLINE;
                        System.out.println("Index to remove:");
                        args.add("" + in.nextLine());
                        break;
                    case "s"://print out the current script to a file
                        if (scr.saveFile == null)
                        {
                            cmd = Command.SAVE_AS;
                            System.out.println("Enter the name of the save file:");
                            args.add(in.nextLine());
                        }
                        else
                        {
                            cmd = Command.SAVE;
                        }
                        break;
                    case "p"://go to script settings menu
                        cmd = Command.NOTHING;
                        currentMenu = menus.SCRIPTSETTINGS;
                        break;
                    case "e"://go to edit plotline menu
                        cmd = Command.NOTHING;
                        System.out.println("Enter the index of the plotline you'd like to edit.");
                        currentPlotIndex = in.nextInt();
                        in.nextLine();
                        currentMenu = menus.EDITPLOTLINE;
                        break;
                    default:
                        break;
                }
                break;
            case SCRIPTSETTINGS:
                switch (option.toLowerCase())
                {
                    case "r"://rename script
                        cmd = Command.CHANGE_SCRIPT_TITLE;
                        System.out.println("Enter new script title:");
                        args.add(in.nextLine());
                        break;
                    case "d"://change script description
                        cmd = Command.CHANGE_SCRIPT_DESCRIPTION;
                        System.out.println("Enter new description:");
                        args.add(in.nextLine());
                        break;
                    case "s"://save as
                        cmd = Command.SAVE_AS;
                        System.out.println("Enter the name of the save file:");
                        args.add(in.nextLine());
                        break;
                    case "l"://load script
                        cmd = Command.LOAD_FILE;
                        System.out.println("Enter the path to the file:");
                        args.add(in.nextLine());
                        break;
                    case "n"://new script
                        cmd = Command.NEW_FILE;
                        System.out.println("Do you want to save the current file? y/n");
                        if (in.nextLine().equalsIgnoreCase("n"))
                        {
                            args.add("0");
                        }
                        else
                        {
                            args.add("1");
                        }
                        break;
                    case "b"://back to main menu
                        currentMenu = menus.MAINMENU;
                        break;
                    default:
                        break;
                }
                break;
            case EDITPLOTLINE:
                switch (option.toLowerCase())
                {
                    case "n"://Rename plotline
                        cmd = Command.CHANGE_PLOTLINE_TITLE;
                        System.out.println("Enter new plotline title:");
                        args.add("" + currentPlotIndex);
                        args.add(in.nextLine());
                        break;
                    case "d"://Change plotline description
                        cmd = Command.CHANGE_PLOTLINE_DESCRIPTION;
                        System.out.println("Enter new plotline description:");
                        args.add("" + currentPlotIndex);
                        args.add(in.nextLine());
                        break;
                    case "t"://Change start time
                        cmd = Command.CHANGE_PLOTLINE_START;
                        System.out.println("Enter new start time in seconds:");
                        args.add("" + currentPlotIndex);
                        args.add(in.nextLine());
                        break;
                    case "l"://Get list of events in this plotline
                        cmd = Command.NOTHING;
                        for (int i = 0; i < scr.getPlotLine(currentPlotIndex).instanceCount(); i++)
                        {
                            Instance inst = scr.getPlotLine(currentPlotIndex).getInstance(i);
                            for (Event e : inst.events)
                            {
                                String str = String.format("(%d) %s", inst.time, e.toString());
                                System.out.println(str);
                            }
                        }
                        break;
                    case "a"://Add new event
                        cmd = Command.ADD_EVENT;
                        args.add("" + currentPlotIndex);
                        System.out.println("Enter time (in seconds) to add new event:");
                        args.add(in.nextLine());
                        System.out.println("Available event types:");
                        //TODO add event builder class
                        System.out.println("Enter index of event type:");
                        args.add(in.nextLine());
                        break;
                    case "r"://Remove event
                        cmd = Command.REMOVE_EVENT;
                        args.add("" + currentPlotIndex);
                        System.out.println("Enter time (in seconds) of event to be removed:");
                        args.add(in.nextLine());
                        //TODO: print out all events in this instance
                        System.out.println("Enter index of event to remove:");
                        args.add(in.nextLine());
                        break;
                    case "e"://Go to edit event menu
                        break;
                    case "m"://Move event
                        cmd = Command.RELOCATE_EVENT;
                        args.add("" + currentPlotIndex);
                        System.out.println("Enter time (in seconds) of event to be moved:");
                        args.add(in.nextLine());
                        //TODO: print out all events in this instance
                        System.out.println("Enter index of event to move:");
                        args.add(in.nextLine());
                        System.out.println("Enter new time (in seconds) for event:");
                        args.add(in.nextLine());
                        break;
                    case "b"://Back to main menu
                        currentMenu = menus.MAINMENU;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        ctrl.readCommand(cmd, args);

    }

    /**
     * When data changes, update our reference.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Script)
        {
            scr = (Script) o;
        }
    }

    /**
     * Print relevant information from the current menu to the terminal.
     */
    public void updateTerminal()
    {
        System.out.println();
        String output = "";
        if (scr == null)
        {
            System.out.println("<No script data>");
            return;
        }

        switch (currentMenu)
        {
            case MAINMENU:
                output = String.format("Current Script: %s\n", scr.scriptTitle);
                output += String.format("Script contains %d plotlines.\n", scr.countPlotlines());
                int length = 0;
                for (int i = 0; i < scr.countPlotlines(); ++i)
                {
                    int plotlength = scr.getPlotLine(i).length() + scr.getPlotLine(i).startTime;
                    if (plotlength > length)
                    {
                        length = plotlength;
                    }
                    output += String.format("%d: %s [%d] (%d seconds)\n",
                            i, scr.getPlotLine(i).title, scr.getPlotLine(i).startTime,
                            scr.getPlotLine(i).length());
                }
                output += String.format("Total run time: %d seconds", length);
                System.out.println(output);
                break;
            case SCRIPTSETTINGS:
                String path = "<None>";
                if (scr.saveFile != null)
                {
                    path = scr.saveFile.getPath();
                }
                output = String.format("Save location: %s\n", path);
                output += String.format("Script Name: %s\n", scr.scriptTitle);
                output += String.format("Script Description:\n\t%s", scr.description);
                System.out.println(output);
                break;
            case EDITPLOTLINE:
                Plotline plt = scr.getPlotLine(currentPlotIndex);
                output = String.format("Plotline Name: %s\n", plt.title);
                output += String.format("Plotline contains %d events across %d moments.\n",
                        plt.totalEventCount(), plt.instanceCount());
                output += String.format("Start time: %d seconds into script\n", plt.startTime);
                output += String.format("Length: %d seconds", plt.length());
                output += String.format("(Ends %d seconds into script)", plt.length() + plt.startTime);
                System.out.println(output);
                break;

            default:
                System.out.println("Well, how did we get here?");
                break;
        }
    }

    public static enum menus
    {

        MAINMENU("[A] Add Plotline | [R] Remove Plotline | [E] Edit Plotline | \n"
                + "[P] Script Settings | [S] Save Script | [X] Exit", new String[]
                {
                    "a", "r", "e", "s", "p", "x"
                }),
        SCRIPTSETTINGS("[R] Rename Script | [D] Change Script Description | [S] Save As | \n"
                + "[L] Load File | [N] New File | [B] Go Back", new String[]
                {
                    "r", "d", "s", "l", "n", "b"
                }),
        EDITPLOTLINE("[N] Rename Plotline | [D] Change Description | [T] Change Start Time | \n"
                + "[L] Get Event List | [A] Add Event | [R] Remove Event | \n"
                + "[E] Edit Event | [M] Move Event | [B] Go Back", new String[]
                {
                    "n", "d", "t", "l", "a", "r", "e", "m", "b"
                });

        public String MENU_TEXT;
        public String[] OPTIONS;

        private menus(String text, String[] options)
        {
            this.MENU_TEXT = text;
            this.OPTIONS = options;
        }
    }

}
