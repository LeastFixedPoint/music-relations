package info.reflectionsofmind.musicanalyzer.gui;

public class PhysObj<T>
{
	public DP point;
	public DP speed;
	public final T object;

	public PhysObj(final T object, final DP point)
	{
		this(object, point, new DP());
	}

	public PhysObj(final T object, final DP point, final DP speed)
	{
		this.object = object;
		this.point = point;
		this.speed = speed;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return this == obj;
	}
}
