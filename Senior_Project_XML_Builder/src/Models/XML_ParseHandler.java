/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Models;

import java.io.File;
import org.mockito.internal.configuration.injection.SpyOnInjectedFieldsHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

/**
 *
 * @author Bryan McGuffin
 * @version Apr 17, 2018
 */
public class XML_ParseHandler extends DefaultHandler
{
	private final int placeholder_time = 0;
	private final String placeholder_buildable_type = "Transcript";
	private Script generatedScript;
	private Plotline generatedPlotline;
	private Instance generatedInstance;
	private Event generatedEvent;
	private Buildable generatedBuildable;
	private int currentPlotIndex;
	private int currentInstIndex;
	private int currentEventIndex;
	private int currentDataFieldIndex;
	private boolean currentlyDefiningEvent;
	private boolean currentlyDefiningBuildable;
	private boolean eventHasExistingPrototype;
	private EventBuilder eb;
	private String characterData;
	
	public XML_ParseHandler()
	{
		System.out.println("Constructor for handler");
		generatedScript = new Script();
		currentDataFieldIndex = currentEventIndex = currentInstIndex = currentPlotIndex = 0;
		currentlyDefiningEvent = currentlyDefiningBuildable = false;
		eventHasExistingPrototype = false;
		eb = EventBuilder.getBuilder();
		characterData = "";
	}

	// When we start a new element, we should update our indices and the stack. We
	// should generate a new instance of the element in question.
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
	{
		System.out.println(String.format("START: %s", qName));
		XML_Writer.tags elementTag = XML_Writer.tags.fromString(qName);
		// If elementTag is null, then this is either a user-defined event or a
		// user-defined buildable
		if (elementTag == null)
		{
			// If we are already defining an event, this custom element must be a buildable.
			if (currentlyDefiningEvent)
			{
				// If we're already defining a buildable, we may have a problem here.
				if (currentlyDefiningBuildable)
				{
					System.out.println("ERROR: defining buildable");
				}
				// If we weren't already defining a buildable, we are now.
				else
				{
					currentlyDefiningBuildable = true;
					// If we already have a prototype, then it already contains the fields we're
					// looking for, so just use those
					if (eventHasExistingPrototype)
					{
						generatedBuildable = generatedEvent.getElement(currentDataFieldIndex);
						generatedBuildable.reset();
					}
					else
					{
						// Switch based on value of element's data_type attribute
						// TODO: add "data_type" attribute to buildable classes
						String data_type = placeholder_buildable_type;
						if (data_type.equalsIgnoreCase("Media"))
						{
							generatedBuildable = generatedEvent.addElement(new Data_Media(qName), qName);
						}
						else if (data_type.equalsIgnoreCase("Menu"))
						{
							generatedBuildable = generatedEvent.addElement(new Data_Menu(qName), qName);
						}
						else if (data_type.equalsIgnoreCase("Text"))
						{
							generatedBuildable = generatedEvent.addElement(new Data_Text(qName), qName);
						}
						else if (data_type.equalsIgnoreCase("Transcript"))
						{
							generatedBuildable = generatedEvent.addElement(new Data_Transcript(qName), qName);
						}
						else
						{
							System.out.println("ERROR: Buildable type");
						}
					}
				}
			}
			// If we weren't already defining an event, we are now.
			else
			{
				currentlyDefiningEvent = true;
				currentlyDefiningBuildable = false;
				generatedEvent = eb.generateEvent(qName);
				// If this is not a null event, a prototype for it already exists.
				if (generatedEvent != null)
				{
					eventHasExistingPrototype = true;
				}
				// If it's a null event, we're building a new event from scratch. A prototype
				// for this event will be generated once it's complete.
				else
				{
					eventHasExistingPrototype = false;
				}
				generatedEvent = generatedInstance.addEvent(generatedEvent);
				// Increment the index of the current event we're working with
				currentEventIndex = generatedInstance.events.size() - 1;
				// Now that we've moved the event index, reset the data index
				currentDataFieldIndex = 0;
			}
		}
		else
		{
			switch (elementTag)
			{
				case INSTANT:
					// Start of a new instance. Generate a new instance from the current plotline,
					// at
					// the time attached to this instance's time attribute.
					// TODO: add "time" attribute to INSTANT tag
					currentInstIndex = placeholder_time;
					generatedInstance = generatedPlotline.addInstance(currentInstIndex);
					// Now that we've moved the instance index, reset the event and data indices
					currentEventIndex = currentDataFieldIndex = 0;
					break;
				case MEDIA_PLAYBACK:
					if (!(generatedBuildable instanceof Data_Media))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case MEDIA_SRC_FILE:
					if (!(generatedBuildable instanceof Data_Media))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case MEDIA_START:
					if (!(generatedBuildable instanceof Data_Media))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case MENU_OPTION:
					if (!(generatedBuildable instanceof Data_Menu))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case MENU_SELECTED:
					if (!(generatedBuildable instanceof Data_Menu))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case PLOTLINE:
					// Start of a new plotline. Generate a new plotline from the current script,
					// starting at the time attached to this plotline's time attribute, with the
					// title given by this plotline's title attribute.
					// TODO: add "time" attribute to PLOTLINE tag
					// TODO: add "title" attribute to PLOTLINE tag
					currentPlotIndex = generatedScript.addPlotline("Placeholder text", placeholder_time);
					generatedPlotline = generatedScript.getPlotLine(currentPlotIndex);
					// Now that we've moved the plot index, reset the instance, event and data
					// indices
					currentInstIndex = currentEventIndex = currentDataFieldIndex = 0;
					break;
				case SCRIPT:
					// Start of a new script.
					generatedScript = new Script();
					generatedScript.scriptTitle = "Placeholder text";
					generatedScript.saveFile = null;
					// TODO: add "title" attribute to SCRIPT tag
					// TODO: find a way to set the save file for the script as it's being loaded
					break;
				case TEXT_FIELD:
					if (!(generatedBuildable instanceof Data_Text))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case TS_DIALOG_LINE:
					if (!(generatedBuildable instanceof Data_Transcript))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					else
					{
						System.out.println("Length before: " + ((Data_Transcript) generatedBuildable).length());
						((Data_Transcript) generatedBuildable).addLine("actor_text", "speech_text");
						System.out.println("Length after: " + ((Data_Transcript) generatedBuildable).length());
					}
					break;
				case TS_LINE_ACTOR:
					if (!(generatedBuildable instanceof Data_Transcript))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				case TS_LINE_SPEECH:
					if (!(generatedBuildable instanceof Data_Transcript))
					{
						System.out.println("ERROR: Type of current buildable is incorrect.");
					}
					break;
				default:
					// This won't run because tags is fully enumerated here
					break;
			}
		}
	}

	// This is where data for the new element is harvested. We should add it to the
	// new element as it's generated.
	@Override
	public void characters(char ch[], int start, int length) throws SAXException
	{
		// Capture the character data for use in endElement.
		characterData = new String(ch, start, length).trim();
	}

	// When we stop getting new data about the element, we can add it to the model.
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		System.out.println(String.format("END: %s", qName));
		XML_Writer.tags elementTag = XML_Writer.tags.fromString(qName);
		// If elementTag is null, then this is either a user-defined event or a
		// user-defined buildable
		if (elementTag == null)
		{
			// If we are already defining an event, this custom element might be an event,
			// or it could be a buildable.
			if (currentlyDefiningEvent)
			{
				// If we're already defining a buildable, this custom element is a buildable.
				if (currentlyDefiningBuildable)
				{
					currentlyDefiningBuildable = false;
					System.out.println("Finished a user-defined buildable.");
					currentDataFieldIndex++;
				}
				// If we weren't already defining a buildable, this custom element is an event.
				else
				{
					currentlyDefiningEvent = false;
					System.out.println("Finished a user-defined event.");
					// TODO: Utilize EventBuilder.recordEventToDeck() to generate a new event config
					// file with the given name.
					currentEventIndex++;
				}
			}
			// If we weren't already defining an event, we may have a problem.
			else
			{
				System.out.println("We shouldn't get here. If we do, ERROR!");
			}
		}
		else
		{
			switch (elementTag)
			{
				case INSTANT:
					// TODO: Fill out this switch case
					break;
				case MEDIA_PLAYBACK:
					if (generatedBuildable instanceof Data_Media)
					{
						int newLength = Integer.parseInt(characterData);
						((Data_Media) generatedBuildable).setPlayLength(newLength);
					}
					break;
				case MEDIA_SRC_FILE:
					if (generatedBuildable instanceof Data_Media)
					{
						((Data_Media) generatedBuildable).setMediaFile(new File(characterData));
					}
					break;
				case MEDIA_START:
					if (generatedBuildable instanceof Data_Media)
					{
						int newStart = Integer.parseInt(characterData);
						((Data_Media) generatedBuildable).setStartTime(newStart);
					}
					break;
				case MENU_OPTION:
					if (generatedBuildable instanceof Data_Menu)
					{
						((Data_Menu) generatedBuildable).addOption(characterData);
					}
					break;
				case MENU_SELECTED:
					if (generatedBuildable instanceof Data_Menu)
					{
						int newIndex = Integer.parseInt(characterData);
						((Data_Menu) generatedBuildable).setSelected(newIndex);
					}
					break;
				case PLOTLINE:
					// TODO: Fill out this switch case
					break;
				case SCRIPT:
					// TODO: Fill out this switch case
					break;
				case TEXT_FIELD:
					if (generatedBuildable instanceof Data_Text)
					{
						((Data_Text) generatedBuildable).setContent(characterData);
					}
					break;
				case TS_DIALOG_LINE:
					if (generatedBuildable instanceof Data_Transcript)
					{}
					break;
				case TS_LINE_ACTOR:
					if (generatedBuildable instanceof Data_Transcript)
					{
						System.out.println("Actor is " + characterData);
						// Index of this line is the last new dialog line added
						int index = ((Data_Transcript) generatedBuildable).length() - 1;
						System.out.println("Current index is " + index);
						((Data_Transcript) generatedBuildable).getLine(index).actor = characterData;
					}
					break;
				case TS_LINE_SPEECH:
					if (generatedBuildable instanceof Data_Transcript)
					{
						// Index of this line is the last new dialog line added
						int index = ((Data_Transcript) generatedBuildable).length() - 1;
						((Data_Transcript) generatedBuildable).getLine(index).dialog = characterData;
					}
					break;
				default:
					// This won't run because tags is fully enumerated here
					break;
			}
		}
	}

	public Script generateScript()
	{
		return generatedScript;
	}
}
