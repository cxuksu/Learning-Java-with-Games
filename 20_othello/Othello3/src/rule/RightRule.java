/*
 * RightRule.java
 */
package rule;

import java.awt.Color;
import othello.Consts;

public class RightRule extends AbsRule {

    private int startRow, startCol;
    private int untilRow, untilCol;

    public RightRule() {
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
        if (((newCol + 1) < Consts.MAXCELL)
                && ((getPieceBoard())[newRow][newCol + 1] != null)) {
            if (!(pieceBoard[newRow][newCol + 1].getColor().equals(theColor))) {
                if (getFlipFlag()) {
                    startRow = newRow;
                    startCol = newCol + 1;
                    untilRow = newRow;
                    untilCol = newCol + 1;
                }
                int col = newCol + 2;
                while ((col < Consts.MAXCELL)
                        && (pieceBoard[newRow][col] != null)) {
                    if (pieceBoard[newRow][col].getColor().equals(theColor)) {
                        if (getFlipFlag()) {
                            untilRow = newRow;
                            untilCol = col;
                        }
                        flag = true;
                        break;
                    } else {
                        col = col + 1;
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
            while (col < untilCol) {
                pieceBoard[row][col].setColor(theColor);
                col++;
            }
        }
        untilRow = startRow;
        untilCol = startCol;
    }
}
