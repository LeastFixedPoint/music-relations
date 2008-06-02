package info.reflectionsofmind.particlesimulation;

public class Vector
{
	public final double x;
	public final double y;

	public Vector(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v)
    {
		this(v.x, v.y);
    }

	public double len()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vector scale(double newLen)
	{
		double oldLen = len();
		return new Vector(this.x / oldLen * newLen, this.y / oldLen * newLen);
	}
	
	public Vector sub(Vector v)
	{
		return new Vector(this.x - v.x, this.y - v.y);
	}
	
	public Vector add(Vector force)
	{
		return new Vector(this.x + force.x, this.y + force.y);
	}
}
