package info.reflectionsofmind.musicanalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Crawler
{
	public static final Crawler INSTANCE = new Crawler();

	private Crawler()
	{

	}

	public Map<Artist, Double> loadSimilars(Artist artist) throws IOException
	{
		final URL url = buildSimilarsURL(artist);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

		final Map<Artist, Double> similars = new HashMap<Artist, Double>();
		
		while (true)
		{
			final String line = reader.readLine();
			
			if (line == null)
			{
				break;
			}
			
			final String name = line.replaceFirst("^[^,]*,[^,]*?,", "");
			final Double similarity = new Double(line.replaceFirst(",.*$", "")) / 100.0;
			similars.put(new Artist(name), similarity);
		}
		
		reader.close();
		
		return similars;
	}

	private URL buildSimilarsURL(Artist artist) throws MalformedURLException
	{
		final StringBuilder builder = new StringBuilder();
		
		builder.append("http://ws.audioscrobbler.com/1.0/artist/");
		builder.append(artist.getName().replace(" ", "%20").replace("&amp;", "and"));
		builder.append("/similar.txt");
		
		return new URL(builder.toString());
	}
}
