package info.reflectionsofmind.musicanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Application
{
	public static Artist getTopArtistWithoutSimilars()
	{
		Artist topArtist = null;
		Double topRating = null;

		for (final Artist artist : Database.INSTANCE.getArtists())
		{
			final Map<Artist, Double> similars = Database.INSTANCE.getSimilars(artist);

			if (similars == null)
			{
				final Double rating = Database.INSTANCE.getRating(artist);

				if (topRating == null || rating > topRating)
				{
					topRating = rating;
					topArtist = artist;
				}
			}
		}

		return topArtist;
	}

	private static List<Artist> getTopArtists()
	{
		final List<Artist> top = new ArrayList<Artist>();

		for (int i = 0; i < 5; i++)
		{
			Artist topArtist = null;
			Double topRating = null;

			for (final Artist artist : Database.INSTANCE.getArtists())
			{
				if (!top.contains(artist))
				{
					final Double rating = Database.INSTANCE.getRating(artist);

					if (topRating == null || rating > topRating)
					{
						topRating = rating;
						topArtist = artist;
					}
				}
			}

			if (topArtist != null)
			{
				top.add(topArtist);
			}
		}

		return top;
	}

	public static void main(final String[] args) throws IOException
	{
		Database.INSTANCE.addArtist(new Artist("Metallica"));

		for (int i = 0; i < 10; i++)
		{
			final Artist artist = getTopArtistWithoutSimilars();

			printStatus();

			System.out.println();
			System.out.println("Loading similars for " + artist);
			System.out.println();

			final Map<Artist, Double> similars = Crawler.INSTANCE.loadSimilars(artist);

			Database.INSTANCE.addSimilars(artist, similars);
		}
	}

	private static void printStatus()
	{
		for (Artist artist : getTopArtists())
		{
			System.out.format("%-40s%5.1f %s\n", artist, Database.INSTANCE.getRating(artist), (Database.INSTANCE.getSimilars(artist) != null ? "#" : "-"));
		}

		System.out.println();

		System.out.println("Total " + Database.INSTANCE.getArtists().size() + " artists, " + //
				Database.INSTANCE.getArtistsWithSimilars().size() + " processed.");
	}
}
