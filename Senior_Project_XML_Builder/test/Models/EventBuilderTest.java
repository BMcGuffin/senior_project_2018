package Models;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Bryan McGuffin
 */
public class EventBuilderTest extends TestCase {
    
    public EventBuilderTest(String testName) {
        super(testName);
    }

    /**
     * Test of getBuilder method, of class EventBuilder.
     */
    public void testGetBuilder()
    {
        System.out.println("getBuilder");
        EventBuilder expResult = null;
        EventBuilder result = EventBuilder.getBuilder();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateEvent method, of class EventBuilder.
     */
    public void testGenerateEvent()
    {
        System.out.println("generateEvent");
        String eventType = "";
        EventBuilder instance = null;
        Event expResult = null;
        Event result = instance.generateEvent(eventType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEventDeck method, of class EventBuilder.
     */
    public void testGetEventDeck()
    {
        System.out.println("getEventDeck");
        EventBuilder instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getEventDeck();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of recordEventToDeck method, of class EventBuilder.
     */
    public void testRecordEventToDeck()
    {
        System.out.println("recordEventToDeck");
        Event evt = null;
        EventBuilder instance = null;
        boolean expResult = false;
        boolean result = instance.recordEventToDeck(evt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
