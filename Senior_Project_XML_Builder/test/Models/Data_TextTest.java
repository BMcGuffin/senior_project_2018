package Models;

import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class Data_TextTest extends TestCase {
    
    public Data_TextTest(String testName) {
        super(testName);
    }

    /**
     * Test of setContent method, of class Data_Text.
     */
    public void testContentSetGet()
    {
        System.out.println("Get and Set Content");
        String str = "Hello, this is a test";
        Data_Text instance = new Data_Text("test text module");
        instance.setContent(str);
        String str2 = instance.getContent();
        
        assertEquals(str, str2);
    }

    /**
     * Test of setContent method, of class Data_Text.
     */
    public void testSetContent()
    {
        System.out.println("setContent");
        String str = "";
        Data_Text instance = null;
        instance.setContent(str);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContent method, of class Data_Text.
     */
    public void testGetContent()
    {
        System.out.println("getContent");
        Data_Text instance = null;
        String expResult = "";
        String result = instance.getContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of duplicate method, of class Data_Text.
     */
    public void testDuplicate()
    {
        System.out.println("duplicate");
        Data_Text instance = null;
        Buildable expResult = null;
        Buildable result = instance.duplicate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
