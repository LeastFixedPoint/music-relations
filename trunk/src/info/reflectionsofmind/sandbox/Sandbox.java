package info.reflectionsofmind.sandbox;

import javax.swing.UIManager;

public class Sandbox
{
	public static void main(String[] args) throws Exception
    {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    new SimlationFrame().setVisible(true);
    }
}
