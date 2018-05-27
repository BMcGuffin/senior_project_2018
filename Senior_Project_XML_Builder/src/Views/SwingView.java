package Views;

import java.awt.EventQueue;
import java.util.Observable;
import javax.swing.JFrame;
import Controls.I_Controller;
import Models.*;
import javafx.scene.layout.Border;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JScrollPane;

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
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		plotlineDisplay.add(new PlotlinePanel());
		

	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof Script)
		{
			scr = (Script) o;
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
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenu mnPlotlines = new JMenu("Plotlines");
		menuBar.add(mnPlotlines);
		JMenu mnEvents = new JMenu("Events");
		menuBar.add(mnEvents);
		JMenu mnMedia = new JMenu("Media");
		menuBar.add(mnMedia);
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
		
		plotlineDisplay = new JPanel(new GridLayout(0, 1));
	
		scrollPane = new JScrollPane(plotlineDisplay);
		frmScriptBuilder.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	private class PlotlinePanel extends JPanel
	{
		PlotlinePanel()
		{
			this.setBackground(Color.WHITE);
			this.setBorder(new LineBorder(new Color(0, 0, 0)));
			this.add(Box.createVerticalStrut(80));
			this.add(Box.createHorizontalStrut(2000));
		}
	}
}
