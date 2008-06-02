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

	public Vector(final Vector v)
	{
		this(v.x, v.y);
	}

	public Vector add(final Vector force)
	{
		return new Vector(this.x + force.x, this.y + force.y);
	}

	public double getLength()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vector scale(final double factor)
	{
		return new Vector(this.x * factor, this.y * factor);
	}

	public Vector setLength(final double newLen)
	{
		return scale(getLength() / newLen);
	}

	public Vector subtract(final Vector v)
	{
		return new Vector(this.x - v.x, this.y - v.y);
	}
}
