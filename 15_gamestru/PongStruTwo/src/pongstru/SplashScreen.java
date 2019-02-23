/*
 * SplashScreen.java -- A class that implements a splash screen.
 */

package pongstru;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class SplashScreen extends JPanel implements ActionListener {
    
    private JButton startBtn;
    private JButton quitBtn;
    private JPanel buttonPn;
    private ScreenPanel parent;
    private CardLayout cardLayout;
    private GameCanvas gameCanvas;
    private int paintY;
    
    public SplashScreen() {
        setLayout(new BorderLayout());
        initComponent();
    }
    
    private void initComponent() {
        buttonPn = new JPanel();
        buttonPn.setLayout(new FlowLayout()); //(1, 2, 10, 0));
        buttonPn.setPreferredSize(new Dimension(Consts.CV_WIDTH, 70));

        startBtn = new JButton("Start Game");
        startBtn.setPreferredSize(new Dimension(280, 50));
        startBtn.setFont(new Font("Times", Font.BOLD, 26));
        startBtn.setOpaque(true);
        startBtn.setBackground(Color.ORANGE);
        startBtn.addActionListener(this);
        buttonPn.add(startBtn);

        quitBtn = new JButton("Quit");
        quitBtn.setPreferredSize(new Dimension(120, 50));
        quitBtn.setFont(new Font("Times", Font.BOLD, 20));
        quitBtn.setOpaque(true);
        quitBtn.setBackground(Color.ORANGE);
        quitBtn.addActionListener(this);
        buttonPn.add(quitBtn);
        
        add(buttonPn, BorderLayout.SOUTH);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // describe the game 
        String script1 = 
            "Two bouncing objects are bounced by the up, left, and bottom edges " +
            "of the playing field and are also bounced by the paddle on the " +
            "right side. The game player moves the paddle up or down to " +
            "bounce the objects and prevent the objects from hitting the right" +
            "edge of the playing field. ";
        String script2 =
            "Whenever the game player bounced the objects, he/she gains " +
            "scores; but if he/she lost the two bouncing objects, the game is " +
            "over.";
        
        paintY = Consts.MINY_DIS;
        g2d.setFont(new Font("Edwardian Script ITC", Font.BOLD, 24));
        paintLongString(g2d, script1);
        paintY = paintY + 10; // make a gap between two scripts
        g2d.setFont(new Font("Times", Font.BOLD, 16));
        g2d.setColor(Color.BLUE);
        paintLongString(g2d, script2);
    }
    
    private void paintLongString(Graphics2D g, String paintStr) {
        // for displaying the description, Consts.java sets MARGIN = 20. 
        // Thus, MAXX_DIS and MINX_DIS are used
        int areaWidth = (Consts.MAXX_DIS) - (Consts.MINX_DIS);

        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();
        
        // concatinate words into a subStr and paint it centerized
        StringTokenizer st =
                new StringTokenizer(paintStr, " ");
        String subStr = "";

        paintY = paintY + charHeight;
        while(st.hasMoreTokens()) {
            String nextT = st.nextToken();
            int subStrW = fm.stringWidth(subStr) + fm.stringWidth(nextT);
            if (subStrW <= areaWidth) {
                subStr = subStr + nextT + " ";
            } else {
                paintY = paintY + Consts.NEXTLINE;
                int subStrWidth = fm.stringWidth(subStr);
                int midW = subStrWidth / 2;
                int startX = ((Consts.MAXX_DIS) - (Consts.MINX_DIS))/2 - midW;
                g.drawString(subStr, (Consts.MINX_DIS)+startX, paintY);
                subStr = nextT + " ";
            }
        }
        // for painting the last piece if any
        if (!subStr.equals(" ")) {
            paintY = paintY + Consts.NEXTLINE;
            int subStrWidth = fm.stringWidth(subStr);
            int midW = subStrWidth / 2;
            int startX = ((Consts.MAXX_DIS) - (Consts.MINX_DIS))/2 - midW;
            g.drawString(subStr, (Consts.MINX_DIS)+startX, paintY);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startBtn) {
            cardLayout.show(parent, "GameCanvas");
            // the card requires focus when it is shown
            gameCanvas.requestFocusInWindow();
            // clear all of sprites and re-instantiates all of them
            gameCanvas.initComponent();
            gameCanvas.startGame();
        } else if (evt.getSource() == quitBtn) {
            System.exit(0);
        }
    }

    @Override
    public ScreenPanel getParent() {
        return parent;
    }

    public void setParent(ScreenPanel parent) {
        this.parent = parent;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
