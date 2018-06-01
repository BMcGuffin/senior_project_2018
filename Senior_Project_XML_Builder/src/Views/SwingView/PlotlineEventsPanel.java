package Views.SwingView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import Controls.I_Controller;
import Models.*;

public class PlotlineEventsPanel extends JPanel
{
	Plotline plt;
	Script scr;
	I_Controller ctrl;

	/**
	 * Create the panel.
	 */
	public PlotlineEventsPanel(Script scr, I_Controller ctrl, int plotIndex)
	{
		// FUTURE Add copy/paste mechanics for events
		this.scr = scr;
		this.ctrl = ctrl;
		plt = scr.getPlotLine(plotIndex);
		this.setBackground(Color.WHITE);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setAlignmentY(TOP_ALIGNMENT);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Dimension defaultSize = new Dimension(PlotlineEditorView.WIDTH_OF_INSTANCE,
				PlotlineEditorView.HEIGHT_OF_INSTANCE);
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setAlignmentX(LEFT_ALIGNMENT);
		horizontalBox.setAlignmentY(TOP_ALIGNMENT);
		int greatestNumberOfEvents = 0;
		for (int i = 0; i < plt.length(); i++)
		{
			JPanel instance = new JPanel();
			instance.setLayout(new BoxLayout(instance, BoxLayout.Y_AXIS));
			instance.setAlignmentX(LEFT_ALIGNMENT);
			instance.setAlignmentY(TOP_ALIGNMENT);
			instance.setMinimumSize(defaultSize);
			instance.setPreferredSize(defaultSize);
			instance.add(Box.createVerticalGlue());
			if (plt.getInstance(i) != null)
			{
				int eventsHere = 0;
				for (Event evt : plt.getInstance(i).events)
				{
					int instIndex = i;
					int evtIndex = eventsHere++;
					EventIcon icon = new EventIcon(evt);
					icon.addMouseListener(new MouseListener()
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
							if (e.getSource() instanceof EventIcon)
							{
								EventIcon evt = ((EventIcon) e.getSource());
								EventEditorView editor = new EventEditorView(scr, ctrl, plotIndex, instIndex, evtIndex);
								editor.setVisible(true);
							}
						}
					});
					instance.add(icon);
					if (eventsHere > greatestNumberOfEvents)
					{
						greatestNumberOfEvents = eventsHere;
					}
				}
			}
			horizontalBox.add(instance);
		}
		horizontalBox.add(Box.createHorizontalGlue());
		add(horizontalBox);
		add(Box.createHorizontalGlue());
		Dimension scriptSize = new Dimension(horizontalBox.getPreferredSize().width,
				greatestNumberOfEvents * PlotlineEditorView.WIDTH_OF_INSTANCE);
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

	private class EventIcon extends JPanel
	{
		public Event event;

		public EventIcon(Event evt)
		{
			event = evt;
			setBorder(new LineBorder(Color.BLACK));
			Dimension bounds = new Dimension(PlotlineEditorView.WIDTH_OF_INSTANCE,
					PlotlineEditorView.WIDTH_OF_INSTANCE);
			setBounds(new Rectangle(bounds));
			setMinimumSize(bounds);
			setMaximumSize(bounds);
			setPreferredSize(bounds);
		}
	}
}