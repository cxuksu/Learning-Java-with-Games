/*
 * WormAuto.java - A class that implements an automatically animated worm body.
 * It does not grow when eat a treat. It won't cause game termination.
 */
package worm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class WormAuto extends AbsSprite2D {

    private ArrayList<Point> wormBody;
    private int initLen;
    private Point wHead;
    private int head, next, tail;
    private int nUnit; // total number of body units (include headPoint)

    private GameCanvas gameCanvas;
    private Treat treat;

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
        setY((Consts.MAXY - Consts.MINY) / 2 + 5 * Consts.WORM_UNIT_RADIUS);
        wHead = new Point(getX(), getY());
        wormBody.add(wHead);
        // add remaining body units (initLen - 1)
        for (int i = 1; i < initLen; i++) {
            wormBody.add(new Point(getX() - i * 2 * Consts.WORM_UNIT_RADIUS, getY()));
        }

        // set up pointers
        head = 0;
        next = 1;
        tail = initLen - 1; // - 1; //i = initLen; the tail index = i - 1;
        nUnit = initLen;
        setVisible(true);
        setActive(true);
    }

    @Override
    public void updateSprite() {
        if (!gameCanvas.wormAutoCollideWorm()) {
            moveHeadOneStep();
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

    // replace the original tail position with a new head object
    // it keeps the total number of units but move the head one step
    public void moveHeadOneStep() {
        next = head;
        head = tail;
        Point nextPoint;
        nextPoint = wormBody.get(next); // current, next points to the head node
        Point newHeadP = prepareNewHead(nextPoint);

        wormBody.set(head, newHeadP); // replace the tail with new head
        tail = tail - 1;
        if (tail == -1) {
            tail = nUnit - 1;
        }
    }

    // create a new head object: x = nextP.x + a unit
    public Point prepareNewHead(Point nextPoint) {
        Point newHeadP = new Point();
        int moveLen = 2 * Consts.WORM_UNIT_RADIUS;
        findDir(); // get values of cos and sin
        int moveX = (int) (moveLen * cos);
        int moveY = (int) (moveLen * sin);

        if ((distanceP.x <= 0) && (distanceP.y <= 0)) { // NW
            newHeadP = new Point(nextPoint.x - moveX, nextPoint.y - moveY);
        } else if ((distanceP.x <= 0) && (distanceP.y >= 0)) { // SW
            newHeadP = new Point(nextPoint.x - moveX, nextPoint.y + moveY);
        } else if ((distanceP.x >= 0) && (distanceP.y <= 0)) { // NE
            newHeadP = new Point(nextPoint.x + moveX, nextPoint.y - moveY);
        } else if ((distanceP.x >= 0) && (distanceP.y >= 0)) { // SE
            newHeadP = new Point(nextPoint.x + moveX, nextPoint.y + moveY);
        }
        return newHeadP;
    }

    public void findDir() {
        distanceP = findDistance();
        int hypotenuseLen = (int) (Math.sqrt(distanceP.x * distanceP.x
                + distanceP.y * distanceP.y));
        sin = Math.abs((double) (distanceP.y * 1.0) / hypotenuseLen);
        cos = Math.abs((double) (distanceP.x * 1.0) / hypotenuseLen);
    }

    public Point findDistance() {
        Point treatP = new Point(treat.getX(), treat.getY());
        Point headP = wormBody.get(next); // current, head is the next
        int xDistance = treatP.x - headP.x;
        int yDistance = treatP.y - headP.y;
        Point disP = new Point(xDistance, yDistance);
        return disP;
    }

    public boolean collideTreat() {
        boolean collide = false;
        Rectangle treatRect = new Rectangle(treat.getX(), treat.getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        Point wHeadP = wormBody.get(head);
        Rectangle wUnitRect = new Rectangle(wHeadP.x, wHeadP.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        if (treatRect.intersects(wUnitRect)) {
            collide = true;
            AllClips.getClips(Consts.AUTO_EAT).play();
            gameCanvas.setNumEatWormAuto(gameCanvas.getNumEatWormAuto() + 1);
        }
        return collide;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        Point headPoint = wormBody.get(head);
        g2d.fillOval(headPoint.x, headPoint.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        g2d.setColor(Color.BLACK);
        int ptr = next;
        for (int i = 1; i < wormBody.size(); i++) {
            g2d.fillOval(wormBody.get(ptr).x, wormBody.get(ptr).y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            ptr = (ptr + 1) % (nUnit);
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

    public int getHead() {
        return head;
    }
}
