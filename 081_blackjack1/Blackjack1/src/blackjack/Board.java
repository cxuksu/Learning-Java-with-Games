/*
 * Board.java - A class defines the board.
 */
package blackjack;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Board extends JPanel {

    private Card aCard;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        this.setPreferredSize(new Dimension(Consts.CV_WIDTH, Consts.BOARD_H));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        aCard.paintCard(g2d);
    }

    public void setaCard(Card aCard) {
        this.aCard = aCard;
    }
}
