package hu.ppke.itk.ai.model.search.impl;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.search.GenericGraphSearch;
import hu.ppke.itk.ai.model.search.container.StackContainer;

public class DFS extends GenericGraphSearch {

    public DFS(MapModel mapModel) {
        super(mapModel, new StackContainer());
    }
}
