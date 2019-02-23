/*
 * RectSprite.java - A class defines a rectangle sprite.
 */
package pongstru;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author cxu
 */
public class RectSprite extends AbsSprite2D {

    private int maxSpeed;
    private int ranSign;
    private GameCanvas gameCanvas;

    public RectSprite() {
        super();
    }

    @Override
    public void initSprite() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setX(cvX - Consts.RECT_W / 2);
        setY(cvY - Consts.RECT_H / 2);
        setWidth(Consts.RECT_W);
        setHeight(Consts.RECT_H);
        setColor(Color.BLUE);
        setActive(true);
        setVisible(true);

        // randomly initializing the speed of the rectangle object
        maxSpeed = 6;
        setDx((int) (Math.random() * maxSpeed) + 1); // [1, 6]
        ranSign = (int) (Math.random() * 2); // [0, 1]
        if (ranSign == 0) {
            setDx(-getDx());
        }
        setDy((int) (Math.random() * maxSpeed) + 1); // [1, 6]
        ranSign = (int) (Math.random() * 2); // [0, 1]
        if (ranSign == 0) {
            setDy(-getDy());
        }
    }

    @Override
    public void updateSprite() {
        setX(getX() + getDx());
        setY(getY() + getDy());
        detectCollision();
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void detectCollision() {
        Rectangle paddleRect;
        Rectangle rect = new Rectangle(getX(), getY(),
                getWidth(), getHeight());

        // whether the sprite hits on any unit of the bouncing wall
        for (int i = 0; i < 4; i++) {
            if ((gameCanvas.getTheWall().getaWall()[i]).intersects(rect)) {
                switch (i) {
                    case 0: // top
                    case 1: // bottom
                        //ballSprite.setDy(-ballSprite.getDy());
                        setDy(-getDy());
                        break;
                    case 2: // left
                        setDx(-getDx());
                        break;
                    case 3: // the ball is lost
                        if (isActive()) {
                            setActive(false);
                        }
                        if (isVisible()) {
                            setVisible(false);
                        }
                        gameCanvas.resetGame();
                        break;
                }
            }
        }
        // whether the element collides the paddle
        Paddle aPaddle = gameCanvas.getThePaddle();
        paddleRect = new Rectangle(aPaddle.getX(), aPaddle.getY(),
                Consts.PADDLE_WIDTH, Consts.PADDLE_HEIGHT);
        if (paddleRect.intersects(rect)) {
            gameCanvas.setHitPaddle(gameCanvas.getHitPaddle() + 1);
            setDx(-getDx());
        }
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
