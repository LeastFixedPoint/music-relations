/**
 * 
 */
package info.reflectionsofmind.musicanalyzer.clusterization.minimaltree;

import info.reflectionsofmind.musicanalyzer.data.IObject;

class Node
{
	public final IObject clusterizable;

	public Node(final IObject clusterizable)
	{
		this.clusterizable = clusterizable;
	}

	public double getLength(final Node node)
	{
		return this.clusterizable.getDistance(node.clusterizable);
	}
}