package info.reflectionsofmind.musicanalyzer.gui;

import info.reflectionsofmind.musicanalyzer.Artist;
import info.reflectionsofmind.musicanalyzer.Database;
import info.reflectionsofmind.musicanalyzer.gui.actions.PauseCrawlingAction;
import info.reflectionsofmind.musicanalyzer.gui.actions.PausePhysicsAction;
import info.reflectionsofmind.musicanalyzer.gui.actions.StartCrawlingAction;
import info.reflectionsofmind.musicanalyzer.gui.actions.StartPhysicsAction;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame
{
	/* Actions */

	public final Action startPhysicsAction = new StartPhysicsAction(this);
	public final Action pausePhysicsAction = new PausePhysicsAction(this);
	public final Action startCrawlingAction = new StartCrawlingAction(this);
	public final Action pauseCrawlingAction = new PauseCrawlingAction(this);

	/* Components */

	public final JButton startPausePhysicsButton;
	public final JButton startPauseCrawlingButton;
	public final JPanel drawingPanel;
	public final JTextArea log;
	
	/* Physics values */

	public final Physics<Artist> physics = new ArtistPhysics();
	public PhysObj<Artist> highlightedObject = null;

	/* Flags */

	public boolean physicsRunning = false;
	public boolean crawlingRunning = false;

	public MainFrame()
	{
		super("Music Analyzer");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);

		this.drawingPanel = new DrawingPanel(this);
		add(this.drawingPanel);

		final JPanel sidebar = new JPanel(new MigLayout("", "[grow]", "[30]6[30]6[grow]"));
		sidebar.setMinimumSize(new Dimension(120, 0));

		this.startPausePhysicsButton = new JButton(this.startPhysicsAction);
		sidebar.add(this.startPausePhysicsButton, "grow, wrap");

		this.startPauseCrawlingButton = new JButton(this.startCrawlingAction);
		sidebar.add(this.startPauseCrawlingButton, "grow, wrap");

		this.log = new JTextArea();
		this.log.setFont(new Font("Monospaced", Font.PLAIN, 12));
		sidebar.add(new JScrollPane(this.log), "grow");

		final JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, this.drawingPanel);
		splitter.setDividerLocation(360);
		splitter.setContinuousLayout(true);
		add(splitter);

		initArtists();

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(final WindowEvent e)
			{
				MainFrame.this.physicsRunning = false;
				MainFrame.this.crawlingRunning = false;
			}
		});

		this.physics.setSlowdown(0.8);

		writeLog("Initialized.");
	}

	private void initArtists()
	{
		final Database db = Database.INSTANCE;
		db.addArtist(new Artist("Within Temptation"));
	}

	public void writeLog(final String message)
	{
		this.log.append(message + "\n");
	}

	public static void main(final String[] args) throws Exception
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new MainFrame().setVisible(true);
	}
}
