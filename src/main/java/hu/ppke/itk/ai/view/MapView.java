package hu.ppke.itk.ai.view;

import hu.ppke.itk.ai.model.Category;
import hu.ppke.itk.ai.model.Map;
import hu.ppke.itk.ai.model.Node;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import static hu.ppke.itk.ai.config.Config.DEFAULT_CANVAS_SIZE;
import static hu.ppke.itk.ai.model.Category.*;
import static java.awt.Color.*;

public class MapView extends Frame{

    private Map map;
    private java.util.Map<Category, Color> categoryToColor;

    public MapView(){
        // Anonymous inner class to handle window close events
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(DEFAULT_CANVAS_SIZE, DEFAULT_CANVAS_SIZE);

        categoryToColor = new HashMap<Category, Color>();
        categoryToColor.put(LAND, GREEN);
        categoryToColor.put(WATER, BLUE);
        categoryToColor.put(AGENT, RED);
    }

    @Override
    public void paint(Graphics g) {

        if (map == null) {
            for (int y = 0; y < DEFAULT_CANVAS_SIZE; y++) {
                for (int x = 0; x < DEFAULT_CANVAS_SIZE; x++) {
                    g.setColor(WHITE);
                    g.fillRect(x, y, 1, 1);
                }
            }
        } else {
            for (Node currentNode : map.getNodes()){
                Color color = categoryToColor.get(currentNode.getCategory());
                if (color != null) {
                    g.setColor(color);
                }
                else {
                    g.setColor(WHITE);
                }

                g.fillRect(currentNode.getxPos() * map.getPixelSize(),
                        currentNode.getyPos() * map.getPixelSize(),
                        map.getPixelSize(), map.getPixelSize());
            }
        }
    }

    public void drawMap(Map map) {
        this.map = map;
        repaint();
    }
}
