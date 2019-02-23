/*
 * Hole.java - A class that implements a hole.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Hole {

    private int x, y;
    private Color color;

    public Hole() {
        initHole();
    }

    private void initHole() {
        setX(Consts.MAXX - Consts.HOLE_WIDTH);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.HOLE_HEIGHT / 2);
        setColor(Color.RED);
    }

    public void paintHole(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), Consts.HOLE_WIDTH, Consts.HOLE_HEIGHT);
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
