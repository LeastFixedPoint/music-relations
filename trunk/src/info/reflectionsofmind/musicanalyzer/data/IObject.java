package info.reflectionsofmind.musicanalyzer.data;

public interface IObject
{
	/** Returns displayable label (or label token) for this {@link IObject}. */
	String getLabel();
	
	/** Returns wrapped object. */ 
	Object getObject();
	
	/** Returns distance (measured from 0 to 1) between two {@link IObject}s. */ 
	double getDistance(IObject clusterizable);
}
