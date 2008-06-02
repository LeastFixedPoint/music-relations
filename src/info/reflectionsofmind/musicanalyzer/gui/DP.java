package info.reflectionsofmind.musicanalyzer.gui;

public class DP
{
	public double x = 0.0;
	public double y = 0.0;

	public DP()
	{

	}

	public DP(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public DP(final DP point)
	{
		this(point.x, point.y);
	}
	
	public double norm()
	{
		return Math.sqrt(x*x + y*y);
	}
}
