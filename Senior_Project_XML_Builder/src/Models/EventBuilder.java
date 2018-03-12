/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates events from templates. Accesses the folder of event templates,
 * known here as the "deck."
 *
 * @author Bryan McGuffin
 * @version Mar 11, 2018
 */
public class EventBuilder
{

    private File templatesFolder;

    /**
     * Contains a prototype for each event type in the template deck.
     */
    private Map<String, Event> prototypes;

    /**
     * Constructor. Builds prototypes for each event. This will be a singleton
     * class.
     */
    private EventBuilder()
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }
    
    /**
     * Get the singleton instance of this builder.
     * 
     * @return the event builder
     */
    public static EventBuilder getBuilder()
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /**
     * Generate a new event of the given type.
     *
     * @param eventType the type of the event.
     * @return a copy of the prototype for the given event.
     */
    public Event generateEvent(String eventType)
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /**
     * Get a list of all valid event types in the deck.
     *
     * @return a list of all names of event types.
     */
    public ArrayList<String> getEventDeck()
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /**
     * Given a new event, create an entry for it in the event deck and add a
     * prototype to the list of prototypes.
     *
     * @param evt
     * @return
     */
    public boolean recordEventToDeck(Event evt)
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }

    /**
     * Create a prototype from a given event type file in the deck.
     *
     * @param typeName the type of the event.
     * @return a prototype event.
     */
    private Event buildPrototype(String typeName)
    {
        //TODO implement this method.
        throw new UnsupportedOperationException("Method not implemented.");
    }

}
