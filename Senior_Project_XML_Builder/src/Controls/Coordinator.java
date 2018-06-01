/*
 * This code was written as part of a senior project, for the
 * Computer Science department at California Polytechnic State
 * University, San Luis Obispo, in Winter/Spring 2018.
 * The author of the project is Bryan McGuffin: bmcguffi@calpoly.edu
 * Advisor is John Clements: aoeuclements@brinckerhoff.org
 */
package Controls;

import Models.*;
import java.io.File;
import java.util.List;

/**
 * The Controller portion of the MCV model. Accepts inputs from the view and
 * subsequently manipulates the model.
 *
 * @author Bryan McGuffin
 * @version Mar 10, 2018
 */
public class Coordinator implements I_Controller
{

	private Script sc;
	private EventBuilder builder;

	private int currentPlotIndex;

	private int currentInstIndex;

	private int currentEventIndex;

	private int currentDataFieldIndex;

	/**
	 * Constructor
	 * 
	 * @param script
	 */
	public Coordinator(Script script)
	{
		sc = script;
		builder = EventBuilder.getBuilder();
		currentPlotIndex = 0;

		currentInstIndex = 0;

		currentEventIndex = 0;

		currentDataFieldIndex = 0;
	}

	@Override
	public void setPlotNum(int newNum)
	{
		currentPlotIndex = newNum;
	}

	@Override
	public void setInstNum(int newNum)
	{
		currentInstIndex = newNum;
	}

	@Override
	public void setEvtNum(int newNum)
	{
		currentEventIndex = newNum;
	}

	@Override
	public void setDataNum(int newNum)
	{
		currentDataFieldIndex = newNum;
	}

	@Override
	public void readCommand(Command cmd, List<String> args)
	{
		int len = args.size();

		switch (cmd)
		{
			case NOTHING:
				break;

			case ADD_PLOTLINE:
				String title;
				int startTime;

				if (len == 2)
				{
					title = args.get(0);
					try
					{
						startTime = Integer.parseInt(args.get(1));
						sc.addPlotline(title, startTime);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;

			case REMOVE_PLOTLINE:
				if (len == 1)
				{
					try
					{
						int index = Integer.parseInt(args.get(0));
						sc.removePlotline(index);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case QUIT:
				System.out.println("Goodbye!");
				System.exit(0);
			case LOAD_FILE:
				if (len == 1)
				{
					try
					{
						File newFile = new File(args.get(0));
						sc.loadFromFile(newFile);
						sc.saveFile = newFile;
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case NEW_FILE:
				if (len == 1)
				{
					try
					{
						int decision = Integer.parseInt(args.get(0));
						if (decision == 1)
						{
							sc.saveToFile();
						}
						sc.newFile();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				break;
			case CHANGE_SCRIPT_TITLE:
				if (len == 1)
				{
					sc.scriptTitle = args.get(0);
				}
				break;
			case CHANGE_PLOTLINE_TITLE:
				if (len == 1)
				{
					try
					{
						sc.getPlotLine(currentPlotIndex).title = args.get(0);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case SAVE_AS:
				if (len == 1)
				{
					try
					{
						String filename = args.get(0);
						if (!filename.endsWith(".xml"))
						{
							filename += ".xml";
						}
						sc.saveFile = new File(filename);
						sc.saveFile.createNewFile();
						sc.saveToFile();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case SAVE:
				sc.saveToFile();
				break;
			case ADD_EVENT:
				if (len == 2)
				{
					try
					{
						int time = Integer.parseInt(args.get(0));
						Event evt = builder.generateEvent(args.get(1));
						if (sc.getPlotLine(currentPlotIndex).getInstance(time) == null)
						{
							sc.getPlotLine(currentPlotIndex).addInstance(time);
						}
						sc.getPlotLine(currentPlotIndex).getInstance(time).addEvent(evt);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					sc.spark();
					// TODO remove dependency on spark
				}
				break;
			case RELOCATE_EVENT:
				if (len == 2)
				{
					try
					{
						int pos = Integer.parseInt(args.get(0));
						int to = Integer.parseInt(args.get(1));
						Instance inst = sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex);
						Event evt = inst.events.get(pos);
						inst.removeEvent(evt);
						if (sc.getPlotLine(currentPlotIndex).getInstance(to) == null)
						{
							sc.getPlotLine(currentPlotIndex).addInstance(to);
						}
						sc.getPlotLine(currentPlotIndex).getInstance(to).addEvent(evt);
						if (inst.events.isEmpty())
						{
							sc.getPlotLine(currentPlotIndex).removeInstance(currentInstIndex);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case REMOVE_EVENT:
				if (len == 1)
				{
					try
					{
						int evt = Integer.parseInt(args.get(0));
						Instance inst = sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex);
						inst.removeEvent(inst.events.get(evt));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					sc.spark();
					// TODO remove dependency on spark
				}
				break;
			case CHANGE_SCRIPT_DESCRIPTION:
				if (len == 1)
				{
					sc.description = args.get(0);
				}
				break;
			case CHANGE_PLOTLINE_DESCRIPTION:
				if (len == 1)
				{
					try
					{
						sc.getPlotLine(currentPlotIndex).description = args.get(0);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case CHANGE_PLOTLINE_START:
				if (len == 1)
				{
					try
					{
						int newStart = Integer.parseInt(args.get(0));
						sc.getPlotLine(currentPlotIndex).startTime = newStart;
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TEXTFIELD_REPLACE_TEXT:
				if (len == 1)
				{
					try
					{
						Data_Text dt = (Data_Text) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						dt.setContent(args.get(0));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_MEDIA_CHANGE_FILE:
				if (len == 1)
				{
					try
					{
						Data_Media dm = (Data_Media) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						dm.setMediaFile(new File(args.get(0)));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_MEDIA_PLAYBACK_LENGTH:
				if (len == 1)
				{
					try
					{
						Data_Media dm = (Data_Media) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int newLen = Integer.parseInt(args.get(0));
						dm.setPlayLength(newLen);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_MEDIA_START_TIME:
				if (len == 1)
				{
					try
					{
						Data_Media dm = (Data_Media) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int newStart = Integer.parseInt(args.get(0));
						dm.setStartTime(newStart);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_MENU_OPTION_SELECT:
				if (len == 1)
				{
					try
					{
						Data_Menu du = (Data_Menu) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int index = Integer.parseInt(args.get(0));
						du.setSelected(index);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TRANSCRIPT_LINE_BACK:
				if (len == 1)
				{
					try
					{
						Data_Transcript dt = (Data_Transcript) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int index = Integer.parseInt(args.get(0));
						dt.moveLineUp(index);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TRANSCRIPT_LINE_FORWARD:
				if (len == 1)
				{
					try
					{
						Data_Transcript dt = (Data_Transcript) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int index = Integer.parseInt(args.get(0));
						dt.moveLineDown(index);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TRANSCRIPT_NEW_LINE:
				if (len == 2)
				{
					try
					{
						Data_Transcript dt = (Data_Transcript) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						dt.addLine(args.get(0), args.get(1));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TRANSCRIPT_REMOVE_LINE:
				if (len == 1)
				{
					try
					{
						Data_Transcript dt = (Data_Transcript) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int index = Integer.parseInt(args.get(0));
						dt.removeLine(index);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case DATA_TRANSCRIPT_EDIT_LINE:
				if (len == 3)
				{
					try
					{
						Data_Transcript dt = (Data_Transcript) sc.getPlotLine(currentPlotIndex).getInstance(currentInstIndex)
								.getEvent(currentEventIndex).getElement(currentDataFieldIndex);
						int index = Integer.parseInt(args.get(0));
						Line l = dt.getLine(index);
						l.actor = args.get(1);
						l.dialog = args.get(2);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
		}

	}

}
