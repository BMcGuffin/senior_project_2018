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

}
