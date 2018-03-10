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

/**
 * A terminal interface for the program. Reads from, and writes to, the
 * terminal.
 *
 * @author Bryan McGuffin
 * @version Mar 5, 2018
 */
public class TerminalView implements I_View
{

    Scanner in;

    I_Controller ctrl;

    boolean quitThis;

    menus currentMenu;

    public TerminalView(I_Controller c)
    {
        in = new Scanner(System.in);
        quitThis = false;
        currentMenu = menus.MAINMENU;
        ctrl = c;

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
            System.out.println(currentMenu.TEXT);
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

        switch (currentMenu)
        {
            case MAINMENU:
                if (option.equalsIgnoreCase("p"))
                {
                    cmd = cmd.PING;
                }
                else if (option.equalsIgnoreCase("q"))
                {
                    cmd = cmd.QUIT;
                }
                break;
        }

        ctrl.actionPerformed(new ActionEvent(this, 0, cmd.name()));

    }

    /**
     * When data changes, print here.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("Update received.");
        System.out.println("o = " + o);
        System.out.println("arg = " + arg);

        if (o instanceof Script)
        {
            Script scr = (Script) o;
            String output = String.format("Current Script: %s\n", scr.scriptTitle);
            output += String.format("Script contains %d plotlines.\n", scr.countPlotlines());

            System.out.println(output);
        }
    }

    public static enum menus
    {

        MAINMENU("Enter a command: [P]ing terminal | [Q]uit", new String[]
        {
            "p", "q"
        });
        public String TEXT;
        public String[] OPTIONS;

        private menus(String text, String[] options)
        {
            this.TEXT = text;
            this.OPTIONS = options;
        }
    }

}
