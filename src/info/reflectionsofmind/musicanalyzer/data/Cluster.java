package info.reflectionsofmind.musicanalyzer.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Cluster implements ICluster
{
	private final Set<IObject> objects = new HashSet<IObject>();

	public void addObject(IObject object)
	{
		this.objects.add(object);
	}
	
	public Set<IObject> getObjects()
	{
		return Collections.unmodifiableSet(this.objects);
	}
}
