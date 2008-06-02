package info.reflectionsofmind.musicanalyzer.gui.actions;

import info.reflectionsofmind.musicanalyzer.Application;
import info.reflectionsofmind.musicanalyzer.Artist;
import info.reflectionsofmind.musicanalyzer.Crawler;
import info.reflectionsofmind.musicanalyzer.Database;
import info.reflectionsofmind.musicanalyzer.gui.DP;
import info.reflectionsofmind.musicanalyzer.gui.MainFrame;
import info.reflectionsofmind.musicanalyzer.gui.PhysObj;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

public final class StartCrawlingAction extends AbstractAction
{
	private final MainFrame frame;

	public StartCrawlingAction(final MainFrame mainFrame)
	{
		super("Start crawling");
		this.frame = mainFrame;
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		StartCrawlingAction.this.frame.startPhysicsAction.setEnabled(false);
		this.frame.startPauseCrawlingButton.setAction(this.frame.pauseCrawlingAction);
		this.frame.crawlingRunning = true;

		new SwingWorker<Void, PhysObj<Artist>>()
		{
			@Override
			@SuppressWarnings("unchecked")
			protected Void doInBackground() throws Exception
			{
				while (StartCrawlingAction.this.frame.crawlingRunning)
				{
					try
					{
						final Artist artist = Application.getTopArtistWithoutSimilars();
						final Map<Artist, Double> similars = Crawler.INSTANCE.loadSimilars(artist);
						Database.INSTANCE.addSimilars(artist, similars);
						final PhysObj<Artist> physObj = StartCrawlingAction.this.frame.physics.addObject(artist, new DP(Math.random() * 400 - 200, Math.random() * 400 - 200));

						publish(physObj);
					}
					catch (final IOException exception)
					{
						throw new RuntimeException();
					}
				}

				return null;
			}

			@Override
			protected void done()
			{
				StartCrawlingAction.this.frame.startPhysicsAction.setEnabled(true);
				StartCrawlingAction.this.frame.startPauseCrawlingButton.setAction(StartCrawlingAction.this.frame.startCrawlingAction);
			}

			@Override
			protected void process(final List<PhysObj<Artist>> chunks)
			{
				for (final PhysObj<Artist> chunk : chunks)
				{
					StartCrawlingAction.this.frame.writeLog("Loaded similars for " + chunk.object.getName());
				}

				StartCrawlingAction.this.frame.drawingPanel.repaint();
			}
		}.execute();
	}
}