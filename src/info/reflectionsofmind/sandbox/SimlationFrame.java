package info.reflectionsofmind.sandbox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimlationFrame extends JFrame
{
	public SimlationFrame()
	{
		super("Particle Simlation Sandbox");

		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(null);

		final JButton startButton = new JButton(new StartAction());
		startButton.setBounds(6, 6, 120, 30);
		add(startButton);

		final JButton pauseButton = new JButton(new PauseAction());
		pauseButton.setBounds(6 + startButton.getWidth() + 6, 6, 120, 30);
		add(pauseButton);

		final JPanel drawingPanel = new JPanel();
		drawingPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		drawingPanel.setBounds(6, 6 + startButton.getHeight() + 6, // 
				getWidth() - 6 - 6, getHeight() - startButton.getHeight() - 6 - 6 - 6);
		add(drawingPanel);
	}

	private class StartAction extends AbstractAction
	{
		public StartAction()
		{
			super("Start");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

		}
	}

	private class PauseAction extends AbstractAction
	{
		public PauseAction()
		{
			super("Pause");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

		}
	}
}
