package hu.ppke.itk.mi;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;

public class GraphicsDemo extends Frame{

    private static final short NR_OF_SQUARES = 50;
    private static final short SIZE_OF_CANVAS = 500;
    private static final short SIZE_OF_RECT = SIZE_OF_CANVAS / NR_OF_SQUARES;


    public GraphicsDemo(){
        // Anonymous inner class to handle window close events
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        setSize(SIZE_OF_CANVAS, SIZE_OF_CANVAS);
    }

    @Override
    public void paint(Graphics g) {

        for (int rowNr = 0; rowNr < NR_OF_SQUARES; rowNr++){
            for (int columnNr = 0; columnNr < NR_OF_SQUARES; columnNr++) {
                if (columnNr % 2 == 0){
                    g.setColor(rowNr % 2 == 0 ? GREEN : BLUE);
                }
                else {
                    g.setColor(rowNr % 2 == 0 ? BLUE : GREEN);
                }
                g.fillRect(columnNr * SIZE_OF_RECT, rowNr * SIZE_OF_RECT, 60, 50);
            }
        }
    }
}
