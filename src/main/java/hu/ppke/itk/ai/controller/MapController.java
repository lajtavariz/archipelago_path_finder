package hu.ppke.itk.ai.controller;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.view.MapView;

public class MapController {

    private MapModel mapModel;
    private MapView mapView;
    private Thread thread;

    public MapController() {
        thread = new Thread();
    }

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
        thread = new Thread(new RandomWalk());
        thread.start();
    }

    public void stopRandomWalkWithAgent() {
        // TODO use another way to stop the random walk
        thread.stop();
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

    private class RandomWalk implements Runnable {

        public void run() {
            try {
                while (true) {
                    Thread.sleep(40);
                    mapModel.makeRandomStepWithAgent();
                    updateView();
                }
            } catch (InterruptedException exc) {
                System.err.println(exc);
            }
        }
    }
}
