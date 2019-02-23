/*
 * AbsGameCanvas.java - An abstract class that specifies common features in
 * a game canvas. It is a superclass of the concrete class GameCanvas.java.
 */
package symball;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public abstract class AbsGameCanvas extends JPanel implements Runnable {

    private ArrayList<AbsSprite2D> spriteAry;
    protected Thread animThread;
    private boolean playing;

    public AbsGameCanvas() {
        spriteAry = new ArrayList<>();
        //initAnimation() invoked by startGame() defined in AbsGameCanvas.java
    }

    private void initAnimation() {
        if (animThread == null) {
            animThread = new Thread(this);
        }
        animThread.start();
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (isPlaying()) {
                updateComponent();
                repaint();
                Thread.sleep(60);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateComponent() {
        for (AbsSprite2D element : spriteAry) {
            if (element.isActive()) {
                (element).updateSprite();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (AbsSprite2D element : spriteAry) {
            if (element.isVisible()) {
                (element).paintSprite(g2d);
            }
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
        animThread.interrupt();
        animThread = null;
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
}
