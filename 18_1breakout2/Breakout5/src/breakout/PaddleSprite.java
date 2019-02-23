/*
 * PaddleSprite.java - A class that implements a paddle sprite. It function
 * is to prevent the ball falling down to the bottom edge of the game canvas.
 */
package breakout;

import java.awt.Rectangle;
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
        } catch (IOException ex) {

        }
        setActive(true);
        setVisible(true);
        initXY();
        setWidth(Consts.PADDLE_W);
        setHeight(Consts.PADDLE_H);
        setAnImage(paddleImg);
    }

    public void initXY() {
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.PADDLE_W / 2);
        setY(Consts.MAXY - Consts.PADDLE_INIT_Y);
    }

    @Override
    public void updateSprite() {
    }

    public String hitPaddle(Rectangle ballRect) {
        String hitPosn = "noHit";
        int paddleMiddle = getX() + getWidth() / 2;
        int paddleTop = getY();
        int paddleLeft = getX();
        int paddleRight = getX() + getWidth();
        int ballTop = (int) (ballRect.getY());
        int ballBottom = (int) (ballRect.getY() + ballRect.getHeight());
        int ballLeft = (int) (ballRect.getX());
        int ballRight = (int) (ballRect.getX() + ballRect.getWidth());
        if ((ballBottom >= paddleTop) && (ballTop <= paddleTop)) {
            if ((ballLeft >= (paddleLeft - ballRect.getWidth() / 2))
                    && (ballRight <= paddleMiddle)) {
                hitPosn = "hitYL";
            } else if ((ballLeft >= paddleMiddle)
                    && (ballRight <= (paddleRight + ballRect.getWidth() / 2))) {
                hitPosn = "hitYR";
            } else if ((ballRight >= paddleLeft) && (ballLeft < paddleMiddle)) {
                hitPosn = "hitXL";
            } else if ((ballLeft <= paddleRight) && (ballRight > paddleMiddle)) {
                hitPosn = "hitXR";
            }
        } else if (ballTop >= paddleTop) {
            hitPosn = "noHit";
        }
        return hitPosn;
    }
}
