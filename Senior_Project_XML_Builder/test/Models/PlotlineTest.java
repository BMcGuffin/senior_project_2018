package Models;

import junit.framework.TestCase;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

/**
 *
 * @author Bryan McGuffin
 */
public class PlotlineTest extends TestCase
{

    public PlotlineTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of getInstance method, of class Plotline.
     */
    public void testGetInstance()
    {
        System.out.println("getInstance");
        int time = 0;
        Script mScript = Mockito.mock(Script.class);
        Plotline plt = new Plotline("getInstance", 0, mScript);

        Instance inst1 = plt.addInstance(time);

        assertEquals(inst1, plt.getInstance(time));
        assertEquals(null, plt.getInstance(time + 1));
    }

    /**
     * Test of addInstance method, of class Plotline.
     */
    public void testAddInstance()
    {
        System.out.println("addInstance");
        int time = 0;
        Script mScript = Mockito.mock(Script.class);
        Plotline plt = new Plotline("addInstance", 0, mScript);
        int size = plt.instanceCount();
        assertEquals(0, size);

        Instance result = plt.addInstance(time);
        size = plt.instanceCount();
        assertNotNull(result);
        assertEquals(1, size);
        assertEquals(result, plt.getInstance(0));

        Instance result2 = plt.addInstance(time);
        size = plt.instanceCount();
        assertNotNull(result2);
        assertTrue(result == result2);
        assertEquals(1, size);
        assertEquals(result, plt.getInstance(0));

        time = 60;
        Instance result3 = plt.addInstance(time);
        size = plt.instanceCount();
        assertNotNull(result3);
        assertNotSame(result, result3);
        assertEquals(2, size);
        assertEquals(result, plt.getInstance(0));
        assertEquals(result3, plt.getInstance(time));

        int newTime = -60;
        Instance result4 = plt.addInstance(newTime);
        size = plt.instanceCount();
        assertNotNull(result4);
        assertEquals(3, size);
        assertEquals(result4, plt.getInstance(0));

        assertSame(result, plt.getInstance(0 - newTime));
        assertSame(result3, plt.getInstance(time - newTime));

    }

    /**
     * Test of removeInstance method, of class Plotline.
     */
    public void testRemoveInstance()
    {
        System.out.println("removeInstance");
        int time = 0;
        Script mScript = Mockito.mock(Script.class);
        Plotline plt = new Plotline("removeInstance", 0, mScript);

        assertFalse(plt.removeInstance(time));
        int size = plt.instanceCount();
        assertEquals(0, size);

        plt.addInstance(time);
        int newTime = 60;
        plt.addInstance(newTime);
        size = plt.instanceCount();
        assertEquals(2, size);

        assertTrue(plt.removeInstance(newTime));
        size = plt.instanceCount();
        assertEquals(1, size);

        assertFalse(plt.removeInstance(newTime));
        size = plt.instanceCount();
        assertEquals(1, size);

        assertTrue(plt.removeInstance(time));
        size = plt.instanceCount();
        assertEquals(0, size);

        assertFalse(plt.removeInstance(time));
        size = plt.instanceCount();
        assertEquals(0, size);

    }

    /**
     * Test of relocateInstance method, of class Plotline.
     */
    public void testRelocateInstance()
    {
        System.out.println("relocateInstance");
        int time = 0;
        Script mScript = Mockito.mock(Script.class);
        Plotline plt = new Plotline("relocateInstance", 0, mScript);
        int from = 0;
        Instance inst1 = plt.addInstance(from);
        assertNotNull(inst1);
        assertEquals(inst1, plt.getInstance(from));
        int to = 10;
        boolean result = plt.relocateInstance(from, to);
        assertTrue(result);
        Instance inst2 = plt.getInstance(from);
        assertNotNull(inst2);
        assertSame(inst1, inst2);

        Instance inst3 = plt.addInstance(to);
        assertNotNull(inst3);
        assertEquals(inst3, plt.getInstance(to));
        assertNotSame(inst1, inst3);

        from = to;
        to = 20;
        result = plt.relocateInstance(from, to);
        Instance inst4 = plt.getInstance(to);
        assertNotNull(inst4);
        assertSame(inst3, inst4);

    }

    /**
     * Test of length method, of class Plotline.
     */
    public void testLength()
    {
        System.out.println("length");
        int time = 0;
        Script mScript = Mockito.mock(Script.class);
        Plotline plt = new Plotline("length", 0, mScript);
        assertEquals(0, plt.length());

        plt.addInstance(0);
        assertEquals(1, plt.length());

        plt.addInstance(10);
        assertEquals(11, plt.length());

        plt.addInstance(20);
        assertEquals(21, plt.length());

        plt.removeInstance(0);
        assertEquals(11, plt.length());
    }

}
