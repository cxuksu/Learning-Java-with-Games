/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private CellBoard cellBoard;
    private PieceBoard pieceBoard;
    private PlayerBoard playerBoard;
    private ScoreBoard scoreBoard;

    public GameCanvas() {
        super();
        setSleepTime(300);
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
    }

    private void initCellBoard() {
        cellBoard = new CellBoard();
        getSpriteAry().add(cellBoard);
    }

    private void initPieceBoard() {
        pieceBoard = new PieceBoard();
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
}
