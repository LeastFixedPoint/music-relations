/**
 * 
 */
package info.reflectionsofmind.musicanalyzer.clusterization.minimaltree;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Subgraph extends CompleteGraph
{
	private final Set<Edge> edges;

	public Subgraph(final CompleteGraph graph, final Set<Edge> edges)
	{
		this(graph.getNodes(), edges);
	}

	public Subgraph(final Set<Node> nodes, final Set<Edge> edges)
	{
		super(nodes);
		this.edges = new HashSet<Edge>(edges);
	}

	public Set<Edge> getEdges()
	{
		return Collections.unmodifiableSet(this.edges);
	}

	/** Returns neighbours of given node. */
	public Set<Node> getNeighbours(final Node node)
	{
		final Set<Node> neighbours = new HashSet<Node>();

		for (final Edge edge : getEdges())
		{
			if (edge.node1 == node)
			{
				neighbours.add(edge.node2);
			}

			if (edge.node2 == node)
			{
				neighbours.add(edge.node1);
			}
		}

		return neighbours;
	}

	/** Returns whether there exists a path from startNode to endNode. */
	public boolean pathExists(final Node startNode, final Node endNode)
	{
		if (startNode == endNode)
		{
			return true;
		}

		final Set<Node> pendingNodes = new HashSet<Node>();
		final Set<Node> examinedNodes = new HashSet<Node>();

		pendingNodes.add(startNode);

		while (!pendingNodes.isEmpty())
		{
			final Node currentNode = pendingNodes.iterator().next();

			final Set<Node> neighbours = getNeighbours(currentNode);

			if (neighbours.contains(endNode))
			{
				return true;
			}

			pendingNodes.addAll(neighbours);
			examinedNodes.add(currentNode);
			pendingNodes.removeAll(examinedNodes);
		}

		return false;
	}


	/** Returns disjoint graph components. */
	public Set<Set<Node>> getComponents()
	{
		final Set<Set<Node>> components = new HashSet<Set<Node>>();
		final Set<Node> unassignedNodes = new HashSet<Node>(getNodes());

		while (!unassignedNodes.isEmpty())
		{
			final Set<Node> component = new HashSet<Node>();
			final Node node = unassignedNodes.iterator().next();

			for (final Node otherNode : unassignedNodes)
			{
				if (pathExists(node, otherNode))
				{
					unassignedNodes.remove(otherNode);
					component.add(otherNode);
				}
			}

			components.add(component);
		}

		return null;
	}
}