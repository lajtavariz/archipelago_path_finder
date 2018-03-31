package hu.ppke.itk.ai.view;

import hu.ppke.itk.ai.model.Category;
import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.model.Node;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static hu.ppke.itk.ai.model.Category.*;
import static java.awt.Color.*;

public class MapView extends JPanel implements ActionListener, Observer {

    private Map map;
    private java.util.Map<Category, Color> categoryToColor;
    private List<PixelDTO> pixels;
    private int pixelSize;
    private SwingWorker worker = new SwingWorker<List<PixelDTO>, Void>() {

        @Override
        protected List<PixelDTO> doInBackground() {
            List<PixelDTO> pixelList = new ArrayList<PixelDTO>();
            if (map != null) {
                for (Node currentNode : map.getNodes()) {
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
    };

    MapView() {

        categoryToColor = new HashMap<Category, Color>();
        categoryToColor.put(LAND, GREEN);
        categoryToColor.put(WATER, BLUE);
        categoryToColor.put(AGENT, RED);

        pixels = new ArrayList<PixelDTO>();
        pixelSize = 0;

        Timer timer = new Timer(40, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!worker.isDone()) {
            return;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        for (PixelDTO pixel : pixels) {
            g.setColor(pixel.getColor());
            g.fillRect(pixel.getX(), pixel.getY(), pixelSize, pixelSize);

        }
    }

    public void update() {
        worker.execute();
    }

    @Override
    public void update(Observable o, Object arg) {
        worker.execute();
    }

    public MapView setMap(Map map) {
        this.map = map;
        return this;
    }
}
