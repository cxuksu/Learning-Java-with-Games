/*
 * SplashScreen.java -- A class that implements a splash screen.
 */
package pong;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private ScreenDeck parent;
    private CardLayout cardLayout;
    private GameCanvas gameCanvas;
    private int paintY;

    public SplashScreen() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        buttonPn = new JPanel();
        buttonPn.setLayout(new FlowLayout()); //(1, 2, 10, 0));
        buttonPn.setPreferredSize(new Dimension(Consts.CV_WIDTH, 65));

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
            "Two bouncing balls are bounced by the up, left, and bottom edges " +
            "of the playing field and are also bounced by the paddle on the " +
            "right side. The game player moves the paddle up or down to " +
            "bounce the balls and prevents the balls from hitting the right" +
            "edge of the playing field. ";
        String script2 =
            "Whenever the game player bounced the balls, he/she gains " +
            "scores. When the score reaches threshould1 or threshould2, " +
            "two balls' speeds are increased. Once the player lost the " +
            "two bouncing balls, the game is over.";

        PaintStrBean paintBean = new PaintStrBean();
        paintBean.setPaintAreaMinX(Consts.MINX_DIS);
        paintBean.setPaintAreaMaxX(Consts.MAXX_DIS);

        paintY = Consts.MINY_DIS - 15;
        paintBean.setPaintY(paintY);
        g2d.setFont(new Font("Edwardian Script ITC", Font.BOLD, 24));
        paintBean.paintLongString(g2d, script1);

        paintY = paintBean.getPaintY() + 10; // make a gap between two scripts
        paintBean.setPaintY(paintY);
        g2d.setFont(new Font("Times", Font.BOLD, 16));
        g2d.setColor(Color.BLUE);
        paintBean.paintLongString(g2d, script2);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startBtn) {
            cardLayout.show(parent, "GameCanvas");
            // the card requires focus when it is shown
            gameCanvas.requestFocusInWindow();
            // reset the game by calling initBall()
            gameCanvas.initBall();
        } else if (evt.getSource() == quitBtn) {
            System.exit(0);
        }
    }

    @Override
    public ScreenDeck getParent() {
        return parent;
    }

    public void setParent(ScreenDeck parent) {
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
