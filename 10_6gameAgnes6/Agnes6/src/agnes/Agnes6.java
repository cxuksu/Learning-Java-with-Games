/*
 * Agnes6.java - The main class of the game Agnes
 */
package agnes;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Agnes6 extends JFrame {

    public Agnes6() {
        setTitle("Game Agnes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        setLayout(new BorderLayout());
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv, BorderLayout.CENTER);
        
        PlayerPanel playerPanel = new PlayerPanel();
        playerPanel.setGameCanvas(gameCv);
        add(playerPanel, BorderLayout.SOUTH);
        
        gameCv.setPlayerPanel(playerPanel);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Agnes6();
    }
}
