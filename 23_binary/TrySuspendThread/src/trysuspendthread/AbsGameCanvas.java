/*
 * AbsGameCanvas.java -- A abstract class that specify a game canvas.
 */
package trysuspendthread;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public abstract class AbsGameCanvas extends JPanel implements Runnable {

    protected ArrayList<AbsSprite> spriteAry;
    protected Thread animation;
    protected boolean running;
    protected boolean gameOver;
    private SuspendRequestor suspender;

    public AbsGameCanvas() {
        spriteAry = new ArrayList<AbsSprite>();
        running = true;
        gameOver = false;
        suspender = new SuspendRequestor();
    }

    public abstract void initComponent();
    
    public void startGame() {
        if (animation == null) {
            animation = new Thread(this);   
        }
        animation.start();
    }

    @Override
    public void run() {
        try {
            while (running) {
                updateComponent();
                repaint();
                Thread.sleep(10);
            }
            //animation = null;
            //System.out.println("Thread is paused");
            //for (int i = 0; i < 100; i++) {}
            //suspender.requestResume();
            //setRunning(true);
            //animation.start();
            
        } catch (Exception ex) {
        }
    }

    public void updateComponent() {
        for (int i = 0; i < spriteAry.size(); i++) {
            (spriteAry.get(i)).updateSprite();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < spriteAry.size(); i++) {
            (spriteAry.get(i)).paintSprite(g2d);
        }
    }

    public ArrayList<AbsSprite> getSpriteAry() {
        return spriteAry;
    }
    
    public void terminateGame() {
        running = false;
        gameOver = true;
    }
    
    public void resetGame() {
        running = true;
        gameOver = false;
        animation = null;
        spriteAry.clear();
    }
    
    public Thread getAnimation() {
        return animation;
    }

    public void setAnimation(Thread animation) {
        this.animation = animation;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
