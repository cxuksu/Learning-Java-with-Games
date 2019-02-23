/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pongextension;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class BrickGroup {
    
    private GameCanvas gameCanvas;
    
    public BrickGroup() {
    }
    
    public void initBrickGroup() {
        BrickSprite aBrick;
        int bricksPerRow = (int)((Consts.MAXY-Consts.MINY-2*Consts.WALL_THICK)
                / Consts.BRICK_W);
        System.out.println("briksPerRow = " + bricksPerRow);
        int numBricks = bricksPerRow * Consts.BRICK_ROW;
        int x = 20;
        int y = Consts.MAXY - Consts.WALL_THICK; // starting from the bottom
        for (int i = 0; i < numBricks; i++) {
            if ((i % bricksPerRow == 0) && (i != 0)) {
                x += Consts.BRICK_H; // move to next row
                y = Consts.MAXY - Consts.WALL_THICK;
            }
            aBrick = new BrickSprite();
            aBrick.setActive(true);
            aBrick.setVisible(true);
            aBrick.setX(x);
            y -= Consts.BRICK_W; // move the coor y to the next position
            aBrick.setY(y); // stay at the same row
            gameCanvas.getSpriteAry().add(aBrick);
        }
    }
    
    /*
    public void paintBrickGroup(Graphics2D g2d) {
        System.out.println("Enter paint brickgroup");
        AbsSprite2D element;
        BrickSprite aBrick;
        ArrayList<AbsSprite2D> aList = gameCanvas.getSpriteAry();
        for (int i = 0; i < aList.size(); i++) {
            System.out.println(" i = " + i);
            element = aList.get(i);
            if (element instanceof BrickSprite) {
                aBrick = (BrickSprite)element;
                System.out.println("before paint brick");
                aBrick.paintSprite(g2d);
            } else if (element instanceof BallSprite) {
                BallSprite aBall = (BallSprite)element;
            }
        }
    }
    */

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
