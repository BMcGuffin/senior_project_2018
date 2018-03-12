package Controls;

import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class CoordinatorTest extends TestCase {
    
    public CoordinatorTest(String testName) {
        super(testName);
    }

    /**
     * Test of readCommand method, of class Coordinator.
     */
    public void testReadCommand()
    {
        System.out.println("readCommand");
        Command cmd = null;
        List<String> args = null;
        Coordinator instance = null;
        instance.readCommand(cmd, args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
