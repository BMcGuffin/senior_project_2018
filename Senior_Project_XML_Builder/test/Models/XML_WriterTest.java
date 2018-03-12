package Models;

import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class XML_WriterTest extends TestCase {
    
    public XML_WriterTest(String testName) {
        super(testName);
    }

    /**
     * Test of openTag method, of class XML_Writer.
     */
    public void testOpenTag()
    {
        System.out.println("openTag");
        String s = "";
        XML_Writer instance = new XML_WriterImpl();
        String expResult = "";
        String result = instance.openTag(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeTag method, of class XML_Writer.
     */
    public void testCloseTag()
    {
        System.out.println("closeTag");
        String s = "";
        XML_Writer instance = new XML_WriterImpl();
        String expResult = "";
        String result = instance.closeTag(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of emptyTag method, of class XML_Writer.
     */
    public void testEmptyTag()
    {
        System.out.println("emptyTag");
        String s = "";
        XML_Writer instance = new XML_WriterImpl();
        String expResult = "";
        String result = instance.emptyTag(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SimpleTag method, of class XML_Writer.
     */
    public void testSimpleTag()
    {
        System.out.println("SimpleTag");
        String s = "";
        XML_Writer instance = new XML_WriterImpl();
        String expResult = "";
        String result = instance.SimpleTag(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class XML_WriterImpl extends XML_Writer
    {
    }

}
