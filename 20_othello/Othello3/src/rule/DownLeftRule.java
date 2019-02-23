/*
 * DownLeftFule.java
 */
package rule;

import java.awt.Color;
import othello.Consts;

/**
 *
 * @author cxu
 */
public class DownLeftRule extends AbsRule {

    private int startRow, startCol;
    private int untilRow, untilCol;

    public DownLeftRule() {
        super();
    }

    @Override
    public boolean isValid(int newRow, int newCol) {
        boolean valid = false;
        Color theColor = getCurPieceColor();
        if (verify(newRow, newCol, theColor)) {
            valid = true;
        }
        return valid;
    }

    public boolean verify(int newRow, int newCol, Color theColor) {
        boolean flag = false;
        if (((newRow + 1) < Consts.MAXCELL) && ((newCol - 1) >= 0)
                && ((getPieceBoard())[newRow + 1][newCol - 1] != null)) {
            if (!(pieceBoard[newRow + 1][newCol - 1].getColor().equals(theColor))) {
                // measure start cell when flipFlag is true
                if (getFlipFlag()) {
                    startRow = newRow + 1;
                    startCol = newCol - 1;
                    untilRow = newRow + 1;
                    untilCol = newCol - 1;
                }
                int row = newRow + 2;
                int col = newCol - 2;
                while ((row < Consts.MAXCELL) && (col >= 0)
                        && (pieceBoard[row][col] != null)) {
                    if (pieceBoard[row][col].getColor().equals(theColor)) {
                        // measure until cell when flipFlag is ture 
                        if (getFlipFlag()) {
                            untilRow = row;
                            untilCol = col;
                        }
                        flag = true;
                        break;
                    } else {
                        row = row + 1;
                        col = col - 1;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void flipPiece() {
        if (getFlipFlag()) {
            Color theColor = getCurPieceColor();
            int row = startRow;
            int col = startCol;
            while ((row < untilRow) && (col > untilCol)) {
                pieceBoard[row][col].setColor(theColor);
                row++;
                col--;
            }
        }
        untilRow = startRow;
        untilCol = startCol;
    }
}
