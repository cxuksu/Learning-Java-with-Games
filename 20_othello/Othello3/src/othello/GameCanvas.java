/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import rule.RuleBase;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private CellBoard cellBoard;
    private PieceBoard pieceBoard;
    private PlayerBoard playerBoard;
    private ScoreBoard scoreBoard;
    private Color curPieceColor;

    private RuleBase ruleBase;
    // for count pieces
    private PieceSprite[][] thePieceBoard;
    private int numWhite = 0, numBlack = 0;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setSleepTime(300);
        addMouseMotionListener(new BrowseCellBoardListener());
        addMouseListener(new ClickPieceBoardListener());
        setCurPieceColor(Consts.WHITEP);
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        new AllClips();

        initCellBoard();
        initPieceBoard();
        initScoreBoard();
        initPlayerBoard();

        // initialize two white and two black pieces
        pieceBoard.initPieces();
        scoreBoard.setNumWhite(scoreBoard.getNumWhite() + 2);
        scoreBoard.setNumBlack(scoreBoard.getNumBlack() + 2);

        initRuleBase();
    }

    private void initCellBoard() {
        cellBoard = new CellBoard();
        cellBoard.setCurPieceColor(curPieceColor);
        cellBoard.addPropertyChangeListener(new CheckRules());
        getSpriteAry().add(cellBoard);
    }

    private void initPieceBoard() {
        pieceBoard = new PieceBoard();
        pieceBoard.setCurPieceColor(curPieceColor);
        pieceBoard.addPropertyChangeListener(new ClickChange());
        // pieceBoard is used inside cellBoard
        cellBoard.setPieceBoard(pieceBoard.getPieceBoard());
        getSpriteAry().add(pieceBoard);
    }

    private void initScoreBoard() {
        scoreBoard = new ScoreBoard();
        getSpriteAry().add(scoreBoard);
    }

    private void initPlayerBoard() {
        playerBoard = new PlayerBoard();
        getSpriteAry().add(playerBoard);
    }

    public void initRuleBase() {
        ruleBase = new RuleBase();
        ruleBase.initRules();
        ruleBase.setPieceBoard(pieceBoard.getPieceBoard());
        ruleBase.setCurPieceColor(curPieceColor);
    }

    // Othello is played by two players. It does not have a current score
    @Override
    public void paintCurrScore(Graphics2D g2d) {
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(Consts.MAXX / 2 - 110, Consts.MAXY / 2 - 50, 200, 100);
        g2d.drawString("Game Terminates", 140, Consts.MAXY / 2);
        g2d.setColor(Color.BLUE);
        g2d.drawString("Start A New Game If Clicking The Paddle",
                40, Consts.MAXY / 2 + 30);
        // in addition, stop game and prepare renew game
        stopGame();
        addMouseListener(new NewGameListener());
    }

    public void countPieces() {
        thePieceBoard = pieceBoard.getPieceBoard();
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                if (thePieceBoard[row][col] != null) {
                    if (thePieceBoard[row][col].getColor().equals(
                            Consts.WHITEP)) {
                        numWhite++;
                    } else if (thePieceBoard[row][col].getColor().equals(
                            Consts.BLACKP)) {
                        numBlack++;
                    }
                }
            }
        }
        scoreBoard.setNumWhite(numWhite);
        numWhite = 0;
        scoreBoard.setNumBlack(numBlack);
        numBlack = 0;
    }

    // A listener for re-starting the game.
    // Assume it is based on a mouse click
    class NewGameListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (true) { // some condition
                renewGame();
            }
        }
    }

    class BrowseCellBoardListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent evt) {
            int x = evt.getX();
            int y = evt.getY();
            cellBoard.setMoveXY(x, y); 
            // prepares for "game loop" to invoke updateSprite()
        }
    }

    class ClickPieceBoardListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            int x = evt.getX();
            int y = evt.getY();
            pieceBoard.setClickXY(x, y);
        }
    }

    class CheckRules implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String checkRule = evt.getPropertyName();
            Point theP = (Point) evt.getNewValue();
            if (checkRule.equals("checkRules")) {
                ruleBase.setNewRow(theP.x);
                ruleBase.setNewCol(theP.y);
                if (ruleBase.isRuleValid()) {
                    cellBoard.setRuleValid(true);
                    pieceBoard.setRuleValid(theP.x, theP.y, true);
                }
            }
        }
    }

    class ClickChange implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("clickDone")) {
                // add method calls for flip pieces and count pieces
                ruleBase.flipPiece(); // flip pieces
                cellBoard.exitOldCell(); // after click, make CELLCOLOR
                countPieces(); // count number of pieces of both
                if (getCurPieceColor().equals(Consts.WHITEP)) {
                    setCurPieceColor(Consts.BLACKP);
                } else if (getCurPieceColor().equals(Consts.BLACKP)) {
                    setCurPieceColor(Consts.WHITEP);
                }
            }
        }
    }

    public Color getCurPieceColor() {
        return curPieceColor;
    }

    public void setCurPieceColor(Color curPieceColor) {
        this.curPieceColor = curPieceColor;
        // reset the current piece color
        cellBoard.setCurPieceColor(curPieceColor);
        pieceBoard.setCurPieceColor(curPieceColor);
        playerBoard.setCurPieceColor(curPieceColor);
        ruleBase.setCurPieceColor(curPieceColor);
    }
}
