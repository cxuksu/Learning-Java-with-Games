/*
 * GameCanvas.java - A game canvas class that implements the game canvas.
 */
package wormgame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameCanvas extends AbsGameCanvas {

    private ControlPanel controlPanel;
    private CardLayout cardLayout;

    private Worm worm;
    private Treat treat;
    private WormAuto wormAuto;
    private Pond pond;
    
    private boolean overlap;
    private boolean gameTerminated;
    
    public GameCanvas() {
        super();
        overlap = true;
        gameTerminated = false;
        addKeyListener(new MyKeyAdapter());
    }

    @Override
    public void initComponent() {
        initWorm();
        initTreat();
        initWormAuto();
        
        getSpriteAry().add(worm);
        getSpriteAry().add(treat);
        getSpriteAry().add(wormAuto);
        
        initPond();
    }
        
    public void initWorm() {
        worm = new Worm();
        worm.setGameCanvas(this);
        worm.initSprite();
    }
    
    public void initTreat() {
        treat = new Treat();
        while (overlap) {
            treat.initSprite();
            overlap = detectCollision();
        }
        worm.setTreat(treat);
    }

    public void initWormAuto() {
        wormAuto = new WormAuto();
        wormAuto.initSprite();
        wormAuto.setTreat(treat); // for collision detection
        wormAuto.setGameCanvas(this); // for terminating game
    }
    
    public void initPond() {
        pond = new Pond();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(150, 200, 100)); //Color.ORANGE);
        g2d.fillRect(Consts.MINX, Consts.MINY, Consts.MAXX, Consts.MAXY);
        pond.paintPond(g2d);
        for (int i = 0; i < getSpriteAry().size(); i++) {
            getSpriteAry().get(i).paintSprite(g2d);
        }
        
        if (gameTerminated) {
            paintTermination(g2d);
        }
    }

    public void paintTermination(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 30));
        g2d.drawString("Game Terminated", 130, 150);
    }
    
    public boolean detectCollision() {
        boolean collide = false;
        Rectangle treatRect = new Rectangle(treat.getX(), treat.getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        ArrayList wBody = worm.getWormBody();
        Rectangle wUnitRect;
        Point wUnitP;
        for (Object wBody1 : wBody) {
            wUnitP = (Point) wBody1;
            wUnitRect = new Rectangle(wUnitP.x, wUnitP.y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            if (treatRect.intersects(wUnitRect)) {
                collide = true;
            }
        }
        return collide;
    }
    
    public boolean wormAutoCollideWorm() {
        boolean collide = false;
        ArrayList theBody = wormAuto.getWormBody();
        Point wormAutoHeadP = (Point)(theBody.get(wormAuto.getHeadPtr()));
        Rectangle headRect = new Rectangle(wormAutoHeadP.x, wormAutoHeadP.y, 
                2*Consts.WORM_UNIT_RADIUS, 2*Consts.WORM_UNIT_RADIUS);
        Point aUnitP;
        Rectangle unitRect;
        ArrayList theWorm = worm.getWormBody();
        for (Object theWorm1 : theWorm) {
            aUnitP = (Point) theWorm1;
            unitRect = new Rectangle(aUnitP.x, aUnitP.y, 
                    2*Consts.WORM_UNIT_RADIUS, 2*Consts.WORM_UNIT_RADIUS);
            if (headRect.intersects(unitRect)) {
                collide = true;
            }
        }
        return collide;
    }
    
    // an inner class for implementing the key event adapter
    // for catching the key control
    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent evt) {
            switch(evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    worm.setDirection(Consts.SOUTH);
                    break;
                case KeyEvent.VK_UP:
                    worm.setDirection(Consts.NORTH);
                    break;
                case KeyEvent.VK_RIGHT:
                    worm.setDirection(Consts.EAST);
                    break;
                case KeyEvent.VK_LEFT:
                    worm.setDirection(Consts.WEST);
                    break;
                default:
            }
        }
    }
    
    @Override
    public void terminateGame() {
        setPlaying(false);
        getAnimThread().interrupt();
        setAnimThread(null);
        gameTerminated = true;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}
