package Models;

import java.io.File;
import java.nio.file.Path;
import junit.framework.TestCase;
import org.mockito.Mockito;

/**
 *
 * @author Bryan McGuffin
 */
public class Data_MediaTest extends TestCase
{

    public Data_MediaTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of setMediaFile method, of class Data_Media.
     */
    public void testSetMediaFile()
    {
        System.out.println("setMediaFile");
        Data_Media instance = new Data_Media("File tester");
        fail("The test case is a prototype.");
        
    }

    /**
     * Test of setPlayLength method, of class Data_Media.
     */
    public void testSetPlayLength()
    {
        System.out.println("setPlayLength");
        int newLength = 0;
        Data_Media instance = null;
        boolean expResult = false;
        boolean result = instance.setPlayLength(newLength);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStartTime method, of class Data_Media.
     */
    public void testSetStartTime()
    {
        System.out.println("setStartTime");
        int newStart = 0;
        Data_Media instance = null;
        boolean expResult = false;
        boolean result = instance.setStartTime(newStart);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayLength method, of class Data_Media.
     */
    public void testGetPlayLength()
    {
        System.out.println("getPlayLength");
        Data_Media instance = null;
        int expResult = 0;
        int result = instance.getPlayLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartTime method, of class Data_Media.
     */
    public void testGetStartTime()
    {
        System.out.println("getStartTime");
        Data_Media instance = null;
        int expResult = 0;
        int result = instance.getStartTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFileName method, of class Data_Media.
     */
    public void testGetFileName()
    {
        System.out.println("getFileName");
        Data_Media instance = null;
        String expResult = "";
        String result = instance.getFileName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of duplicate method, of class Data_Media.
     */
    public void testDuplicate()
    {
        System.out.println("duplicate");
        Data_Media instance = null;
        Buildable expResult = null;
        Buildable result = instance.duplicate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
