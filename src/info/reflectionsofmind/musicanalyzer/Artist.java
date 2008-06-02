package info.reflectionsofmind.musicanalyzer;

public class Artist
{
	private final String name;

	public Artist(final String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object object)
	{
		return (object instanceof Artist) ? getName().equals(((Artist)object).getName()) : false;
	}
	
	@Override
	public String toString()
	{
		return "[Artist: " + getName() + "]";
	}
}
