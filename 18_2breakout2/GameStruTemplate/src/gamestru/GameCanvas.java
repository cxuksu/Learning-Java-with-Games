/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package gamestru;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private int currScore;

    public GameCanvas() {
        super();
        setSleepTime(300); // set the thread sleep time for different games 
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        new AllClips();
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
    
    @Override
    public void renewGame() {
        // add more statements if a specific game needs more codes
        // for example, in the game Breakout5:
        //stopGame();
        // remove NewGameListener and PaddleMovedListener
        //removeMouseListener(getMouseListeners()[1]);
        //removeMouseMotionListener(getMouseMotionListeners()[1]);
        super.renewGame();
    }
}
