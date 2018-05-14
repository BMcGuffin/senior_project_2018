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
    public void testAddEvent_0Args()
    {
        System.out.println("addEvent");
        Plotline mPlot = Mockito.mock(Plotline.class);
        Instance instance = new Instance(mPlot);
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
        Instance instance = new Instance(mPlot);
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
    }

    /**
     * Test of addEvent method, of class Instance.
     */
    public void testAddEvent_Event()
    {
    }

    /**
     * Test of toString method, of class Instance.
     */
    public void testToString()
    {
    }

    /**
     * Test of toXML method, of class Instance.
     */
    public void testToXML()
    {
    }

}
