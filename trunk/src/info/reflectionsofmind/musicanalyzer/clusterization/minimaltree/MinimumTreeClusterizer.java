package info.reflectionsofmind.musicanalyzer.clusterization.minimaltree;

import info.reflectionsofmind.musicanalyzer.clusterization.IClusterizer;
import info.reflectionsofmind.musicanalyzer.data.Cluster;
import info.reflectionsofmind.musicanalyzer.data.ClusterTree;
import info.reflectionsofmind.musicanalyzer.data.IObject;

import java.util.Set;

public class MinimumTreeClusterizer implements IClusterizer
{
	private int numberOfClusters = 2;

	public MinimumTreeClusterizer(final int numberOfClusters)
	{
		this.numberOfClusters = numberOfClusters;
	}

	@Override
	public ClusterTree clusterize(final Set<IObject> objects)
	{
		final CompleteGraph graph = new CompleteGraph();

		for (final IObject object : objects)
		{
			graph.addNode(new Node(object));
		}

		final Subgraph subgraph = graph.getMinimalSubtree();

		for (int i = 1; i < this.numberOfClusters; i++)
		{
			Edge mostExpensiveEdge = subgraph.getEdges().iterator().next();

			for (final Edge edge : subgraph.getEdges())
			{
				if (edge.getLength() > mostExpensiveEdge.getLength())
				{
					mostExpensiveEdge = edge;
				}
			}

			subgraph.getEdges().remove(mostExpensiveEdge);
		}

		return toClusterTree(subgraph.getComponents());
	}

	public int getNumberOfClusters()
	{
		return this.numberOfClusters;
	}

	public void setNumberOfClusters(final int numberOfClusters)
	{
		this.numberOfClusters = numberOfClusters;
	}

	private static ClusterTree toClusterTree(final Set<Set<Node>> components)
	{
		final ClusterTree clusterization = new ClusterTree();

		for (final Set<Node> component : components)
		{
			final Cluster cluster = new Cluster();

			for (final Node node : component)
			{
				cluster.addObject(node.clusterizable);
			}

			clusterization.addSubcluster(cluster);
		}

		return clusterization;
	}
}
