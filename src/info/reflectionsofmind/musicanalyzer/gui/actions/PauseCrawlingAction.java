package info.reflectionsofmind.musicanalyzer.gui.actions;

import info.reflectionsofmind.musicanalyzer.gui.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public final class PauseCrawlingAction extends AbstractAction
{
	private final MainFrame frame;

	public PauseCrawlingAction(final MainFrame frame)
	{
		super("Pause crawling");
		this.frame = frame;
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		this.frame.crawlingRunning = false;
	}
}