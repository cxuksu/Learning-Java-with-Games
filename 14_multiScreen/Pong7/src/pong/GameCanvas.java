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
    private Ball ball1;
    private Ball ball2;
    private Paddle thePaddle;
    private Thread anim = null;
    private int currScore;
    private int hitPaddle;
    private ScreenDeck parent;
    private CardLayout cardLayout;
    private int sleepTime;
    private boolean threshold1;
    private boolean threshold2;

    public GameCanvas() {
        initComponent();
        start();
    }

    private void initComponent() {
        addKeyListener(new MyKeyAdapter());
        theWall = new FieldWall();
        thePaddle = new Paddle();
        initBall();
    }
    
    public void initBall() {
        int cvX = (Consts.MAXX-Consts.MINX) / 2;
        int cvY = (Consts.MAXY-Consts.MINY) / 2;
        ball1 = new Ball();
        ball1.setRadius(Consts.BALL_RADIUS1);
        ball1.setX(cvX - ball1.getRadius());
        ball1.setY(cvY - ball1.getRadius());
        ball1.setBallColor(Color.RED);
        ball1.setDx(3);
        ball1.setDy(2);
        ball1.setLost(false);
        ball2 = new Ball();
        ball2.setRadius(Consts.BALL_RADIUS2);
        ball2.setX(cvX - ball2.getRadius());
        ball2.setY(cvY - ball2.getRadius());
        ball2.setBallColor(Color.BLUE);
        ball2.setDx(2);
        ball2.setDy(3);
        ball2.setLost(false);
        currScore = 0;
        hitPaddle = 0;
        threshold1 = false;
        threshold2 = false;
        
        sleepTime = 60;
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
                ball1.updateBall();
                ball2.updateBall();
                detectCollision();
                repaint();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
        }
    }
    
    public void detectCollision() {
        // make a bounding rectangle of the ball
        Rectangle ballRect1 = new Rectangle(ball1.getX(), ball1.getY(),
                2 * Consts.BALL_RADIUS1, 2 * Consts.BALL_RADIUS1);
        Rectangle ballRect2 = new Rectangle(ball2.getX(), ball2.getY(),
                2 * Consts.BALL_RADIUS2, 2 * Consts.BALL_RADIUS2);
        // whether the ball hits on any unit of the bouncing wall
        for (int i = 0; i < 4; i++) {
            if ((theWall.getaWall()[i]).intersects(ballRect1)) {
                switch (i) {
                    case 0: // top
                    case 1: // bottom
                        ball1.setDy(-ball1.getDy());
                        break;
                    case 2: // left
                        ball1.setDx(-ball1.getDx());
                        break;
                    case 3: // right, game is over
                        ball1.setLost(true);
                        callResetGame();
                        break;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((theWall.getaWall()[i]).intersects(ballRect2)) {
                switch (i) {
                    case 0: // top
                    case 1: // bottom
                        ball2.setDy(-ball2.getDy());
                        break;
                    case 2: // left
                        ball2.setDx(-ball2.getDx());
                        break;
                    case 3: // right, game is over
                        ball2.setLost(true);
                        callResetGame();
                        break;
                }
            }
        }
        // whether the ball collides the paddle
        Rectangle paddleRect = new Rectangle(thePaddle.getX(), thePaddle.getY(),
            Consts.PADDLE_WIDTH, Consts.PADDLE_HEIGHT);
        if (paddleRect.intersects(ballRect1)) {
            hitPaddle++;
            ball1.setDx(-ball1.getDx());
        }
        if (paddleRect.intersects(ballRect2)) {
            hitPaddle++;
            ball2.setDx(-ball2.getDx());
        }
    }

    public void callResetGame() {
        if ((ball1.isLost()) && (ball2.isLost())) {
            resetGame();
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
        ball1.paintBall(g2d);
        ball2.paintBall(g2d);
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
            sleepTime = (int)(sleepTime / 2);
            threshold1 = true;
        } else if ((!threshold2) && (currScore >= Consts.THRESHOLD2)) {
            sleepTime = (int)(sleepTime / 2);
            threshold2 = true;
        }
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
