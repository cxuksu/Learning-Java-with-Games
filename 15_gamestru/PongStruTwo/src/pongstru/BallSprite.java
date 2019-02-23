/*
 * BallSprite.java - A class that implements a ball sprite.
 */
package pongstru;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author cxu
 */
public class BallSprite extends AbsSprite2D {

    private GameCanvas gameCanvas;

    public BallSprite() {
        super();
    }

    @Override
    public void initSprite() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setX(cvX - Consts.BALL_RADIUS);
        setY(cvY - Consts.BALL_RADIUS);
        setWidth(2 * Consts.BALL_RADIUS);
        setHeight(2 * Consts.BALL_RADIUS);
        setColor(Color.RED);
        setActive(true);
        setVisible(true);
        setDx(3);
        setDy(2);
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
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    public void detectCollision() {
        Rectangle paddleRect;
        Rectangle ballRect = new Rectangle(getX(), getY(),
                getWidth(), getHeight());

        // whether the sprite hits on any unit of the field-wall
        for (int i = 0; i < 4; i++) {
            if ((gameCanvas.getTheWall().getaWall()[i]).intersects(ballRect)) {
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
        if (paddleRect.intersects(ballRect)) {
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
