/*
 * AbsGameCanvas.java -- A abstract class that specifies a game canvas.
 */
package pongextension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public abstract class AbsGameCanvas extends JPanel implements Runnable {

    private ArrayList<AbsSprite2D> spriteAry;
    private Thread animation;
    private boolean playing;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbsGameCanvas() {
        spriteAry = new ArrayList<>();
        initComponent();
        startGame();
    }

    public abstract void initComponent();
    
    private void initAnimation() {
        if (animation == null) {
            animation = new Thread(this);
            animation.start();
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
                Thread.sleep(60);
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
}
