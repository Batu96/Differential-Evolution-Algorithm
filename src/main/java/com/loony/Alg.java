package com.loony;

import java.util.Random;
import java.util.Scanner;

public class Alg {
//  Due to the meta-heuristic nature of this algorithm, the same parameter values may cause different results!
    public void start(Random random) throws InterruptedException {
//        np: number of population, cr= crossing-over rate, f: factor(step size)
//
//        162 int np = 20, dim=3;
//        double cr = 0.2, f = 0.5;

        int np , dim;
        double cr , f ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLease enter the dimension of the problem: ");
        dim = scanner.nextInt();
        System.out.println("PLease enter the number of population(>=4): ");
        np = scanner.nextInt();
        System.out.println("PLease enter the crossover probability[0,1]: ");
        cr = scanner.nextDouble();
        System.out.println("PLease enter the scaling factor(>0): ");
        f = scanner.nextDouble();

        double[][] population = new double[np][dim];
        createPopulation(population, dim, np, random);
        double[] min = new double[dim];
        for (int i = 0; i < dim; i++) {min[i] = 1;}
        int count = 0;
        while (objFunc(min) != 0 && count < 1000000) {
            count++;
            for (int i = 0; i < np; i++) {
                double[] tempSol = createSolution(population, dim, f, cr, np, population[i], random);
                if (objFunc(tempSol) < objFunc(population[i]))
                    population[i] = tempSol;

//                System.out.println(objFunc(population[i]));
            }
            System.out.println(objFunc(population[np - 1]));
            System.out.println("count: "+count);

            for (int i = 0; i < np - 1; i++) {
                min = population[i];
                if (objFunc(population[i + 1]) < objFunc(min))
                    min = population[i + 1];
            }

        }

        System.out.println("function minimum: "+objFunc(min)+"\n count: "+count );
    }

    double[] createSolution(double[][] X, int dim, double f, double cr, int np, double[] sol, Random random) {
        int randIndex, i1, i2, i3;
        double[] m = new double[dim];
        double[] newSol = arrayCopy(sol);

        do {
            randIndex = random.nextInt(0, dim);
            i1 = random.nextInt(0, np);
            i2 = random.nextInt(0, np);
            i3 = random.nextInt(0, np);
        } while (!(randIndex == i1 || i1 == i2 || i2 == i3 || randIndex == i3 || randIndex == i2 || i1 == i3));

        for (int i = 0; i < dim; i++)
            m[i] = X[i3][i] + f * (X[i1][i] - X[i2][i]);

        for (int i = 0; i < dim; i++)
            if (cr > random.nextDouble() || randIndex == i)
                newSol[i] = m[i];
        return newSol;
    }

    void createPopulation(double[][] X, int dim, int np, Random random) {
        for (int i = 0; i < np; i++)
            for (int j = 0; j < dim; j++)
                X[i][j] = random.nextDouble() * 10 - 5;
    }

    double objFunc(double[] sol) {
//      Rastrigin function
        double sum = 0;
        for (int i = 0; i < sol.length; i++)
            sum += Math.pow(sol[i], 2) - 10 * Math.cos(2 * Math.PI * sol[i]);
        sum += 10 * sol.length;
        return sum;
    }

    double[] arrayCopy(double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++)
            y[i] = x[i];
        return y;
    }
}
