package Models;

import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class Data_TranscriptTest extends TestCase
{

    public Data_TranscriptTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of addLine method, of class Data_Transcript.
     */
    public void testAddLine()
    {
        System.out.println("addLine");
        Data_Transcript instance = new Data_Transcript("text transcript module");
        assertEquals(0, instance.length());
        instance.addLine("act1", "hello 1");
        assertEquals(1, instance.length());
        instance.addLine("act2", "hello 2");
        assertEquals(2, instance.length());
        instance.addLine("act3", "hello 3");
        assertEquals(3, instance.length());
    }

    /**
     * Test of getActors method, of class Data_Transcript.
     */
    public void testGetActors()
    {
        System.out.println("getActors");
        Data_Transcript instance = new Data_Transcript("text transcript module");
        String[] expResult =
        {
        };
        String[] result = instance.getActors();
        assertTrue(Arrays.equals(expResult, result));

        instance.addLine("act1", "hello 1");
        instance.addLine("act2", "hello 2");
        instance.addLine("act1", "hello 3");
        instance.addLine("act2", "hello 4");

        String[] expResult2 =
        {
            "act1", "act2"
        };
        String[] result2 = instance.getActors();
        assertTrue(Arrays.equals(expResult2, result2));

        instance.addLine("act3", "hello 5");

        String[] expResult3 =
        {
            "act1", "act2", "act3"
        };
        String[] result3 = instance.getActors();
        assertTrue(Arrays.equals(expResult3, result3));

    }

    /**
     * Test of getLine method, of class Data_Transcript.
     */
    public void testGetLine()
    {
        System.out.println("getLine");
        int index = 0;
        Data_Transcript instance = new Data_Transcript("text transcript module");
        Line result = instance.getLine(index);
        assertNull(result);
        instance.addLine("act1", "hello 1");
        result = instance.getLine(index);
        assertEquals("act1", result.actor);
        assertEquals("hello 1", result.dialog);
        result = instance.getLine(-1);
        assertNull(result);
    }

    /**
     * Test of removeLine method, of class Data_Transcript.
     */
    public void testRemoveLine()
    {
        System.out.println("removeLine");
        Data_Transcript instance = new Data_Transcript("text transcript module");
        assertEquals(0, instance.length());
        instance.addLine("act1", "hello 1");
        assertEquals(1, instance.length());
        instance.addLine("act2", "hello 2");
        assertEquals(2, instance.length());
        instance.addLine("act3", "hello 3");
        assertEquals(3, instance.length());

        assertTrue(instance.removeLine(0));
        assertEquals(2, instance.length());
        assertFalse(instance.removeLine(2));
        assertFalse(instance.removeLine(-1));
        assertEquals(2, instance.length());
        assertTrue(instance.removeLine(0));
        assertEquals(1, instance.length());
        assertTrue(instance.removeLine(0));
        assertEquals(0, instance.length());
        assertFalse(instance.removeLine(0));

    }

    /**
     * Test of moveLineUp method, of class Data_Transcript.
     */
    public void testMoveLineUp()
    {
        System.out.println("moveLineUp");
        Data_Transcript instance = new Data_Transcript("text transcript module");
        instance.addLine("act1", "hello 1");
        instance.addLine("act2", "hello 2");
        assertFalse(instance.moveLineUp(0));
        Line result = instance.getLine(0);
        assertEquals("act1", result.actor);
        assertEquals("hello 1", result.dialog);
        assertTrue(instance.moveLineUp(1));
        result = instance.getLine(0);
        assertEquals("act2", result.actor);
        assertEquals("hello 2", result.dialog);

    }

    /**
     * Test of moveLineDown method, of class Data_Transcript.
     */
    public void testMoveLineDown()
    {
        System.out.println("moveLineDown");
        Data_Transcript instance = new Data_Transcript("text transcript module");
        instance.addLine("act1", "hello 1");
        instance.addLine("act2", "hello 2");
        assertFalse(instance.moveLineDown(1));
        Line result = instance.getLine(1);
        assertEquals("act2", result.actor);
        assertEquals("hello 2", result.dialog);
        assertTrue(instance.moveLineDown(0));
        result = instance.getLine(1);
        assertEquals("act1", result.actor);
        assertEquals("hello 1", result.dialog);
    }

    /**
     * Test of length method, of class Data_Transcript.
     */
    public void testLength()
    {
    }

    /**
     * Test of duplicate method, of class Data_Transcript.
     */
    public void testDuplicate()
    {
    }

}
