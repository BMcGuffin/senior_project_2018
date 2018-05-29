package Views.SwingView;

import java.awt.EventQueue;
import java.util.Observable;
import javax.swing.JFrame;
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
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;

public class SwingView implements I_View
{
	private JFrame frmScriptBuilder;
	private I_Controller ctrl;
	private Script scr;
	private JScrollPane scrollPane;
	private JPanel plotlineDisplay;

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
		System.out.println("Got an update event for the GUI.");
		if (o instanceof Script)
		{
			plotlineDisplay.removeAll();
			System.out.println("This is a script event.");
			scr = (Script) o;
			System.out.println("Script contains " + scr.countPlotlines() + " plotlines.");
			for (Plotline plt : scr.plotlines)
			{
				plotlineDisplay.add(new PlotlinePanel(plt));
				System.out.println("Added a plotline panel.");
			}
			System.out.println("Finished updating.");
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
		frmScriptBuilder = new JFrame();
		frmScriptBuilder.setTitle("Script Builder");
		frmScriptBuilder.setBounds(0, 0, 1000, 700);
		frmScriptBuilder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		frmScriptBuilder.setJMenuBar(menuBar);
		// File
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnFile.add(mntmNew);
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnFile.add(mntmLoad);
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnFile.add(mntmSave);
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnFile.add(mntmSaveAs);
		JMenuItem mntmScriptPreferences = new JMenuItem("Script Preferences");
		mntmScriptPreferences.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnFile.add(mntmScriptPreferences);
		// Plotlines
		JMenu mnPlotlines = new JMenu("Plotlines");
		menuBar.add(mnPlotlines);
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnPlotlines.add(mntmAdd);
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnPlotlines.add(mntmRemove);
		JMenuItem mntmEdit = new JMenuItem("Edit");
		mntmEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnPlotlines.add(mntmEdit);
		// Events
		JMenu mnEvents = new JMenu("Events");
		menuBar.add(mnEvents);
		JMenuItem mntmCreatePrototype = new JMenuItem("Create Prototype");
		mntmCreatePrototype.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PrototypeView prototypePanel = new PrototypeView();
				prototypePanel.setVisible(true);
				// TODO Implement this method.
			}
		});
		mnEvents.add(mntmCreatePrototype);
		JMenuItem mntmEditPrototype = new JMenuItem("Edit Prototype");
		mntmEditPrototype.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnEvents.add(mntmEditPrototype);
		// Media
		JMenu mnMedia = new JMenu("Media");
		menuBar.add(mnMedia);
		JMenuItem mntmImportMedia = new JMenuItem("Import Media");
		mntmImportMedia.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Implement this method.
			}
		});
		mnMedia.add(mntmImportMedia);
		// Options
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
