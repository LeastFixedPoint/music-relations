package info.reflectionsofmind.musicanalyzer;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nu.xom.Attribute;
import nu.xom.Element;

/**
 * Integrity constraints:
 * <ul>
 * <li>For every artist in {@link #artists} there should be its rating in {@link #ratings} and vice versa.</li>
 * <li>There should be no <code>null</code> records in {@link #similars}.</li>
 * </ul>
 */
public final class Database
{
	public final static Database INSTANCE = new Database();

	private final Set<Artist> artists = new HashSet<Artist>();

	private Database()
	{

	}
	
	public void save(final File file)
	{
		final Element artistsElement = new Element("artists");
		
		for (Artist artist : getArtists())
		{
			final Element artistElement = new Element("artist");
			artistElement.addAttribute(new Attribute("name", artist.getName()));
			
			if (artist.getSimilars() != null)
			{
				for (Map.Entry<Artist, Double> similar : artist.getSimilars().entrySet())
				{
					final Element similarElement = new Element("similar");
					similarElement.addAttribute(new Attribute("name", similar.getKey().getName()));
					similarElement.addAttribute(new Attribute("similarity", similar.getValue().toString()));
					artistElement.appendChild(similarElement);
				}
			}
			
			artistsElement.appendChild(artistElement);
		}
	}
	
	public void load(final File file)
	{
		
	}

	public Artist addArtist(final String name)
	{
		if (getArtistByName(name) == null)
		{
			return new Artist(name);
		}
		else
		{
			throw new RuntimeException("An artist with name \"" + name + "\" already exists.");
		}
	}

	public Artist getArtistByName(final String name)
	{
		for (final Artist artist : this.artists)
		{
			if (artist.getName().equals(name))
			{
				return artist;
			}
		}

		return null;
	}

	public Set<Artist> getArtists()
	{
		return Collections.unmodifiableSet(this.artists);
	}

	public Set<Artist> getArtistsWithSimilars()
	{
		final Set<Artist> artistsWithSimilars = new HashSet<Artist>();

		for (final Artist artist : getArtists())
		{
			if (artist.getSimilars() != null)
			{
				artistsWithSimilars.add(artist);
			}
		}

		return Collections.unmodifiableSet(artistsWithSimilars);
	}

	public final class Artist
	{
		private Map<Artist, Double> similars = null;
		private final String name;
		private double rating = 0.0;

		private Artist(final String name)
		{
			this.name = name;
			Database.this.artists.add(this);
		}

		public String getName()
		{
			return this.name;
		}

		public Double getRating()
		{
			return this.rating;
		}

		public double getSimilarity(final Artist artist)
		{
			if (getSimilars() == null)
			{
				throw new RuntimeException("Similars not loaded for " + this);
			}

			if (artist.getSimilars() == null)
			{
				throw new RuntimeException("Similars not loaded for " + artist);
			}

			final Double similarity12 = getSimilars().get(artist);
			final Double similarity21 = artist.getSimilars().get(this);

			return ((similarity12 == null ? 0.0 : similarity12) + (similarity21 == null ? 0.0 : similarity21)) / 2.0;
		}

		public Map<Artist, Double> getSimilars()
		{
			return this.similars;
		}

		public void setSimilars(final Map<String, Double> similars)
		{
			if (this.similars == null)
			{
				final Map<Artist, Double> resolvedSimilars = new HashMap<Artist, Double>();

				for (final Map.Entry<String, Double> similar : similars.entrySet())
				{
					Artist similarArtist = getArtistByName(similar.getKey());

					if (similarArtist == null)
					{
						similarArtist = addArtist(similar.getKey());
					}

					this.rating += similar.getValue();
					resolvedSimilars.put(similarArtist, similar.getValue());
				}

				this.similars = resolvedSimilars;
			}
			else
			{
				throw new RuntimeException("Similars already loaded for " + this);
			}
		}
	}
}
