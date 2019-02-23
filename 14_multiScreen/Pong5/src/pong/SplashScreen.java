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

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SplashScreen() {
        setLayout(new BorderLayout());
        initComponent();
    }

    private void initComponent() {
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
        g2d.drawString(
                "A bouncing ball is bounced by the up, left, and bottom edges ", 50, 50);
        g2d.drawString(
                "of the playing field and is also bounced by the paddle on the ", 50, 70);
        g2d.drawString(
                "right side. The game player moves the paddle up or down to ", 50, 90);
        g2d.drawString(
                "bounce the ball and prevents the ball from hitting the right", 50, 110);
        g2d.drawString(
                "edge of the playing field. ", 50, 130);
        g2d.drawString(
                "Whenever the game player bounced the ball, he/she gain a ", 50, 160);
        g2d.drawString(
                "score; but if he/she lost bouncing the ball and allowed the ", 50, 180);
        g2d.drawString(
                "ball hitting on the right edge of the playing field, the player ", 50, 200);
        g2d.drawString(
                "is lost the game.", 50, 220);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startBtn) {
            cardLayout.show(parent, "GameCanvas");
            // the card requires focus when it is shown
            gameCanvas.requestFocusInWindow();
            // reset the game by replacing the ball
            gameCanvas.setTheBall(new Ball());
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
