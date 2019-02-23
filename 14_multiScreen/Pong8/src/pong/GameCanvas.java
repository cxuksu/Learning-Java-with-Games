/*
 * GameCanvas.java -- A canvas for the game Pong.
 */
package pong;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel implements Runnable {

    private FieldWall theWall;
    private Ball theBall;
    private Hole hole;
    private int currScore;
    private int catchBall;
    private int numPenalty;
    private Thread anim = null;
    private ScreenDeck parent;
    private CardLayout cardLayout;
    private int sleepTime = 60;

    public GameCanvas() {
        initComponent();
        start();
    }

    private void initComponent() {
        addKeyListener(new MyKeyAdapter());
        theWall = new FieldWall();
        hole = new Hole();
        currScore = 0; // first time, initalize them as zero
        catchBall = 0;
        numPenalty = 0;
        initBall();
    }

    public void initBall() {
        theBall = new Ball();
        if (catchBall >= Consts.MAX_BALL) { // if a new game
            currScore = 0;
            catchBall = 0;
            numPenalty = 0;
        }
    }

    private void start() {
        if (anim == null) {
            anim = new Thread(this);
            anim.start();
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (true) {
                theBall.updateBall();
                detectCollision();
                repaint();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void detectCollision() {
        // make a bounding rectangle of the ball
        Rectangle ballRect = new Rectangle(theBall.getX(), theBall.getY(),
                2 * Consts.BALL_RADIUS, 2 * Consts.BALL_RADIUS);
        // whether the ball hits on any unit of the bouncing wall
        for (int i = 0; i < 4; i++) {
            if ((theWall.getaWall()[i]).intersects(ballRect)) {
                switch (i) {
                    case 0: // top
                    case 1: // bottom
                        theBall.setDy(-theBall.getDy());
                        break;
                    case 2: // left
                        theBall.setDx(-theBall.getDx());
                        break;
                    case 3: // right
                        if (!collideHole(ballRect)) {
                            theBall.setDx(-theBall.getDx());
                            numPenalty++;
                            if (numPenalty >= Consts.MAX_BALL) {
                                resetGame();
                            }
                            initBall();
                        }
                        break;
                }
            }
        }
    }

    public boolean collideHole(Rectangle ballRect) {
        boolean collide = false;
        // whether the ball collides the hole
        Rectangle holeRect = new Rectangle(hole.getX(),
                hole.getY(), Consts.HOLE_WIDTH, Consts.HOLE_HEIGHT);
        if (holeRect.intersects(ballRect)) {
            catchBall++;
            theBall.setActive(false);
            theBall.setVisible(false);
            collide = true;
            initBall();
        }
        return collide;
    }

    public void resetGame() {
        cardLayout.show(parent, "SplashSheet");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // draw a boundcing wall of the playing field
        theWall.paintBouncingWall(g2d);
        // draw a red ball at the center of the canvas
        theBall.paintBall(g2d);
        // draw a paddle on the right side of the playing field
        hole.paintHole(g2d);

        // paint the game score
        paintCurrScore(g2d);
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    if ((hole.getY() + Consts.HOLE_HEIGHT)
                            < (Consts.MAXY - Consts.WALL_THICK)) {
                        hole.setY(hole.getY() + 10);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (hole.getY() > (Consts.MINY + Consts.WALL_THICK)) {
                        hole.setY(hole.getY() - 10);
                    }
                    break;
                default:
            }
            repaint();
        }
    }

    public void paintCurrScore(Graphics2D g2d) {
        currScore = Consts.SCORE_UNIT * catchBall - Consts.PENALTY * numPenalty;
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("Score: " + currScore, 25, 25);
        g2d.drawString("Ball lost: " + numPenalty , 130, 25);
    }

    public Ball getTheBall() {
        return theBall;
    }

    public void setTheBall(Ball theBall) {
        this.theBall = theBall;
    }

    @Override
    public ScreenDeck getParent() {
        return parent;
    }

    public void setParent(ScreenDeck parent) {
        this.parent = parent;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}
