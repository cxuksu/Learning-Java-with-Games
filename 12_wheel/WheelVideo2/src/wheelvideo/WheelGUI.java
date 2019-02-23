/*
 * WheelGUI.java - A class rotates the wheel in using the animation technique.
 */
package wheelvideo;

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
    private String strCurrScore;

    private Board board;
    private PlayerPanel playerPanel;

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
            // statements that should be executed after the thread is
            // interrupted must be placed here
            strCurrScore = theWheel.stopStatistic();
            sendCurrScore(strCurrScore);
        }
    }

    public void updateAll() {
        theWheel.updateWheel();
        period = (int) (period * 1.1);
        if (period >= 1200) { // a defined threshold for stoping the theWheel
            stopWheel();
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
        // these statement won't guarantee the wheel stops at the position
        // it should be stopped. Thus, no any other statements can be 
        // correctly executed at this method. Other statements should be
        // added to the catch block of the game loop
    }

    public void reStart() {
        // reset variables and call start() again
        turnWheel = true;
        period = 50;
        theWheel.reStartWheel();
        start();
    }

    public void sendCurrScore(String score) {
        int intCurrScore;

        if (score.equals("Zero")) {
            intCurrScore = -1;
        } else {
            intCurrScore = Integer.parseInt(score);
        }
        playerPanel.setCurrScoreInt(intCurrScore);
        if (intCurrScore != -1) { // if current score is Bankrupt, no guess
            board.inputGuess();
        }
    }

    // to allow the player inputs the guess char
    public void setBoard(Board board) {
        this.board = board;
    }

    // to pass the current score to playerPanel
    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
}
