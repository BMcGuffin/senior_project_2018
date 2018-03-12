/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Represents the DTD used by this script. Has a reference to the script it's
 * part of. Contains a tree of tags.
 *
 * Tags are in the following format: <AN_ELEMENT_NAME.FIELD_OF_THAT_ELEMENT>
 *
 * @author Bryan McGuffin
 * @version Feb 5, 2018
 */
public class DTDBuilder
{

    private Script script;

    private static DTDBuilder singleton;

    private ArrayList<String> elements;

    private ArrayList<String> reserved;

    private TreeMap<String, ArrayList<String>> subelements;

    private DTDBuilder(Script scr)
    {
        script = scr;
        reserved = new ArrayList<>();
        elements = new ArrayList<>();
        subelements = new TreeMap<>();
        addTopLevelTags();
    }

    private void addTopLevelTags()
    {
        reserved.add("SCRIPT");
        reserved.add("PLOTLINE");
        reserved.add("INSTANT");
        reserved.add("EVENT");
    }

    /**
     * Access this DTDBuilder.
     *
     * @param scr the current script
     * @return the DTDBuilder for this script
     */
    public static DTDBuilder getDTDBuilder(Script scr)
    {
        if (singleton == null)
        {
            singleton = new DTDBuilder(scr);
        }
        return singleton;
    }

    /**
     * Generate the DTD file for this script.
     */
    public void generateDTD() throws IOException
    {
        String filename = getDTDName();
        File file = new File(filename);

        file.createNewFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(formatExternalDTD());
        bw.flush();
        bw.close();

    }

    public String getDTDName()
    {
        return "" + script.scriptTitle + ".dtd";
    }

    /**
     * Check each prospective tag in this event. If the event type of this event
     * is not unique and valid, or more than one data field has the same label,
     * reject it; otherwise, format new tags for all data fields and add them to
     * the DTD.
     *
     * @param evt the event to be checked
     * @return true if the event can be safely added to the DTD.
     */
    public boolean digestEvent(Event evt)
    {
        //Check to make sure event name is not reserved keyword.
        if (reserved.contains(evt.eventType.toUpperCase()))
        {
            return false;
        }

        //Check to make sure event name is not in list of known elements.
        if (elements.contains(evt.eventType.toUpperCase()))
        {
            return false;
        }
        //Check to make sure the event name doesn't contain an invalid character.
        if (evt.eventType.contains(".") || evt.eventType.contains("_"))
        {
            return false;
        }

        //Check data field names for uniqueness.
        ArrayList<String> fieldNames = new ArrayList<>();
        for (int i = 0; i < evt.fieldCount(); i++)
        {
            if (fieldNames.contains(evt.getElement(i).elementName()))
            {
                return false;
            }
            fieldNames.add(evt.getElement(i).elementName());
        }

        //If we get here, we can safely add all elements to the DTD.
        addNewElement(evt.eventType);
        for (String field : fieldNames)
        {
            addNewSubElement(evt.eventType, field);
        }

        return true;
    }

    /**
     * Update the DTD elements relating to this event. Remove tags that have
     * been deprecated, and add newly created tags.
     *
     * @param evt the event in question
     * @return true if the new tags retain uniqueness
     */
    public boolean updateEvent(Event evt)
    {
        //Check data field names for uniqueness.
        ArrayList<String> fieldNames = new ArrayList<>();
        for (int i = 0; i < evt.fieldCount(); i++)
        {
            if (fieldNames.contains(formatTag(evt.getElement(i).elementName())))
            {
                return false;
            }
            fieldNames.add(evt.getElement(i).elementName());
        }

        //Remove data field names relevant to this event.
        subelements.remove(evt.eventType);

        //If we get here, we can safely add all elements to the DTD.
        addNewElement(evt.eventType);
        for (String field : fieldNames)
        {
            addNewSubElement(evt.eventType, field);
        }

        return true;
    }

    /**
     * Remove all tags related to this event.
     *
     * @param evt the event in question.
     * @return true if the event was successfully removed.
     */
    public void removeEvent(Event evt)
    {
        ArrayList<String> newElements = new ArrayList<>();
        for (String element : elements)
        {
            if (!element.startsWith(evt.eventType))
            {
                newElements.add(element);
            }
        }
        elements = newElements;
    }

    /**
     * Add an element into this DTD.
     *
     * @param tag
     */
    private void addNewElement(String tag)
    {
        elements.add(formatTag(tag));
        subelements.put(formatTag(tag), new ArrayList<>());
    }

    private void addNewSubElement(String eventType, String field)
    {
        subelements.get(formatTag(eventType)).add(formatTag(eventType + "." + field));
    }

    public static String formatTag(String tag)
    {
        String upper = tag.toUpperCase();
        String underscores = upper.replace(" ", "_");
        return underscores;
    }

    /**
     * Get the DTD-formatted version of this tag.
     *
     * @param evt
     * @param b
     * @return
     */
    public String getElement(Event evt, Buildable b)
    {
        String formatted = formatTag(evt.eventType + "." + b.elementName());
        if (elements.contains(formatTag(evt.eventType))
                && subelements.get(formatTag(evt.eventType)).contains(formatted))
        {
            return formatted;
        }
        return null;
    }

    private String formatExternalDTD()
    {
        String output = "";
        output += elementWithSubs("SCRIPT", "PLOTLINE");
        output += elementWithSubs("PLOTLINE", "INSTANT");
        output += elementWithSubs("INSTANT", "EVENT");
        String[] elementsArray = elements.toArray(new String[elements.size()]);
        output += elementWithSubs("EVENT", elementsArray);
        for (String element : elementsArray)
        {
            ArrayList<String> sublist = subelements.get(element);
            if (sublist != null)
            {
                String[] subElementsArray = sublist.toArray(
                        new String[sublist.size()]);
                output += elementWithSubs(element, subElementsArray);
                for (String subElement : subElementsArray)
                {
                    output += elementWithSubs(subElement, "#PCDATA");
                }
            }
        }
        return output;
    }

    private String elementWithSubs(String element, String... subs)
    {
        String output = elementOpen(element);
        if (subs.length > 0)
        {
            output += "(";
            for (int i = 0; i < subs.length; i++)
            {
                output += subs[i];
                if (i < subs.length - 1)
                {
                    output += ",";
                }

            }
            output += ")";
        }
        output += ">\n";
        return output;
    }

    private String elementOpen(String tag)
    {
        return "<!ELEMENT " + tag + " ";
    }

    private String attListOpen(String element, String attName)
    {
        return "<!ATTLIST " + element + " " + attName + " ";
    }

    public void reset()
    {
        reserved = new ArrayList<>();
        elements = new ArrayList<>();
        subelements = new TreeMap<>();
        addTopLevelTags();
    }

}
