package edu.anadolu;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }


        mTSP best = null;
        int minCost = Integer.MAX_VALUE;


        for (int i = 0; i < 100_000; i++) {

            mTSP mTSP = new mTSP(params.getNumDepots(), params.getNumSalesmen());

            mTSP.randomSolution();
            mTSP.validate();
            // mTSP.print(false);

            final int cost = mTSP.cost();

            // System.out.println("Total cost is " + cost);

            if (cost < minCost) {
                best = mTSP;
                minCost = cost;
            }
        }


        if (best != null) {
            best.print(params.getVerbose());
            System.out.println("Random Solution **Total cost is " + best.cost());
        }

        best.hillClimbing();
        best.print(params.getVerbose());
        System.out.println("Hill Climbing **Total cost is " + best.cost());

        mTSP mTSP_nn = new mTSP(params.getNumDepots(), params.getNumSalesmen());

        mTSP_nn.nearestNeighbour(params.getInitial());
        mTSP_nn.print(params.getVerbose());
        System.out.println("Nearest Neighbour **Total cost is " + mTSP_nn.cost());

        mTSP_nn.hillClimbing();
        mTSP_nn.print(params.getVerbose());
        System.out.println("Hill Climbing **Total cost is " + mTSP_nn.cost());

    }





}
