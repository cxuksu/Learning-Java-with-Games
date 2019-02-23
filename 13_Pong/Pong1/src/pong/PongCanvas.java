/*
 * PongCanvas.java -- A canvas for the game Pong.
 */
package pong;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PongCanvas extends JPanel {

    private FieldWall theWall;
    private Ball theBall;
    private Paddle thePaddle;

    public PongCanvas() {
        initComponent();
    }

    private void initComponent() {
        theWall = new FieldWall();
        theBall = new Ball();
        thePaddle = new Paddle();
    }

    @Override
    public void paint(Graphics g) {
        // draw a boundcing wall of the playing field
        theWall.paintFieldWall(g);

        // draw a red ball at the center of the canvas
        theBall.paintBall(g);

        // draw a paddle on the right side of the playing field
        thePaddle.paintPaddle(g);
    }
}
