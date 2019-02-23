/*
 * GameCanvas.java - A game canvas class that implements the game canvas.
 */
package pongextension;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameCanvas extends AbsGameCanvas {

    private BallSprite ballSprite;
    private BouncingWall theWall;
    private Paddle thePaddle;
    private BrickGroup brickGroup;

    private int currScore;
    private int hitPaddle;

    public GameCanvas() {
        super();
        addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
    }

    @Override
    public void initComponent() {
        ballSprite = new BallSprite();
        ballSprite.setGameCanvas(this);
        getSpriteAry().add(ballSprite);
        theWall = new BouncingWall();
        thePaddle = new Paddle();
        brickGroup = new BrickGroup();
        brickGroup.setGameCanvas(this);
        brickGroup.initBrickGroup();
        currScore = 0;
        hitPaddle = 0;
    }

    public void resetGame() {
        pauseGame();
        ballSprite.initSprite();
        hitPaddle = 0;
        resumeGame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // draw a boundcing wall of the playing field
        theWall.paintBouncingWall(g2d);
        // draw a red ball at the center of the canvas
        //ballSprite.paintSprite(g2d);
        // draw a paddle on the right side of the playing field
        thePaddle.paintPaddle(g2d);
        // draw the group of bricks
        //brickGroup.paintBrickGroup(g2d);

        // paint the game score
        paintCurrScore(g2d);
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

    public int getHitPaddle() {
        return hitPaddle;
    }

    public void setHitPaddle(int hitPaddle) {
        this.hitPaddle = hitPaddle;
    }

    public BouncingWall getTheWall() {
        return theWall;
    }

    public void setTheWall(BouncingWall theWall) {
        this.theWall = theWall;
    }

    public Paddle getThePaddle() {
        return thePaddle;
    }

    public void setThePaddle(Paddle thePaddle) {
        this.thePaddle = thePaddle;
    }
}
