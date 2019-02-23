/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BufferedImage[] brickImages; // holds 8 different brick images
    private BallSprite ballSprite;
    private PaddleSprite paddleSprite;
    private int numBrickHit;
    private int numBallLost;
    private int currScore;

    public GameCanvas() {
        super();
        addMouseMotionListener(new PaddleDraggedListener());
        addMouseListener(new MouseReleasedListener());
    }

    // it is invoked in the class AbsGameCanvas.java
    @Override
    public void initComponent() {
        getSpriteAry().clear();
        brickImages = new BufferedImage[Consts.NUM_BRICK_IMAGES];
        initBrickImages();
        initBrickSprites();
        initBallSprite();
        initPaddleSprite();
        currScore = 0;
        numBrickHit = 0;
        numBallLost = 0;
        new AllClips();
    }

    private void initBrickImages() { // get and hold 8 brick imagess
        try {
            brickImages[0]
                    = ImageIO.read(getClass().getResource("../images/aquaTile.gif"));
            brickImages[1]
                    = ImageIO.read(getClass().getResource("../images/blackTile.gif"));
            brickImages[2]
                    = ImageIO.read(getClass().getResource("../images/blueTile.gif"));
            brickImages[3]
                    = ImageIO.read(getClass().getResource("../images/greenTile.gif"));
            brickImages[4]
                    = ImageIO.read(getClass().getResource("../images/lightGreyTile.gif"));
            brickImages[5]
                    = ImageIO.read(getClass().getResource("../images/purpleTile.gif"));
            brickImages[6]
                    = ImageIO.read(getClass().getResource("../images/redTile.gif"));
            brickImages[7]
                    = ImageIO.read(getClass().getResource("../images/yellowTile.gif"));
        } catch (IOException ex) {
        }
    }

    private void initBrickSprites() {
        BrickSprite aBrick;
        int numBricks = Consts.BRICKS_PER_ROW * Consts.MAX_ROWS;
        int x = 0;
        int y = 30; // leave a line for the value of global score
        int idx;
        for (int i = 0; i < numBricks; i++) {
            if ((i % Consts.BRICKS_PER_ROW == 0) && (i != 0)) {
                x = 0;
                y += Consts.BRICK_H; // move to next row
            }
            aBrick = new BrickSprite(); // it initializes part of attributes
            aBrick.setX(x);
            x += Consts.BRICK_W; // move the coor x to the next position
            aBrick.setY(y); // stay at the same row
            idx = (int) (Math.random() * Consts.NUM_BRICK_IMAGES);
            aBrick.setAnImage(brickImages[idx]); // randomly pick up an image
            getSpriteAry().add(aBrick);
        }
    }

    public void initBallSprite() {
        ballSprite = new BallSprite();
        ballSprite.initSprite();
        ballSprite.addPropertyChangeListener(new LocChangeListener());
        getSpriteAry().add(ballSprite);
    }

    public void initPaddleSprite() {
        paddleSprite = new PaddleSprite();
        paddleSprite.initSprite();
        getSpriteAry().add(paddleSprite);
    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {
        currScore = Consts.SCORE_UNIT * numBrickHit
                - Consts.PENALTY_LOST_BALL * numBallLost;
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("Score: " + currScore, 20, 20);
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(Consts.MAXX / 2 - 110, Consts.MAXY / 2 - 50, 200, 100);
        g2d.drawString("Game Terminates", 140, Consts.MAXY / 2);
        g2d.setColor(Color.BLUE);
        g2d.drawString("Start A New Game By Clicking The Paddle",
                40, Consts.MAXY / 2 + 30);
        addMouseListener(new NewGameListener());
    }

    // click the paddle to re-start a new game.
    class NewGameListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if ((evt.getX() > (Consts.MAXX / 2 - paddleSprite.getWidth() / 2))
                    && (evt.getX() < (Consts.MAXX / 2 + paddleSprite.getWidth() / 2))
                    && (evt.getY() > (Consts.MAXY - Consts.PADDLE_INIT_Y))
                    && (evt.getY() < (Consts.MAXY - Consts.PADDLE_INIT_Y
                    + Consts.PADDLE_H))) {
                renewGame();
            }
        }
    }

    class LocChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            switch (evt.getPropertyName()) {
                case "newP":
                    Point newL = (Point) evt.getNewValue();
                    Rectangle ballRect = new Rectangle((int) newL.getX(),
                            (int) newL.getY(), 2 * Consts.BALL_RADIUS,
                            2 * Consts.BALL_RADIUS);
                    Point ballLeft = new Point((int) ballRect.getX(),
                            (int) (ballRect.getY() + ballRect.getHeight() / 2));
                    Point ballRight = new Point(
                            (int) (ballRect.getX() + ballRect.getWidth()),
                            (int) (ballRect.getY() + ballRect.getHeight() / 2));
                    Point ballTop = new Point(
                            (int) (ballRect.getX() + ballRect.getWidth() / 2),
                            (int) (ballRect.getY()));
                    Point ballBottom = new Point(
                            (int) (ballRect.getX() + ballRect.getWidth() / 2),
                            (int) (ballRect.getY() + ballRect.getHeight()));
                    // ball hits bricks?
                    BrickSprite aBrick;
                    ArrayList<AbsSpriteImage> aryList = getSpriteAry();
                    String hitPosn; // = "noHit";
                    for (AbsSpriteImage element : aryList) {
                        hitPosn = "noHit";
                        // only it is a brick for the checking
                        if (element instanceof BrickSprite) {
                            aBrick = (BrickSprite) element;
                            if (aBrick.isVisible()) { // it exists
                                Rectangle brickRect = new Rectangle(aBrick.getX(),
                                        aBrick.getY(), aBrick.getWidth(),
                                        aBrick.getHeight());
                                if (brickRect.contains(ballLeft)) {
                                    aBrick.setVisible(false);
                                    hitPosn = "hitX";
                                } else if (brickRect.contains(ballRight)) {
                                    aBrick.setVisible(false);
                                    hitPosn = "hitX";
                                } else if (brickRect.contains(ballTop)) {
                                    aBrick.setVisible(false);
                                    hitPosn = "hitY";
                                } else if (brickRect.contains(ballBottom)) {
                                    aBrick.setVisible(false);
                                    hitPosn = "hitY";
                                }
                                if (!(hitPosn.equals("noHit"))) {
                                    AllClips.getClips(Consts.HIT_BRICK).play();
                                    // whithout the following statement, some
                                    // sound won't be played (?)
                                    //new AllClips();
                                    ballSprite.hitBrickResetVelocity(hitPosn);
                                    numBrickHit++;
                                    if (numBrickHit == Consts.BRICKS_PER_ROW
                                            * Consts.MAX_ROWS) {
                                        resetBallAndPaddle();
                                        stopGame();
                                    }
                                    break; // ones hit, don't hit other brick
                                }
                            } else { // the element is not valid, go to the next
                            }
                        }
                    }
                    // ball hits the paddle?
                    hitPosn = "noHit"; // don't forget re-initialize it
                    Rectangle paddleRect = new Rectangle(paddleSprite.getX(),
                            paddleSprite.getY(), paddleSprite.getWidth(),
                            paddleSprite.getHeight());
                    if (paddleRect.contains(ballLeft)) {
                        hitPosn = "hitX";
                    } else if (paddleRect.contains(ballRight)) {
                        hitPosn = "hitX";
                    } else if (paddleRect.contains(ballBottom)) {
                        hitPosn = "hitY";
                    }
                    if (!(hitPosn.equals("noHit"))) {
                        ballSprite.hitBrickResetVelocity(hitPosn);
                        AllClips.getClips(Consts.HIT_PADDLE).play();
                    }
                    break;
                case "initP":
                    AllClips.getClips(Consts.BALL_LOST).play();
                    resetBallAndPaddle();
                    numBallLost++;
                    if (numBallLost > Consts.MAX_LOST) {
                        stopGame();
                    }
                    break;
            }
        }
    }

    public void resetBallAndPaddle() {
        // there are two mouseMotionListeners, remove the 
        // PaddleMovedListener that is in the array[1], which is
        // PaddleMovedListener so that the paddle won't move
        if (getMouseMotionListeners().length == 2) {
            removeMouseMotionListener(getMouseMotionListeners()[1]);
        }
        paddleSprite.initXY();
        ballSprite.initXY();
    }

    class PaddleDraggedListener extends MouseMotionAdapter {

        @Override
        public void mouseDragged(MouseEvent evt) {
            paddleSprite.setX(evt.getX() - Consts.PADDLE_W / 2);
            ballSprite.setX(evt.getX() - Consts.BALL_RADIUS);
        }
    }

    class PaddleMovedListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent evt) {
            paddleSprite.setX(evt.getX() - Consts.PADDLE_W / 2);
        }
    }

    // release mouse to make the ball bouncing and to let the paddle
    // follow the mouse movement
    class MouseReleasedListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent evt) {
            ballSprite.setActive(true); // ball starts animation
            // making ball move up
            ballSprite.setDx(ballSprite.getDx());
            if (ballSprite.getDy() < 0) {
                ballSprite.setDy(ballSprite.getDy());
            } else {
                ballSprite.setDy(-ballSprite.getDy());
            }
            // switch from paddle dragged event to paddle moved event
            if (getMouseMotionListeners().length == 1) {
                addMouseMotionListener(new PaddleMovedListener());
            }
        }
    }
}
