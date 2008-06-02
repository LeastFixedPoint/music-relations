/**
 * 
 */
package info.reflectionsofmind.musicanalyzer.clusterization.minimaltree;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class CompleteGraph
{
	private final Set<Node> nodes;

	public CompleteGraph()
	{
		this(new HashSet<Node>());
	}

	public CompleteGraph(final Set<Node> nodes)
	{
		this.nodes = new HashSet<Node>(nodes);
	}

	public void addNode(final Node node)
	{
		this.nodes.add(node);
	}

	/** Returns minimal subtree of this graph. */
	public Subgraph getMinimalSubtree()
	{
		final Set<Set<Node>> components = new HashSet<Set<Node>>();

		for (final Node node : getNodes())
		{
			final Set<Node> component = new HashSet<Node>();
			component.add(node);
			components.add(component);
		}

		final Set<Edge> edges = new HashSet<Edge>();

		while (components.size() > 1) // Tree is disjoint
		{
			final Set<Edge> newEdges = new HashSet<Edge>();

			for (final Set<Node> component : components)
			{
				for (final Node node : component)
				{
					Edge cheapestEdge = null;

					for (final Set<Node> disjointComponent : components)
					{
						if (disjointComponent == component)
						{
							continue;
						}

						for (final Node disjointNode : disjointComponent)
						{
							if (cheapestEdge == null || cheapestEdge.getLength() > node.getLength(disjointNode))
							{
								cheapestEdge = new Edge(node, disjointNode);
							}
						}
					}

					newEdges.add(cheapestEdge);
				}
			}

			for (final Edge edge : newEdges)
			{
				Set<Node> component1 = null;
				Set<Node> component2 = null;

				for (final Set<Node> component : components)
				{
					if (component.contains(edge.node1))
					{
						component1 = component;
					}

					if (component.contains(edge.node1))
					{
						component2 = component;
					}
				}

				if (component1 != component2)
				{
					components.remove(component2);
					component1.addAll(component2);
				}
			}

			edges.addAll(newEdges);
		}

		return new Subgraph(getNodes(), edges);
	}

	public Set<Node> getNodes()
	{
		return Collections.unmodifiableSet(this.nodes);
	}
}