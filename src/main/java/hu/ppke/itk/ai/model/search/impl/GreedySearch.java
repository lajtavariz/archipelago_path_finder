package hu.ppke.itk.ai.model.search.impl;

import hu.ppke.itk.ai.enumeration.Algorithm;
import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.search.GenericGraphSearch;
import hu.ppke.itk.ai.model.search.container.PriorityQueueContainer;

public class GreedySearch extends GenericGraphSearch<PriorityQueueContainer> {

    public GreedySearch(MapModel mapModel) {
        super(mapModel, new PriorityQueueContainer(mapModel.getGoalNode()), Algorithm.GREEDY);
    }
}
