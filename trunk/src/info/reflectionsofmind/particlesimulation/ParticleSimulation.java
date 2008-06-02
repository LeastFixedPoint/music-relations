package info.reflectionsofmind.particlesimulation;

import java.util.HashMap;
import java.util.Map;

interface AttractionFunction
{
	double evaluate(Particle p1, Particle p2);
}

public class ParticleSimulation
{
	private final Map<Object, Particle> particles = new HashMap<Object, Particle>();
	private final Map<Object, Vector> forces = new HashMap<Object, Vector>();

	private Thread simulationThread = null;

	private final AttractionFunction attractionFunction;
	private final SlowdownFunction slowdownFunction;

	public ParticleSimulation(final AttractionFunction af, final SlowdownFunction sf)
	{
		this.attractionFunction = af;
		this.slowdownFunction = sf;
	}

	public void add(final Object object, final double x, final double y, final double m)
	{
		this.particles.put(object, new Particle(new Vector(x, y), m));
	}

	public synchronized void pause()
	{
		if (simulationThread == null)
		{
			throw new RuntimeException("Simulation already paused!");
		}
	}

	public synchronized void start()
	{
		if (simulationThread != null)
		{
			throw new RuntimeException("Simulation already running!");
		}
		
		this.simulationThread = new SimulationThread();
		
		this.simulationThread.start();
	}

	public synchronized void step(final double dt)
	{
		if (simulationThread != null)
		{
			throw new RuntimeException("Simulation already running!");
		}
		
		slowdownSpeeds(dt);
		calculateForces();
		applyForces(dt);
	}

	private void applyForces(final double dt)
    {
	    for (final Object object : this.particles.keySet())
		{
			final Particle particle = this.particles.get(object);
			final Vector oldSpeed = particle.getSpeed();
			final Vector newSpeed = oldSpeed.add(this.forces.get(object).scale(dt / particle.getMass()));
			particle.setSpeed(newSpeed);
		}
    }

	private void calculateForces()
    {
	    this.forces.clear();

		for (final Object object : this.particles.keySet())
		{
			final Particle particle = this.particles.get(object);
			Vector totalForce = new Vector(0.0, 0.0);

			for (final Object otherObject : this.particles.keySet())
			{
				if (otherObject != object)
				{
					final Particle otherParticle = this.particles.get(otherObject);
					final double attraction = this.attractionFunction.evaluate(particle, otherParticle);
					final Vector positionDifference = particle.position.subtract(otherParticle.position);
					final Vector force = positionDifference.setLength(attraction);
					totalForce = totalForce.add(force);
				}
			}

			this.forces.put(object, totalForce);
		}
    }

	private void slowdownSpeeds(final double dt)
    {
	    for (final Object object : this.particles.keySet())
		{
			final Particle particle = this.particles.get(object);
			final Vector oldSpeed = particle.getSpeed();
			final double oldSpeedValue = oldSpeed.getLength();
			final double newSpeedValue = this.slowdownFunction.evaluate(oldSpeedValue, dt);
			final Vector newSpeed = oldSpeed.setLength(newSpeedValue);
			particle.setSpeed(newSpeed);
		}
    }
	
	private class SimulationThread extends Thread
	{
		private boolean mustStop = false;
		private final Object lock = new Object();
		
		@Override
		public void run()
		{
			while (!mustStop)
			{
				ParticleSimulation.this.step(ParticleSimulation.this.getTimeStep());
			}
			
			synchronized (lock)
            {
				lock.notify();
            }
		}
		
		public void requestStop()
        {
			synchronized (lock)
            {
				this.mustStop = true;
				
				try
                {
	                lock.wait();
                }
                catch (InterruptedException exception)
                {
	                throw new RuntimeException(exception);
                }
            }
        }
	}

	public double getTimeStep()
    {
	    return 0.001;
    }
}

interface SlowdownFunction
{
	double evaluate(double speedValue, double dt);
}
