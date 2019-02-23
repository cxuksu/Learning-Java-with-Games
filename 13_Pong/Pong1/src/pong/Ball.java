/*
 * Ball.java - A class that implements a ball for the game Pong.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author cxu
 */
public class Ball {

    private int x, y;
    private Color ballColor;

    public Ball() {
        initBall();
    }

    private void initBall() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setX(cvX - Consts.BALL_RADIUS);
        setY(cvY - Consts.BALL_RADIUS);
        setBallColor(Color.RED);
    }

    public void paintBall(Graphics g) {
        g.setColor(getBallColor());
        g.fillOval(x, y, 2 * Consts.BALL_RADIUS, 2 * Consts.BALL_RADIUS);
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
}
