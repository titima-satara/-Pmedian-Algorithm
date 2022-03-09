/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patchara
 */
public class Individual {

    int gene[];
    int facilityNode[];
    int assignNode[][];
    int fitness;

    //for offspring
    public Individual(int n) {
        this.gene = new int[n];
    }

    //for population
    public Individual(int n, int p) {
        this.gene = new int[n];
        this.facilityNode = new int[p];
        this.assignNode = new int[n][n];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gene.length);
        for (int g : gene) {
            sb.append(g);
        }
        return sb.toString();
    }
}
