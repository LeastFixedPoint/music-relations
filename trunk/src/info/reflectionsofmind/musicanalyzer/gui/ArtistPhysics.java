package info.reflectionsofmind.musicanalyzer.gui;

import info.reflectionsofmind.musicanalyzer.Artist;
import info.reflectionsofmind.musicanalyzer.Database;
import junit.framework.Assert;

import org.junit.Test;

public final class ArtistPhysics extends Physics<Artist>
{
	private static final double CLOSEST_DISTANCE = 10.0;
	private static final double IDEAL_1_DISTANCE = 20.0;
	private static final double IDEAL_0_DISTANCE = 1000.0;

	@Override
	public double getAttractionForce(final PhysObj<Artist> physObj1, final PhysObj<Artist> physObj2)
	{
		final double similarity = Database.INSTANCE.getSimilarity(physObj1.object, physObj2.object);
		final double distance = distance(physObj1, physObj2);

		if (distance < CLOSEST_DISTANCE)
		{
			return -Math.pow(CLOSEST_DISTANCE / distance, 8);
		}

		final double properDistance = IDEAL_0_DISTANCE + (IDEAL_1_DISTANCE - IDEAL_0_DISTANCE) * norm(similarity);

		return (distance - CLOSEST_DISTANCE) / (properDistance - CLOSEST_DISTANCE) - 1;
	}

	private double norm(final double x)
	{
		// return (Math.sin((x - 0.5) * Math.PI) + 1) / 2;
//		return 1 - (1 - x) * (1 - x);
//		return (x > 0.1) ? 1.0 : 0.0;
		return x;
	}

	@Test
	@SuppressWarnings("unused")
	private void zz_testNorm()
	{
		Assert.assertEquals(0.0, norm(0.0));
		Assert.assertEquals(0.5, norm(0.5));
		Assert.assertEquals(1.0, norm(1.0));
	}
}
