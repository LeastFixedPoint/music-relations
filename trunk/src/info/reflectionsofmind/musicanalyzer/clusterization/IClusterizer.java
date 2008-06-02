package info.reflectionsofmind.musicanalyzer.clusterization;

import info.reflectionsofmind.musicanalyzer.data.ClusterTree;
import info.reflectionsofmind.musicanalyzer.data.IObject;

import java.util.Set;

public interface IClusterizer
{
	ClusterTree clusterize(Set<IObject> objects);
}
