
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Patchara
 */
public class GeneticAlgorithm {

    int E;
    int p;
    int[][] w;
    int lchrom;
    int tournamentSize = 2;
    int seed = 24;
    int popSize = 100;
    int maxGen = 100;
    double mutationRate = 0.02;
    double crossoverRate = 0.8;

    public GeneticAlgorithm() {

    }

    public void setSeed(int seed) {
        this.seed = seed;
        System.out.println(seed);
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        System.out.println(tournamentSize);
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
        System.out.println(popSize);
    }

    public void setGen(int maxGen) {
        this.maxGen = maxGen;
        System.out.println(maxGen);
    }

    public void setMRate(double mutationRate) {
        this.mutationRate = mutationRate;
        System.out.println(mutationRate);
    }

    public void setCRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
        System.out.println(crossoverRate);
    }

    public Individual solve() {
        //Random Generator for Integer
        Random rand = new Random((long) seed);
        //Create new population
        Individual population[] = new Individual[popSize];
        for (int i = 0; i < popSize; i++) {
            population[i] = new Individual(lchrom, p);
        }
        //fills individaul's gene with binary randomly (p node per individual)
        for (int i = 0; i < popSize; i++) {
            int count = 0;
            while (count < p) {
                int randomNode = rand.nextInt(lchrom);
                if (population[i].gene[randomNode] == 0) {
                    population[i].gene[randomNode] = 1;
                    count++;
                }
            }
        }
        //loop maxGen
        Individual bestIndi = null;
        for (int gcount = 0; gcount < maxGen; gcount++) {
            //evaluate each individual
            for (int i = 0; i < popSize; i++) {
                //clear assign node before evaluate
                population[i].assignNode = new int[lchrom][lchrom];
                //Find facility node
                int count = 0;
                population[i].fitness = 0;
                for (int j = 0; j < lchrom; j++) {
                    if (population[i].gene[j] == 1) {
                        population[i].facilityNode[count] = j;
                        population[i].assignNode[j][j] = 1;
                        count++;
                    }
                }
                //Find closed node to facility
                for (int k = 0; k < lchrom; k++) {
                    if (population[i].gene[k] == 0) {
                        int minWeight = Integer.MAX_VALUE;
                        int closedFacility = 0;
                        for (int l = 0; l < p; l++) {
                            if (w[k][population[i].facilityNode[l]] < minWeight) {
                                minWeight = w[k][population[i].facilityNode[l]];
                                closedFacility = population[i].facilityNode[l];
                            }
                        }
                        population[i].assignNode[closedFacility][k] = 1;
                        population[i].fitness += minWeight;
                    }
                }
            }
            //Get best Indicidual
            bestIndi = getBestIndividual(population, popSize);
            //print Genome
            for (int i = 0; i < popSize; i++) {
                System.out.println(population[i]);
            }
            //Total Fitness and Average
            System.out.println("Total fitness " + getTotalFitness(population, popSize));
            System.out.println("Mean fitness " + getMeanFitness(population, popSize));
            //Create new offspring
            Individual offsprings[] = new Individual[popSize];
            for (int i = 0; i < popSize; i++) {
                offsprings[i] = new Individual(lchrom);
            }
            //Tournament Selection
            for (int i = 0; i < popSize; i++) {
                int champion = rand.nextInt(popSize), contender;
                for (int j = 0; j < tournamentSize - 1; j++) {
                    contender = rand.nextInt(popSize);
                    if (population[champion].fitness > population[contender].fitness) {
                        champion = contender;
                    }
                }
                System.arraycopy(population[champion].gene, 0, offsprings[i].gene, 0, lchrom);
            }
            //one-point Crossover
            for (int i = 0; i < popSize; i += 2) {
                if (rand.nextDouble() < crossoverRate) {
                    int splitPoint = rand.nextInt(lchrom);
                    for (int j = splitPoint; j < lchrom; j++) {
                        int temp = offsprings[i].gene[j];
                        offsprings[i].gene[j] = offsprings[i + 1].gene[j];
                        offsprings[i + 1].gene[j] = temp;
                    }
                }
            }
            //one-point mutation
            for (int i = 0; i < popSize; i++) {
                if (rand.nextDouble() < mutationRate) {
                    int mutationPoint = rand.nextInt(lchrom);
                    if (offsprings[i].gene[mutationPoint] == 1) {
                        offsprings[i].gene[mutationPoint] = 0;
                    } else {
                        offsprings[i].gene[mutationPoint] = 1;
                    }
                }
            }
            //fix bit string that have node < p or > p
            for (int i = 0; i < popSize; i++) {
                int node = countNode(offsprings[i], lchrom);
                if (node < p) {
                    for (int j = 0; j < (p - node); j++) {
                        while (true) {
                            int randomNode = rand.nextInt(lchrom);
                            if (offsprings[i].gene[randomNode] == 0) {
                                offsprings[i].gene[randomNode] = 1;
                                break;
                            }
                        }
                    }
                } else if (node > p) {
                    for (int j = 0; j < (node - p); j++) {
                        while (true) {
                            int randomNode = rand.nextInt(lchrom);
                            if (offsprings[i].gene[randomNode] == 1) {
                                offsprings[i].gene[randomNode] = 0;
                                break;
                            }
                        }
                    }
                }
            }
            //copy offspring to the next population
            for (int i = 0; i < popSize; i++) {
                System.arraycopy(offsprings[i].gene, 0, population[i].gene, 0, lchrom);
            }
        }
        return bestIndi;
    }

    int countNode(Individual indi, int lchrom) {
        int count = 0;
        for (int i = 0; i < lchrom; i++) {
            if (indi.gene[i] == 1) {
                count++;
            }
        }
        return count;
    }

    int getTotalFitness(Individual[] pop, int popSize) {
        int totalFitness = 0;
        for (int i = 0; i < popSize; i++) {
            totalFitness += pop[i].fitness;
        }
        return totalFitness;
    }

    double getMeanFitness(Individual[] pop, int popSize) {
        double meanFitness = getTotalFitness(pop, popSize) / (double) popSize;
        return meanFitness;
    }

    Individual getBestIndividual(Individual[] pop, int popSize) {
        Individual bestIndi = null;
        int minFitness = Integer.MAX_VALUE;
        for (int i = 0; i < popSize; i++) {
            if (pop[i].fitness < minFitness) {
                minFitness = pop[i].fitness;
                bestIndi = pop[i];
            }
        }
        return bestIndi;
    }
}
