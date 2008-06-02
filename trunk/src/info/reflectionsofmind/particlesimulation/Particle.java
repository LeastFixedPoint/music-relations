package info.reflectionsofmind.particlesimulation;

public class Particle
{
	public Vector p = null;
	public Vector s = new Vector(0.0, 0.0);
	public double m = 0.0;

	public Particle(final Vector p, final double m)
	{
		this.p = p;
		this.m = m;
	}
	
	public void step(double dt)
	{
		this.p.x += this.s.x * dt;
		this.p.y += this.s.y * dt;
	}
}
