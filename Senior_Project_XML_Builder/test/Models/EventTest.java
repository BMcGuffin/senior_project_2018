package Models;

import junit.framework.TestCase;
import org.mockito.Mockito;

/**
 *
 * @author Bryan McGuffin
 */
public class EventTest extends TestCase
{

    public EventTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of addElement method, of class Event.
     */
    public void testAddElement()
    {
        System.out.println("addElement");
        Buildable b = Mockito.mock(Buildable.class);
        String label = "hello";
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        assertEquals(0, instance.fieldCount());

        Buildable result = instance.addElement(b, label);
        assertSame(b, result);
        assertEquals(1, instance.fieldCount());

    }

    /**
     * Test of getElement method, of class Event.
     */
    public void testGetData()
    {
        System.out.println("getData");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));

    }

    /**
     * Test of getLabel method, of class Event.
     */
    public void testGetLabel()
    {
        System.out.println("getLabel");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertEquals("a", instance.getLabel(0));
        assertEquals("b", instance.getLabel(1));
    }

    /**
     * Test of removeElement method, of class Event.
     */
    public void testRemoveElement()
    {
        System.out.println("removeElement");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));

        assertFalse(instance.removeElement(2));

        assertTrue(instance.removeElement(0));
        assertSame(b, instance.getElement(0));
    }

    /**
     * Test of moveElementUp method, of class Event.
     */
    public void testMoveElementUp()
    {
        System.out.println("moveElementUp");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));

        assertFalse(instance.moveElementUp(0));

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));
        assertEquals("a", instance.getLabel(0));
        assertEquals("b", instance.getLabel(1));

        assertTrue(instance.moveElementUp(1));

        assertSame(b, instance.getElement(0));
        assertSame(a, instance.getElement(1));
        assertEquals("b", instance.getLabel(0));
        assertEquals("a", instance.getLabel(1));
    }

    /**
     * Test of moveElementDown method, of class Event.
     */
    public void testMoveElementDown()
    {
        System.out.println("moveElementDown");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));

        assertFalse(instance.moveElementDown(1));

        assertSame(a, instance.getElement(0));
        assertSame(b, instance.getElement(1));
        assertEquals("a", instance.getLabel(0));
        assertEquals("b", instance.getLabel(1));

        assertTrue(instance.moveElementDown(0));

        assertSame(b, instance.getElement(0));
        assertSame(a, instance.getElement(1));
        assertEquals("b", instance.getLabel(0));
        assertEquals("a", instance.getLabel(1));
    }

    /**
     * Test of isMediaEvent method, of class Event.
     */
    public void testIsMediaEvent()
    {
        System.out.println("isMediaEvent");
        Buildable a = Mockito.mock(Buildable.class);
        Buildable b = Mockito.mock(Buildable.class);
        Buildable med = Mockito.mock(Data_Media.class);
        Instance ins = Mockito.mock(Instance.class);
        Event instance = new Event(ins);
        instance.addElement(a, "a");
        instance.addElement(b, "b");

        assertFalse(instance.isMediaEvent());

        instance.addElement(med, "med");

        assertTrue(instance.isMediaEvent());

        assertTrue(instance.removeElement(2));

        assertFalse(instance.isMediaEvent());

    }

    /**
     * Test of setParentInstance method, of class Event.
     */
    public void testSetParentInstance()
    {
    }

    /**
     * Test of getElement method, of class Event.
     */
    public void testGetElement()
    {
    }

    /**
     * Test of fieldCount method, of class Event.
     */
    public void testFieldCount()
    {
    }

    /**
     * Test of toString method, of class Event.
     */
    public void testToString()
    {
    }

    /**
     * Test of toXML method, of class Event.
     */
    public void testToXML()
    {
    }

}
