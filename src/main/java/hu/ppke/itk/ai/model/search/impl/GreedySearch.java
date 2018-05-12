package hu.ppke.itk.ai.model.search.impl;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.search.GenericGraphSearch;
import hu.ppke.itk.ai.model.search.container.IContainer;

public class GreedySearch extends GenericGraphSearch {


    protected GreedySearch(MapModel mapModel, IContainer nodes) {
        super(mapModel, nodes);
    }
}
