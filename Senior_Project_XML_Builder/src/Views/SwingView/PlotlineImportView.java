package Views.SwingView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Controls.I_Controller;
import Models.Script;

public class PlotlineImportView extends JFrame
{
	private JPanel contentPane;
	private Script scr;
	private I_Controller ctrl;


	/**
	 * Create the frame.
	 */
	public PlotlineImportView(Script s, I_Controller c)
	{
		scr = s;
		ctrl = c;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Import Plotlines");
	}
}
