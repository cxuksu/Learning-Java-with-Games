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
    private int guessed;
    private boolean guessCorrect;
    private boolean visible;

    public Cell() {
        width = Consts.CELL_W;
        height = Consts.CELL_H;
        color = Color.BLUE;
        digit = 0;
        visible = true;
        guessCorrect = false;
    }

    public void paintCell(Graphics2D g2d) {
        // show a rectangle in blue color
        g2d.setColor(color);
        g2d.drawRect(x, y, width, height);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 22));
        if (visible) {
            // show the initialized digit in blue color
            g2d.drawString("" + digit, x + Consts.CELL_LEFT, y + Consts.CELL_TOP);
        } else { // invisible
            // show the empty cell in yellow color
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x + 2, y + 2, width - 2, height - 2);
            // show the guessed digit
            if (guessed != 0) {
                if (!guessCorrect) { // in red color for haven't checked yet
                    g2d.setColor(Color.RED);
                } else { // in green color for a correct guessed digit
                    g2d.setColor(Color.GREEN);
                }
                g2d.drawString("" + guessed, x + Consts.CELL_LEFT, y + Consts.CELL_TOP);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getGuessed() {
        return guessed;
    }

    public void setGuessed(int guessed) {
        this.guessed = guessed;
    }
    
    public void setGuessCorrect(boolean guessCorrect) {
        this.guessCorrect = guessCorrect;
    }
}
