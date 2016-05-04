package com.example.sample;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class FewestChangeSum {

	private static int MAX_ALLOWED_EVOLUTIONS = 100;

	public static void main(String[] args) throws Exception {
		// Start with a DefaultConfiguration, which comes setup with the
		// most common settings.
		// -------------------------------------------------------------
		Configuration conf = new DefaultConfiguration();

		// Set the fitness function we want to use, which is our
		// MinimizingMakeChangeFitnessFunction that we created earlier.
		// We construct it with the target amount of change provided
		// by the user.
		// ------------------------------------------------------------
		int targetAmount = Integer.parseInt(args[0]);
		FitnessFunction myFunc = new MinimizeMakeChangeFitnessFunction(targetAmount);

		conf.setFitnessFunction(myFunc);

		// Now we need to tell the Configuration object how we want our
		// Chromosomes to be setup. We do that by actually creating a
		// sample Chromosome and then setting it on the Configuration
		// object. As mentioned earlier, we want our Chromosomes to
		// each have four genes, one for each of the coin types. We
		// want the values of those genes to be integers, which represent
		// how many coins of that type we have. We therefore use the
		// IntegerGene class to represent each of the genes. That class
		// also lets us specify a lower and upper bound, which we set
		// to sensible values for each coin type.
		// --------------------------------------------------------------
		Gene[] sampleGenes = new Gene[4];

		sampleGenes[0] = new IntegerGene(conf, 0, 3); // Quarters
		sampleGenes[1] = new IntegerGene(conf, 0, 2); // Dimes
		sampleGenes[2] = new IntegerGene(conf, 0, 1); // Nickels
		sampleGenes[3] = new IntegerGene(conf, 0, 4); // Pennies

		Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

		conf.setSampleChromosome(sampleChromosome);

		// Finally, we need to tell the Configuration object how many
		// Chromosomes we want in our population. The more Chromosomes,
		// the larger the number of potential solutions (which is good
		// for finding the answer), but the longer it will take to evolve
		// the population each round. We'll set the population size to
		// 500 here.
		// --------------------------------------------------------------
		conf.setPopulationSize(500);

		// TODO: Add the code following below in this example here
		Genotype population = Genotype.randomInitialGenotype(conf);

		IChromosome bestSolutionSoFar;

		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
		}	
		bestSolutionSoFar = population.getFittestChromosome();

		System.out.println("The best solution contained the following: ");

		System.out.println(
			MinimizeMakeChangeFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 0) + " quarters."
		);

		System.out.println(
			MinimizeMakeChangeFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 1) + " dimes."
		);

		System.out.println(
			MinimizeMakeChangeFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 2) + " nickels."
		);

		System.out.println(
			MinimizeMakeChangeFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 3) + " pennies."
		);

		System.out.println(
			"For a total of " + MinimizeMakeChangeFitnessFunction.amountOfChange(bestSolutionSoFar) + " cents in "
			+ MinimizeMakeChangeFitnessFunction.getTotalNumberOfCoins(bestSolutionSoFar) + " coins."
		);
		
	}
}
