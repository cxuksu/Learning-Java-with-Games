/*
 * PlayerPanel.java - A class defines a player's panel for buttons of "New Game"
 * and "Quit".
 */
package agnes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private JLabel msgLbl;
    private JButton newGameBtn;
    private JButton quitBtn;
    private GameCanvas gameCanvas;
    private MyActionListener myActionListener;
    private boolean gameTerminate = false;

    public PlayerPanel() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH, Consts.PLAYER_PANEL_H));
        initPanel();
        addMouseListener(new HidePanelMouseAdapter());
    }

    private void initPanel() {
        myActionListener = new MyActionListener();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        add(Box.createHorizontalGlue());
        
        msgLbl = new JLabel("You won!!! Congratulations!!!");
        msgLbl.setForeground(Color.red);
        newGameBtn = new JButton("New Game");
        quitBtn = new JButton("Quit");
        newGameBtn.addMouseListener(new HidePanelMouseAdapter());
        newGameBtn.addActionListener(myActionListener);
        quitBtn.addMouseListener(new HidePanelMouseAdapter());
        quitBtn.addActionListener(myActionListener);
        
        add(msgLbl);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(newGameBtn);
        add(Box.createRigidArea(new Dimension(8, 0)));
        add(quitBtn);

        msgLbl.setVisible(false);
        newGameBtn.setVisible(false);
        quitBtn.setVisible(false);
    }
    
    class HidePanelMouseAdapter extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent evt) {
            newGameBtn.setVisible(true);
            quitBtn.setVisible(true);
        }
        
        @Override
        public void mouseExited(MouseEvent evt) {
            newGameBtn.setVisible(false);
            quitBtn.setVisible(false);
        }
    }
    
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == newGameBtn) {
                if (gameCanvas != null) {
                    gameCanvas.renewGame(); 
                }
            } else if (evt.getSource() == quitBtn) {
                System.exit(0);
            }
        }
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void setGameTerminate(boolean gameTerminate) {
        this.gameTerminate = gameTerminate;
        if (this.gameTerminate) {
            msgLbl.setVisible(true);
            newGameBtn.setVisible(true);
            quitBtn.setVisible(true);
        } else {
            msgLbl.setVisible(false);
            newGameBtn.setVisible(false);
            quitBtn.setVisible(false);
        }
    }
}
