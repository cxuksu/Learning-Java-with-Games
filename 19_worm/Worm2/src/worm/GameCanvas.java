/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package worm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private int currScore;
    private Worm worm;
    private Treat treat;
    private boolean overlap;

    public GameCanvas() {
        super();
        setSleepTime(300);
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        new AllClips();
        overlap = true;
        initWorm();
        initTreat();
    }

    public void initWorm() {
        worm = new Worm();
        worm.initSprite();
        getSpriteAry().add(worm);
    }

    public void initTreat() {
        treat = new Treat();
        while (overlap) { // init treat until no overlap with worm
            treat.initSprite();
            overlap = detectOverlap();
        }
        getSpriteAry().add(treat);
    }

    public boolean detectOverlap() {
        boolean collide = false;
        Rectangle treatRect = new Rectangle(treat.getX(), getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        ArrayList wBody = worm.getWormBody();
        Rectangle wUnitRect;
        Point wUnitP;
        for (Object wBody1 : wBody) {
            wUnitP = (Point) wBody1;
            wUnitRect = new Rectangle(wUnitP.x, wUnitP.y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            if (treatRect.intersects(wUnitRect)) {
                collide = true;
            }
        }
        return collide;
    }
    
    @Override
    public void paintCurrScore(Graphics2D g2d) {
        currScore = 0; // adding a canculating expression
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
        // in addition, stop game and prepare renew game
        stopGame();
        addMouseListener(new NewGameListener());
    }

    // A listener for re-starting the game.
    // Assume it is based on a mouse click
    class NewGameListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (true) { // some condition
                renewGame();
            }
        }
    }
}
