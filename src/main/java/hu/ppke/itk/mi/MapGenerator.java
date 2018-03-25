package hu.ppke.itk.mi;

import hu.ppke.itk.mi.noise.OpenSimplexNoise;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;

public class MapGenerator extends Frame implements IMapGenerator{

    private static final short SIZE_OF_CANVAS = 800;

    private byte featureSize = 5;
    private float threshold = 0.5f;

    private OpenSimplexNoise noise;

    public MapGenerator(){
        // Anonymous inner class to handle window close events
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(SIZE_OF_CANVAS, SIZE_OF_CANVAS);

        noise = new OpenSimplexNoise();
    }

    @Override
    public void paint(Graphics g) {

        for (int y = 0; y < SIZE_OF_CANVAS; y++){
            for (int x = 0; x < SIZE_OF_CANVAS; x++) {
                double noiseValue = noise.eval(x / featureSize, y / featureSize);

                if (noiseValue > threshold){
                    g.setColor(GREEN);
                }
                else {
                    g.setColor(BLUE);
                }
                g.fillRect(x, y, 1, 1);
            }
        }
    }


    public void changeThreshold(float threshold) {
        this.threshold = threshold;
        this.repaint();
    }

    public void changeFeatureSize(byte featureSize) {
        this.featureSize = featureSize;
        this.repaint();
    }
}
