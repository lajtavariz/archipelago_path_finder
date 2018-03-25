package hu.ppke.itk.ai;

import asg.cliche.Command;
import asg.cliche.ShellFactory;
import hu.ppke.itk.ai.controller.MapController;
import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.view.MapView;

import java.io.IOException;

public class App
{
    private MapController mapController;

    private App() {
        MapView mapView = new MapView();
        mapView.setTitle("MapView");
        mapView.setVisible(true);

        mapController = new MapController().setMapView(mapView);
    }

    @Command
    public void changeThreshold(float threshold) {
        mapController.changeThreshold(threshold);
        mapController.updateView();
    }

    @Command
    public void changeFeatureSize(String featureSize) {
        mapController.changeFeatureSize(Byte.valueOf(featureSize));
        mapController.updateView();
    }

    @Command
    public void initMap() {
        Map mapModel = new Map();
        mapController.setMapModel(mapModel);
        mapController.updateView();
    }

    @Command
    public void regenerateMap() {
        mapController.regenerateMap();
        mapController.updateView();
    }

    public static void main(String[] args ) throws IOException
    {
        ShellFactory.createConsoleShell("app", "", new App())
                .commandLoop();
    }
}
