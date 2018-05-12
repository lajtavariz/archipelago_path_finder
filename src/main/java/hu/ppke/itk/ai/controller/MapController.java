package hu.ppke.itk.ai.controller;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.view.MapView;

import static hu.ppke.itk.ai.enumeration.Algorithm.*;

public class MapController {

    private MapModel mapModel;
    private MapView mapView;

    public void regenerateMap() {
        mapModel.reGenerateNodes();
    }

    public void changePixelSize(int pixelSize) {
        mapModel.setPixelSize(pixelSize);
        mapView.setPixelSize(pixelSize);
        mapView.update();
    }

    public void changeThreshold(float threshold) {
        mapModel.setThreshold(threshold);
    }

    public void startRandomWalkWithAgent() {
        mapModel.startSearch(RANDOM);
    }

    public void stopRandomWalkWithAgent() {
        mapModel.stopSearch(RANDOM);
    }

    public void startBFS() {
        mapModel.startSearch(BFS);
    }

    public void stopBFS() {
        mapModel.stopSearch(BFS);
    }

    public void startDFS() {
        mapModel.startSearch(DFS);
    }

    public void stopDFS() {
        mapModel.stopSearch(DFS);
    }

    public void startGreedy() {
        mapModel.startSearch(GREEDY);
    }

    public void stopGreedy() {
        mapModel.stopSearch(GREEDY);
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
