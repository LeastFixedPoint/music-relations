package info.reflectionsofmind.particlesimulation;

public class Particle
{
	public Vector position = null;
	private Vector speed = new Vector(0.0, 0.0);
	private double mass = 0.0;

	public Particle(final Vector p, final double m)
	{
		this.position = p;
		this.setMass(m);
	}
	
	public void step(double dt)
	{
		this.position = new Vector(this.getSpeed().x * dt, this.getSpeed().y * dt);
	}

	public void setSpeed(Vector speed)
    {
	    this.speed = speed;
    }

	public Vector getSpeed()
    {
	    return speed;
    }

	public void setMass(double mass)
    {
	    this.mass = mass;
    }

	public double getMass()
    {
	    return mass;
    }
}
