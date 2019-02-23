/*
 * Worm.java - A class that implements an animated worm body
 */
package wormgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class Worm extends AbsSprite2D implements Serializable {

    private ArrayList<Point> wormBody;
    private int initLen;
    private Point wHead;
    private int direction;
    private int headPtr, nextPtr, tailPtr;
    private int nUnit; // total number of body units (include headPoint)

    private GameCanvas gameCanvas;
    private Treat treat;

    public Worm() {
        super();
        wormBody = new ArrayList();
        initLen = 5;
    }

    @Override
    public void initSprite() {
        // add the headPoint
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.WORM_UNIT_RADIUS);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.WORM_UNIT_RADIUS);
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
        setHeadPoint();
        // if the worm eats the treat
        if (gameCanvas.detectCollision()) {
            treat.initSprite();
            addHeadPoint();
            // pause it for a half second
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
        }
        if (collideEdge()) {
            gameCanvas.terminateGame();
        }
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

    public void addHeadPoint() {
        nextPtr = headPtr;
        headPtr = tailPtr;
        Point nextPoint;
        nextPoint = wormBody.get(nextPtr);
        Point newHeadP = getNewHeadPoint(nextPoint);

        wormBody.add(headPtr + 1, newHeadP);
        headPtr = headPtr + 1;
        nUnit++;
    }

    public Point getNewHeadPoint(Point nextPoint) {
        Point newHeadP = new Point();
        switch (direction) {
            case Consts.EAST:
                newHeadP = new Point(nextPoint.x + 2 * Consts.WORM_UNIT_RADIUS,
                        nextPoint.y);
                break;
            case Consts.WEST:
                newHeadP = new Point(nextPoint.x - 2 * Consts.WORM_UNIT_RADIUS,
                        nextPoint.y);
                break;
            case Consts.SOUTH:
                newHeadP = new Point(nextPoint.x,
                        nextPoint.y + 2 * Consts.WORM_UNIT_RADIUS);
                break;
            case Consts.NORTH:
                newHeadP = new Point(nextPoint.x,
                        nextPoint.y - 2 * Consts.WORM_UNIT_RADIUS);
                break;
            default:
        }
        return newHeadP;
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

    public void setDirection(int direction) {
        // to prevent directly change from EAST to WEST or vice versa
        if ((this.direction == Consts.EAST)
                || (this.direction == Consts.WEST)) {
            if ((direction == Consts.SOUTH) || (direction == Consts.NORTH)) {
                this.direction = direction;
            }
        } else if ((this.direction == Consts.SOUTH)
                || (this.direction == Consts.NORTH)) {
            if ((direction == Consts.EAST) || (direction == Consts.WEST)) {
                this.direction = direction;
            }
        }
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
}
