/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package breakout;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BufferedImage[] brickImages; // holds 8 different brick images

    public GameCanvas() {
        super();
        initComponent();
    }

    private void initComponent() {
        brickImages = new BufferedImage[Consts.NUM_BRICK_IMAGES];
        initBrickImages();
        initBrickSprites();
        startGame();
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
}
