package Models;

import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class ScriptTest extends TestCase
{

    public ScriptTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of addPlotline method, of class Script.
     */
    public void testAddPlotline()
    {
        System.out.println("addPlotline");
        Script instance = new Script();
        //Add a plotline at time 0
        int start = 0;
        int expResult = 0;
        int result = instance.addPlotline("1", start);
        assertEquals(expResult, result);

        //Add a second plotline at time 0
        expResult = 1;
        result = instance.addPlotline("2", start);
        assertEquals(expResult, result);

        //Add a third plotline at time 120
        start = 120;
        expResult = 2;
        result = instance.addPlotline("3", start);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPlotLine method, of class Script.
     */
    public void testGetPlotLine()
    {
        System.out.println("getPlotLine");
        Script instance = new Script();
        //Add a plotline at time 0
        int start = 0;
        int expResult = 0;
        int result = instance.addPlotline("1", start);
        //Add a second plotline at time 0
        result = instance.addPlotline("2", start);
        //Add a third plotline at time 120
        start = 120;
        result = instance.addPlotline("3", start);

        Plotline p0 = instance.getPlotLine(0);
        Plotline p1 = instance.getPlotLine(1);
        Plotline p2 = instance.getPlotLine(2);
        Plotline p3 = instance.getPlotLine(3);

        assertNotNull(p0);
        assertNotNull(p1);
        assertNotNull(p2);
        assertNull(p3);

        assertSame(p0, p0);
        assertNotSame(p0, p1);

    }

    /**
     * Test of removePlotline method, of class Script.
     */
    public void testRemovePlotline()
    {
        System.out.println("removePlotline");
        Script instance = new Script();
        assertFalse(instance.removePlotline(0));
        //Add a plotline at time 0
        int start = 0;
        int expResult = 0;
        int result = instance.addPlotline("1", start);
        //Add a second plotline at time 0
        result = instance.addPlotline("2", start);
        //Add a third plotline at time 120
        start = 120;
        result = instance.addPlotline("3", start);

        Plotline p0 = instance.getPlotLine(0);
        Plotline p1 = instance.getPlotLine(1);
        Plotline p2 = instance.getPlotLine(2);
        Plotline p3 = instance.getPlotLine(3);

        assertNotNull(p0);
        assertNotNull(p1);
        assertNotNull(p2);
        assertNull(p3);

        //Trying to remove an element that doesn't exist should return false
        assertFalse(instance.removePlotline(3));
        //Removing an element that exists should return true
        assertTrue(instance.removePlotline(2));

        p0 = instance.getPlotLine(0);
        p1 = instance.getPlotLine(1);
        p2 = instance.getPlotLine(2);
        p3 = instance.getPlotLine(3);

        assertNotNull(p0);
        assertNotNull(p1);
        assertNull(p2);
        assertNull(p3);

        //Removing an element should shorten the list by 1
        assertTrue(instance.removePlotline(0));

        p0 = instance.getPlotLine(0);
        p1 = instance.getPlotLine(1);
        p2 = instance.getPlotLine(2);
        p3 = instance.getPlotLine(3);

        assertNotNull(p0);
        assertNull(p1);
        assertNull(p2);
        assertNull(p3);
    }

    /**
     * Test of countPlotlines method, of class Script.
     */
    public void testCountPlotlines()
    {
        System.out.println("countPlotLines");
        Script instance = new Script();
        int start = 0;
        int expResult = 0;
        int result = instance.countPlotlines();
        assertEquals(expResult, result);
        //Add a plotline at time 0
        expResult = 1;
        instance.addPlotline("1", start);
        result = instance.countPlotlines();
        assertEquals(expResult, result);
        //Add a second plotline at time 0
        expResult = 2;
        instance.addPlotline("2", start);
        result = instance.countPlotlines();
        assertEquals(expResult, result);
        //Add a third plotline at time 120
        start = 120;
        expResult = 3;
        instance.addPlotline("3", start);
        result = instance.countPlotlines();
        assertEquals(expResult, result);

        //Remove all plotlines 1 at a time
        expResult = 2;
        instance.removePlotline(0);
        result = instance.countPlotlines();
        assertEquals(expResult, result);
        expResult = 1;
        instance.removePlotline(0);
        result = instance.countPlotlines();
        assertEquals(expResult, result);
        expResult = 0;
        instance.removePlotline(0);
        result = instance.countPlotlines();
        assertEquals(expResult, result);

    }

}
