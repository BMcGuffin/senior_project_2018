package Views.SwingView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import Controls.Command;
import Controls.I_Controller;
import Models.*;

public class PlotlineEditorView extends JFrame implements Observer
{
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JPanel plotlineDisplay;
	private Script scr;
	private int plotIndex;
	private Plotline plt;
	private I_Controller ctrl;
	private EventBuilder eb;
	// GUI constants
	public static int WIDTH_OF_INSTANCE = 20;
	public static int HEIGHT_OF_MEDIA_INSTANCE = 80;
	public static int HEIGHT_OF_INSTANCE = 100;

	/**
	 * Create the frame.
	 */
	public PlotlineEditorView(Script script, I_Controller control, int plotindex)
	{
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scr = script;
		this.plotIndex = plotindex;
		plt = scr.getPlotLine(plotIndex);
		ctrl = control;
		scr.addObserver(this);
		setBounds(10, 10, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Edit Plotline: " + plt.title);
		initialize();
		eb = EventBuilder.getBuilder();
		update(scr, null);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("Got an update event for the plotline editor.");
		if (o instanceof Script)
		{
			scr = (Script) o;
			plt = scr.getPlotLine(plotIndex);
			contentPane.removeAll();
			contentPane.add(new PlotlineEventsPanel(scr, ctrl, plotIndex));
			// Reset the "add events" sub-menu
			mnAddEvent.removeAll();
			ArrayList<String> eventTypes = eb.viableEventTypes();
			for (String type : eventTypes)
			{
				JMenuItem menuItem = new JMenuItem(type);
				menuItem.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						ArrayList<String> args = new ArrayList<>();
						String result = JOptionPane.showInputDialog(contentPane,
								"Enter time (in seconds) for new event:");
						if (result != null)
						{
							try
							{
								args.add("" + plotIndex);
								args.add("" + Integer.parseInt(result));
								args.add(menuItem.getText());
								ctrl.readCommand(Command.ADD_EVENT, args);
							}
							catch (Exception ex)
							{
								System.out.println("Failed to parse number.");
							}
						}
					}
				});
				mnAddEvent.add(menuItem);
			}
			contentPane.revalidate();
			contentPane.repaint();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// TODO Add relative and absolute timestamps
		// TODO Add "add time" buttons
		// FUTURE Add zoom bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		/*********************************************************/
		// File
		/*********************************************************/
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		/*
		 * // File > Save JMenuItem mntmSave = new JMenuItem("Save");
		 * mntmSave.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { } });
		 * mnFile.add(mntmSave); // File > Save As JMenuItem mntmSaveAs = new
		 * JMenuItem("Save As"); mntmSaveAs.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { } });
		 * mnFile.add(mntmSaveAs);
		 */
		// File > Plotline Preferences
		JMenuItem mntmPlotlinePreferences = new JMenuItem("Plotline Preferences");
		mntmPlotlinePreferences.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PlotlinePreferencesView plotPrefs = new PlotlinePreferencesView(scr, ctrl, plotIndex);
				plotPrefs.setVisible(true);
			}
		});
		mnFile.add(mntmPlotlinePreferences);
		/*********************************************************/
		// Events
		/*********************************************************/
		JMenu mnEvents = new JMenu("Events");
		menuBar.add(mnEvents);
		// Events > Create Prototype
		JMenuItem mntmCreatePrototype = new JMenuItem("Create Prototype");
		mntmCreatePrototype.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PrototypeView prototypePanel = new PrototypeView();
				prototypePanel.setVisible(true);
			}
		});
		mnEvents.add(mntmCreatePrototype);
		// Events > Edit Prototype
		JMenuItem mntmEditPrototype = new JMenuItem("Edit Prototype");
		mntmEditPrototype.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// FUTURE Implement prototype editing.
			}
		});
		mnEvents.add(mntmEditPrototype);
		// Events > Import Event Type
		JMenuItem mntmImportEventType = new JMenuItem("Import Event Type");
		mntmImportEventType.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ImportEventsView importEvents = new ImportEventsView();
				importEvents.setVisible(true);
			}
		});
		mnEvents.add(mntmImportEventType);
		// Events > Add Event
		mnAddEvent = new JMenu("Add Event");
		mnEvents.add(mnAddEvent);
		/*********************************************************/
		// Media
		/*********************************************************/
		JMenu mnMedia = new JMenu("Media");
		menuBar.add(mnMedia);
		// Media > Import Media
		JMenuItem mntmImportMedia = new JMenuItem("Import Media");
		mntmImportMedia.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// FUTURE Implement media importing.
			}
		});
		mnMedia.add(mntmImportMedia);
		/*********************************************************/
		// Options
		/*********************************************************/
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel topPanel = new JPanel();
		getContentPane().add(topPanel, BorderLayout.NORTH);
		Component verticalStrut = Box.createVerticalStrut(80);
		topPanel.add(verticalStrut);
		contentPane = new JPanel();
		BoxLayout pltLayout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
		contentPane.setLayout(pltLayout);
		scrollPane = new JScrollPane(contentPane);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	public void addEvent(String eventType)
	{}

	private JMenu mnAddEvent;
}
