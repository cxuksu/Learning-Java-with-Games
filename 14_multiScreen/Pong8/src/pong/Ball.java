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
    private Color ballColor;
    private int dx, dy;
    private int radius;
    private boolean active;
    private boolean visible;

    public Ball() {
        initBall();
    }

    private void initBall() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setRadius((int) (Math.random() * Consts.BALL_RADIUS) + 5);
        setX(cvX - getRadius());
        setY(cvY - getRadius());
        setDx((int) (Math.random() * Consts.BALL_DX) + 2); // [2, 7]
        int sign = (int) Math.round(Math.random()); // 0 or 1
        if (sign == 0) { // if 0, assign negative dx
            setDx(-getDx());
        }
        setDy((int) (Math.random() * Consts.BALL_DY) + 2); // [3, 5]
        sign = (int) Math.round(Math.random());
        if (sign == 0) {
            setDy(-getDy());
        }
        setBallColor(Color.BLUE);
        setActive(true); // after is caught, the ball disappears
        setVisible(true);
    }

    public void updateBall() {
        setX(getX() + getDx());
        setY(getY() + getDy());
    }
    
    public void paintBall(Graphics2D g2d) {
        if (isVisible()) {
            g2d.setColor(getBallColor());
            g2d.fillOval(getX(), getY(), 2 * radius, 2 * radius);
        }
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
