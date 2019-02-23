/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package symball;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BallSprite ballSprite;
    private int midx = (Consts.MAXX - Consts.MINX) / 2;
    private int midy = (Consts.MAXY - Consts.MINY) / 2;
    private int step = 0;
    private final int tSTEPS = 8;

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
            activateBalls(); // setActive(true) for all of balls
            super.run();
        } catch (InterruptedException ex) {
        }
    }

    public void growBalls() {
        int length = getSpriteAry().size();
        if (length == 0) {
            addBall(midx, midy);
        }
        int idx = 0;
        while (idx < length) {
            BallSprite ball = (BallSprite) getSpriteAry().get(idx);
            int x = ball.getX();
            int y = ball.getY();
            if (x == midx) {
                addBall(x + Consts.GAP, y);
                addBall(x - Consts.GAP, y);
            } else if (x > midx) {
                addBall(x + Consts.GAP, y);
            } else if (x < midx) {
                addBall(x - Consts.GAP, y);
            }

            if (y == midy) {
                addBall(x, y + Consts.GAP);
                addBall(x, y - Consts.GAP);
            } else if (y > midy) {
                addBall(x, y + Consts.GAP);
            } else if (y < midy) {
                addBall(x, y - Consts.GAP);
            }
            idx++;
        }
    }

    public void addBall(int x, int y) {
        // don't add a same ball multiple times
        boolean toAdd = true;
        for (int i = 0; i < getSpriteAry().size(); i++) {
            BallSprite ball = (BallSprite) getSpriteAry().get(i);
            if ((ball.getX() == x) && (ball.getY() == y)) {
                toAdd = false; // once find a same ball, don't add it
                break;
            }
        }
        if (toAdd) {
            addBallSprite(x, y); // every ball has different (x, y)
        }
    }

    public void addBallSprite(int x, int y) {
        ballSprite = new BallSprite();
        ballSprite.initSprite(); // init all attributes
        ballSprite.setX(x); // set special attributes
        ballSprite.setY(y);
        ballSprite.setActive(false);
        getSpriteAry().add(ballSprite);
    }

    public void activateBalls() { // allow marching starting
        for (int i = 0; i < getSpriteAry().size(); i++) {
            getSpriteAry().get(i).setActive(true);
        }
    }
}
