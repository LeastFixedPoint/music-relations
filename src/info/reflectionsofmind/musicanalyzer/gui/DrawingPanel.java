package info.reflectionsofmind.musicanalyzer.gui;

import info.reflectionsofmind.musicanalyzer.Artist;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class DrawingPanel extends JPanel
{
	private static final int RADIUS = 4;
	private static final double SCALE = 1.0;

	private final MainFrame frame;
	public boolean artistPickedUp = false;

	public DrawingPanel(final MainFrame frame)
	{
		this.frame = frame;

		final MouseInputListener listener = new MouseInputListener();

		addMouseListener(listener);
		addMouseMotionListener(listener);

		setOpaque(false);
	}

	public PhysObj<Artist> getObjectAt(final Point point)
	{
		for (final PhysObj<Artist> physObj : this.frame.physics.getObjects())
		{
			if (distance(translate(physObj.point), point) < RADIUS)
			{
				return physObj;
			}
		}

		return null;
	}

	@Override
	public void paint(final Graphics graphics)
	{
		final Graphics2D g2d = (Graphics2D) graphics.create();

		g2d.setBackground(new Color(222, 222, 128));

		for (final PhysObj<Artist> physObj : this.frame.physics.getObjects())
		{
			final Point artistPoint = translate(physObj.point);

			g2d.drawOval(artistPoint.x - RADIUS, artistPoint.y - RADIUS, RADIUS * 2, RADIUS * 2);

			if (physObj == this.frame.highlightedObject)
			{
				final Rectangle2D rect = graphics.getFontMetrics().getStringBounds( //
						this.frame.highlightedObject.object.getName(), graphics);

				g2d.clearRect( //
						artistPoint.x + (int) rect.getX() - 2 - (int) (rect.getWidth() / 2), //
						artistPoint.y + (int) rect.getY() - RADIUS - 6, //
						(int) rect.getWidth() + 2, (int) rect.getHeight());

				g2d.drawRect( //
						artistPoint.x + (int) rect.getX() - 2 - (int) (rect.getWidth() / 2), //
						artistPoint.y + (int) rect.getY() - RADIUS - 6, //
						(int) rect.getWidth() + 2, (int) rect.getHeight());

				g2d.drawString(this.frame.highlightedObject.object.getName(), //
						artistPoint.x - (int) (rect.getWidth() / 2), //
						artistPoint.y - RADIUS - 6);

				g2d.drawRect(artistPoint.x - RADIUS - 2, artistPoint.y - RADIUS - 2, RADIUS * 2 + 4, RADIUS * 2 + 4);
			}
		}
	}

	private Point translate(final DP artistCoordinates)
	{
		return new Point( //
				getWidth() / 2 + (int) (SCALE * artistCoordinates.x), //
				getHeight() / 2 - (int) (SCALE * artistCoordinates.y));
	}

	private DP translate(final Point mouseCoordinates)
	{
		return new DP( //
				(mouseCoordinates.getX() - getWidth() / 2) / SCALE, //
				(getHeight() / 2 - mouseCoordinates.getY()) / SCALE);
	}

	private static double distance(final Point p1, final Point p2)
	{
		final double dx = p1.x - p2.x;
		final double dy = p1.y - p2.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	private class MouseInputListener extends MouseInputAdapter
	{
		@Override
		public void mouseClicked(final MouseEvent event)
		{
			if (DrawingPanel.this.frame.physicsRunning)
			{
				return;
			}

			if (DrawingPanel.this.frame.highlightedObject != null)
			{
				DrawingPanel.this.artistPickedUp = !DrawingPanel.this.artistPickedUp;
				DrawingPanel.this.frame.startPhysicsAction.setEnabled(!DrawingPanel.this.artistPickedUp);
			}

			repaint();
		}

		@Override
		public void mouseMoved(final MouseEvent event)
		{
			if (DrawingPanel.this.artistPickedUp)
			{
				DrawingPanel.this.frame.highlightedObject.point = translate(event.getPoint());
			}
			else
			{
				DrawingPanel.this.frame.highlightedObject = getObjectAt(event.getPoint());
			}

			repaint();
		}
	}
}
