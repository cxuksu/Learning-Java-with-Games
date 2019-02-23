/*
 * Paddle.java - A class that implements a paddle for the game Pong.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author cxu
 */
public class Paddle {

    private int x, y;
    private Color paddleColor;

    public Paddle() {
        initPaddle();
    }

    private void initPaddle() {
        setX(Consts.MAXX - Consts.PADDLE_MARGIN);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.PADDLE_HEIGHT / 2);
        setPaddleColor(Color.ORANGE);
    }

    public void paintPaddle(Graphics g) {
        g.setColor(getPaddleColor());
        g.fillRect(x, y, Consts.PADDLE_WIDTH, Consts.PADDLE_HEIGHT);
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

    public Color getPaddleColor() {
        return paddleColor;
    }

    public void setPaddleColor(Color paddleColor) {
        this.paddleColor = paddleColor;
    }
}
