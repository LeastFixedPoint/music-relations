package info.reflectionsofmind.musicanalyzer.visualization;

import info.reflectionsofmind.musicanalyzer.data.ClusterTree;

import java.awt.Graphics2D;

public interface IVisualizer
{
	void draw(Graphics2D graphics, ClusterTree clusterization);
}
