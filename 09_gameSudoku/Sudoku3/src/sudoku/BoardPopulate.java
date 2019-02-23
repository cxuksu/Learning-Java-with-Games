/*
 * BoardPopulate.java - A class for populating data with the board.
 */
package sudoku;

import java.util.Random;

/**
 *
 * @author cxu
 */
public class BoardPopulate {

    private Random random;
    private Cell[][] validBoard; // a reference points to the sudokuBoard
    private int[] alreadyTested;
    private boolean allTested;
    private int diffLevel;
    private Board board;

    public BoardPopulate() {
        random = new Random();
        alreadyTested = new int[9];
    }

    public void populateBoard() {
        int aDigit = 0;
        boolean stop;

        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                initAry(alreadyTested);
                allTested = false;
                stop = false;
                while (!stop) { // the loop may fully fill alreadyTested ary
                    aDigit = Math.abs(random.nextInt() % 9) + 1;
                    if ((isNewDigit(aDigit, alreadyTested)) && (!allTested)) {
                        if ((validRow(aDigit, row, col))
                                && (validColumn(aDigit, row, col))
                                && (validMiniBoard(aDigit, row, col))) {
                            stop = true;
                        }
                    } else if (allTested) {
                        stop = true;
                    }
                }
                if (!allTested) {
                    insertDigit(aDigit, row, col);
                } else {
                    board.initBoard();
                    row = 0;
                    col = -1;
                }
            }
        }
        hideCell(diffLevel);
    }

    private boolean isNewDigit(int aValue, int[] ary) {
        if (ary[ary.length - 1] != 0) {
            allTested = true;
            return false;
        }
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == aValue) {
                return false;
            } else if (ary[i] == 0) {
                ary[i] = aValue;
                break;
            }
        }
        return true;
    }

    private boolean validRow(int aValue, int row, int col) {
        for (int i = 0; i < col; i++) {
            if (validBoard[row][i].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private boolean validColumn(int aValue, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (validBoard[i][col].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private boolean validMiniBoard(int aValue, int row, int col) {
        Cell[][] vMini = copyMiniBoard(row, col);
        int digit;
        for (int mRow = 0; mRow < 3; mRow++) { // treat a 2D array as a 1D
            for (int mCol = 0; mCol < 3; mCol++) {
                digit = vMini[mRow][mCol].getDigit();
                if ((digit != 0) && (digit == aValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Cell[][] copyMiniBoard(int row, int column) {
        Cell[][] aMini = new Cell[3][3];
        if (row < 3 && column < 3) {
            for (int mRow = 0; mRow < 3; mRow++) {
                System.arraycopy(validBoard[mRow], 0, aMini[mRow], 0, 3);
            }
        } else if (column > 2 && column < 6 && row < 3) {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow][mCol + 1 * 3];
                }
            }
        } else if (column > 5 && row < 3) {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow][mCol + 2 * 3];
                }
            }
        } else if (row > 2 && row < 6 && column < 3) {
            for (int mRow = 0; mRow < 3; mRow++) {
                System.arraycopy(validBoard[mRow + 1 * 3], 0, aMini[mRow], 0, 3);
            }
        } else if (row > 2 && row < 6 && column > 2 && column < 6) {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow + 1 * 3][mCol + 1 * 3];
                }
            }
        } else if (row > 2 && column > 5 && row < 6) {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow + 1 * 3][mCol + 2 * 3];
                }
            }
        } else if (row > 5 && column < 3) {
            for (int mRow = 0; mRow < 3; mRow++) {
                System.arraycopy(validBoard[mRow + 2 * 3], 0, aMini[mRow], 0, 3);
            }
        } else if (row > 5 && column > 2 && column < 6) {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow + 2 * 3][mCol + 1 * 3];
                }
            }
        } else {
            for (int mRow = 0; mRow < 3; mRow++) {
                for (int mCol = 0; mCol < 3; mCol++) {
                    aMini[mRow][mCol] = validBoard[mRow + 2 * 3][mCol + 2 * 3];
                }
            }
        }
        return aMini;
    }

    private void insertDigit(int aValue, int row, int col) {
        validBoard[row][col].setDigit(aValue);
    }

    public void hideCell(int level) {
        int numCell = 5;
        if (level == Consts.EASY) {
            numCell = Math.abs(random.nextInt() % 11) + 5; // 5-15
        } else if (level == Consts.MEDIUM) {
            numCell = Math.abs(random.nextInt() % 15) + 16; // 16-30
        } else if (level == Consts.HARD) {
            numCell = Math.abs(random.nextInt() % 20) + 31; // 31-50
        }
        for (int i = 0; i < numCell; i++) {
            int ranRow = Math.abs(random.nextInt() % 9);
            int ranCol = Math.abs(random.nextInt() % 9);
            validBoard[ranRow][ranCol].setVisible(false);
        }
    }
    
    public void initAry(int[] ary) {
        for (int i = 0; i < Consts.MAX_CELLS; i++) {
            ary[i] = 0;
        }
    }

    public void setBoard(Board board) {
        this.board = board;
        validBoard = this.board.getSudokuBoard();
    }

    public void setDiffLevel(int diffLevel) {
        this.diffLevel = diffLevel;
    }
}
