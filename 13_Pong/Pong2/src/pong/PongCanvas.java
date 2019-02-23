/*
 * PongCanvas.java -- A canvas for the game Pong.
 */
package pong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PongCanvas extends JPanel implements Runnable {

    private FieldWall theWall;
    private Ball theBall;
    private Paddle thePaddle;
    private Thread anim = null;

    public PongCanvas() {
        initComponent();
        start();
    }

    private void initComponent() {
        theWall = new FieldWall();
        theBall = new Ball();
        thePaddle = new Paddle();
    }

    private void start() {
        if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                theBall.updateBall();
                detectCollision();
                repaint();
                Thread.sleep(60);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void detectCollision() {
        // make a bounding rectangle of the ball
        Rectangle ballRect = new Rectangle(theBall.getX(), theBall.getY(),
                2 * Consts.BALL_RADIUS, 2 * Consts.BALL_RADIUS);
        int ci = -1;
        // whether the ball hits on any unit of the bouncing wall
        for (int i = 0; i < 4; i++) {
            if ((theWall.getaWall()[i]).intersects(ballRect)) {
                switch (i) {
                    case 0: // top
                    case 1: // bottom
                        theBall.setDy(-theBall.getDy());
                        ci = 0;

                        break;
                    case 2: // left
                        theBall.setDx(-theBall.getDx());
                        break;
                    case 3: // right, lose points 30
                        resetGame();
                        break;
                }
            }
        }

        // whether the ball collides the paddle
        Rectangle paddleRect = new Rectangle(thePaddle.getX(), thePaddle.getY(),
                Consts.PADDLE_WIDTH, Consts.PADDLE_HEIGHT);
        if (paddleRect.intersects(ballRect)) {
            theBall.setDx(-theBall.getDx());
        }
        // prevent the ball from sticking on the paddle
        while (paddleRect.intersects(ballRect)) {
            theBall.updateBall();
            ballRect = new Rectangle(theBall.getX(), theBall.getY(),
                    2 * Consts.BALL_RADIUS, 2 * Consts.BALL_RADIUS);
        }
    }

    public void resetGame() {
        // define it later
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // draw a boundcing wall of the playing field
        theWall.paintFieldWall(g2d);
        // draw a red ball at the center of the canvas
        theBall.paintBall(g2d);
        // draw a paddle on the right side of the playing field
        thePaddle.paintPaddle(g2d);
    }
}
