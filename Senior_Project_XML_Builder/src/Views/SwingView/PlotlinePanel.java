package Views.SwingView;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import Models.Plotline;

public class PlotlinePanel extends JPanel
{
	PlotlinePanel(Plotline plt)
	{
		this.setBackground(Color.WHITE);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		Dimension defaultSize = new Dimension(10, 80);
		Dimension scriptSize = new Dimension(plt.script.getLength(), 80);
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
		this.setAlignmentX(LEFT_ALIGNMENT);
	}
}
