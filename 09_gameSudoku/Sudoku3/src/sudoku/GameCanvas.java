/*
 * GameCanvas.java - A class defines the canvas for the game Sudoku.
 */
package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Board board;
    private BoardPopulate boardPopulate;
    private CursorMark cursorMark;
    private int diffLevel;
    private NotePanel playerPanel;
    private boolean gameOver = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        setLayout(new BorderLayout());
        diffLevel = Consts.EASY;
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        initComponent();
    }

    private void initComponent() {
        playerPanel = new NotePanel();
        add(playerPanel, BorderLayout.EAST);
        board = new Board();
        initBoardPopulate();
        initCursorMark();
    }

    public void initBoardPopulate() {
        boardPopulate = new BoardPopulate();
        boardPopulate.setBoard(board);
        boardPopulate.setDiffLevel(diffLevel);
        boardPopulate.populateBoard();
    }

    public void initCursorMark() {
        cursorMark = new CursorMark();
        board.setCursorMark(cursorMark);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        board.paintBoard(g2d);
        cursorMark.paintCursor(g2d);
        if (gameOver) {
            paintTerminate(g2d);
        }
    }

    public void paintTerminate(Graphics2D g2d) {
        playerPanel.setVisible(false);
        g2d.drawRect(Consts.PLAYER_P_H + Consts.LEFT_M, Consts.TOP_M,
                Consts.PLAYER_P_W - Consts.LEFT_M, Consts.PLAYER_P_H - 2 * Consts.TOP_M);
        g2d.setFont(new Font("Times", Font.BOLD, 28));
        int leftMargin = Consts.PLAYER_P_H + Consts.LEFT_M + 10;
        g2d.drawString("Game Terminates", leftMargin, 2 * Consts.TOP_M);
        int score = board.checkGuessed();
        score *= diffLevel;
        g2d.setColor(Color.MAGENTA);
        g2d.drawString("Your score is " + score + "", leftMargin, 4 * Consts.TOP_M);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Times", Font.BOLD, 18));
        g2d.drawString("Press 'N' for New Game", leftMargin, 6 * Consts.TOP_M);
        g2d.drawString("Press 'Q' for Quit Game", leftMargin, 7 * Consts.TOP_M);
        repaint();
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_E:
                    diffLevel = Consts.EASY;
                    resetBoard(diffLevel);
                    break;
                case KeyEvent.VK_M:
                    diffLevel = Consts.MEDIUM;
                    resetBoard(diffLevel);
                    break;
                case KeyEvent.VK_H:
                    diffLevel = Consts.HARD;
                    resetBoard(diffLevel);
                    break;
                case KeyEvent.VK_UP:
                    cursorMark.updatePosition(Consts.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    cursorMark.updatePosition(Consts.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    cursorMark.updatePosition(Consts.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    cursorMark.updatePosition(Consts.RIGHT);
                    break;
                case KeyEvent.VK_C:
                    int score = board.checkGuessed() * diffLevel;
                    break;
                case KeyEvent.VK_N: // new game
                    resetBoard(Consts.EASY);
                    break;
                case KeyEvent.VK_Q:
                    System.exit(0);
                    break;
            }
            repaint();
        }

        private void resetBoard(int diffLevel) {
            boardPopulate.setDiffLevel(diffLevel);
            boardPopulate.populateBoard();
            initCursorMark();

            // switch back to the playerPanel
            playerPanel.setVisible(true);
            // eliminate the termination screen painting
            gameOver = false;
        }

        @Override
        public void keyTyped(KeyEvent evt) {
            int row = cursorMark.getMoveRow();
            int col = cursorMark.getMoveCol();
            char keyChar = evt.getKeyChar();
            if ((keyChar >= '1') && (keyChar <= '9')) {
                int digit = (int) keyChar - 48;
                board.setGuessDigit(row, col, digit);
                if (guessComplete()) {
                    gameOver = true;
                }
            }
            repaint();
        }

        private boolean guessComplete() {
            Cell aCell;
            Cell[][] aBoard = board.getSudokuBoard();
            for (int row = 0; row < Consts.MAX_CELLS; row++) {
                for (int col = 0; col < Consts.MAX_CELLS; col++) {
                    aCell = aBoard[row][col];
                    if ((!aCell.isVisible()) && (aCell.getGuessed() == 0)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
