package hu.ppke.itk.ai.view;

import hu.ppke.itk.ai.model.Category;
import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.Node;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static hu.ppke.itk.ai.config.Config.DEFAULT_PIXEL_SIZE;
import static hu.ppke.itk.ai.model.Category.*;
import static java.awt.Color.*;

public class MapView extends JPanel implements ActionListener, Observer {

    private MapModel mapModel;
    private java.util.Map<Category, Color> categoryToColor;
    private List<PixelDTO> pixels;
    private int pixelSize;
    private SwingWorker worker = new MapWorker();

    MapView() {
        categoryToColor = new HashMap<Category, Color>();
        categoryToColor.put(LAND, GREEN);
        categoryToColor.put(WATER, BLUE);
        categoryToColor.put(AGENT, RED);
        categoryToColor.put(GOAL, BLACK);

        pixels = new ArrayList<PixelDTO>();
        pixelSize = DEFAULT_PIXEL_SIZE;

        Timer timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!worker.isDone()) {
            return;
        }
        repaint();
    }

    public void update() {
        worker = new MapWorker();
        worker.execute();
    }

    @Override
    public void paintComponent(Graphics g) {

        for (PixelDTO pixel : pixels) {
            g.setColor(pixel.getColor());
            g.fillRect(pixel.getX(), pixel.getY(), pixelSize, pixelSize);

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        worker = new MapWorker();
        worker.execute();
    }

    public MapView setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
        return this;
    }

    public MapView setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
        return this;
    }

    private class MapWorker extends SwingWorker<List<PixelDTO>, Object> {

        @Override
        protected List<PixelDTO> doInBackground() {
            List<PixelDTO> pixelList = new ArrayList<PixelDTO>();
            if (mapModel != null) {
                for (Node currentNode : mapModel.getNodes()) {
                    Color color = categoryToColor.get(currentNode.getCategory());
                    if (color == null) {
                        color = WHITE;
                    }
                    pixelList.add(new PixelDTO(currentNode.getxPos() * pixelSize,
                            currentNode.getyPos() * pixelSize, color));
                }
            }

            return pixelList;
        }

        @Override
        protected void done() {
            try {
                pixels = get();
            } catch (InterruptedException e) {
                System.err.println("Error while generating the pixels: " + e);
            } catch (ExecutionException e) {
                System.err.println("Error while generating the pixels: " + e);
            }
        }
    }
}
