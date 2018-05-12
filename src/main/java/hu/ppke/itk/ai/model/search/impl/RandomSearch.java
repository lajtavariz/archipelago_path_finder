package hu.ppke.itk.ai.model.search.impl;

import hu.ppke.itk.ai.enumeration.Algorithm;
import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.search.AbstractSearch;

import java.util.Random;

public class RandomSearch extends AbstractSearch {

    public RandomSearch(MapModel mapModel) {
        super(mapModel);
    }

    @Override
    protected void makeStep() {
        makeRandomStepWithAgent();
    }

    private void makeRandomStepWithAgent() {
        Random rand = new Random(System.currentTimeMillis());
        int n = rand.nextInt(100000) % 4;
        if (mapModel.getAgentNode().isOneOfTheNeighborsTheGoal()) {
            System.out.println("Goal has been found!");
            mapModel.stopSearch(Algorithm.RANDOM);
        }

        mapModel.makeStep(n);
    }
}