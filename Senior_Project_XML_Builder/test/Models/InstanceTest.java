package Models;

import junit.framework.TestCase;
import org.mockito.Mockito;

/**
 *
 * @author Bryan McGuffin
 */
public class InstanceTest extends TestCase
{

    public InstanceTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of addEvent method, of class Instance.
     */
    public void testAddEvent()
    {
        System.out.println("addEvent");
        Plotline mPlot = Mockito.mock(Plotline.class);
        Instance instance = new Instance(mPlot, 0);
        assertEquals(0, instance.events.size());
        Event e = instance.addEvent();
        assertEquals(1, instance.events.size());
        assertSame(e, instance.events.get(0));

    }

    /**
     * Test of removeEvent method, of class Instance.
     */
    public void testRemoveEvent()
    {
        System.out.println("removeEvent");
        Plotline mPlot = Mockito.mock(Plotline.class);
        Instance instance = new Instance(mPlot, 0);
        assertEquals(0, instance.events.size());
        Event e = instance.addEvent();
        assertEquals(1, instance.events.size());
        assertSame(e, instance.events.get(0));
        assertTrue(instance.removeEvent(e));
        assertEquals(0, instance.events.size());
        assertFalse(instance.removeEvent(e));
    }

    /**
     * Test of addEvent method, of class Instance.
     */
    public void testAddEvent_0args()
    {
        System.out.println("addEvent");
        Instance instance = null;
        Event expResult = null;
        Event result = instance.addEvent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEvent method, of class Instance.
     */
    public void testAddEvent_Event()
    {
        System.out.println("addEvent");
        Event e = null;
        Instance instance = null;
        Event expResult = null;
        Event result = instance.addEvent(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Instance.
     */
    public void testToString()
    {
        System.out.println("toString");
        Instance instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Instance.
     */
    public void testToXML()
    {
        System.out.println("toXML");
        Instance instance = null;
        String expResult = "";
        String result = instance.toXML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
