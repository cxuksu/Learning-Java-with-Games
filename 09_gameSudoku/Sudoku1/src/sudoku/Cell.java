/*
 * Cell.java - A class defines one cell in the board for the game Sudoku.
 */
package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Cell {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int digit;

    public Cell() {
        width = Consts.CELL_W;
        height = Consts.CELL_H;
        color = Color.BLUE;
        digit = 0;
    }

    public void paintCell(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.drawRect(x, y, width, height);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 22));
        g2d.drawString("" + digit, x + Consts.CELL_LEFT, y + Consts.CELL_TOP);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
