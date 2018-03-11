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
                        args.add("" + in.nextInt());
                        in.nextLine();
                        break;
                    case "r"://remove a plotline from the script
                        cmd = Command.REMOVE_PLOTLINE;
                        System.out.println("Index to remove:");
                        args.add("" + in.nextInt());
                        in.nextLine();
                        break;
                    case "p"://print out the current script to a file
                        cmd = Command.PRINT;
                        System.out.println("Enter name of output file (or leave blank to overwrite current output file):");
                        args.add(in.nextLine());
                        break;
                    case "s"://go to script settings menu
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
                        break;
                    case "d"://change script description
                        break;
                    case "s"://save as
                        break;
                    case "l"://load script
                        break;
                    case "n"://new script
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
                        break;
                    case "d"://Change plotline description
                        break;
                    case "t"://Change start time
                        break;
                    case "l"://Get list of events in this plotline
                        break;
                    case "a"://Add new event
                        break;
                    case "r"://Remove event
                        break;
                    case "e"://Go to edit event menu
                        break;
                    case "m"://Move event
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
     * When data changes, update our reference, and then print as appropriate.
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
                + "[S] Script Settings | [P] Print Script | [X] Save and exit", new String[]
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
