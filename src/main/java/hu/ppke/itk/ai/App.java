package hu.ppke.itk.ai;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import hu.ppke.itk.ai.controller.MapController;
import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.view.MapFrame;
import hu.ppke.itk.ai.view.MapView;

import java.io.IOException;

public class App
{
    private MapController mapController;

    private App() {
        MapFrame mapFrame = new MapFrame();
        mapFrame.setTitle("MapView");
        mapFrame.setVisible(true);

        Map mapModel = new Map();
        MapView mapView = mapFrame.getMapView();
        mapModel.addObserver(mapView);
        mapView.setMap(mapModel);

        mapController = new MapController().setMapModel(mapModel).setMapView(mapView);
        mapController.updateView();
    }

    @Command
    public void changeThreshold(float threshold) {
        mapController.changeThreshold(threshold);
        mapController.updateView();
    }

    @Command
    public void changePixelSize(int pixelSize) {
        mapController.changePixelSize(pixelSize);
        mapController.updateView();
    }

    @Command
    public void regenerateMap() {
        mapController.regenerateMap();
        mapController.updateView();
    }

    @Command
    public void startRandomWalk() {
        mapController.startRandomWalkWithAgent();
    }

    @Command
    public void stopRandomWalk() {
        mapController.stopRandomWalkWithAgent();
    }

    @Command
    public void makeStep(int direction) {
        mapController.makeStepWithAgent(direction);
    }

    public static void main(String[] args ) throws IOException
    {
        ShellFactory.createConsoleShell("app", "", new App())
                .commandLoop();
    }
}
