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
    private Paddle thePaddle;
    private Thread anim = null;
    private int currScore;
    private int hitPaddle;
    private int sleepTime = 60;
    private boolean threshold1;
    private boolean threshold2;
    private ScreenDeck parent;
    private CardLayout cardLayout;

    public GameCanvas() {
        initComponent();
        start();
    }

    private void initComponent() {
        addKeyListener(new MyKeyAdapter());
        theWall = new FieldWall();
        theBall = new Ball();
        thePaddle = new Paddle();
        currScore = 0;
        hitPaddle = 0;
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
            hitPaddle++;
            theBall.setDx(-theBall.getDx());
        }
    }

    public void resetGame() {
        hitPaddle = 0;
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
        thePaddle.paintPaddle(g2d);
        
        // paint the game score
        paintCurrScore(g2d);
    }
    
    // an inner class for implementing the key event adapter
    // for catching the up or down keys to move the paddle
    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent evt) {
            switch(evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    if ((thePaddle.getY() + Consts.PADDLE_HEIGHT) < 
                            (Consts.MAXY - Consts.WALL_THICK)) {
                        thePaddle.setY(thePaddle.getY() + 10);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (thePaddle.getY() > (Consts.MINY + Consts.WALL_THICK)) {
                        thePaddle.setY(thePaddle.getY() - 10);
                    }
                    break;
                default:
            }
            repaint();
        }
    }
    
    public void paintCurrScore(Graphics2D g2d) {
        currScore = Consts.SCORE_UNIT * hitPaddle;
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("Score: " + currScore, 25, 25);
        checkScore(currScore);
    }
    
    public void checkScore(int currScore) {
        if ((!threshold1) && (currScore >= Consts.THRESHOLD1)) {
            theBall.setDx((int) (theBall.getDx() * 2));
            theBall.setDy((int) (theBall.getDy() * 1.6));
            threshold1 = true;
        } else if ((!threshold2) && (currScore >= Consts.THRESHOLD2)) {
            theBall.setDx((int) (theBall.getDx() * 2.2));
            theBall.setDy((int) (theBall.getDy() * 2.2));
            threshold2 = true;
        }
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
