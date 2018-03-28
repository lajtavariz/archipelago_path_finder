package hu.ppke.itk.ai.controller;

import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.view.MapView;

public class MapController {

    private Map mapModel;
    private MapView mapView;

    public void regenerateMap() {
        mapModel.reGenerateNodes();
    }

    public void changePixelSize(int pixelSize) {
        mapModel.setPixelSize(pixelSize);
    }

    public void changeThreshold(float threshold) {
        mapModel.setThreshold(threshold);
    }

    public void makeRandomStepWithAgent() {
        mapModel.makeRandomStepWithAgent();
    }

    public void makeStepWithAgent(int direction) {
        mapModel.makeStep(direction);
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
