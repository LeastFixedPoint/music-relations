package info.reflectionsofmind.particlesimulation;

import java.util.HashMap;
import java.util.Map;

interface AttractionFunction
{
	double evaluate(Particle p1, Particle p2);
}

interface SlowdownFunction
{
	double evaluate(double speed, double dt);
}

public class ParticleSimulation
{
	private final Map<Object, Particle> particles = new HashMap<Object, Particle>();
	private final Map<Object, Force> forces = new HashMap<Object, Force>();
	private final boolean paused = true;
	private final AttractionFunction af;
	private final SlowdownFunction sf;

	public ParticleSimulation(AttractionFunction af, SlowdownFunction sf)
	{
		this.af = af;
		this.sf = sf;
	}

	public void add(final Object object, final double x, final double y, final double m)
	{
		this.particles.put(object, new Particle(new Vector(x, y), m));
	}

	public void pause()
	{

	}

	public void start()
	{

	}

	public void step(final double dt)
	{
		// slowdown

		for (Object object : particles.keySet())
		{
			final Particle particle = particles.get(object);
			particle.s.scale(this.sf.evaluate(particle.s.len(), dt));
		}

		// calculate forces

		this.forces.clear();

		for (Object o1 : particles.keySet())
		{
			final Particle p1 = particles.get(o1);
			Vector force = new Vector(0.0, 0.0);

			for (Object o2 : particles.keySet())
			{
				if (o2 != o1)
				{
					final Particle p2 = particles.get(o2);
					final double attraction = this.af.evaluate(p1, p2);
					final Vector f = new Vector(p1.p.sub(p2.p).scale(attraction));
					force = force.add(f);
				}
			}

			particle.s.scale(this.sf.evaluate(particle.s.len(), dt));
		}

	}
}
