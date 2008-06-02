package info.reflectionsofmind.musicanalyzer.gui.actions;

import info.reflectionsofmind.musicanalyzer.gui.MainFrame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public final class PausePhysicsAction extends AbstractAction
{
	private final MainFrame frame;

	public PausePhysicsAction(final MainFrame frame)
	{
		super("Pause physics");
		this.frame = frame;
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		this.frame.physicsRunning = false;
	}
}