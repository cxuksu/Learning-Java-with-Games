/*
 * CursorMark.java - A class defines a square cursor on the board.
 */
package sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author cxu
 */
public class CursorMark extends Rectangle {

    private int moveRow, moveCol;

    public CursorMark() {
        moveRow = 0;
        moveCol = 0;
    }

    public void paintCursor(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int) getX(), (int) getY(), (int) getWidth(),
                (int) getHeight());
    }

    public void updatePosition(int position) {
        switch (position) {
            case Consts.UP:
                if (moveRow > 0) {
                    setLocation((int) getX(), (int) (getY() - getHeight()));
                    moveRow--;
                }
                break;
            case Consts.DOWN:
                if (moveRow < (Consts.MAX_CELLS - 1)) {
                    setLocation((int) getX(), (int) (getY() + getHeight()));
                    moveRow++;
                }
                break;
            case Consts.LEFT:
                if (moveCol > 0) {
                    setLocation((int) (getX() - getWidth()), (int) getY());
                    moveCol--;
                }
                break;
            case Consts.RIGHT:
                if (moveCol < (Consts.MAX_CELLS - 1)) {
                    setLocation((int) (getX() + getWidth()), (int) getY());
                    moveCol++;
                }
                break;
            default:
                break;
        }
    }

    public int getMoveRow() {
        return moveRow;
    }

    public int getMoveCol() {
        return moveCol;
    }
}
