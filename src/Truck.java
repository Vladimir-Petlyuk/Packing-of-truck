import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Truck {
    public static final int SMALL_TRUCK_MAX_LENGTH = 9000;
    public static final int SMALL_TRUCK_MAX_WIDTH = 2500;
    public static final int SMALL_TRUCK_SQUARE = SMALL_TRUCK_MAX_LENGTH * SMALL_TRUCK_MAX_WIDTH;
    public static final int SMALL_PALLET_SQUARE = 1200 * 800;
    public static final int SMALL_PALLET_WIDTH = 800;
    public static final int SMALL_PALLET_LENGTH = 1200;


    //array of items to put into the knapsnack
    public static final PalletItem ITEMS[] = new PalletItem[] {
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),
        new PalletItem(1200, 800),

        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),
        new PalletItem(800, 1200),




    };


    //number of genes in a genome
    public static final int GENES_COUNT = ITEMS.length;

    //number of individuals in population
    public static final int POPULATION_COUNT = 200;

    //likelihood (in percent) of the mutation
    public static final double MUTATION_LIKELIHOOD = 0.5;

    //number of individuals participating in tournament selection
    public static final int TOURNAMENT_PARTICIPANTS_COUNT = 5;

    //number of "generations".
    public static final int MAX_ITERATIONS = 200;

    //array of individuals (Chromosomes)
    private Chromosome population[] = new Chromosome[POPULATION_COUNT];

    private static List<PalletItem> Trucks = new ArrayList<>();


    /*
     * Writes a string to the log
     * */
    public static void log(String message) {
//        System.out.println(message);
    }


    /*
     * Creates an initial population
     * */
    private void createInitialPopulation() {
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            population[i] = new Chromosome();
            Chromosome.fillChromosomeWithRandomGenes(population[i]);
        }
    }

    /*
     * Iterate through all chromosomes and fill their "fitness" property
     * */
    private void fillChromosomesWithFitnesses() {
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            double currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            System.out.println(population[i]);
        }

    }


    private int[][] getPairsForCrossover() {

        int[][] pairs = new int[POPULATION_COUNT][2];

        for (int i = 0; i < POPULATION_COUNT; ++i) {

            int firstChromosome = findIndividualForCrossoverByTournament();
            int secondChromosome;
            do {
                secondChromosome = findIndividualForCrossoverByTournament();
            }
            while (firstChromosome == secondChromosome);  //prevent individual's crossover with itself :)

            pairs[i][0] = firstChromosome;
            pairs[i][1] = secondChromosome;
        }
        return pairs;
    }


    /*
     * Performs a tournament between TOURNAMENT_PARTICIPANTS_COUNT individuals,
     * selects the best of them and returns its index in population[] array
     * */
    private int findIndividualForCrossoverByTournament() {

        int bestIndividualNumber = 0;
        double bestFitness = 0;

        for (int i = 0; i < TOURNAMENT_PARTICIPANTS_COUNT; ++i) {
            int currIndividualNumber = getRandomInt(0, POPULATION_COUNT - 1);

            if (population[currIndividualNumber].getFitness() > bestFitness) {
                bestFitness = population[currIndividualNumber].getFitness();
                bestIndividualNumber = currIndividualNumber;
            }

        }
        System.out.println(population[bestIndividualNumber]);
        return bestIndividualNumber;

    }


    private Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(int[][] pairs) {
        Chromosome nextGeneration[] = new Chromosome[POPULATION_COUNT];
        nextGeneration[0] = findIndividualWithMaxFitness();

        for (int i = 1; i < POPULATION_COUNT; ++i) {
            Chromosome firstParent = population[pairs[i][0]];
            Chromosome secondParent = population[pairs[i][1]];
            Chromosome result = firstParent.singleCrossover(secondParent);
            nextGeneration[i] = result;
            nextGeneration[i] = nextGeneration[i].mutateWithGivenLikelihood();
        }
        return nextGeneration;
    }

    /*
     * Returns random integer number between min and max ( all included :)  )
     * */
    public static int getRandomInt(int min, int max) {
        Random randomGenerator;
        randomGenerator = new Random();
        return randomGenerator.nextInt(max + 1) + min;
    }

    /*
     * Returns random float number between min (included) and max ( NOT included :)  )
     * */
    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * max + min);
    }

    public Chromosome findIndividualWithMaxFitness() {
        double currMaxFitness = 0;
        Chromosome result = population[0];
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            if (population[i].getFitness() > currMaxFitness) {
                result = population[i];
                currMaxFitness = population[i].getFitness();
            }
        }
        return result;
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }

    public static void main(String[] args) {


        Truck maximum = new Truck();
        maximum.createInitialPopulation();

        long iterationsNumber = 0;
        do {
            maximum.fillChromosomesWithFitnesses();
            int[][] pairs = maximum.getPairsForCrossover();
            Chromosome nextGeneration[];
            nextGeneration = maximum.performCrossoverAndMutationForThePopulationAndGetNextGeneration(pairs);
            maximum.setPopulation(nextGeneration);


        } while (iterationsNumber++ < MAX_ITERATIONS);

        System.out.println(maximum.population[0]);
        String genome = maximum.population[0].getGenome();
        for (int i = 0; i < ITEMS.length; i++) {
            if (genome.charAt(i) == '1') {
                System.out.println(ITEMS[i]);
            }
        }

    }

}


