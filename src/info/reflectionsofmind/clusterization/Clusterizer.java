package info.reflectionsofmind.clusterization;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Clusterizer
{
	private static final double THRESHOLD = 0.5;

	public static void main(final String[] args)
	{
		final Clusterizer clusterizer = new Clusterizer();

		final List<Object> objects = Arrays.<Object> asList(0, 1, 2, 3);

		final SimilarityFunction similarity = new SimilarityFunction()
		{
			final double[][] similarities = new double[][] { //
			/*    */{ 1.0, 0.8, 0.1, 0.2 }, //
					{ 0.9, 1.0, 0.0, 0.1 }, //
					{ 0.2, 0.0, 1.0, 0.7 }, //
					{ 0.0, 0.1, 1.0, 1.0 } //
			};

			@Override
			public double compute(Object a, Object b)
			{
				return similarities[(Integer) a][(Integer) b];
			}
		};

		final Map<Object, double[]> result = clusterizer.clusterize(objects, similarity);

		for (Map.Entry<Object, double[]> entry : result.entrySet())
		{
			System.out.format("%1d : %s\n", entry.getKey(), Arrays.toString(entry.getValue()));
		}

		System.out.println();
		System.out.println(clusterizer.evaluate(similarity, result));
	}

	private double[][] clusterize(final int objectCount, final SimilarityFunction f)
	{
		final int seedCount = 10;
		final int clusterCount = 2;
		final double[][][] population = new double[seedCount][objectCount][clusterCount];
		final double[] fitness = new double[seedCount];

		for (int seedIndex = 0; seedIndex < seedCount; seedIndex++)
		{
			for (int objectIndex = 0; objectIndex < objectCount; objectIndex++)
			{
				final double[] object = new double[clusterCount];
				double sumSquares = 0;
				
				for (int clusterIndex = 0; clusterIndex < clusterCount; clusterIndex++)
				{
					object[clusterIndex] = Math.random();
					sumSquares += object[clusterIndex];
				}
				
				sumSquares = Math.sqrt(sumSquares);
				
				for (int clusterIndex = 0; clusterIndex < clusterCount; clusterIndex++)
				{
					object[clusterIndex] = object[clusterIndex]/sumSquares;
				}
				
				population[seedIndex][objectIndex] = object;
			}
			
			fitness[seedIndex] = evaluate(f, population[seedIndex]);
		}

		final Random random = new Random();
		
		for (int iterationIndex = 0; iterationIndex < 100; iterationIndex++)
		{
			final int seed1Index = random.nextInt(seedCount);  
			final int seed2Index = random.nextInt(seedCount);
			
			int weakestSeedIndex = seed1Index;
			
			for (int seedIndex = 0; seedIndex < seedCount; seedIndex++)
			{
				if (fitness[seedIndex] < fitness[weakestSeedIndex])
				{
					weakestSeedIndex = seedIndex;
				}
			}
			
			double[][] crossover = new double[objectCount][clusterCount];
		}
		
		
		return population[0];
	}

	public Map<Object, double[]> clusterize(final List<Object> objects, final SimilarityFunction similarity)
	{
		final double[][] rawClusterization = clusterize(objects.size(), similarity);
		final Map<Object, double[]> clusterization = new LinkedHashMap<Object, double[]>();

		for (final Object object : objects)
		{
			clusterization.put(object, rawClusterization[objects.indexOf(object)]);
		}

		return clusterization;
	}

	public double evaluate(final SimilarityFunction similarity, final Map<Object, double[]> clusterization)
	{
		double evaluation = 0;

		for (final Map.Entry<Object, double[]> entry1 : clusterization.entrySet())
		{
			for (final Map.Entry<Object, double[]> entry2 : clusterization.entrySet())
			{
				for (int i = 0; i < Math.max(entry1.getValue().length, entry2.getValue().length); i++)
				{
					final double sim = similarity.compute(entry1.getKey(), entry2.getKey());
					final double c1 = entry1.getValue()[i];
					final double c2 = entry2.getValue()[i];
					evaluation += c1 * c2 * (sim - THRESHOLD);
				}
			}
		}

		return evaluation;
	}

	private double evaluate(final SimilarityFunction similarity, double[][] clusterization)
	{
		double evaluation = 0;

		for (int i1 = 0; i1 < clusterization.length; i1++)
		{
			for (int i2 = 0; i2 < clusterization.length; i2++)
			{
				for (int i = 0; i < Math.max(clusterization[i1].length, clusterization[i2].length); i++)
				{
					final double sim = similarity.compute(i1, i2);
					final double c1 = clusterization[i1][i];
					final double c2 = clusterization[i2][i];
					evaluation += c1 * c2 * (sim - THRESHOLD);
				}
			}
		}

		return evaluation;
	}
}
