package hu.ppke.itk.mi;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GraphicsDemo extends Frame{

    private static final short NR_OF_SQUARES = 10;
    private static final short SIZE_OF_CANVAS = 500;


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
        short sizeOfRect = SIZE_OF_CANVAS / NR_OF_SQUARES;

        for (int i = 0; i < NR_OF_SQUARES; i++){
            for (int j = 0; j < NR_OF_SQUARES; j++) {
                if ( j%2 == 0){

                }



            }


        }

        g.drawRect(20, 150, 60, 50);
        g.fillRect(110, 150, 60, 50);
    }
}
