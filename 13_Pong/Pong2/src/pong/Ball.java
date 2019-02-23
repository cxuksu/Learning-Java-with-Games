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
    
    public Ball() {
        initBall();
        dx = -3;
        dy = -3;
    }
    
    private void initBall() {
        int cvX = (Consts.MAXX-Consts.MINX) / 2;
        int cvY = (Consts.MAXY-Consts.MINY) / 2;
        setX(cvX - Consts.BALL_RADIUS);
        setY(cvY - Consts.BALL_RADIUS);
        setBallColor(Color.RED);
    }
    
    public void updateBall() {
        setX(getX() + getDx());
        setY(getY() + getDy());
    }

    public void paintBall(Graphics2D g2d) {
        g2d.setColor(getBallColor());
        g2d.fillOval(x, y, 2*Consts.BALL_RADIUS, 2*Consts.BALL_RADIUS);
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
}
