/*
 * AbsSprite2D.java - An abstract class as a superclass of all sprites of
 * 2D geometrical shapes.
 */
package pongstru;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public abstract class AbsSprite2D extends AbsSprite {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int dx;
    private int dy;

    public AbsSprite2D() {
        super();
    }

    public abstract void initSprite();

    public abstract void updateSprite();

    public abstract void paintSprite(Graphics2D g2d);

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
