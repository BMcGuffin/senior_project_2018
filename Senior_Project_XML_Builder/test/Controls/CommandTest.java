package Controls;

import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class CommandTest extends TestCase {
    
    public CommandTest(String testName) {
        super(testName);
    }

    /**
     * Test of values method, of class Command.
     */
    public void testValues()
    {
        System.out.println("values");
        Command[] expResult = null;
        Command[] result = Command.values();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class Command.
     */
    public void testValueOf()
    {
        System.out.println("valueOf");
        String name = "";
        Command expResult = null;
        Command result = Command.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
