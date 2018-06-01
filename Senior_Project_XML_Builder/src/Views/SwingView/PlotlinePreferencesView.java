package Views.SwingView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controls.I_Controller;
import Models.Plotline;
import Models.Script;
import javafx.scene.paint.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.Box;
import java.awt.Component;

public class PlotlinePreferencesView extends JFrame
{
	private Plotline plt;
	private Script scr;
	private I_Controller ctrl;
	private JPanel contentPane;
	private JTextField plotNameField;
	private JTextField plotColorField;
	private JTextField plotStartTimeField;

	/**
	 * Create the frame.
	 */
	public PlotlinePreferencesView(Script scr, I_Controller ctrl, int plotIndex)
	{
		this.scr = scr;
		this.ctrl = ctrl;
		plt = scr.getPlotLine(plotIndex);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox);
		JPanel panel = new JPanel();
		verticalBox.add(panel);
		JLabel lblPlotlineName = new JLabel("Plotline Name");
		panel.add(lblPlotlineName);
		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);
		plotNameField = new JTextField();
		plotNameField.setText(plt.title);
		// TODO Make plot name field call to the controller when it changes
		panel.add(plotNameField);
		plotNameField.setColumns(10);
		JPanel panel_1 = new JPanel();
		verticalBox.add(panel_1);
		JLabel lblDescription = new JLabel("Description");
		panel_1.add(lblDescription);
		JTextPane plotDescriptionPane = new JTextPane();
		plotDescriptionPane.setText(plt.description);
		// TODO Make plot description field call to the controller when it changes
		// TODO Make plot description field editable
		panel_1.add(plotDescriptionPane);
		JPanel panel_2 = new JPanel();
		verticalBox.add(panel_2);
		JLabel lblEventCount = new JLabel("Event Count");
		lblEventCount.setText(plt.totalEventCount() + " events");
		panel_2.add(lblEventCount);
		JPanel panel_3 = new JPanel();
		verticalBox.add(panel_3);
		JLabel lblEventTypeCount = new JLabel("Event Type Count");
		panel_3.add(lblEventTypeCount);
		JPanel panel_4 = new JPanel();
		verticalBox.add(panel_4);
		JLabel lblColor = new JLabel("Color");
		panel_4.add(lblColor);
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panel_4.add(horizontalGlue_1);
		plotColorField = new JTextField();
		// FUTURE Add color as a thing, or get rid of this option
		panel_4.add(plotColorField);
		plotColorField.setColumns(10);
		JPanel panel_5 = new JPanel();
		verticalBox.add(panel_5);
		JLabel lblPlotlineLength = new JLabel("Plotline Length");
		// TODO Format plotline length field
		panel_5.add(lblPlotlineLength);
		JPanel panel_6 = new JPanel();
		verticalBox.add(panel_6);
		JLabel lblStartTime = new JLabel("Start Time");
		panel_6.add(lblStartTime);
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panel_6.add(horizontalGlue_2);
		plotStartTimeField = new JTextField();
		panel_6.add(plotStartTimeField);
		// TODO Format start time field
		// TODO Make this field call to the controller when it changes
		plotStartTimeField.setColumns(10);
		JPanel panel_7 = new JPanel();
		verticalBox.add(panel_7);
		JLabel lblEndTime = new JLabel("End Time");
		// TODO Format this field
		panel_7.add(lblEndTime);
		setTitle("Plotline Preferences");
	}
}
