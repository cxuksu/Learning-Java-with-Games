/*
 * SplashScreen.java -- A class that implements a splash screen.
 */

package wormgame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class SplashScreen extends JPanel implements ActionListener {
    private JButton startButton;
    private ControlPanel parent;
    private CardLayout cardLayout;
    private GameCanvas gameCanvas;
    private int paintY;
    
    public SplashScreen() {
        setLayout(new BorderLayout());
        initComponent();
    }
    
    private void initComponent() {
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(50, 50));
        startButton.setFont(new Font("Times", Font.BOLD, 26));
        startButton.setOpaque(true);
        startButton.setBackground(Color.ORANGE);
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // describe the game 
        String script1 = 
            "A bouncing ball is bounced by the up, left, and bottom edges " +
            "of the playing field and is also bounced by the paddle on the " +
            "right side. The game player moves the paddle up or down to " +
            "bounce the ball and prevents the ball from hitting the right" +
            "edge of the playing field. ";
        String script2 =
            "Whenever the game player bounced the ball, he/she gain a " +
            "score; but if he/she lost bouncing the ball and allowed the " +
            "ball hitting on the right edge of the playing field, the player " +
            "is lost the game.";
        
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
        ArrayList<AbsSprite2D> theList;
        if (evt.getSource() == startButton) {
            cardLayout.show(parent, "GameCanvas");
            // the card requires focus when it is shown
            gameCanvas.requestFocusInWindow();
            // reset the game by initializing sprites
            theList = gameCanvas.getSpriteAry();
            for (AbsSprite2D element : theList) {
                element.initSprite();
            }
            // reset the playing to true to resume the game
            gameCanvas.startGame();
        }
    }

    @Override
    public ControlPanel getParent() {
        return parent;
    }

    public void setParent(ControlPanel parent) {
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
