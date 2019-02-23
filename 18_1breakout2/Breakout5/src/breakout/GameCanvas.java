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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BrickGroup brickGroup;
    private BallSprite ballSprite;
    private PaddleSprite paddleSprite;
    private int numBrickHit;
    private int numBallLost;
    private int currScore;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        addMouseMotionListener(new PaddleDraggedListener());
        addMouseListener(new MouseReleasedListener());
    }

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void initComponent() {
        getSpriteAry().clear();
        initBrickGroup();
        initBallSprite();
        initPaddleSprite();
        currScore = 0;
        numBrickHit = 0;
        numBallLost = 0;
        new AllClips();
    }

    public void initBrickGroup() {
        brickGroup = new BrickGroup();
        brickGroup.initSprite();
        brickGroup.setVisible(true);
        getSpriteAry().add(brickGroup);
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
        g2d.drawString("Start A New Game If Clicking The Paddle",
                40, Consts.MAXY / 2 + 30);
        addMouseListener(new NewGameListener());
    }

    // click the paddle to re-start a new game.
    class NewGameListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            Rectangle paddleRect = new Rectangle(paddleSprite.getX(),
                    paddleSprite.getY(), paddleSprite.getWidth(),
                    paddleSprite.getHeight());
            Point clickP = new Point(evt.getX(), evt.getY());
            if (paddleRect.contains(clickP)) {
                renewGame();
            }
        }
    }

    class LocChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String hitDir;
            if ((evt.getPropertyName()).equals("newP")) {
                Point newL = (Point) evt.getNewValue();
                Rectangle ballRect = new Rectangle((int) newL.getX(),
                        (int) newL.getY(), 2 * Consts.BALL_RADIUS,
                        2 * Consts.BALL_RADIUS);
                // ball hits bricks
                hitDir = brickGroup.hitOneBrick(ballRect);
                if (!(hitDir.equals("noHit"))) {
                    ballSprite.hitBrickResetVelocity(hitDir);
                    numBrickHit++;
                    if (numBrickHit == Consts.BRICKS_PER_ROW
                            * Consts.MAX_ROWS) {
                        resetBallAndPaddle();
                        stopGame();
                    }
                }
                // ball hits paddle
                if (ballSprite.isActive()) {
                    hitDir = paddleSprite.hitPaddle(ballRect);
                    if (!(hitDir.equals("noHit"))) {
                        ballSprite.hitPaddleResetVelocity(hitDir);
                    }
                }
            } else if ((evt.getPropertyName()).equals("initP")) { // ball lost
                resetBallAndPaddle();
                numBallLost++;
                if (numBallLost > Consts.MAX_LOST) {
                    stopGame();
                }
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
