/**
 * 
 */
package info.reflectionsofmind.musicanalyzer.clusterization.minimaltree;


class Edge
{
	public final Node node1;
	public final Node node2;

	public Edge(final Node node1, final Node node2)
	{
		this.node1 = node1;
		this.node2 = node2;
	}

	public double getLength()
	{
		return this.node1.clusterizable.getDistance(this.node2.clusterizable);
	}
}