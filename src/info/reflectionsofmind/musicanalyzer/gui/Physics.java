package info.reflectionsofmind.musicanalyzer.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class Physics<T>
{
	private static final double DT = 0.01;

	public final double distance(final PhysObj<?> p1, final PhysObj<?> p2)
	{
		final double dx = p1.point.x - p2.point.x;
		final double dy = p1.point.y - p2.point.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	private final List<PhysObj<T>> objects = new ArrayList<PhysObj<T>>();

	private double slowdown = 1;

	public final PhysObj<T> addObject(final T object, final DP point)
	{
		final PhysObj<T> physObj = new PhysObj<T>(object, point);
		this.objects.add(physObj);
		return physObj;
	}

	/** Must return attraction force value between two points. Force value is equivalent to acceleration. */
	public abstract double getAttractionForce(final PhysObj<T> object1, final PhysObj<T> object2);

	public final List<PhysObj<T>> getObjects()
	{
		return this.objects;
	}

	public final double getSlowdown()
	{
		return this.slowdown;
	}

	public final void run()
	{
		for (final PhysObj<T> object : getObjects())
		{
			object.point.x += object.speed.x * DT;
			object.point.y += object.speed.y * DT;
		}

		for (final PhysObj<T> object : getObjects())
		{
			final DP acceleration = new DP();

			for (final PhysObj<T> otherObject : getObjects())
			{
				if (object == otherObject)
				{
					continue;
				}

				final double force = getAttractionForce(object, otherObject);

				final double dist = distance(object, otherObject);

				acceleration.x += force * (otherObject.point.x - object.point.x) / dist;
				acceleration.y += force * (otherObject.point.y - object.point.y) / dist;
			}

			object.speed.x += acceleration.x * DT;
			object.speed.y += acceleration.y * DT;

			object.speed.x *= Math.pow(this.slowdown, DT);
			object.speed.y *= Math.pow(this.slowdown, DT);
			
			if (object.speed.norm() > 1/DT)
			{
				object.speed.x = (1/DT) * object.speed.x / object.speed.norm();
				object.speed.y = (1/DT) * object.speed.y / object.speed.norm();
			}
		}
	}

	public final void setSlowdown(final double slowdown)
	{
		this.slowdown = slowdown;
	}
}
