package Models;

import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class Data_MenuTest extends TestCase
{

    public Data_MenuTest(String testName)
    {
        super(testName);
    }

    /**
     * Test of addOption method, of class Data_Menu.
     */
    public void testAddOption()
    {
        System.out.println("addOption");
        String newOp = "op 1";
        Data_Menu instance = new Data_Menu("menu test");
        assertEquals(0, instance.menuSize());
        assertTrue(instance.addOption(newOp));
        assertEquals(1, instance.menuSize());
    }

    /**
     * Test of removeOption method, of class Data_Menu.
     */
    public void testRemoveOption()
    {
        System.out.println("removeOption");
        String newOp = "op 1";
        Data_Menu instance = new Data_Menu("menu test");
        assertEquals(0, instance.menuSize());
        assertFalse(instance.removeOption(0));
        assertTrue(instance.addOption(newOp));
        assertEquals(1, instance.menuSize());
        assertFalse(instance.removeOption(1));
        assertFalse(instance.removeOption(-1));
        assertEquals(1, instance.menuSize());
        assertTrue(instance.removeOption(0));
        assertEquals(0, instance.menuSize());
        assertFalse(instance.removeOption(0));
    }

    /**
     * Test of getSelected method, of class Data_Menu.
     */
    public void testGetSelected()
    {
        System.out.println("getSelected");
        String newOp = "op 1";
        Data_Menu instance = new Data_Menu("menu test");
        assertEquals(0, instance.menuSize());
        assertNull(instance.getSelected());
        assertTrue(instance.addOption(newOp));
        assertEquals("op 1", instance.getSelected());
    }

    /**
     * Test of setSelected method, of class Data_Menu.
     */
    public void testSetSelected()
    {
        System.out.println("setSelected");
        Data_Menu instance = new Data_Menu("menu test");
        assertEquals(0, instance.menuSize());
        assertNull(instance.getSelected());
        assertFalse(instance.setSelected(1));
        assertTrue(instance.addOption("op 1"));
        assertEquals("op 1", instance.getSelected());
        assertFalse(instance.setSelected(1));
        assertTrue(instance.setSelected(0));
        assertTrue(instance.addOption("op 2"));
        assertTrue(instance.setSelected(1));
        assertEquals("op 2", instance.getSelected());
        assertFalse(instance.setSelected(2));
        assertFalse(instance.setSelected(-1));
    }

}
