/*
 * WallUnit.java - A class that implements a unit of the field wall.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author cxu
 */
public class WallUnit {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color unitColor;

    public WallUnit() {
    }

    public void paintUnit(Graphics g) {
        g.setColor(getUnitColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getUnitColor() {
        return unitColor;
    }

    public void setUnitColor(Color unitColor) {
        this.unitColor = unitColor;
    }
}
