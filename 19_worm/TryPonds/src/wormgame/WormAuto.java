/*
 * Worm.java - A class that implements an animated worm body
 */
package wormgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class WormAuto extends AbsSprite2D implements Serializable {

    private ArrayList<Point> wormBody;
    private int initLen;
    private Point wHead;
    private int direction;
    private int headPtr, nextPtr, tailPtr;
    private int nUnit; // total number of body units (include headPoint)

    private GameCanvas gameCanvas;
    private Treat treat;
    private Worm worm;
    
    private double sin, cos;
    private Point distanceP;

    public WormAuto() {
        super();
        wormBody = new ArrayList();
        initLen = 10;
    }

    @Override
    public void initSprite() {
        // add the headPoint
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.WORM_UNIT_RADIUS);
        setY((Consts.MAXY - Consts.MINY) / 2 + 5*Consts.WORM_UNIT_RADIUS);
        wHead = new Point(getX(), getY());
        wormBody.add(wHead);
        // add remaining body units (initLen - 1)
        for (int i = 1; i < initLen; i++) {
            wormBody.add(new Point(getX() - i * 2 * Consts.WORM_UNIT_RADIUS, getY()));
        }

        // set up pointers
        headPtr = 0;
        nextPtr = 1;
        tailPtr = initLen - 1; // - 1; //i = initLen; the tail index = i - 1;
        nUnit = initLen;
        direction = Consts.EAST;
    }

    @Override
    @SuppressWarnings("WaitWhileNotSynced")
    public void updateSprite() {
        if (!gameCanvas.wormAutoCollideWorm()) {
            setHeadPoint();
        }
        // if the worm eats the treat
        if (collideTreat()) {
            treat.initSprite();
            setTreat(treat);
            // pause it for a half second
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
        }
        // wormAuto won't cause the termination of the game
        //if (collideEdge()) {
        //    gameCanvas.terminateGame();
        //}
    }

    public void setHeadPoint() {
        nextPtr = headPtr;
        headPtr = tailPtr;
        Point nextPoint;
        nextPoint = wormBody.get(nextPtr);
        Point newHeadP = getNewHeadPoint(nextPoint);

        wormBody.set(headPtr, newHeadP);
        tailPtr = tailPtr - 1;
        if (tailPtr == -1) {
            tailPtr = nUnit - 1;
        }
    }

    public Point getNewHeadPoint(Point nextPoint) {
        Point newHeadP = new Point();
        int moveLen = 2*Consts.WORM_UNIT_RADIUS;
        findDir();
        
        if ((distanceP.x <= 0) && (distanceP.y <= 0)) {
            newHeadP = new Point(nextPoint.x - (int)(moveLen*cos), 
                nextPoint.y - (int)(moveLen*sin));
        } else if ((distanceP.x <= 0) && (distanceP.y >= 0)) {
            newHeadP = new Point(nextPoint.x - (int)(moveLen*cos), 
                nextPoint.y + (int)(moveLen*sin));
        } else if ((distanceP.x >= 0) && (distanceP.y <= 0)) {
            newHeadP = new Point(nextPoint.x + (int)(moveLen*cos), 
                nextPoint.y - (int)(moveLen*sin));
        } else if ((distanceP.x >= 0) && (distanceP.y >= 0)) {
            newHeadP = new Point(nextPoint.x + (int)(moveLen*cos), 
                nextPoint.y + (int)(moveLen*sin));
        }
        return newHeadP;
    }

    public boolean collideTreat() {
        boolean collide = false;
        Rectangle treatRect = new Rectangle(treat.getX(), treat.getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        ArrayList wAuto = getWormBody();
        Rectangle wUnitRect;
        Point wUnitP;
        for (Object wBody1 : wAuto) {
            wUnitP = (Point) wBody1;
            wUnitRect = new Rectangle(wUnitP.x, wUnitP.y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            if (treatRect.intersects(wUnitRect)) {
                collide = true;
            }
        }
        return collide;
    }
    
    public boolean collideEdge() {
        boolean hitEdge = false;
        Point headP = wormBody.get(headPtr);
        if (headP.x <= Consts.MINX) {
            hitEdge = true;
        } else if (headP.x >+ Consts.MAXX) {
            hitEdge = true;
        } else if (headP.y <= Consts.MINY) {
            hitEdge = true;
        } else if (headP.y >= Consts.MAXY) {
            hitEdge = true;
        }
        return hitEdge;
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        Point headPoint = wormBody.get(headPtr);
        g2d.fillOval(headPoint.x, headPoint.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        g2d.setColor(Color.BLACK);
        int ptr = nextPtr;
        for (int i = 1; i < wormBody.size(); i++) {
            g2d.fillOval(wormBody.get(ptr).x, wormBody.get(ptr).y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            ptr = (ptr + 1) % (nUnit);
        }
    }

    public int getDirection() {
        return direction;
    }

    public void findDir() {
        distanceP = findDistance();
        int slashLen = (int)(Math.sqrt(distanceP.x * distanceP.x + 
                distanceP.y * distanceP.y));
        sin = Math.abs((double)(distanceP.y * 1.0) / slashLen);
        cos = Math.abs((double)(distanceP.x * 1.0) / slashLen);
    }
    
    public Point findDistance() {
        Point treatP = new Point(treat.getX(), treat.getY());
        Point headP = wormBody.get(headPtr);
        int xDistance = treatP.x - headP.x;
        int yDistance = treatP.y - headP.y;
        Point disP = new Point(xDistance, yDistance);
        return disP;
    }
    
    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void setTreat(Treat treat) {
        this.treat = treat;
    }

    public ArrayList<Point> getWormBody() {
        return wormBody;
    }

    public int getHeadPtr() {
        return headPtr;
    }
}
