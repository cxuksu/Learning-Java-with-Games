/*
 * WheelGUI.java - A class defines the theWheel component.
 */
package testwheel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class WheelGUI extends JPanel implements Runnable {

    private final TheWheel theWheel;
    private boolean turnWheel = false;
    private Thread animThread;
    private int period;

    public WheelGUI() {
        theWheel = new TheWheel();
    }

    public void start() {
        if (animThread == null) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (turnWheel) {
                updateAll();
                repaint();
                Thread.sleep(period);
            }
        } catch (InterruptedException iex) {
        }
    }

    public void updateAll() {
        theWheel.updateWheel();
        period = (int) (period * 1.1);
        if (period >= 1200) { // a defined threshold for stoping the theWheel
            stopWheel();
            theWheel.stopStatistic();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        theWheel.paintWheel(g2d);
    }
    
    public void stopWheel() {
        turnWheel = false;
        animThread.interrupt();
        animThread = null;
    }

    public void reStart() {
        // reset variables and call start() again
        turnWheel = true;
        period = 50;
        theWheel.reStartWheel();
        start();
    }
}
