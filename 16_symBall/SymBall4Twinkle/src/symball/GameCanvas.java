/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package symball;

import java.awt.Graphics2D;

public class GameCanvas extends AbsGameCanvas {

    private int midx = (Consts.MAXX - Consts.MINX) / 2;
    private int midy = (Consts.MAXY - Consts.MINY) / 2;
    private int step = 0;
    private final int tSTEPS = 8;
    private Graphics2D g2d;

    public GameCanvas() {
        super();
        initComponent();
    }

    private void initComponent() {
        getSpriteAry().clear();
        // all ballSprites are initialized by the run() method
        startGame(); // set playing true and start thread in AbsGameCanvas
    }

    // This application needs to dynamically add balls in eight steps and
    // each step should be displayed. It is similar with update-repaint-pause
    // process. Thus, we need the following overridden method run().
    // Above startGame() call activates the thread in the superclass 
    // AbsGameCanvas.java. Due to the "dynamic binding", the system searches 
    // for a run() method from the bottom of the inheritance hierarchy. It
    // finds this overridden run() first.
    // After this run() finishes, it invokes the run() in the superclass.
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (step < tSTEPS) {
                // all balls are inactive so that no animation
                growBalls();
                repaint();
                Thread.sleep(Consts.SLEEP_TIME);
                step = step + 1;
            }
            super.run();
        } catch (InterruptedException ex) {
        }
    }

    public void growBalls() {
        int length = getSpriteAry().size();
        if (length == 0) {
            addBall(midx, midy); // the central ball
        }
        int idx = 0;
        while (idx < length) {
            int x = (getSpriteAry().get(idx)).getX();
            int y = (getSpriteAry().get(idx)).getY();
            if (x == midx) {
                addRightBall(x + Consts.GAP, y);
                addLeftBall(x - Consts.GAP, y);
                addBall(x, y - Consts.GAP);
                addBall(x, y + Consts.GAP);
            } else if (x > midx) {
                addRightBall(x + Consts.GAP, y);
                addRightBall(x - Consts.GAP, y);
                addRightBall(x, y - Consts.GAP);
                addRightBall(x, y + Consts.GAP);
            } else if (x < midx) {
                addLeftBall(x + Consts.GAP, y);
                addLeftBall(x - Consts.GAP, y);
                addLeftBall(x, y - Consts.GAP);
                addLeftBall(x, y + Consts.GAP);
            }
            idx++;
        }
    }

    public void addRightBall(int x, int y) {
        BallSprite newBall;
        if (allowAdd(x, y)) {
            newBall = new ToRightBall();
            insertAry(newBall, x, y);
        }
    }

    public void addLeftBall(int x, int y) {
        BallSprite newBall;
        if (allowAdd(x, y)) {
            newBall = new ToLeftBall();
            insertAry(newBall, x, y);
        }
    }

    public void addBall(int x, int y) {
        BallSprite newBall;
        if (allowAdd(x, y)) {
            newBall = new BallSprite();
            insertAry(newBall, x, y);
        }
    }

    public void insertAry(BallSprite newBall, int x, int y) {
        newBall.setX(x);
        newBall.setY(y);
        newBall.setActive(true);
        // assign each ball a Graphics for painting random color
        g2d = (Graphics2D) getGraphics();
        newBall.setGra(g2d);
        newBall.ballStart(); // start changing its color
        getSpriteAry().add(newBall);
    }

    public boolean allowAdd(int x, int y) {
        // don't add a same ball multiple times
        boolean toAdd = true;
        for (int i = 0; i < getSpriteAry().size(); i++) {
            BallSprite ball = (BallSprite) getSpriteAry().get(i);
            if ((ball.getX() == x) && (ball.getY() == y)) {
                toAdd = false; // once find a same ball, don't add it
                break;
            }
        }
        return toAdd;
    }
}
