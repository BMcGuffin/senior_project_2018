package Views.SwingView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import Controls.Command;
import Controls.I_Controller;
import Models.*;

public class EventEditorView extends JFrame
{
	private Script scr;
	private I_Controller ctrl;
	private int plotIndex;
	private int instIndex;
	private int evtIndex;
	private JPanel contentPane;
	private Event evt;

	/**
	 * Create the frame.
	 */
	public EventEditorView(Script scr, I_Controller ctrl, int plotindex, int instIndex, int evtIndex)
	{
		this.scr = scr;
		this.ctrl = ctrl;
		this.plotIndex = plotindex;
		this.instIndex = instIndex;
		this.evtIndex = evtIndex;
		evt = scr.getPlotLine(plotindex).getInstance(instIndex).getEvent(evtIndex);
		setBounds(100, 100, 750, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		setTitle(evt.eventType + " event at time " + instIndex);
		initialize();
	}

	// TODO Structure this window so it looks cleaner.
	// PRIORITY Connect editable fields to control and script.
	public void initialize()
	{
		for (int buildableIndex = 0; buildableIndex < evt.fieldCount(); buildableIndex++)
		{
			JPanel panel = new JPanel();
			Buildable b = evt.getElement(buildableIndex);
			if (b instanceof Data_Menu)
			{
				Data_Menu bMenu = (Data_Menu) b;
				JLabel menuLabel = new JLabel(bMenu.elementName);
				JComboBox<String> menu = new JComboBox<>();
				
				for (String item : bMenu.getElements())
				{
					menu.addItem(item);
				}
				menu.setSelectedIndex(bMenu.getSelectedIndex());
				//TODO Add a listener to this combo box which tells the controller to update the menu model
				panel.add(menuLabel);
				panel.add(menu);
			}
			else if (b instanceof Data_Media)
			{
				Data_Media bMedia = (Data_Media) b;
				JLabel mediaLabel = new JLabel(bMedia.elementName);
				JTextField fileNameField = new JTextField(bMedia.getFileName());
				JButton browseButton = new JButton("Browse...");
				browseButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// TODO Implement file browser window.
					}
				});
				//TODO Add a listener to file name field which tells the controller to update the media model
				JLabel startLabel = new JLabel("Start time:");
				JTextField startField = new JTextField(bMedia.getStartTime());
				//TODO Add a listener to start field field which tells the controller to update the media model
				JLabel playbackLabel = new JLabel("Playback length:");
				JTextField playBackField = new JTextField(bMedia.getPlayLength());
				//TODO Add a listener to playback field which tells the controller to update the media model
				panel.add(mediaLabel);
				panel.add(fileNameField);
				panel.add(browseButton);
				panel.add(startLabel);
				panel.add(startField);
				panel.add(playbackLabel);
				panel.add(playBackField);
			}
			else if (b instanceof Data_Transcript)
			{
				int bIndex = buildableIndex;
				Data_Transcript bTranscript = (Data_Transcript) b;
				JLabel transcriptLabel = new JLabel(bTranscript.elementName);
				TableModel tm = new TableModel()
				{
					@Override
					public void setValueAt(Object aValue, int rowIndex, int columnIndex)
					{
						ArrayList<String> args = new ArrayList<>();
						args.add("" + plotIndex);
						args.add("" + instIndex);
						args.add("" + evtIndex);
						args.add("" + bIndex);
						args.add("" + rowIndex);
						if (columnIndex == 0)
						{
							args.add((String) aValue);
							args.add(bTranscript.getLine(rowIndex).dialog);
						}
						else
						{
							args.add(bTranscript.getLine(rowIndex).actor);
							args.add((String) aValue);
						}
						ctrl.readCommand(Command.DATA_TRANSCRIPT_EDIT_LINE, args);
					}

					@Override
					public void removeTableModelListener(TableModelListener l)
					{}

					@Override
					public boolean isCellEditable(int rowIndex, int columnIndex)
					{
						return true;
					}

					@Override
					public Object getValueAt(int rowIndex, int columnIndex)
					{
						if (columnIndex == 0)
						{
							return bTranscript.getLine(rowIndex).actor;
						}
						else
						{
							return bTranscript.getLine(rowIndex).dialog;
						}
					}

					@Override
					public int getRowCount()
					{
						return bTranscript.length();
					}

					@Override
					public String getColumnName(int columnIndex)
					{
						if (columnIndex == 0)
						{
							return "Actors";
						}
						else if (columnIndex == 1)
						{
							return "Lines";
						}
						return "Undefined";
					}

					@Override
					public int getColumnCount()
					{
						return 2;
					}

					@Override
					public Class<?> getColumnClass(int columnIndex)
					{
						return String.class;
					}

					@Override
					public void addTableModelListener(TableModelListener l)
					{}
				};
				//TODO Add listeners to table model which tells the controller to update the transcript model
				JTable transcriptTable = new JTable(tm);
				panel.add(transcriptLabel);
				panel.add(transcriptTable);
			}
			else if (b instanceof Data_Text)
			{
				Data_Text bText = (Data_Text) b;
				JLabel textLabel = new JLabel(bText.elementName);
				JTextArea text = new JTextArea(bText.getContent());
				//TODO Add a listener to text field which tells the controller to update the text model
				panel.add(textLabel);
				panel.add(text);
			}
			contentPane.add(panel);
		}
	}
}
