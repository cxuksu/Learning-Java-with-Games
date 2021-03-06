/*
 * AbsGameCanvas.java -- A abstract class that specifies a game canvas.
 */
package sumonescomp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public abstract class AbsGameCanvas extends JPanel implements Runnable {

    private ArrayList<AbsSprite> spriteAry;
    private Thread animThread;
    private int sleepTime; // thread sleeping time, ms
    private boolean playing;
    private boolean gameOver;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbsGameCanvas() {
        gameOver = false;
        spriteAry = new ArrayList<AbsSprite>();
        //initComponent();
        //startGame();
    }

    public abstract void initComponent();

    public abstract void paintCurrScore(Graphics2D g2d);

    public abstract void announceTermination(Graphics2D g2d);

    private void initAnimation() {
        if (animThread == null) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (isPlaying()) {
                updateComponent();
                repaint();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateComponent() {
        for (AbsSprite element : spriteAry) {
            if (element.isActive()) {
                (element).updateSprite();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (AbsSprite element : spriteAry) {
            if (element.isVisible()) {
                (element).paintSprite(g2d);
            }
        }
        if (gameOver) {
            announceTermination(g2d);
        }
    }

    public void startGame() {
        setPlaying(true);
        initAnimation();
    }

    public void pauseGame() {
        setPlaying(false);
    }

    public void resumeGame() {
        setPlaying(true);
    }

    public void stopGame() {
        setPlaying(false);
        setGameOver(true);
        if (animThread != null) {
            animThread.interrupt();
        }
        animThread = null;
    }

    public void renewGame() {
        setGameOver(false);
        initComponent();
        startGame();
    }

    public ArrayList<AbsSprite> getSpriteAry() {
        return spriteAry;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}

