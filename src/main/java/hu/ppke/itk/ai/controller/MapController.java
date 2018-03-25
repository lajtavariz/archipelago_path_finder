package hu.ppke.itk.ai.controller;

import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.view.MapView;

public class MapController {

    private Map mapModel;
    private MapView mapView;

    public void regenerateMap() {
        mapModel.reGenerateNodes();
    }

    public void changeFeatureSize(byte featureSize) {
        mapModel.changeFeatureSize(featureSize);
    }

    public void changeThreshold(float threshold) {
        mapModel.changeThreshold(threshold);
    }

    public void updateView () {
        mapView.drawMap(mapModel);
    }

    public MapController setMapModel(Map mapModel) {
        this.mapModel = mapModel;
        return this;
    }

    public MapController setMapView(MapView mapView) {
        this.mapView = mapView;
        return this;
    }
}
