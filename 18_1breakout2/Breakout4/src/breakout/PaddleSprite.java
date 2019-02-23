/*
 * PaddleSprite.java - A class that implements a paddle sprite. It function
 * is to prevent the ball falling down to the bottom edge of the game canvas.
 */
package breakout;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class PaddleSprite extends AbsSpriteImage {
    
    public PaddleSprite() {
        super();
    }
    
    @Override
    public void initSprite() {
        BufferedImage paddleImg = null;
        try {
            paddleImg = ImageIO.read(getClass().getResource("../images/paddle.gif"));
        } catch(IOException ex) {
            
        }
        setActive(true);
        setVisible(true);
        initXY();
        setWidth(Consts.PADDLE_W);
        setHeight(Consts.PADDLE_H);
        setAnImage(paddleImg);
    }
    
    public void initXY() {
        setX((Consts.MAXX-Consts.MINX)/2 - Consts.PADDLE_W/2);
        setY(Consts.MAXY - Consts.PADDLE_INIT_Y);
    }
    
    @Override
    public void updateSprite() {
    }
}


