/*
 * BrickGroup.java - A class that groups all brick sprites into 
 * an array so that it is easy to treat all bricks.
 */
package breakout;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class BrickGroup extends AbsSprite {

    @SuppressWarnings("FieldMayBeFinal")
    private BufferedImage[] brickImages; // holds 8 different brick images
    @SuppressWarnings("FieldMayBeFinal")
    private BrickSprite[] brickAry;
    private BrickSprite aBrick;
    @SuppressWarnings("FieldMayBeFinal")
    private int numBricks;

    public BrickGroup() {
        numBricks = Consts.BRICKS_PER_ROW * Consts.MAX_ROWS;
        brickImages = new BufferedImage[Consts.NUM_BRICK_IMAGES];
        brickAry = new BrickSprite[numBricks];
        initBrickImages();
    }

    private void initBrickImages() { // get and hold 8 brick images
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

    @Override
    public void initSprite() {
        // store bricks in a 1D array, but arrange them in a 2D format
        int x = 0;
        int y = 30; // leave a line for the value of global score
        int idx;
        for (int i = 0; i < numBricks; i++) {
            if ((i % Consts.BRICKS_PER_ROW == 0) && (i != 0)) {
                x = 0;
                y += Consts.BRICK_H; // move to next row
            }
            aBrick = new BrickSprite();
            aBrick.initSprite();
            aBrick.setX(x);
            x += Consts.BRICK_W; // move the coor x to the next position
            aBrick.setY(y); // stay at the same row
            idx = (int) (Math.random() * Consts.NUM_BRICK_IMAGES);
            aBrick.setAnImage(brickImages[idx]); // randomly pick up an image
            brickAry[i] = aBrick;
        }
    }

    @Override
    public void updateSprite() {
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        for (BrickSprite oneBrick : brickAry) {
            if (oneBrick.isVisible()) { // check each individual brick
                oneBrick.paintSprite(g2d);
            }
        }
    }

    public String hitOneBrick(Rectangle ballRect) {
        String hitDir = "noHit";
        for (BrickSprite oneBrick : brickAry) {
            hitDir = oneBrick.hitBrick(ballRect);
            if (!(hitDir.equals("noHit"))) {
                break;
            }
        }
        return hitDir;
    }
}
