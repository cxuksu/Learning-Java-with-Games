/*
 * WheelGUI.java - A class uses a Thread to rotate a radius line.
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

    private final RadiusLine radiusLine;
    private boolean turnWheel = false;
    private Thread animThread;
    private int period;

    public WheelGUI() {
        radiusLine = new RadiusLine();
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
        radiusLine.updateLine();
        period = (int) (period * 1.1);
        if (period >= 1000) { // a defined threshold for stoping the radiusLine
            stopLine();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        radiusLine.paintLine(g2d);
    }

    public void stopLine() {
        turnWheel = false;
        animThread.interrupt();
        animThread = null;
    }

    public void reStart() {
        // reset variables and call start() again
        turnWheel = true;
        period = 50;
        radiusLine.reStartLine();
        start();
    }
}
