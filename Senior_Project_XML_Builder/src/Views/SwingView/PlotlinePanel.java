package Views.SwingView;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import Controls.I_Controller;
import Models.Plotline;
import Models.Script;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;

public class PlotlinePanel extends JPanel
{
	private Script scr;
	private I_Controller ctrl;
	private Plotline plt;
	
	PlotlinePanel(Script scr, I_Controller ctrl, int plotIndex)
	{
		this.scr = scr;
		this.ctrl = ctrl;
		plt = scr.getPlotLine(plotIndex);
		this.setBackground(Color.WHITE);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Dimension defaultSize = new Dimension(SwingView.WIDTH_OF_INSTANCE, SwingView.HEIGHT_OF_INSTANCE);
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setAlignmentX(LEFT_ALIGNMENT);
		horizontalBox.setAlignmentY(TOP_ALIGNMENT);
		horizontalBox.add(Box.createRigidArea(new Dimension(plt.startTime * SwingView.WIDTH_OF_INSTANCE - 1, 0)));
		for (int i = 0; i < plt.length(); i++)
		{
			JPanel instance = new JPanel();
			instance.setLayout(new BoxLayout(instance, BoxLayout.Y_AXIS));
			instance.setAlignmentX(LEFT_ALIGNMENT);
			instance.setAlignmentY(TOP_ALIGNMENT);
			instance.setMinimumSize(defaultSize);
			instance.setPreferredSize(defaultSize);
			instance.setBorder(new LineBorder(new Color(0, 0, 0)));
			if (plt.getInstance(i) != null)
			{
				instance.setBackground(Color.YELLOW);
			}
			else
			{
				instance.setBackground(Color.BLUE);
			}
			instance.add(Box.createVerticalGlue());
			horizontalBox.add(instance);
		}
		horizontalBox.add(Box.createHorizontalGlue());
		add(horizontalBox);
		add(Box.createHorizontalGlue());
		Dimension scriptSize = horizontalBox.getPreferredSize();
		if (defaultSize.width > scriptSize.width)
		{
			System.out.println("Using default size.");
			this.setMaximumSize(defaultSize);
			this.setMinimumSize(defaultSize);
			this.setPreferredSize(defaultSize);
		}
		else
		{
			System.out.println("Using plotline size.");
			this.setMaximumSize(scriptSize);
			this.setMinimumSize(scriptSize);
			this.setPreferredSize(scriptSize);
		}
	}
}
