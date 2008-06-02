package info.reflectionsofmind.musicanalyzer.gui.actions;

import info.reflectionsofmind.musicanalyzer.gui.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

public final class StartPhysicsAction extends AbstractAction
{
	private final MainFrame frame;

	public StartPhysicsAction(final MainFrame frame)
	{
		super("Start physics");
		this.frame = frame;
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		this.frame.startPausePhysicsButton.setAction(this.frame.pausePhysicsAction);
		frame.startCrawlingAction.setEnabled(false);
		this.frame.physicsRunning = true;

		new Thread()
		{
			@Override
			public void run()
			{
				final Object lock = new Object();

				while (StartPhysicsAction.this.frame.physicsRunning)
				{
					StartPhysicsAction.this.frame.physics.run();

					synchronized (lock)
					{
						SwingUtilities.invokeLater(new Runnable()
						{
							@Override
							public void run()
							{
								synchronized (lock)
								{
									StartPhysicsAction.this.frame.drawingPanel.repaint();
									lock.notify();
								}
							}
						});

						try
						{
							lock.wait();
						}
						catch (final InterruptedException exception)
						{
							throw new RuntimeException();
						}
					}
				}

				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						frame.startPausePhysicsButton.setAction(frame.startPhysicsAction);
						frame.startCrawlingAction.setEnabled(true);
					}
				});
			}
		}.start();
	}
}