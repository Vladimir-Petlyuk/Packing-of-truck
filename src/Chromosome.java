import java.util.List;

public class Chromosome {
    private String genome;
    private double fitness;

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }


    public static void fillChromosomeWithRandomGenes(Chromosome chromosome) {
        StringBuilder resultGenome = new StringBuilder();
        for (int i = 0; i < Truck.GENES_COUNT; i++) {
            resultGenome.append("0");
        }

        for (int i = 0; i < Truck.GENES_COUNT; i++) {
            int currIndividualNumber = Truck.getRandomInt(0, Truck.GENES_COUNT - 2);
            resultGenome.setCharAt(currIndividualNumber, Truck.getRandomInt(0, 1) == 0 ? '0' : '1');
        }
        chromosome.setGenome(resultGenome.toString());
    }

    public PalletItem getFilledTruck() {
        int lengthSum = 0;
        int widthSum = 0;
        int palletSquareSum = 0;


        for (int i = 0; i < Truck.GENES_COUNT; ++i) {
            if (this.genome.charAt(i) == '1') {
                palletSquareSum += Truck.ITEMS[i].getPalletSquare();
                lengthSum += Truck.ITEMS[i].getLength();
                widthSum += Truck.ITEMS[i].getWidth();

            }
        }


        return new PalletItem(lengthSum, widthSum, palletSquareSum, palletSquareSum);
    }

    public int calculateFitness() {
        PalletItem filledTruck = getFilledTruck();
        int columnNumbers = (int)Math.floor(Truck.SMALL_TRUCK_MAX_LENGTH / Truck.SMALL_PALLET_WIDTH);
        int rowNumbers = (int)Math.floor(Truck.SMALL_TRUCK_MAX_WIDTH / Truck.SMALL_PALLET_LENGTH);

        if (filledTruck.getWidth() / columnNumbers > Truck.SMALL_TRUCK_MAX_WIDTH) {
            return 0;
        }
        if (filledTruck.getLength() / rowNumbers > Truck.SMALL_TRUCK_MAX_LENGTH) {
            return 0;
        }
        if (filledTruck.getPalletSquare()  > Truck.SMALL_TRUCK_SQUARE) {
            return 0;
        }

        return filledTruck.getPrice();
    }


    protected Object clone() {
        Chromosome resultChromosome = new Chromosome();
        resultChromosome.setFitness(this.getFitness());
        resultChromosome.setGenome(genome);

        return resultChromosome;
    }


    public Chromosome mutateWithGivenLikelihood() {
        Chromosome result = (Chromosome) this.clone();
        StringBuilder resultGenome = new StringBuilder(genome);
        for (int i = 0; i < Truck.GENES_COUNT; ++i) {
            double randomPercent = Truck.getRandomFloat(0, 100);
            if (randomPercent < Truck.MUTATION_LIKELIHOOD) {
                char oldValue = genome.charAt(i);
                char newValue = oldValue == '1' ? '0' : '1';
                resultGenome.setCharAt(i, newValue);
            }
        }
        result.setGenome(resultGenome.toString());
        return result;
    }


    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Genome: " + genome + "\n");
        result.append("Fitness:" + fitness + "\n");

        return result.toString();
    }

    private int getRandomCrossoverLine() {
        return Truck.getRandomInt(0, Truck.GENES_COUNT - 2);
    }

    public Chromosome[] doubleCrossover(Chromosome chromosome) {

        int crossoverLine = getRandomCrossoverLine();
        Chromosome[] result = new Chromosome[2];
        result[0] = new Chromosome();
        result[1] = new Chromosome();

        StringBuffer resultGenome0 = new StringBuffer();
        StringBuffer resultGenome1 = new StringBuffer();

        for (int i = 0; i < Truck.GENES_COUNT; ++i) {

            resultGenome0.append(' ');
            resultGenome1.append(' ');

            if (i <= crossoverLine) {
                resultGenome0.setCharAt(i, this.getGenome().charAt(i));
                resultGenome1.setCharAt(i, chromosome.getGenome().charAt(i));
            } else {
                resultGenome0.setCharAt(i, chromosome.getGenome().charAt(i));
                resultGenome1.setCharAt(i, this.getGenome().charAt(i));
            }
        }

        result[0].setGenome(resultGenome0.toString());
        result[1].setGenome(resultGenome1.toString());

        return result;
    }

    public Chromosome singleCrossover(Chromosome chromosome) {
        Chromosome[] children = doubleCrossover(chromosome);
        int childNumber = Truck.getRandomInt(0, 1);
        return children[childNumber];
    }

}
