package Views;

import java.util.Observable;
import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class TerminalViewTest extends TestCase {
    
    public TerminalViewTest(String testName) {
        super(testName);
    }

    /**
     * Test of setVisible method, of class TerminalView.
     */
    public void testSetVisible()
    {
        System.out.println("setVisible");
        boolean visible = false;
        TerminalView instance = null;
        instance.setVisible(visible);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextInput method, of class TerminalView.
     */
    public void testGetNextInput()
    {
        System.out.println("getNextInput");
        TerminalView instance = null;
        instance.getNextInput();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processCommand method, of class TerminalView.
     */
    public void testProcessCommand()
    {
        System.out.println("processCommand");
        String option = "";
        TerminalView instance = null;
        instance.processCommand(option);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class TerminalView.
     */
    public void testUpdate()
    {
        System.out.println("update");
        Observable o = null;
        Object arg = null;
        TerminalView instance = null;
        instance.update(o, arg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateTerminal method, of class TerminalView.
     */
    public void testUpdateTerminal()
    {
        System.out.println("updateTerminal");
        TerminalView instance = null;
        instance.updateTerminal();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
