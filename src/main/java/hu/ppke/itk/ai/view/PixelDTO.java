package hu.ppke.itk.ai.view;

import java.awt.*;

/**
 * DTO for storing pixel data
 */
public class PixelDTO {

    private int x, y;
    private Color color;

    PixelDTO(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
