package hu.ppke.itk.ai.model.search.impl;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.search.GenericGraphSearch;
import hu.ppke.itk.ai.model.search.container.QueueContainer;

public class BFS extends GenericGraphSearch {

    public BFS(MapModel mapModel) {
        super(mapModel, new QueueContainer());
    }
}