package hu.ppke.itk.ai.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static hu.ppke.itk.ai.config.Config.DEFAULT_CANVAS_SIZE;

public class MapFrame extends Frame {

    private MapView mapView;

    public MapFrame() throws HeadlessException {
        // Anonymous inner class to handle window close events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(DEFAULT_CANVAS_SIZE, DEFAULT_CANVAS_SIZE);
        mapView = new MapView();
        this.add(mapView);
    }

    public MapView getMapView() {
        return mapView;
    }
}
