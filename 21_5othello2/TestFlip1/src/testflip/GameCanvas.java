/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package testflip;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private int currScore;
    private PieceSpriteTemp pieceSprite;
    private PieceSprite pieceSprite2;

    public GameCanvas() {
        super();
        setSleepTime(300);
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        new AllClips();
        initPiece();
    }

    public void initPiece() {
        pieceSprite = new PieceSpriteTemp();
        pieceSprite.setGameCanvas(this);
        getSpriteAry().add(pieceSprite);
        pieceSprite2 = new PieceSprite();
        pieceSprite2.setGameCanvas(this);
        getSpriteAry().add(pieceSprite2);
    }
    
    @Override
    public void paintCurrScore(Graphics2D g2d) {
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
        /*
        g2d.setColor(Color.RED);
        g2d.drawRect(Consts.MAXX / 2 - 110, Consts.MAXY / 2 - 50, 200, 100);
        g2d.drawString("Game Terminates", 140, Consts.MAXY / 2);
        g2d.setColor(Color.BLUE);
        g2d.drawString("Start A New Game If Clicking The Paddle",
                40, Consts.MAXY / 2 + 30);
        // in addition, stop game and prepare renew game
        stopGame();
        addMouseListener(new NewGameListener());
                */
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
