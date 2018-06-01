package Views.SwingView;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JFrame;
import Controls.Command;
import Controls.I_Controller;
import Models.*;
import Views.I_View;
import javafx.scene.layout.Border;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SwingView implements I_View
{
	private JFrame frmScriptBuilder;
	private I_Controller ctrl;
	private Script scr;
	private JScrollPane scrollPane;
	private JPanel plotlineDisplay;
	// Constants for GUI design
	public static int WIDTH_OF_INSTANCE = 10;
	public static int HEIGHT_OF_INSTANCE = 80;

	/**
	 * Create the application.
	 */
	public SwingView(I_Controller c)
	{
		ctrl = c;
		initialize();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof Script)
		{
			plotlineDisplay.removeAll();
			scr = (Script) o;
			for (int i = 0; i < scr.plotlines.size(); i++)
			{
				Plotline plt = scr.getPlotLine(i);
				PlotlinePanel pp = new PlotlinePanel(scr, ctrl, i);
				pp.addMouseListener(new MouseListener()
				{
					@Override
					public void mouseReleased(MouseEvent e)
					{}

					@Override
					public void mousePressed(MouseEvent e)
					{}

					@Override
					public void mouseExited(MouseEvent e)
					{}

					@Override
					public void mouseEntered(MouseEvent e)
					{}

					@Override
					public void mouseClicked(MouseEvent e)
					{
						if (e.getClickCount() == 2)
						{
							int index = scr.plotlines.indexOf(plt);
							System.out.println("Index of plot " + plt.title + " is " + index);
							PlotlineEditorView pltEditor = new PlotlineEditorView(scr, ctrl, index);
							scr.addObserver(pltEditor);
							pltEditor.setVisible(true);
						}
					}
				});
				plotlineDisplay.add(pp);
			}
			plotlineDisplay.revalidate();
			plotlineDisplay.repaint();
		}
	}

	@Override
	public void setVisible(boolean visible)
	{
		frmScriptBuilder.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		// TODO Add timestamp
		// FUTURE Add zoom bar
		// TODO Add Plotline Options panel
		// TODO Add info panel
		frmScriptBuilder = new JFrame();
		frmScriptBuilder.setTitle("Script Builder");
		frmScriptBuilder.setBounds(0, 0, 1000, 700);
		frmScriptBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		frmScriptBuilder.setJMenuBar(menuBar);
		/*********************************************************/
		// File
		/*********************************************************/
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		// File > New
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int saveThis = 0;
				int result = JOptionPane.showConfirmDialog(frmScriptBuilder, "Do you want to save the current Script?");
				if (result == JOptionPane.OK_OPTION)
				{
					saveThis = 1;
					if (scr.saveFile == null)
					{
						JFileChooser jfc = new JFileChooser();
						ArrayList<String> arg = new ArrayList<>();
						if (jfc.showSaveDialog(frmScriptBuilder) == JFileChooser.APPROVE_OPTION)
						{
							arg.add(jfc.getSelectedFile().getPath());
							ctrl.readCommand(Command.SAVE_AS, arg);
						}
					}
				}
				ArrayList<String> args = new ArrayList<>();
				args.add("" + saveThis);
				ctrl.readCommand(Command.NEW_FILE, args);
			}
		});
		mnFile.add(mntmNew);
		// File > Load
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser jfc = new JFileChooser();
				ArrayList<String> args = new ArrayList<>();
				if (jfc.showOpenDialog(frmScriptBuilder) == JFileChooser.APPROVE_OPTION)
				{
					args.add(jfc.getSelectedFile().getPath());
					ctrl.readCommand(Command.LOAD_FILE, args);
				}
			}
		});
		mnFile.add(mntmLoad);
		// File > Save
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (scr.saveFile != null)
				{
					ctrl.readCommand(Command.SAVE, new ArrayList<String>());
				}
				else
				{
					JFileChooser jfc = new JFileChooser();
					ArrayList<String> args = new ArrayList<>();
					if (jfc.showSaveDialog(frmScriptBuilder) == JFileChooser.APPROVE_OPTION)
					{
						args.add(jfc.getSelectedFile().getPath());
						ctrl.readCommand(Command.SAVE_AS, args);
					}
				}
			}
		});
		mnFile.add(mntmSave);
		// File > Save As
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser jfc = new JFileChooser();
				ArrayList<String> args = new ArrayList<>();
				if (jfc.showSaveDialog(frmScriptBuilder) == JFileChooser.APPROVE_OPTION)
				{
					args.add(jfc.getSelectedFile().getPath());
					ctrl.readCommand(Command.SAVE_AS, args);
				}
			}
		});
		mnFile.add(mntmSaveAs);
		// File > Script Preferences
		JMenuItem mntmScriptPreferences = new JMenuItem("Script Preferences");
		mntmScriptPreferences.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ScriptPreferencesView scriptPrefs = new ScriptPreferencesView(scr, ctrl);
				scriptPrefs.setVisible(true);
			}
		});
		mnFile.add(mntmScriptPreferences);
		/*********************************************************/
		// Plotlines
		/*********************************************************/
		JMenu mnPlotlines = new JMenu("Plotlines");
		menuBar.add(mnPlotlines);
		// Plotlines > Add
		// FIXME Plotlines added at times other than 0 don't appear until events added.
		JMenuItem mntmAdd = new JMenuItem("Add New");
		mntmAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<String> args = new ArrayList<>();
				String title = JOptionPane.showInputDialog("Name of new Plotline:");
				if (title != null)
				{
					args.add(title);
					String time = JOptionPane.showInputDialog("Start time (in seconds):");
					if (time != null)
					{
						try
						{
							args.add("" + Integer.parseInt(time));
							ctrl.readCommand(Command.ADD_PLOTLINE, args);
						}
						catch (Exception ex)
						{
							System.out.println("Failed to parse number.");
						}
					}
				}
			}
		});
		mnPlotlines.add(mntmAdd);
		// Plotlines > Remove
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Object result = JOptionPane.showInputDialog(frmScriptBuilder, "Select Plotline:", "Delete Plotline",
						JOptionPane.PLAIN_MESSAGE, null, scr.plotlines.toArray(), scr.getPlotLine(0));
				if (result instanceof Plotline)
				{
					Plotline plot = (Plotline) result;
					int index = scr.plotlines.indexOf(plot);
					int response = JOptionPane.showConfirmDialog(frmScriptBuilder,
							"Are you sure you want to delete plotline " + plot.title + "?", "Confirm Delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (response == JOptionPane.OK_OPTION && index != -1)
					{
						ArrayList<String> args = new ArrayList<>();
						args.add("" + index);
						ctrl.readCommand(Command.REMOVE_PLOTLINE, args);
					}
				}
			}
		});
		mnPlotlines.add(mntmRemove);
		// Plotlines > Import Existing
		JMenuItem mntmImportExisting = new JMenuItem("Import Existing");
		mntmImportExisting.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PlotlineImportView pltImport = new PlotlineImportView(scr, ctrl);
				pltImport.setVisible(true);
			}
		});
		mnPlotlines.add(mntmImportExisting);
		// Plotlines > Edit
		JMenuItem mntmEdit = new JMenuItem("Edit");
		mntmEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Object result = JOptionPane.showInputDialog(frmScriptBuilder, "Select Plotline:", "Edit Plotline",
						JOptionPane.PLAIN_MESSAGE, null, scr.plotlines.toArray(), scr.getPlotLine(0));
				if (result instanceof Plotline)
				{
					Plotline plot = (Plotline) result;
					int index = scr.plotlines.indexOf(plot);
					System.out.println("Index of plot " + plot.title + " is " + index);
					PlotlineEditorView pltEditor = new PlotlineEditorView(scr, ctrl, index);
					scr.addObserver(pltEditor);
					pltEditor.setVisible(true);
				}
			}
		});
		mnPlotlines.add(mntmEdit);
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
		frmScriptBuilder.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel topPanel = new JPanel();
		frmScriptBuilder.getContentPane().add(topPanel, BorderLayout.NORTH);
		Component verticalStrut = Box.createVerticalStrut(80);
		topPanel.add(verticalStrut);
		JPanel sidePanel = new JPanel();
		frmScriptBuilder.getContentPane().add(sidePanel, BorderLayout.WEST);
		Component horizontalStrut = Box.createHorizontalStrut(80);
		sidePanel.add(horizontalStrut);
		plotlineDisplay = new JPanel();
		BoxLayout pltLayout = new BoxLayout(plotlineDisplay, BoxLayout.Y_AXIS);
		plotlineDisplay.setLayout(pltLayout);
		scrollPane = new JScrollPane(plotlineDisplay);
		frmScriptBuilder.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
}
