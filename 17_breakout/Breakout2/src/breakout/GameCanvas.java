/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package breakout;

import java.awt.Point;
import java.awt.Rectangle;
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

    public GameCanvas() {
        super();
        initComponent();
        startGame();
    }

    private void initComponent() {
        brickImages = new BufferedImage[Consts.NUM_BRICK_IMAGES];
        initBrickImages();
        initBrickSprites();
        initBallSprite();
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
            aBrick = new BrickSprite();
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

    class LocChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ((evt.getPropertyName()).equals("newP")) {
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
                // ball hits bricks
                BrickSprite aBrick;
                ArrayList<AbsSpriteImage> aryList = getSpriteAry();
                String hitPosn = "noHit";
                for (AbsSpriteImage element : aryList) {
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
                                ballSprite.hitBrickResetVelocity(hitPosn);
                                break; // ones hit, don't hit other brick
                            }
                        } else { // the element is not valid, go to the next
                        }
                    }
                }
            }
        }
    }
}
