/*
 * AbsGameCanvas.java -- A abstract class that specifies a game canvas.
 */
package wormgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public abstract class AbsGameCanvas extends JPanel implements Runnable {

    private ArrayList<AbsSprite2D> spriteAry;
    private Thread animThread;
    private boolean playing;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbsGameCanvas() {
        spriteAry = new ArrayList<>();
    }

    public abstract void initComponent();
    public abstract void terminateGame();
    
    private void initAnimation() {
        if (animThread == null) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (playing) {
                    updateComponent();
                }
                repaint();
                Thread.sleep(200);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateComponent() {
        for (AbsSprite2D element : spriteAry) {
            (element).updateSprite();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (AbsSprite2D element : spriteAry) {
            (element).paintSprite(g2d);
        }
    }

    public void startGame() {
        initComponent();
        setPlaying(true);
        initAnimation();
    }
    
    public void pauseGame() {
        setPlaying(false);
    }
    
    public void resumeGame() {
        setPlaying(true);
    }
    
    public ArrayList<AbsSprite2D> getSpriteAry() {
        return spriteAry;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Thread getAnimThread() {
        return animThread;
    }

    public void setAnimThread(Thread animThread) {
        this.animThread = animThread;
    }
}
