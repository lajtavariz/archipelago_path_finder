package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.model.MapModel;

import java.util.Random;

public class RandomWalk extends AbstractSearch {

    public RandomWalk(MapModel mapModel) {
        super(mapModel);
    }

    @Override
    protected void makeStep() {
        makeRandomStepWithAgent();
    }

    private void makeRandomStepWithAgent() {
        Random rand = new Random(System.currentTimeMillis());
        int n = rand.nextInt(100000) % 4;

        mapModel.makeStep(n);
    }
}