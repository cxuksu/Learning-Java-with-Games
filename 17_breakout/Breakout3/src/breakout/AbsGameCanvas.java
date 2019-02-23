/*
 * AbsGameCanvas.java -- A abstract class that specifies a game canvas.
 */
package breakout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public abstract class AbsGameCanvas extends JPanel implements Runnable {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<AbsSpriteImage> spriteAry;
    private Thread animation;
    private boolean playing;

    public AbsGameCanvas() {
        spriteAry = new ArrayList<AbsSpriteImage>();
    }

    private void initAnimation() {
        if (animation == null) {
            animation = new Thread(this);
            animation.start();
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (isPlaying()) {
                updateComponent();
                repaint();
                Thread.sleep(8);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateComponent() {
        for (AbsSpriteImage element : spriteAry) {
            if (element.isActive()) {
                (element).updateSprite();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (AbsSpriteImage element : spriteAry) {
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

    public void stopGame() { // clean up the thread
        setPlaying(false);
        animation.interrupt();
        animation = null;
    }
    
    public ArrayList<AbsSpriteImage> getSpriteAry() {
        return spriteAry;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
