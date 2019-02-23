/*
 * GameCanvas.java - A concrete class implements the game canvas. It is the
 * subclass of the abstract class AbsGameCanvasOri.java.
 */
package pongstru;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private ScreenPanel parent;
    private CardLayout cardLayout;

    private BallSprite ballSprite;
    private RectSprite rectSprite;
    private FieldWall theWall;
    private Paddle thePaddle;

    private int currScore;
    private int hitPaddle;

    public GameCanvas() {
        super();
        addKeyListener(new MyKeyAdapter());
    }

    public void initComponent() {
        super.getSpriteAry().clear(); // clear the spriteAry
        // then instantiate all components for a new game
        initBallSprite();
        initRectSprite();
        initFieldWall();
        initPaddle();
        currScore = 0;
        hitPaddle = 0;
    }

    public void initBallSprite() {
        ballSprite = new BallSprite();
        ballSprite.initSprite();
        ballSprite.setGameCanvas(this);
        getSpriteAry().add(ballSprite);
    }

    public void initRectSprite() {
        rectSprite = new RectSprite();
        rectSprite.initSprite();
        rectSprite.setGameCanvas(this);
        getSpriteAry().add(rectSprite);
    }

    public void initFieldWall() {
        theWall = new FieldWall();
        theWall.initSprite();
        getSpriteAry().add(theWall);
    }

    public void initPaddle() {
        thePaddle = new Paddle();
        thePaddle.initSprite();
        getSpriteAry().add(thePaddle);
    }

    public void resetGame() {
        if (!(ballSprite.isActive()) && (!(rectSprite.isActive()))) {
            stopGame();
            cardLayout.show(parent, "SplashSheet");
        }
    }

    // an inner class for implementing the key event adapter
    // for catching the up or down keys to move the paddle
    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    if ((thePaddle.getY() + Consts.PADDLE_HEIGHT)
                            < (Consts.MAXY - Consts.WALL_THICK)) {
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

    // override the method paintComponent defined in the AbsGameCanvas.java
    // for painting the current score.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        paintCurrScore(g2d);
    }
    
    // this is a special method that is not included in the AbsGameCanvasOri.java
    // it is repeatedly called in both BallSprite.java and Rectsprite.java
    // such that only when both lost, the current score is invisible
    public void paintCurrScore(Graphics2D g2d) {
        currScore = Consts.SCORE_UNIT * hitPaddle;
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("Score: " + currScore, 25, 25);
    }

    public BallSprite getBallSprite() {
        return ballSprite;
    }

    public void setBallSprite(BallSprite ballSprite) {
        this.ballSprite = ballSprite;
    }

    public void setParent(ScreenPanel screenPanel) {
        this.parent = screenPanel;
    }

    public RectSprite getRectSprite() {
        return rectSprite;
    }

    public void setRectSprite(RectSprite rectSprite) {
        this.rectSprite = rectSprite;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public int getHitPaddle() {
        return hitPaddle;
    }

    public void setHitPaddle(int hitPaddle) {
        this.hitPaddle = hitPaddle;
    }

    public FieldWall getTheWall() {
        return theWall;
    }

    public void setTheWall(FieldWall theWall) {
        this.theWall = theWall;
    }

    public Paddle getThePaddle() {
        return thePaddle;
    }

    public void setThePaddle(Paddle thePaddle) {
        this.thePaddle = thePaddle;
    }
}
