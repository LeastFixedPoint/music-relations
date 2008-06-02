package info.reflectionsofmind.musicanalyzer.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ClusterTree implements ICluster
{
	private final Set<ICluster> subclusters = new HashSet<ICluster>();

	public void addSubcluster(final ICluster subcluster)
	{
		this.subclusters.add(subcluster);
	}

	public Set<IObject> getObjects()
	{
		final Set<IObject> objects = new HashSet<IObject>();
		
		for (ICluster cluster : getSubclusters())
		{
			objects.addAll(cluster.getObjects());
		}
		
		return Collections.unmodifiableSet(objects);
	}

	public Set<ICluster> getSubclusters()
	{
		return Collections.unmodifiableSet(this.subclusters);
	}
}
