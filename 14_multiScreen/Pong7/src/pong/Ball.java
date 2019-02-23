/*
 * Ball.java - A class that implements a ball for the game Pong.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Ball {

    private int x, y;
    private int radius;
    private Color ballColor;
    private int dx, dy;
    private boolean lost;

    public Ball() {
    }

    public void updateBall() {
        setX(getX() + getDx());
        setY(getY() + getDy());
    }
    
    public void paintBall(Graphics2D g2d) {
        g2d.setColor(getBallColor());
        g2d.fillOval(getX(), getY(), 2 * getRadius(), 2 * getRadius());
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public void setBallColor(Color ballColor) {
        this.ballColor = ballColor;
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

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
