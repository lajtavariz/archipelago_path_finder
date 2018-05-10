package hu.ppke.itk.ai.controller;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.view.MapView;

public class MapController {

    private MapModel mapModel;
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

    public void startRandomWalkWithAgent() {
        mapModel.startRandomWalkWithAgent();
    }

    public void stopRandomWalkWithAgent() {
        mapModel.stopRandomWalWithAgent();
    }

    public void startBFS() {
        mapModel.startBFS();
    }

    public void stopBFS() {
        mapModel.stopBFS();
    }

    public void makeStepWithAgent(int direction) {
        mapModel.makeStep(direction);
    }

    public void updateView () {
        mapView.update();
    }

    public MapController setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
        return this;
    }

    public MapController setMapView(MapView mapView) {
        this.mapView = mapView;
        return this;
    }
}
